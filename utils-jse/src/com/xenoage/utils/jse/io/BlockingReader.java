package com.xenoage.utils.jse.io;

import java.io.IOException;

import com.xenoage.utils.io.AsyncReader;
import com.xenoage.utils.io.AsyncReaderCallback;
import com.xenoage.utils.io.InputStream;

/**
 * This class can be used to wrap an {@link AsyncReader}, so that
 * it does not work asynchronously any more, but blocks until
 * the result is there or an error occurs.
 * 
 * @author Andreas Wenger
 */
public class BlockingReader<T> {

	//input
	private AsyncReader<T> reader;
	private InputStream inputStream;
	//result
	private boolean finished = false;
	private T data = null;
	private IOException ex = null;
	
	
	/**
	 * Reads the input stream, while blocking, and returns the result or throws
	 * an exception.
	 */
	public static <T> T readBlocking(AsyncReader<T> reader, InputStream inputStream)
		throws IOException {
		return new BlockingReader<T>(reader, inputStream).read();
	}


	/**
	 * Initializes the {@link BlockingReader} with the given asynchronous reader
	 * and the input stream.
	 */
	private BlockingReader(AsyncReader<T> reader, InputStream inputStream) {
		this.reader = reader;
		this.inputStream = inputStream;
	}

	/**
	 * Reads the input stream, while blocking, and returns the result or throws
	 * an exception.
	 */
	private T read()
		throws IOException {
		//start asynchonous call
		finished = false;
		AsyncReaderCallback<T> callback = new AsyncReaderCallback<T>() {

			@Override public void readSuccess(T data) {
				BlockingReader.this.data = data;
				finished = true;
			}

			@Override public void readFailure(IOException ex) {
				BlockingReader.this.ex = ex;
				finished = true;
			}
		};
		reader.read(inputStream, callback);
		//wait for the result
		while (false == finished) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException interruptedEx) {
			}
		}
		//return result or throw exception
		if (ex != null) //we start by the exception, because result data may be null
			throw ex;
		else
			return data;
	}

}
