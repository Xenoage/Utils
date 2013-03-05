package com.xenoage.utils.pdlib;

import static com.xenoage.utils.pdlib.IVector.ivec;
import static junit.framework.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;


/**
 * Tests for {@link IVector}.
 * 
 * @author Andreas Wenger
 */
public class IVectorTest
{
	
	/**
	 * Tests the branching feature of the {@link IVector} class.
	 */
	@Test public void testBranch()
	{
		//test branching without modifications (very fast)
		long[] t = performBranchTest(false);
		assertTrue(t[0] / 100 > t[1]);
		//test branching with modifications (about as fast as normal copies)
		t = performBranchTest(true);
		assertTrue(Math.abs(1 - t[0] / t[1]) < 0.01);
	}
	
	
	/**
	 * Measures the time for both a non-branching and a branching test.
	 * Returns the time in ms for the non-branching (index 0) and the branching (index 1) test.
	 * @param modify  true, if created vector should be modified
	 */
	private long[] performBranchTest(boolean modify)
	{
		//branches must be (if not modified) much faster than normal copies
		int testsCount = 10000;
		//prepare data
		ArrayList<Integer> originalA = new ArrayList<Integer>();
		for (int i = 0; i < 20000; i++)
			originalA.add(i);
		IVector<Integer> originalI = new IVector<Integer>();
		originalI.addAll(originalA);
		originalI.close();
		//run test without branching
		IVector<Integer> iv = new IVector<Integer>();
		long startTimeNoBranch = System.currentTimeMillis();
		for (int i = 0; i < testsCount; i++) {
			iv = ivec(originalA);
			if (modify) {
				for (int j = 0; j < 10; j++)
					iv.set(j, j * 5);
			}
		}
		long durationNoBranch = System.currentTimeMillis() - startTimeNoBranch;
		//same test with branching
		iv = new IVector<Integer>();
		long startTimeBranch = System.currentTimeMillis();
		for (int i = 0; i < testsCount; i++) {
			iv = ivec(originalI);
			if (modify) {
				for (int j = 0; j < 10; j++)
					iv.set(j, j * 5);
			}
		}
		long durationBranch = System.currentTimeMillis() - startTimeBranch;
		iv.set(0, iv.get(0)); //just that the compiler can not simplify something
		return new long[]{durationNoBranch, durationBranch};
	}
	

}
