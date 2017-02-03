package com.xenoage.utils.promise;

/**
 * Utils for promises.
 */
public class Utils {

	/**
	 * Turns a {@link Consumer} into a {@link Function} that returns null.
	 */
	public static Function toFunction(final Consumer consumer) {
		return new Function() {
			@Override public Object run(Object value) {
				consumer.run(value);
				return null;
			}
		};
	}

}
