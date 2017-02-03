package com.xenoage.utils.async;

/**
 * A promise executor can either end by success or by failure.
 * This interface provides methods for both possibilities.
 *
 * @author Andreas Wenger
 */
public interface Return<T> {

	void resolve(T result);

	void reject(Exception error);

}
