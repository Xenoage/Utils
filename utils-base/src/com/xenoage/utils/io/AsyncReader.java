package com.xenoage.utils.io;


/**
 * Interface for classes which read data from an {@link InputStream},
 * but do not return the result immediately, but by a callback method.
 * 
 * @author Andreas Wenger
 */
public interface AsyncReader<T>
	extends InputStreamCallback {
	
	/**
	 * The method which starts the reading.
	 * It may return before the reading is finished.
	 * @param inputStream  the input stream of which to read from
	 */
	public void read(InputStream inputStream, AsyncReaderCallback<T> callback);

}
