package com.xenoage.utils.async;

/**
 * Encapsulated promise executor, providing a
 * resolve method.
 *
 * @author Andreas Wenger
 */
public interface PromiseFn<T> {

	void run(Consumer<T> resolve);

}
