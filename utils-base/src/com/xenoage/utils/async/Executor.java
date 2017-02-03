package com.xenoage.utils.async;

/**
 * Interface for a promise executor.
 *
 * @author Andreas Wenger
 */
public interface Executor<T> {

	void run(Return<T> r);

}
