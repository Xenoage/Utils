package com.xenoage.utils.gwt.io;

import com.xenoage.utils.io.File;

/**
 * GWT implementation of {@link File} metadata.
 * 
 * @author Andreas Wenger
 */
public class GwtFile
	implements File {
	
	private final String name;
	private final boolean directory;
	
	
	/**
	 * Creates a new GWT file metadata.
	 * @param name       the relative path of the file
	 * @param directory  true, iff it is a directory
	 */
	public GwtFile(String name, boolean directory) {
		this.name = name;
		this.directory = directory;
	}

	@Override public String getName() {
		return name;
	}

	@Override public boolean isDirectory() {
		return directory;
	}

}
