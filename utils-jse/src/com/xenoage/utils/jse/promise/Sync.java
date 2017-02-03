package com.xenoage.utils.jse.promise;

import com.xenoage.utils.async.Promise;

/**
 * This class allows block a promise until it finished
 * successfully or until it fails.
 * 
 * @author Andreas Wenger
 */
public class Sync {
	
	private static class State<T> {
		public Object lock = new Object();
		public boolean finished = false;
		public T data = null;
		public Exception exception = null;
	}
	
	/**
	 * Waits for the result of the given {@link Promise}.
	 * The result is returned or an exception is thrown.
	 */
	public static <T> T sync(Promise<T> promise)
		throws Exception {
		return null;
		/*
		//start production
		final State<T> state = new State<T>();
		promise
			.thenDo(new Consumer<T>() {
				@Override public void run(T data) {
					state.data = data;
					synchronized (state.lock) {
						state.lock.notify();
						state.finished = true;
					}
				}
			})
			.whenFails(new Consumer<Exception>() {
				@Override public void run(Exception ex) {
					state.exception = ex;
					synchronized (state.lock) {
						state.lock.notify();
						state.finished = true;
					}
				}
			});
		//wait for promise to finish
		synchronized (state.lock) {
			try {
				//if the promise was faster then the main thread, we would
				//wait forever, so we use an additional boolean variable
				if (false == state.finished)
					state.lock.wait();
			} catch (InterruptedException ex) {
				throw ex;
			}
		}
		//throw exception or return result
		if (state.exception != null)
			throw state.exception;
		else
			return state.data; */
	}

}
