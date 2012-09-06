package com.xenoage.utils.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 * Input/output implementation for the application.
 * 
 * The application uses at least directories for the data file:
 * The user's application settings directory and the directory
 * where the program was installed. There may also be a third
 * directory where to search for data (a "shared" directory
 * for several applications).
 * 
 * When listing files, all directories are listed sequentially,
 * beginning with the user directory, then the system directory
 * and finally the shared directory (if it exists).
 * When reading files, first the user's directory is read.
 * When writing files, always the user's directory is written to.
 * 
 * This allows files to be overwritten by individual users, e.g.
 * to replace some symbols with other ones, without destroying
 * the original installation.
 *
 * @author Andreas Wenger
 */
public class ApplicationIO
	implements IOInterface
{

	private final File userDir;
	private final File systemDir;
	private final File sharedDir;


	/**
	 * Creates a new IO implementation for an application.
	 * @param programName  name of the program. An user directory for this program
	 *                     will be created if there is none. If null, no directory is
	 *                     created and the working directory is used as the user directory.
	 */
	public ApplicationIO(String programName)
	{
		this(programName, null, null);
	}


	/**
	 * Creates a new IO implementation for an application.
	 * @param programName  name of the program. An user directory for this program
	 *                     will be created if there is none. If null, no directory is
	 *                     created and the working directory is used as the user directory.
	 * @param systemDir    the custom system directory. if null, the working directory is used
	 * @param sharedDir    an additional directory with shared files. may be null.
	 */
	public ApplicationIO(String programName, File systemDir, File sharedDir)
	{
		this.systemDir = systemDir != null ? systemDir : new File(
			System.getProperty("user.dir"));
		if (programName != null) {
			userDir = FileUtils.getUserAppDataDirectory(programName);
			if (!userDir.exists()) {
				userDir.mkdirs();
			}
		} else {
			userDir = systemDir;
		}
		this.sharedDir = sharedDir;
	}


	/**
	 * Returns true, when the given data file exists,
	 * otherwise false.
	 */
	@Override public boolean existsDataFile(String filepath)
	{
		return new File(userDir, filepath).exists() || new File(systemDir, filepath).exists()
			|| (sharedDir != null ? new File(sharedDir, filepath).exists() : false);
	}


	/**
	 * Returns true, when the given data directory exists,
	 * otherwise false.
	 */
	@Override public boolean existsDataDirectory(String directory)
	{
		File userFile = new File(userDir, directory);
		if (userFile.exists() && userFile.isDirectory())
			return true;
		File systemFile = new File(systemDir, directory);
		if (systemFile.exists() && systemFile.isDirectory())
			return true;
		if (sharedDir != null) {
			File sharedFile = new File(sharedDir, directory);
			if (sharedFile.exists() && sharedFile.isDirectory())
				return true;
		}
		return false;
	}


	/**
	 * Gets the modification date of the given data file,
	 * or null, if the date is unavailable.
	 */
	@Override public Date getDataFileModificationDate(String filepath)
	{
		File file = new File(userDir, filepath);
		if (!file.exists()) {
			file = new File(systemDir, filepath);
		}
		if (!file.exists() && sharedDir != null) {
			file = new File(sharedDir, filepath);
		}
		if (file.exists()) {
			return new Date(file.lastModified());
		} else {
			return null;
		}
	}


	/**
	 * Opens and returns an input stream for the data file with
	 * the given relative path. The file is searched in the user's
	 * application settings directory first, and if not found, in
	 * the directory where the program was installed.
	 */
	@Override public InputStream openInputStream(String filepath)
		throws IOException
	{
		File file = new File(userDir, filepath);
		if (!file.exists()) {
			file = new File(systemDir, filepath);
		}
		if (!file.exists() && sharedDir != null) {
			file = new File(sharedDir, filepath);
		}
		if (file.exists()) {
			return new FileInputStream(file);
		} else {
			throw new FileNotFoundException(filepath);
		}
	}


	/**
	 * Opens and returns an input stream for the data file with
	 * the given absolute or relative path. The path is guaranteed to
	 * be untouched (no automatic rerouting to home directory or something
	 * like that).
	 */
	@Override public InputStream openInputStreamPreservePath(String filepath)
		throws IOException
	{
		return new FileInputStream(filepath);
	}


	/**
	 * Opens and returns an output stream for the data file with
	 * the given relative path. It is always opened in the user's
	 * application settings folder.
	 */
	@Override public OutputStream openOutputStream(String filepath)
		throws IOException
	{
		File file = new File(userDir, filepath);
		//create the parent directory on demand
		File parent = file.getParentFile();
		if (parent != null && !parent.exists()) {
			parent.mkdirs();
		}
		//open output stream
		return new FileOutputStream(file);
	}


	/**
	 * Removes the data file with the given relative path.
	 * @param system  If true, not only the user's private settings file is deleted
	 *                but also the system data file.
	 *                But files in the shared folder are never deleted.
	 */
	@Override public void deleteDataFile(String filepath, boolean system)
	{
		File file = new File(userDir, filepath);
		if (file.exists())
			file.delete();
		if (system) {
			file = new File(systemDir, filepath);
			if (file.exists())
				file.delete();
		}
	}


	/**
	 * Finds and returns the data files in the given directory.
	 */
	@Override public Set<String> listDataFiles(String directory)
		throws IOException
	{
		return listDataFiles(directory, null);
	}


	/**
	 * Finds and returns the data files in the given directory
	 * matching the given filename filter.
	 */
	@Override public Set<String> listDataFiles(String directory, FilenameFilter filter)
		throws IOException
	{
		Set<String> ret = new HashSet<String>();
		boolean found = false;
		for (int iDir = 0; iDir < 3; iDir++) {
			File baseDir = null;
			switch (iDir) {
				case 0:
					baseDir = userDir;
					break;
				case 1:
					baseDir = systemDir;
					break;
				case 2:
					baseDir = sharedDir;
					break;
			}
			if (baseDir != null) {
				File dir = new File(baseDir, directory);
				String[] files = (filter != null ? dir.list(filter) : dir.list());
				if (files != null) {
					found = true;
					ret.addAll(Arrays.asList(files));
				}
			}
		}
		if (!found)
			throw new FileNotFoundException();
		return ret;
	}


	/**
	 * Finds and returns the data directories in the given directory.
	 */
	@Override public Set<String> listDataDirectories(String directory)
		throws IOException
	{
		Set<String> ret = new HashSet<String>();
		boolean found = false;
		for (int iDir = 0; iDir < 3; iDir++) {
			File baseDir = null;
			switch (iDir) {
				case 0:
					baseDir = userDir;
					break;
				case 1:
					baseDir = systemDir;
					break;
				case 2:
					baseDir = sharedDir;
					break;
			}
			if (baseDir != null) {
				File[] dirs = new File(baseDir, directory).listFiles(FileUtils
					.getDirectoriesFilter());
				if (dirs != null) {
					found = true;
					for (int i = 0; i < dirs.length; i++) {
						ret.add(dirs[i].getName());
					}
				}
			}
		}
		if (!found)
			throw new FileNotFoundException();
		return ret;
	}

}
