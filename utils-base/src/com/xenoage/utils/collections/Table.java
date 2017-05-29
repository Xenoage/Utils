package com.xenoage.utils.collections;

import java.util.ArrayList;

import static com.xenoage.utils.collections.CollectionUtils.alist;
import static com.xenoage.utils.collections.CollectionUtils.alistInit;
import static com.xenoage.utils.kernel.Range.range;

/**
 * Two-dimensional array.
 *
 * Columns and rows can be added, inserted and removed
 * and cells can be modified.
 *
 * This class is thread-safe.
 *
 * @author Andreas Wenger
 */
public class Table<T> {

	private ArrayList<ArrayList<T>> cells = alist(); //[y][x]

	private int columnsCount = 0;
	private int rowsCount = 0;

	public Table(int columnsCount, int rowsCount) {
		this.columnsCount = columnsCount;
		this.rowsCount = rowsCount;
		cells = alistInit(null, rowsCount);
		for (int y : range(rowsCount))
			cells.set(y, alistInit(null, columnsCount));
	}

	/**
	 * Gets the number of columns.
	 * When there are no rows, the number of columns is always 0.
	 */
	public int getColumnsCount() {
		if (rowsCount == 0)
			return 0;
		return columnsCount;
	}

	/**
	 * Gets the number of rows.
	 * When there are no columns, the number of rows is always 0.
	 */
	public int getRowsCount() {
		if (columnsCount == 0)
			return 0;
		return rowsCount;
	}

	/**
	 * Adds a column at the end of the table.
	 */
	public synchronized void addColumn() {
		columnsCount++;
		for (int i : range(rowsCount))
			cells.get(i).add(null);
	}

	/**
	 * Inserts a column at the given column index.
	 */
	public synchronized void addColumn(int x) {
		columnsCount++;
		for (int i : range(rowsCount))
			cells.get(i).add(x, null);
	}

	/**
	 * Removes the column at the given column index.
	 */
	public synchronized void removeColumn(int x) {
		columnsCount--;
		for (int i : range(rowsCount))
			cells.get(i).remove(x);
	}

	/**
	 * Adds a row at the end of the table.
	 */
	public synchronized void addRow() {
		rowsCount++;
		ArrayList<T> row = alistInit(null, columnsCount);
		cells.add(row);
	}

	/**
	 * Adds a row at the given row index.
	 */
	public synchronized void addRow(int y) {
		rowsCount++;
		ArrayList<T> row = alistInit(null, columnsCount);
		cells.add(y, row);
	}

	/**
	 * Removes the row at the given row index.
	 */
	public synchronized void removeRow(int y) {
		rowsCount--;
		cells.remove(y);
	}

	/**
	 * Gets the value at the given column and row, or null if not set.
	 */
	public synchronized T get(int x, int y) {
		return cells.get(y).get(x);
	}

	/**
	 * Sets the value at the given column and row.
	 */
	public synchronized void set(int x, int y, T value) {
		cells.get(y).set(x, value);
	}

}
