package com.xenoage.utils.async;

import static com.xenoage.utils.async.PromiseState.Pending;
import static com.xenoage.utils.async.PromiseState.Resolved;

/**
 * TODO: Experimental.
 *
 * This class is very tedious to use with old Java style (anonymous classes).
 * Instead, use the lambda style. See the test cases for this class.
 *
 * @author Andreas Wenger
 */
public class Promise<T> {

	private static class Handler<T, R> {
		Function<Object, R> onResolved;
		Consumer<Object> resolve;

		Handler(Function<Object, R> onResolved, Consumer<Object> resolve) {
			this.onResolved = onResolved;
			this.resolve = resolve;
		}
	}


	private PromiseState state = Pending;
	private Object value;
	//asynchronous callback, if the value can not be resolved immediately
	private Handler<T, Object> deferred = null; //TODO: list, instead of only the last registered one


	public Promise(PromiseFn<T> fn) {
		fn.run(new Consumer<T>() {
			@Override public void run(T value) {
				resolve(value);
			}
		});
	}

	private synchronized void resolve(Object value) {
		if (value instanceof Promise) {
			//resolve promise
			((Promise) value).thenSync(new Function() {
				@Override public Object run(Object value) {
					resolve(value);
					return null;
				}
			});
		}
		else {
			//plain value
			this.state = Resolved;
			this.value = value;
			if (deferred != null) {
				handle(deferred);
			}
		}
	}

	private <R> void handle(Handler<T, R> handler) {
		if (state == Pending)
			deferred = (Handler) handler;
		else if (handler.onResolved == null)
			handler.resolve.run((T) value);
		else {
			Object ret = handler.onResolved.run(value);
			handler.resolve.run(ret);
		}
	}

	/**
	 * Returns a promise for the result of the given synchronous function
	 * applied to the value of this promise.
	 */
	public synchronized <R> Promise<R> thenSync(final Function<T, R> onResolved) {
		return thenInternal((Function) onResolved);
	}

	/**
	 * Returns a promise for the result of the given asynchronous function
	 * applied to the value of this promise.
	 */
	public synchronized <R> Promise<R> thenAsync(final Function<T, Promise<R>> onResolved) {
		return thenInternal((Function) onResolved);
	}

	private <R> Promise<R> thenInternal(final Function<T, Object> onResolved) {
		return new Promise<R>(new PromiseFn<R>() {
			@Override public void run(Consumer<R> resolve) {
				handle((Handler) new Handler<T, R>((Function) onResolved, (Consumer) resolve));
			}
		});
	}

}
