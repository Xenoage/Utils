package com.xenoage.pdlib;

import static com.xenoage.pdlib.PVector.pvec;
import static com.xenoage.utils.kernel.Tuple2.t;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;


/**
 * Test cases for the {@link PVector} class.
 * 
 * @author Andreas Wenger
 */
public class PVectorTest
{

	@Test public void withTest()
	{
		PVector<Integer> v = new PVector<Integer>();
		try {
			v = v.with(5, 10);
			fail("Should throw IndexOutOfBoundsException");
		} catch (IndexOutOfBoundsException ex) {
		}
	}


	@Test public void withExtendTest()
	{
		PVector<Integer> v = new PVector<Integer>();
		v = v.withExtend(3, 5, 1);
		assertEquals(pvec(1, 1, 1, 5), v);
	}


	@Test public void splitTest()
	{
		PVector<Integer> v = pvec(1, 2, 3, 4, 5);
		//split in middle
		assertEquals(t(pvec(1, 2), pvec(3, 4, 5)), v.split(2));
		//split at start
		assertEquals(t(pvec(), v), v.split(-1));
		//split at end
		assertEquals(t(v, pvec()), v.split(5));
	}

}
