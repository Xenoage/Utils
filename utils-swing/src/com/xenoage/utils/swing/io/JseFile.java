package com.xenoage.utils.swing.io;

import java.io.File;

/**
 * Wrapper for a Java SE {@link File}.
 * 
 * @author Andreas Wenger
 */
public class JseFile
	implements com.xenoage.utils.io.generic.File {
	
	private File jseFile;
	
	
	public JseFile(File jseFile) {
		this.jseFile = jseFile;
	}

	@Override public String getName() {
		return jseFile.getName();
	}

	@Override public boolean isDirectory() {
		return jseFile.isDirectory();
	}

}
