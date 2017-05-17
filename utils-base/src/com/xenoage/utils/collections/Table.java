package com.xenoage.utils.collections;

import java.util.ArrayList;

import static com.xenoage.utils.collections.CollectionUtils.alist;

/**
 * Two-dimensional array.
 *
 * Columns and rows can be added, inserted and removed
 * and cells can be modified.
 *
 * @author Andreas Wenger
 */
public class Table<T> {

	//vote[x,y]=(y * columns.count + x)
	private ArrayList<T> cells = alist();

	private int columnsCount = 0;
	private int rowsCount = 0;


	/**
	 * Adds a column at the end of the table.
	 */
	public void addColumn() {
		columnsCount++;
		cells.ensureCapacity(columnsCount * rowsCount);
		for (int i = 0; i < rowsCount; i++)
			cells.add((i + 1) * columnsCount - 1,null);
	}

	/**
	 * Inserts a column at the given column index.
	 */
	public void addColumn(int x) {
		columnsCount++;
		cells.ensureCapacity(columnsCount * rowsCount);
		for (int i = 0; i < rowsCount; i++)
			cells.add(i * columnsCount + x, null);
	}

	/**
	 * Removes the column at the given column index.
	 */
	public void removeColumn(int x) {
		for (int i = rowsCount - 1; i >= 0; i--)
			cells.remove(i * columnsCount + x);
		columnsCount--;
	}

	/**
	 * Adds a row at the end of the table.
	 */
	public void addRow() {
		rowsCount++;
		cells.ensureCapacity(columnsCount * rowsCount);
		for (int i = 0; i < columnsCount; i++)
			cells.add(null);
	}

	/**
	 * Adds a row at the given row index.
	 */
	public void addRow(int y) {
		rowsCount++;
		cells.ensureCapacity(columnsCount * rowsCount);
		for (int i = 0; i < columnsCount; i++)
			cells.add(y * columnsCount, null);
	}

	/**
	 * Removes the row at the given row index.
	 */
	public void removeRow(int y) {
		for (int i = 0; i < columnsCount; i++)
			cells.remove(y * columnsCount);
		rowsCount--;
	}

	/**
	 * Gets the value at the given column and row, or null if not set.
	 */
	public T get(int x, int y) {
		int index = y * columnsCount + x;
		return cells.get(index);
	}

	/**
	 * Sets the value at the given column and row.
	 */
	public void set(int x, int y, T value) {
		int index = y * columnsCount + x;
		cells.set(index, value);
	}

}
