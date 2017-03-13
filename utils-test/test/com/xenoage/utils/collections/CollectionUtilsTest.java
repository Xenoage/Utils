package com.xenoage.utils.collections;

import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 * Tests for {@link CollectionUtils}.
 * 
 * @author Andreas Wenger
 */
public class CollectionUtilsTest {

	@Test public void getMaxTest() {
		assertEquals(8, (int) CollectionUtils.getMax(list()));
	}

	@Test public void getMinTest() {
		assertEquals(1, (int) CollectionUtils.getMin(list()));
	}

	private LinkedList<Integer> list() {
		LinkedList<Integer> ret = new LinkedList<>();
		ret.add(2);
		ret.add(8);
		ret.add(3);
		ret.add(1);
		ret.add(5);
		return ret;
	}

}
