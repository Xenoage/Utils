package com.xenoage.utils;

import java.util.Collection;

/**
 * Checks the integrity of data structures.
 * 
 * @author Andreas Wenger
 */
public class CheckUtils {

	/**
	 * Throws an {@link IllegalStateException} if the given object is null.
	 */
	public static void checkNotNull(Object o)
		throws IllegalStateException {
		if (o == null)
			throw new IllegalStateException();
	}

	/**
	 * Throws an {@link IllegalStateException} with the given message
	 * if the given object is null.
	 */
	public static void checkNotNull(Object o, String message)
		throws IllegalStateException {
		if (o == null)
			throw new IllegalStateException(message);
	}

	/**
	 * Throws an {@link IllegalStateException} if the given collection is null
	 * or contains null.
	 */
	public static void checkNotNullIn(Collection<?> c)
		throws IllegalStateException {
		if (c == null)
			throw new IllegalStateException();
		for (Object o : c)
			if (o == null)
				throw new IllegalStateException();
	}

	/**
	 * Throws an {@link IllegalStateException} with the given message
	 * if the given collection is null or contains null.
	 */
	public static void checkNotNullIn(Collection<?> c, String message)
		throws IllegalStateException {
		if (c == null)
			throw new IllegalStateException(message);
		for (Object o : c)
			if (o == null)
				throw new IllegalStateException(message);
	}
	
	/**
	 * Throws an {@link IllegalArgumentException} if one of the given arguments is null.
	 */
	public static void checkArgsNotNull(Object... o) {
		for (int i = 0; i < o.length; i++) {
			if (o[i] == null) {
				throw new IllegalArgumentException(
					"Argument may not be null (checked argument with index " + i + ")");
			}
		}
	}

	/**
	 * Throws an {@link IllegalStateException} if the given collection is null
	 * or is empty.
	 */
	public static void checkNotEmpty(Collection<?> c)
		throws IllegalStateException {
		if (c == null || c.size() == 0)
			throw new IllegalStateException();
	}

	/**
	 * Throws an {@link IllegalStateException} with the given message
	 * if the given collection is null or is empty.
	 */
	public static void checkNotEmpty(Collection<?> c, String message)
		throws IllegalStateException {
		if (c == null || c.size() == 0)
			throw new IllegalStateException(message);
	}

}
