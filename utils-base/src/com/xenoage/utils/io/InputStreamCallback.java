package com.xenoage.utils.io;

import java.io.IOException;

/**
 * Functional interface for classes which open an {@link InputStream}.
 * The interface contains a callback methods for successful opening
 * and for a failure.
 * 
 * @author Andreas Wenger
 */
public interface InputStreamCallback {

	/**
	 * This method is called when the {@link InputStream} was
	 * successfully opened.
	 */
	public void inputStreamSuccess();
	
	/**
	 * This method is called when an error occurred while opening
	 * the {@link InputStream}.
	 */
	public void inputStreamFailure(IOException ex);
	
	
}
