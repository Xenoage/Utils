package com.xenoage.utils.io.generic;

import java.io.IOException;

/**
 * System independent interface for files.
 * 
 * For example, there may be implementations for Java SE, Android or GWT.
 * 
 * @author Andreas Wenger
 */
public interface OutputStream {
	//GOON: support more methods, especially faster ones (write array)
	
	/**
   * Writes the specified byte to this output stream.
   * Only the eight low-order bits of the given value are used.
   */
	public void write(int b)
		throws IOException;

}
