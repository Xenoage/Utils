package com.xenoage.utils.base.collections;

import static com.xenoage.utils.base.collections.CollectionUtils.alist;
import static org.junit.Assert.assertEquals;

import org.junit.Test;


/**
 * Test cases for the {@link WeakList}.
 * 
 * @author Andreas Wenger
 */
public class WeakListTest
{
	
	
	@Test public void test()
		throws InterruptedException
	{
		//create list
		WeakList<Object> list = new WeakList<Object>();
		Object o1 = new Object();
		Object o2Temp = new Object();
		Object o3 = new Object();
		list.add(o1);
		list.add(o2Temp);
		list.add(o3);
		//check list
		assertEquals(alist(o1, o2Temp, o3), list.getAll());
		//remove an element
		list.remove(o1);
		//check list
		assertEquals(alist(o2Temp, o3), list.getAll());
		//garbage-collect an element
		o2Temp = null;
		System.gc();
		assertEquals(alist(o3), list.getAll());
	}

}
