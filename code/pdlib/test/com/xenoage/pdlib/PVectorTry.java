package com.xenoage.pdlib;

import java.util.ArrayList;


/**
 * Test cases for the {@link PVector} class.
 * 
 * @author Andreas Wenger
 */
public class PVectorTry
{
	
	
	public static void main(String... args)
	{
		new PVectorTry().tryMemoryUsage();
	}
	
	
	/**
	 * Create many modified versions of an array.
	 * Save the first and the last one. After running the GC,
	 * we should not need much more memory than before creating
	 * the first array, because the references to the middle arrays
	 * should be gc'ed.
	 * Should not be run in parallel with other tests, which could
	 * distort the memory usage.
	 */
	public void tryMemoryUsage()
	{
		System.out.println("Memory usage test for " + PVector.class.getName());
		int size = 3000000;
		int steps = 5;
		//create raw array
		ArrayList<Integer> raw = new ArrayList<Integer>(size);
		for (int i = 0; i < size; i++)
		{
			raw.add(i);
		}
		//create PVector
		PVector<Integer> p = new PVector<Integer>(raw);
		PVector<Integer> firstRef = p;
		//measure memory usage
		gcAndSleep();
		long memoryBefore = getMemoryUsage();
		System.out.println("Start with " + memoryBefore + " bytes.");
		//modify (increment each position by 1, <steps> times. do not use with(), but the
		//more complicated minus and plus operations)
		for (int step = 0; step < steps; step++)
		{
			for (int i = 0; i < size; i++)
			{
				if (i % (steps * size / 100) == 0) System.out.print(".");
				int val = p.get(i);
				p = p.minus(i);
				p = p.plus(i, val + 1);
			}
		}
		System.out.println(" Done");
		PVector<Integer> lastRef = p;
		//measure memory usage
		gcAndSleep();
		long memoryAfter = getMemoryUsage();
		System.out.println("End with " + memoryAfter + " bytes.");
		//check correctness
		for (int i = 0; i < size; i++)
		{
			if (firstRef.get(i) != i || lastRef.get(i) != (i + steps))
			{
				System.out.println("FAILED! Wrong values!");
				return;
			}
		}
		System.out.println("Bytes used by first and last: " + (memoryAfter - memoryBefore));
		//delete first version
		System.out.println("Now deleting the first version...");
		firstRef = null;
		gcAndSleep();
		long memoryAfterDeletingFirst = getMemoryUsage();
		System.out.println("Bytes used by last: " + (memoryAfterDeletingFirst - memoryBefore));
		//"read" raw and lastRef again, just to ensure that the GC does not remove it before.
		raw.get(0);
		raw.get(raw.size() - 1);
		lastRef.get(0);
		lastRef.get(lastRef.size() - 1);
	}
	
	
	/**
	 * Calls the gc and sleeps 5 seconds to let it work.
	 */
	private void gcAndSleep()
	{
		for (int i = 0; i < 5; i++)
		{
			System.gc();
			try { Thread.sleep(1000); } catch (InterruptedException ex) {}
		}
	}
	
	
	/**
	 * Gets the current memory usage in bytes.
	 */
	private long getMemoryUsage()
	{
		Runtime rt = Runtime.getRuntime();  
		long totalMem = rt.totalMemory();  
		long freeMem = rt.freeMemory();  
		long usedMem = totalMem - freeMem;
		return usedMem;
	}
	

}
