package com.xenoage.utils.io;

import java.io.IOException;

/**
 * Callback methods for a {@link AsyncReader}.
 * 
 * @author Andreas Wenger
 */
public interface AsyncReaderCallback<T> {

	/**
	 * This method is called when the reading was successful.
	 * @param data  the resulting data
	 */
	public void readSuccess(T data);
	
	/**
	 * This method is called when the reading was not successful.
	 * @param ex  details about the error
	 */
	public void readFailure(IOException ex);
	
}
