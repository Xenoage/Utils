package com.xenoage.utils.gwt.io;

import java.io.IOException;
import java.util.List;

import com.xenoage.utils.gwt.GwtPlatformUtils;
import com.xenoage.utils.io.FileFilter;
import com.xenoage.utils.io.FilesystemInput;
import com.xenoage.utils.io.InputStream;

/**
 * Some useful input methods for a GWT client application.
 * 
 * Currently only reading a file is supported.
 * 
 * Use {@link GwtPlatformUtils#gwtIO()} to get an instance of this class.
 *
 * @author Andreas Wenger
 */
public class GwtIO
	implements FilesystemInput {

	@Override public boolean existsFile(String filepath) {
		throw new UnsupportedOperationException(); //TODO
	}

	@Override public boolean existsDirectory(String directory) {
		throw new UnsupportedOperationException(); //TODO
	}
	
	@Override public InputStream openFile(String filePath)
		throws IOException {
		return new GwtInputStream(filePath);
	}

	@Override public List<String> listFiles(String directory)
		throws IOException {
		throw new UnsupportedOperationException(); //TODO
	}

	@Override public List<String> listFiles(String directory, FileFilter filter)
		throws IOException {
		throw new IOException(new UnsupportedOperationException()); //TODO
	}

	/**
	 * Not supported on Android.
	 */
	@Override public List<String> listDirectories(String directory)
		throws IOException {
		throw new IOException(new UnsupportedOperationException()); //TODO
	}

}
