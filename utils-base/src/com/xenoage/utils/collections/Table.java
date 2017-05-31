package com.xenoage.utils.collections;

import java.util.ArrayList;

import static com.xenoage.utils.collections.CollectionUtils.alist;
import static com.xenoage.utils.collections.CollectionUtils.alistInit;

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

	private ArrayList<T> cells; //index = y * columnsCount + x

	private int columnsCount;
	private int rowsCount;

	public Table() {
		columnsCount = 0;
		rowsCount = 0;
		cells = alist();
	}

	public Table(int columnsCount, int rowsCount) {
		this.columnsCount = columnsCount;
		this.rowsCount = rowsCount;
		cells = alistInit(null, rowsCount * columnsCount);
	}

	/**
	 * Gets the number of columns.
	 * When there are no rows, the number of columns is always 0.
	 */
	public synchronized int getColumnsCount() {
		if (rowsCount == 0)
			return 0;
		return columnsCount;
	}

	/**
	 * Gets the number of rows.
	 * When there are no columns, the number of rows is always 0.
	 */
	public synchronized int getRowsCount() {
		if (columnsCount == 0)
			return 0;
		return rowsCount;
	}

	/**
	 * Gets the raw cell data. This method is only provided for JPA support
	 * and should not be used otherwise.
	 */
	public ArrayList<T> getCells() {
		return cells;
	}

	/**
	 * Adds a column at the end of the table.
	 */
	public synchronized void addColumn() {
		columnsCount++;
		cells.ensureCapacity(columnsCount * rowsCount);
		for (int i = 0; i < rowsCount; i++)
			cells.add((i + 1) * columnsCount - 1,null);
	}

	/**
	 * Inserts a column at the given column index.
	 */
	public synchronized void addColumn(int x) {
		columnsCount++;
		cells.ensureCapacity(columnsCount * rowsCount);
		for (int i = 0; i < rowsCount; i++)
			cells.add(i * columnsCount + x, null);
	}

	/**
	 * Removes the column at the given column index.
	 */
	public synchronized void removeColumn(int x) {
		for (int i = rowsCount - 1; i >= 0; i--)
			cells.remove(i * columnsCount + x);
		columnsCount--;
	}

	/**
	 * Adds a row at the end of the table.
	 */
	public synchronized void addRow() {
		rowsCount++;
		cells.ensureCapacity(columnsCount * rowsCount);
		for (int i = 0; i < columnsCount; i++)
			cells.add(null);
	}

	/**
	 * Adds a row at the given row index.
	 */
	public synchronized void addRow(int y) {
		rowsCount++;
		cells.ensureCapacity(columnsCount * rowsCount);
		for (int i = 0; i < columnsCount; i++)
			cells.add(y * columnsCount, null);
	}

	/**
	 * Removes the row at the given row index.
	 */
	public synchronized void removeRow(int y) {
		for (int i = 0; i < columnsCount; i++)
			cells.remove(y * columnsCount);
		rowsCount--;
	}

	/**
	 * Gets the value at the given column and row, or null if not set.
	 */
	public synchronized T get(int x, int y) {
		int index = y * columnsCount + x;
		return cells.get(index);
	}

	/**
	 * Sets the value at the given column and row.
	 */
	public synchronized void set(int x, int y, T value) {
		int index = y * columnsCount + x;
		cells.set(index, value);
	}

}
