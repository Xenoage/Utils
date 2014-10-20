package com.xenoage.utils.collections;

import static com.xenoage.utils.collections.BiMap.biMap;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests for {@link BiMap}.
 * 
 * @author Andreas Wenger
 */
public class BiMapTest {
	
	@Test public void testDifferentTypes() {
		BiMap<String, Integer> map = biMap();
		assertEquals(0, map.size());
		map.put("old", 5);
		map.put("other", 3);
		map.put("other", 8);
		map.put("new", 5);
		assertEquals(2, map.size());
		assertEquals("new", map.get1(5));
		assertEquals("other", map.get1(8));
		assertEquals((Integer) 5, map.get2("new"));
		assertEquals((Integer) 8, map.get2("other"));
	}
	
	@Test public void testSameTypes() {
		BiMap<String, String> map = biMap();
		assertEquals(0, map.size());
		map.put("1", "1");
		map.put("2", "1");
		map.put("1", "3");
		assertEquals(2, map.size());
		assertEquals("2", map.get1("1"));
		assertEquals("1", map.get2("2"));
		assertEquals("1", map.get1("3"));
		assertEquals("3", map.get2("1"));
	}

}
