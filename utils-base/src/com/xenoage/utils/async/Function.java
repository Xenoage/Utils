package com.xenoage.utils.async;

/**
 * TODO: Experimental.
 *
 * @author Andreas Wenger
 */
public interface Function<I, O> {

	O run(I value);

}
