package com.xenoage.utils.jse.io;

import static com.xenoage.utils.PlatformUtils.platformUtils;
import static com.xenoage.utils.collections.CollectionUtils.alist;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.xenoage.utils.io.FileFilter;
import com.xenoage.utils.io.FilesystemInput;
import com.xenoage.utils.io.InputStream;
import com.xenoage.utils.jse.JsePlatformUtils;

/**
 * Some useful input/output methods for a desktop application.
 * 
 * A desktop application uses different directories:
 * <ul>
 *   <li>The directory where the program is installed (program directory),
 *     also containing the provided data files</li>
 *   <li>The user's custom data and settings directory (user directory)</li>
 *   <li>Optional: a directory where to search for data, which is shared between
 *     different programs (shared directory)</li>
 * </ul>
 * 
 * When listing files, all directories are listed sequentially,
 * beginning with the user directory, then the system directory
 * and finally the shared directory (if it exists).
 * When reading files, first the user's directory is read.
 * When writing files, always the user's directory is written to.
 * 
 * This allows files to be overwritten by individual users, e.g.
 * to replace some provided files with own ones, without destroying
 * the original installation.
 * 
 * Use {@link JsePlatformUtils#desktopIO()} to get an instance of this class.
 *
 * @author Andreas Wenger
 */
public class DesktopIO
	implements FilesystemInput {

	private File userDir;
	private File systemDir;
	private File sharedDir;

	
	/**
	 * Creates a {@link DesktopIO}.
	 * When the directory "../shared" or "shared" exists, it is used as the shared directory.
	 * @param programName  Name of the program. An user directory for this program
	 *                     will be created if there is none. If null, no directory is
	 *                     created and the working directory is used as the user directory.
	 */
	public DesktopIO(String programName) {
		sharedDir = new File("../shared");
		if (!sharedDir.exists())
			sharedDir = new File("shared");
		if (!sharedDir.exists())
			sharedDir = null;
		init(programName, null, sharedDir);
	}

	/**
	 * Creates a {@link DesktopIO}.
	 * @param programName  Name of the program. An user directory for this program
	 *                     will be created if there is none. If null, no directory is
	 *                     created and the working directory is used as the user directory.
	 * @param systemDir    The custom system directory. If null, the working directory is used
	 * @param sharedDir    An additional directory with shared files. may be null.
	 */
	public DesktopIO(String programName, File systemDir, File sharedDir) {
		init(programName, systemDir, sharedDir);
	}

	private void init(String programName, File systemDir, File sharedDir) {
		this.systemDir = systemDir != null ? systemDir : new File(System.getProperty("user.dir"));
		if (programName != null) {
			userDir = JseFileUtils.getUserAppDataDirectory(programName);
			if (!userDir.exists()) {
				userDir.mkdirs();
			}
		}
		else {
			userDir = systemDir;
		}
		this.sharedDir = sharedDir;
	}

	/**
	 * Creates a {@link DesktopIO} for testing (e.g. unit tests).
	 * The program name is composed of "xenoage" and the name of the latest calling class
	 * ending with "Test" or "Try" (otherwise "unknown").
	 * When the directory "../shared" exists, it is used as the shared directory.
	 */
	public static DesktopIO createTestIO() {
		File sharedDir = new File("../shared");
		if (!sharedDir.exists())
			sharedDir = null;
		//get first test class name
		String s = "unknown";
		int i = 2;
		while (s != null) {
			s = platformUtils().getCaller(i).getClassName();
			if (s.endsWith("Test") || s.endsWith("Try"))
				break;
			i++;
		}
		return new DesktopIO("xenoage/" + s, null, sharedDir);
	}

	@Override public boolean existsFile(String filepath) {
		return new File(userDir, filepath).exists() || new File(systemDir, filepath).exists() ||
			(sharedDir != null ? new File(sharedDir, filepath).exists() : false);
	}

	@Override public boolean existsDirectory(String directory) {
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
	 * Gets the modification date of the given file, or null, if the date is unavailable.
	 */
	public Date getFileModificationDate(String filepath) {
		File file = new File(userDir, filepath);
		if (!file.exists()) {
			file = new File(systemDir, filepath);
		}
		if (!file.exists() && sharedDir != null) {
			file = new File(sharedDir, filepath);
		}
		if (file.exists()) {
			return new Date(file.lastModified());
		}
		else {
			return null;
		}
	}

	/**
	 * Gets the data file at the given relative path. The file is searched in the user's
	 * application settings directory first, and if not found, in the directory where the
	 * program was installed. If still not found, null is returned.
	 */
	public File findFile(String filepath)
		throws IOException {
		File file = new File(userDir, filepath);
		if (!file.exists()) {
			file = new File(systemDir, filepath);
		}
		if (!file.exists() && sharedDir != null) {
			file = new File(sharedDir, filepath);
		}
		if (file.exists()) {
			return file;
		}
		else {
			return null;
		}
	}
	
	@Override public InputStream openFile(String filePath)
		throws IOException {
		File file = findFile(filePath);
		return new JseInputStream(new FileInputStream(file));
	}

	/**
	 * Gets the data file for writing at the given relative path.
	 * It is always located in the user's application settings folder.
	 * If not existing, the parent directories are created.
	 */
	public File createFile(String filepath) {
		File file = new File(userDir, filepath);
		//create the parent directory on demand
		File parent = file.getParentFile();
		if (parent != null && !parent.exists()) {
			parent.mkdirs();
		}
		//open output stream
		return file;
	}

	/**
	 * Removes the data file at the given relative path.
	 * @param system  If true, not only the user's private data file is deleted,
	 *                but also the system data file.
	 *                Files in the shared folder are never deleted.
	 */
	public void deleteFile(String filepath, boolean system) {
		File file = new File(userDir, filepath);
		if (file.exists())
			file.delete();
		if (system) {
			file = new File(systemDir, filepath);
			if (file.exists())
				file.delete();
		}
	}

	@Override public List<String> listFiles(String directory) {
		return listFiles(directory, null);
	}

	@Override public List<String> listFiles(String directory, FileFilter filter) {
		FilenameFilter jseFilenameFilter = JseFileUtils.getFilter(filter);
		Set<String> ret = new HashSet<String>();
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
				String[] files = (filter != null ? dir.list(jseFilenameFilter) : dir.list());
				if (files != null) {
					ret.addAll(Arrays.asList(files));
				}
			}
		}
		return alist(ret);
	}

	@Override public List<String> listDirectories(String directory) {
		Set<String> ret = new HashSet<String>();
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
				File[] dirs = new File(baseDir, directory).listFiles(JseFileUtils.getDirectoriesFilter());
				if (dirs != null) {
					for (int i = 0; i < dirs.length; i++) {
						ret.add(dirs[i].getName());
					}
				}
			}
		}
		return alist(ret);
	}

}
