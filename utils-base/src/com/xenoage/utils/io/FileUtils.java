package com.xenoage.utils.io;

import com.xenoage.utils.kernel.Tuple2;

/**
 * Some useful file system functions.
 *
 * @author Andreas Wenger
 */
public class FileUtils {

	/**
	 * Gets the name of the file without any extensions
	 * (ends before the first dot, but a dot on the very
	 * first position is allowed)
	 */
	public static String getNameWithoutExt(File file) {
		return getNameWithoutExt(file.getName());
	}

	/**
	 * Gets the name of the file without any extensions
	 * The returned name ends before the first dot, but a dot on the very
	 * first position is allowed. It begins after the last "/" or "\".
	 */
	public static String getNameWithoutExt(String filePath) {
		String fileName = filePath;
		int pos = -1;
		if ((pos = fileName.lastIndexOf('/')) > -1)
			fileName = fileName.substring(pos + 1);
		if ((pos = fileName.lastIndexOf('\\')) > -1)
			fileName = fileName.substring(pos + 1);
		if ((pos = fileName.indexOf(".", 1)) > -1)
			fileName = fileName.substring(0, pos);
		return fileName;
	}

	/**
	 * Gets the directory and the filename of the given path.
	 * For example, when "1/2/3.pdf" is given, "1/2" and "3.pdf"
	 * is returned. For "4.xml", "" and "4.xml" is returned.
	 * When a directory is given (e.g. "1/2/3/"), the last directory
	 * is split off ("1/2" and "3").
	 */
	public static Tuple2<String, String> splitDirectoryAndFilename(String path) {
		String p = cleanPath(path);
		int endPos = p.lastIndexOf('/');
		if (endPos > -1) {
			String dir = p.substring(0, endPos);
			String file = p.substring(endPos + 1);
			return new Tuple2<String, String>(dir, file);
		}
		else {
			//only filename
			return new Tuple2<String, String>("", p);
		}
	}

	/**
	 * Gets the directory name of the given path.
	 * For example, when "1/2/3.pdf" is given, "1/2"
	 * is returned. For "4.xml", "" is returned.
	 * When a directory is given (e.g. "1/2/3/"), the path without
	 * the last directory is returned ("1/2").
	 */
	public static String getDirectoryName(String path) {
		return splitDirectoryAndFilename(path).get1();
	}

	/**
	 * Gets the filename of the given path.
	 * For example, when "1/2/3.pdf" is given, "3.pdf"
	 * is returned. For "4.xml", "4.xml" is returned.
	 * When a directory is given (e.g. "1/2/3/"), the
	 * last directory is returned ("3").
	 */
	public static String getFileName(String path) {
		return splitDirectoryAndFilename(path).get2();
	}

	/**
	 * Cleans the given path.
	 * <ul>
	 * 	<li>replace all backslashes ("\") by forward slashes ("/")</li>
	 * 	<li>replace double slashes ("//") by single slashes ("/")</li>
	 * 	<li>removes trailing "/"</li>
	 * </ul>
	 */
	public static String cleanPath(String path) {
		//use only "/" as delimiter
		path = path.replaceAll("\\\\", "/");
		//remove double slahes
		path = path.replaceAll("//", "/");
		//remove last "/"
		if (true == path.endsWith("/"))
			path = path.substring(0, path.length() - 1);
		return path;
	}
}
