package com.xenoage.utils.io;

import java.util.List;

/**
 * Interface for reading from filesystems.
 * 
 * For example, there may be implementations for the Java SE
 * filesystem, Android or HTTP for example.
 * 
 * Some technologies like HTTP might not support listing directories
 * out of the box. In this case, implementing classes must include
 * a workaround, e.g. using a file list script on the server or a
 * static file including all available paths.
 * 
 * @author Andreas Wenger
 */
public interface FilesystemInput {
	
	/**
	 * Returns true, when the given file exists, otherwise false.
	 */
	public boolean existsFile(String filepath);

	/**
	 * Returns true, when the given directory exists, otherwise false.
	 */
	public boolean existsDirectory(String directory);

	/**
	 * Finds and returns the files in the given directory.
	 * If nothing is found, an empty list is returned.
	 */
	public List<String> listFiles(String directory);
	
	/**
	 * Finds and returns the files in the given directory
	 * matching the given file filter.
	 * If nothing is found, an empty list is returned.
	 */
	public List<String> listFiles(String directory, FileFilter filter);

	/**
	 * Finds and returns the directories in the given directory.
	 * If nothing is found, an empty set is returned.
	 */
	public List<String> listDirectories(String directory);
	
	/**
	 * Opens the data file at the given path and returns an input stream.
	 * If not found, null is returned.
	 */
	public InputStream openFile(String filepath);

}
