package com.xenoage.utils.io;

/**
 * System independent interface for files.
 * 
 * For example, there may be implementations for Java SE, Android or GWT.
 * 
 * @author Andreas Wenger
 */
public interface File {
	
	//TODO: add more methods

	/**
	 * Returns the name of the file or directory denoted by this abstract pathname.
	 * This is just the last name in the pathname's name sequence.
	 */
	public String getName();

	/**
	 * Tests whether the file denoted by this abstract pathname is a directory.
	 */
	public boolean isDirectory();
}
