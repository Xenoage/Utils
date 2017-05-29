package com.xenoage.utils.collections;

import org.junit.Test;

import static com.xenoage.utils.kernel.Range.range;
import static org.junit.Assert.assertEquals;

/**
 * Tests for {@link Table}.
 *
 * @author Andreas Wenger
 */
public class TableTest {

	private Table<Integer> table;

	@Test public void test() {
		//start with empty table
		table = new Table<>(0, 0);
		assertCells(new Integer[][]{});
		table.addRow();
		table.addColumn();
		assertCells(new Integer[][]{{null}});
		table.set(0, 0, 5);
		assertCells(new Integer[][]{{5}});
		table.addColumn(0);
		assertCells(new Integer[][]{{null, 5}});
		table.addRow(0);
		assertCells(new Integer[][]{{null, null}, {null, 5}});
		//start with 3x3 table
		table = new Table<>(3, 3);
		assertCells(new Integer[][]{{null, null, null}, {null, null, null}, {null, null, null}});
		table.set(1, 0, 5);
		table.set(2, 1, 6);
		table.set(0, 2, 7);
		assertCells(new Integer[][]{{null, 5, null}, {null, null, 6}, {7, null, null}});
		table.removeRow(1);
		assertCells(new Integer[][]{{null, 5, null}, {7, null, null}});
		table.removeColumn(1);
		assertCells(new Integer[][]{{null, null}, {7, null}});
	}

	public void assertCells(Integer[][] content) {
		assertEquals(content.length > 0 ? content[0].length : 0, table.getColumnsCount());
		assertEquals(content.length, table.getRowsCount());
		for (int y : range(table.getRowsCount()))
			for (int x : range(table.getColumnsCount()))
				assertEquals("Error at (" + x + "," + y + ")", content[y][x], table.get(x, y));
	}

}
