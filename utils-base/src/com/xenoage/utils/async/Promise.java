package com.xenoage.utils.async;

import static com.xenoage.utils.async.PromiseState.*;

/**
 * TODO: Experimental.
 *
 * @author Andreas Wenger
 */
public abstract class Promise<T> {

	private PromiseState state = Pending;

	private T result;
	private Exception error;

	private boolean isProcessed = false;
	private Promise<?> waitPromise;


	/**
	 * Code to produce the promised value asynchronously.
	 * Call the {@link #success(Object)} or {@link #failure(Exception)} method
	 * when the production is finished or has failed.
	 * This metjod must return this instance to allow chaining.
	 */
	public abstract Promise<T> produce();

	/**
	 * Sets a function, which is called when the promise is finished.
	 * The function transforms the result of the promise into some other type,
	 * which is returned as another promise.
	 */
	public <R> Promise<R> then(final Function<T, Promise<R>> executor) {
		final Promise<T> self = this;
		//result of this promise already known? then apply executor and report success or failure
		if (state == Success) {
			Promise<R> ret = executor.run(result);
			return ret.produce();
		}
		else if (state == Failure) {
			Promise<R> ret = new Promise<R>() {  //forward error in different Promise type
				@Override public Promise<R> produce() {
					this.failure(self.error);
					return this;
				}
			};
			return ret.produce();
		}
		//otherwise, wait for result until going on
		else {
			waitPromise = new Promise<R>() {
				@Override public Promise<R> produce() {
					if (self.state == Success) {
						Promise<R> ret = executor.run(self.result);
						return ret.produce();
					}
					else if (self.state == Failure) {
						Promise<R> ret = new Promise<R>() {  //forward error in different Promise type
							@Override public Promise<R> produce() {
								this.failure(self.error);
								return this;
							}
						};
						return ret.produce();
					}
					else {
						throw new IllegalStateException();
					}
				}
			};
			return (Promise<R>) waitPromise;
		}
	}

	public Promise<T> thenDo(final Consumer<T> executor) {
		final Promise<T> self = this;
		//result of this promise already known? then apply executor
		if (state == Success) {
			executor.run(result);
			return this;
		}
		else if (state == Failure) {
			return this; //forward error
		}
		//otherwise, wait for result until going on
		else {
			waitPromise = new Promise<T>() {
				@Override public Promise<T> produce() {
					if (self.state == Success) {
						executor.run(self.result);
						return self;
					}
					else if (self.state == Failure) {
						return self; //forward error
					}
					else {
						throw new IllegalStateException();
					}
				}
			};
			return (Promise<T>) waitPromise;
		}
	}

	public Promise<T> whenFails(final Consumer<Exception> executor) {
		final Promise<T> self = this;
		//result of this promise already known? then apply executor
		if (state == Success) {
			return this; //forward success
		}
		else if (state == Failure) {
			executor.run(self.error);
			return this;
		}
		//otherwise, wait for result until going on
		else {
			waitPromise = new Promise<T>() {
				@Override public Promise<T> produce() {
					if (self.state == Success) {
						return self; //forward success
					} else if (self.state == Failure) {
						executor.run(self.error);
						return self;
					} else {
						throw new IllegalStateException();
					}
				}
			};
			return (Promise<T>) waitPromise;
		}
	}

	public void success(T result) {
		this.state = Success;
		this.result = result;
		if (waitPromise != null) {
			waitPromise.produce();
			waitPromise = null;
		}
	}

	public void failure(Exception error) {
		this.state = Failure;
		this.error = error;
		if (waitPromise != null) {
			waitPromise.produce();
			waitPromise = null;
		}

	}

}
