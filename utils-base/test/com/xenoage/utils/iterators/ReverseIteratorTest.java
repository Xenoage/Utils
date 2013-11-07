package com.xenoage.utils.iterators;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

/**
 * Tests for {@link ReverseIterator}.
 *
 * @author Andreas Wenger
 */
public class ReverseIteratorTest {

	@Test public void test() {
		int count = 5;
		ArrayList<Integer> a = new ArrayList<Integer>(count);
		for (int i = 0; i < count; i++)
			a.add(i);
		for (int i : new ReverseIterator<Integer>(a))
			assertEquals(--count, i);
	}

}
