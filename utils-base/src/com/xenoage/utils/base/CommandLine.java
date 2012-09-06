package com.xenoage.utils.base;


/**
 * Command line arguments.
 * 
 * @author Andreas Wenger
 */
public class CommandLine
{
	
	private static String[] args = null;

	
	public static String[] getArgs()
	{
		return args;
	}

	
	public static void setArgs(String[] args)
	{
		CommandLine.args = args;
	}
	

}
