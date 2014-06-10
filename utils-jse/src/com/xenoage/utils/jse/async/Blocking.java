package com.xenoage.utils.jse.async;

import com.xenoage.utils.async.AsyncCallback;
import com.xenoage.utils.async.AsyncProducer;

/**
 * This class allows to wrap asynchronous methods into
 * blocking methods for convenience.
 * 
 * @author Andreas Wenger
 */
public class Blocking {
	
	private static class AsyncResult<T> {
		public Object sync = new Object();
		public boolean finished = false;
		public T data = null;
		public Exception exception = null;
	}
	
	/**
	 * Runs the given {@link AsyncProducer} and waits for the result.
	 * The result is returned or an exception is thrown.
	 */
	public static <T> T blocking(AsyncProducer<T> producer)
		throws Exception {
		//start async production
		final AsyncResult<T> result = new AsyncResult<T>();
		producer.produce(new AsyncCallback<T>() {

			@Override public void onSuccess(T data) {
				result.data = data;
				synchronized (result.sync) {
					result.sync.notify();
					result.finished = true;
				}
			}

			@Override public void onFailure(Exception ex) {
				result.exception = ex;
				synchronized (result.sync) {
					result.sync.notify();
					result.finished = true;
				}
			}
		});
		//wait for async production to finish
		synchronized (result.sync) {
			try {
				//if the producer was faster then the main thread, we would
				//wait forever, so we use an additional boolean variable
				if (false == result.finished)
					result.sync.wait();
			} catch (InterruptedException ex) {
				throw ex;
			}
		}
		//throw exception or return result
		if (result.exception != null)
			throw result.exception;
		else
			return result.data;
	}

}
