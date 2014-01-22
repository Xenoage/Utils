package com.xenoage.utils.jse.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;

import com.xenoage.utils.jse.OSUtils;
import com.xenoage.utils.jse.OSUtils.OS;
import com.xenoage.utils.kernel.Tuple2;

/**
 * Some useful file system functions.
 *
 * @author Andreas Wenger
 */
public class JseFileUtils {

	private static FileFilter directoriesFilter = null;


	/**
	 * Returns a filter for directories.
	 * Directories beginning with a "." are ignored
	 * (e.g. ".svn").
	 */
	public static FileFilter getDirectoriesFilter() {
		if (directoriesFilter == null) {
			directoriesFilter = new FileFilter() {

				@Override public boolean accept(File file) {
					return file.isDirectory() && !file.getName().startsWith(".");
				}
			};
		}
		return directoriesFilter;
	}

	/**
	 * Returns a filename filter for the given {@link com.xenoage.utils.io.FileFilter}.
	 */
	public static FilenameFilter getFilter(final com.xenoage.utils.io.FileFilter fileFilter) {
		FilenameFilter ret = new FilenameFilter() {

			@Override public boolean accept(File dir, String name) {
				return fileFilter.accept(new JseFile(new File(dir, name)));
			}
		};
		return ret;
	}

	/**
	 * Returns a filename filter which accepts all of the given filters.
	 */
	public static FilenameFilter orFilter(final FilenameFilter... filters) {
		FilenameFilter ret = new FilenameFilter() {

			@Override public boolean accept(File dir, String name) {
				for (FilenameFilter filter : filters) {
					if (filter.accept(dir, name))
						return true;
				}
				return false;
			}
		};
		return ret;
	}

	/**
	 * Reads the given file into a string and returns it.
	 * If the file does not exist or there is another I/O error,
	 * null is returned.
	 */
	public static String readFile(File file) {
		try {
			StringBuffer fileData = new StringBuffer(1024);
			BufferedReader reader = new BufferedReader(new FileReader(file));
			char[] buf = new char[1024];
			int numRead = 0;
			while ((numRead = reader.read(buf)) != -1) {
				String readData = String.valueOf(buf, 0, numRead);
				fileData.append(readData);
			}
			reader.close();
			return fileData.toString();
		} catch (IOException ex) {
			return null;
		}
	}

	/**
	 * Writes the given string into the given file.
	 * If it fails, nothing happens.
	 */
	public static void writeFile(String content, String filepath) {
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filepath)));
			bw.write(content);
			bw.close();
		} catch (Exception ex) {
		}
	}

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
	 * (ends before the first dot, but a dot on the very
	 * first position is allowed)
	 */
	public static String getNameWithoutExt(String filename) {
		int dotPos = filename.indexOf(".", 1);
		if (dotPos == -1)
			return filename;
		else
			return filename.substring(0, dotPos);
	}

	/**
	 * Gets the directory and the filename of the given path.
	 * For example, when "1/2/3.pdf" is given, "1/2" and "3.pdf"
	 * is returned. For "4.xml", "" and "4.xml" is returned.
	 */
	public static Tuple2<String, String> splitDirectoryAndFilename(String path) {
		String p = path.replaceAll("\\\\", "/"); //use only / for the moment
		int endPos = p.lastIndexOf('/');
		if (endPos > -1) {
			String dir = p.substring(0, endPos);
			dir = dir.replaceAll("/", Matcher.quoteReplacement(File.separator));
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
	 */
	public static String getDirectoryName(String path) {
		return splitDirectoryAndFilename(path).get1();
	}

	/**
	 * Gets the filename of the given path.
	 * For example, when "1/2/3.pdf" is given, "3.pdf"
	 * is returned. For "4.xml", "4.xml" is returned.
	 */
	public static String getFileName(String path) {
		return splitDirectoryAndFilename(path).get2();
	}

	/**
	 * Gets the directory for temporary files.
	 */
	public static File getTempFolder() {
		return new File(System.getProperty("java.io.tmpdir"));
	}

	/**
	 * Creates a new subdirectory with a random name in the
	 * directory for temporary files and returns it.
	 */
	public static File createNewTempFolder() {
		File tempFolder = getTempFolder();
		UUID id = UUID.randomUUID();
		File ret = new File(tempFolder, id.toString());
		if (ret.mkdir())
			return ret;
		else
			throw new IllegalStateException("Could not create temp folder " + id.toString());
	}

	/**
	 * Deletes the given directory, which may be non-empty.
	 */
	public static boolean deleteDirectory(File path) {
		if (path.exists()) {
			File[] files = path.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					deleteDirectory(files[i]);
				}
				else {
					files[i].delete();
				}
			}
		}
		return path.delete();
	}

	/**
	 * Lists all files in a given directory.
	 * @param dir        the directory
	 * @param subdirs    true, if recursive search in subdirectories, otherwise false 
	 */
	public static List<File> listFiles(File dir, boolean subdirs) {
		LinkedList<File> list = new LinkedList<File>();
		listFiles(list, dir, subdirs);
		return list;
	}

	private static void listFiles(LinkedList<File> list, File dir, boolean subdirs) {
		File[] children = dir.listFiles();
		if (children != null) {
			for (int i = 0; i < children.length; i++) {
				if (children[i].isDirectory() && subdirs)
					listFiles(list, children[i], subdirs);
				else
					list.add(children[i]);
			}
		}
	}

	/**
	 * Copies a file.
	 */
	public static void copyFile(String in, String out) {
		try {
			FileInputStream fis = new FileInputStream(in);
			FileOutputStream fos = new FileOutputStream(out);
			byte[] buf = new byte[1024];
			int i = 0;
			while ((i = fis.read(buf)) != -1) {
				fos.write(buf, 0, i);
			}
			fis.close();
			fos.close();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Copies the given file into the given {@link OutputStream},
	 * which is closed at the end.
	 */
	public static void copyFile(String filepath, OutputStream out)
		throws IOException {
		FileInputStream fis = new FileInputStream(filepath);
		byte[] buf = new byte[1024];
		int i = 0;
		while ((i = fis.read(buf)) != -1) {
			out.write(buf, 0, i);
		}
		fis.close();
		out.close();
	}

	/**
	 * Returns a list of all files in the given directory matching the given filter.
	 * @param directory  the directory
	 * @param filter     the filename filter
	 * @param recurse    true, to search also recursively in subdirectories, otherwise false
	 */
	public static List<File> listFiles(File directory, FilenameFilter filter, boolean recurse) {
		ArrayList<File> files = new ArrayList<File>();
		File[] entries = directory.listFiles();
		for (File entry : entries) {
			if (filter == null || filter.accept(directory, entry.getName())) {
				files.add(entry);
			}
			if (recurse && entry.isDirectory()) {
				files.addAll(listFiles(entry, filter, recurse));
			}
		}
		return files;
	}

	/**
	 * Returns a list of all files below the given directory (including
	 * subdirectories in any depth) matching the given filter.
	 */
	public static Collection<File> listFilesDeep(File directory, FilenameFilter filter) {
		return listFiles(directory, filter, true);
	}

	/**
	 * Gets the default directory of the operating system to store application
	 * data for the current user and the given application name.
	 * For user "andi" and program "myapp" this is for example:
	 * <ul>
	 * 	<il>under Linux and Solaris: "/home/andi/.myapp/"</il>
	 *  <il>under MacOSX: "/Users/andi/Library/Application Support/myapp"</il>
	 * 	<il>under Windows: "C:/Users/andi/AppData/Roaming" (under Vista at least)</il>
	 * </ul>
	 * Of course these settings may differ between different versions of
	 * the operating system. This function determines the right place to store
	 * the settings. If impossible to find the perfectly right folder, the user's
	 * home folder extended by "/myapp" is returned.
	 */
	public static File getUserAppDataDirectory(String program) {
		OS os = OSUtils.getOS();
		if (os == OS.Linux || os == OS.Solaris) {
			//Linux, Solaris: <user-home>/.<program>
			return new File(System.getProperty("user.home") + "/." + program);
		}
		else if (os == OS.MacOSX) {
			//Mac OS X: <user-home>/Library/Application Support/<program>
			String path = System.getProperty("user.home") + "/Library/Application Support/";
			if (new File(path).exists()) {
				return new File(path + "/" + program);
			}
		}
		else if (os == OS.Windows) {
			//Windows: use APPDATA environment variable.
			String path = System.getenv("APPDATA");
			if (path != null && new File(path).exists()) {
				return new File(path + "/" + program);
			}
		}
		//otherwise: <user-home>/<program>
		return new File(System.getProperty("user.home") + "/" + program);
	}

}
