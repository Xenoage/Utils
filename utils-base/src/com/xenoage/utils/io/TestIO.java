package com.xenoage.utils.io;

import com.xenoage.utils.io.IO;
import com.xenoage.utils.log.Log;


/**
 * Utility class to init the IO for testing.
 * 
 * @author Andreas Wenger
 */
public class TestIO
{

	/**
	 * Inits basic IO and logging (silent) and uses the
	 * current directory as the IO's system directory.
	 */
	public static void init()
	{
		IO.initTest();
		Log.initNoLog();
	}


	/**
	 * Inits basic IO and logging (silent) and sets the
	 * "../shared/" directory as the IO's shared directory.
	 */
	public static void initWithSharedDir()
	{
		IO.initTest();
		Log.initNoLog();
	}

}
