package com.xenoage.utils.base.files;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import com.xenoage.utils.base.collections.WeakList;
import com.xenoage.utils.io.FileUtils;
import com.xenoage.utils.io.IO;


/**
 * Manages a list of the recently opened files.
 * It is loaded from and saved from a text file called
 * "data/recentfiles". Only existing files
 * are listed, other ones are automatically removed.
 * 
 * Listeners can be registered that are notified when the list changes.
 * 
 * @author Andreas Wenger
 */
public class RecentFiles
{

	static final String DATA_FILE = "data/recentfiles";
	static final int MAX_ENTRIES_COUNT = 5;
	
	private static WeakList<RecentFilesListener> listeners = new WeakList<RecentFilesListener>();


	/**
	 * Gets the list of recent files. If an error occurs, an empty
	 * list is returned (no error reporting is done).
	 */
	public static ArrayList<File> getRecentFiles()
	{
		ArrayList<File> ret = new ArrayList<File>(MAX_ENTRIES_COUNT);
		String list = FileUtils.readFile(DATA_FILE);
		if (list != null) {
			String[] files = list.split("\n");
			for (int i = 0; i < MAX_ENTRIES_COUNT && i < files.length; i++) {
				File file = new File(files[i]);
				if (file.exists())
					ret.add(file);
			}
		}
		return ret;
	}


	/**
	 * Adds a new file to the beginning of the list.
	 * It is automatically stored on disk.
	 * Errors are not reported.
	 */
	public static void addRecentFile(File file)
	{
		if (!file.exists())
			return;
		file = file.getAbsoluteFile();
		//get list and add given file
		ArrayList<File> files = getRecentFiles();
		files.remove(file);
		files.add(0, file);
		//trim list
		while (files.size() > MAX_ENTRIES_COUNT)
			files.remove(files.size() - 1);
		//save list
		try {
			Writer writer = new OutputStreamWriter(IO.openOutputStream(DATA_FILE));
			for (File f : files) {
				writer.append(f.getAbsolutePath() + "\n");
			}
			writer.close();
		} catch (IOException ex) {
		}
		//notify listeners
		for (RecentFilesListener listener : listeners.getAll()) {
			listener.recentFilesChanged();
		}
	}
	
	
	/**
	 * Adds the given {@link RecentFilesListener}.
	 * 
	 * Unregistering is not necessary. This class stores only weak
	 * references of the components, so they can be removed by the
	 * garbage collector when they are not used any more.
	 */
	public static void addListener(RecentFilesListener listener)
	{
		listeners.add(listener);
	}


	/**
	 * Removes all {@link RecentFilesListener}s.
	 */
	static void removeAllListeners()
	{
		listeners.clear();
	}


}
