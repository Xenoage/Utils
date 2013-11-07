package com.xenoage.utils.io;

import java.io.IOException;

/**
 * System independent interface for input streams.
 * 
 * For example, there may be implementations for Java SE, Android or GWT.
 * 
 * @author Andreas Wenger
 */
public interface InputStream {
	
	//TODO: support more methods, especially faster ones (read array)

	/**
	 * Reads the next byte of data from the input stream.
	 * The returned value is in the range 0 to 255, or -1 if the end of the stream
	 * has been reached.
	 */
	public int read()
		throws IOException;

}
