package com.xenoage.utils.io;

import static com.xenoage.utils.collections.CollectionUtils.alist;
import static com.xenoage.utils.kernel.Range.rangeReverse;

import java.util.List;

/**
 * Index of relative paths to files and directories.
 * 
 * Some file systems do not provide methods for listing files and directories,
 * for example web servers with HTTP access or files within the JAR files on
 * the classpath. On these filesystems, a file {@value #indexFile} can be
 * provided which contains a static list of all available files.
 * 
 * The index file is a UTF-8 encoded text file, containing the relative paths
 * to all files and directories, one per line. Paths can both use "/" and "\"
 * as separators. Paths may begin with a separator, but this is not required.
 * Any directory path must end with a separator to indicate that
 * it is a directory and not a file.
 * 
 * This class can parse such a file and store the index.
 * 
 * @author Andreas Wenger
 */
public class FilesystemIndex {

	public static final String indexFile = ".index";

	private List<String> paths = alist();


	/**
	 * Creates an index from the given index file data.
	 * @param indexFileContent  the complete content of the index file
	 */
	public FilesystemIndex(String indexFileContent) {
		String[] pathsArray = indexFileContent.split("\\n");
		for (String item : pathsArray) {
			paths.add(cleanPath(item));
		}
	}
	
	private String cleanPath(String path) {
		//clean file path (e.g. "\" -> "/")
		path = path.trim();
		path = FileUtils.cleanPath(path);
		//add leading "/" if missing
		if (false == path.startsWith("/"))
			path = "/" + path;
		return path;
	}
	
	private String cleanFilePath(String file) {
		file = cleanPath(file);
		//when it is a file, it may not end with "/"
		if (file.endsWith("/"))
			file = file.substring(0, file.length() - 1);
		return file;
	}
	
	private String cleanDirectoryPath(String directory) {
		directory = cleanPath(directory);
		//when it is a directory, it must end with "/"
		if (false == directory.endsWith("/"))
			directory += "/";
		return directory;
	}
	
	/**
	 * Returns true, when the given file exists in the index, otherwise false.
	 */
	public boolean existsFile(String file) {
		file = cleanFilePath(file);
		return paths.contains(file);
	}

	/**
	 * Returns true, when the given directory exists, otherwise false.
	 */
	public boolean existsDirectory(String directory) {
		directory = cleanDirectoryPath(directory);
		return paths.contains(directory);
	}

	/**
	 * Returns the names of the files in the given directory.
	 * If nothing is found, an empty list is returned.
	 * Only the file names are returned, not their complete paths.
	 */
	public List<String> listFiles(String directory) {
		directory = cleanDirectoryPath(directory);
		//to find the direct file children,
		//find all paths, which path starts with the directory path,
		//and does not contain further "/"
		List<String> ret = alist();
		for (String path : paths) {
			if (path.startsWith(directory)) {
				String possibleFilename = path.substring(directory.length());
				if (false == possibleFilename.contains("/"))
					ret.add(possibleFilename);
			}
		}
		return ret;
	}
	
	/**
	 * Returns the names of the files in the given directory
	 * matching the given file filter.
	 * If nothing is found, an empty list is returned.
	 * Only the file names are returned, not their complete paths.
	 */
	public List<String> listFiles(String directory, FileFilter filter) {
		//retrieve unfiltered list
		List<String> candidates = listFiles(directory);
		//remove files which do not match
		for (int i : rangeReverse(candidates))
			if (false == filter.accept(candidates.get(i)))
				candidates.remove(i);
		return candidates;
	}

	/**
	 * Returns the directories in the given directory.
	 * If nothing is found, an empty list is returned.
	 * Only the directory names are returned, not their complete paths.
	 */
	public List<String> listDirectories(String directory) {
		directory = cleanDirectoryPath(directory);
		//to find the direct directory children,
		//find all paths, which path starts with the directory path,
		//ends with a "/" but does not contain further "/"
		List<String> ret = alist();
		for (String path : paths) {
			if (path.endsWith("/") && path.startsWith(directory)) {
				String possibleDirName = path.substring(directory.length(), path.length() - 1);
				if (false == possibleDirName.contains("/"))
					ret.add(possibleDirName);
			}
		}
		return ret;
	}

}
