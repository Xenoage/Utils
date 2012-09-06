package com.xenoage.utils.base.thread;


/**
 * Useful methods for working with {@link Thread}s.
 * 
 * @author Andreas Wenger
 */
public class ThreadUtils
{
	
	
	/**
	 * {@link Thread#sleep(long)}, with ignoring the {@link InterruptedException}.
	 */
	public static void sleep(int seconds)
	{
		try
		{
			Thread.sleep(seconds * 1000);
		}
		catch (InterruptedException e)
		{
		}
	}

}
