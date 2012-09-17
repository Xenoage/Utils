package com.xenoage.utils.base;

import java.util.Collection;


/**
 * Checks the integrity of data structures.
 * 
 * @author Andreas Wenger
 */
public class CheckUtils
{
	
	/**
	 * Throws an {@link IllegalStateException} if the given object is null.
	 */
	public static void checkNotNull(Object o)
		throws IllegalStateException
	{
		if (o == null)
			throw new IllegalStateException();
	}
	
	
	/**
	 * Throws an {@link IllegalStateException} with the given message
	 * if the given object is null.
	 */
	public static void checkNotNull(Object o, String message)
		throws IllegalStateException
	{
		if (o == null)
			throw new IllegalStateException(message);
	}
	
	
	/**
	 * Throws an {@link IllegalStateException} if the given collection is null
	 * or contains null.
	 */
	public static void checkNotNullIn(Collection<?> c)
		throws IllegalStateException
	{
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
		throws IllegalStateException
	{
		if (c == null)
			throw new IllegalStateException(message);
		for (Object o : c)
			if (o == null)
				throw new IllegalStateException(message);
	}
	
	
	/**
	 * Throws an {@link IllegalStateException} if the given collection is null
	 * or is empty.
	 */
	public static void checkNotEmpty(Collection<?> c)
		throws IllegalStateException
	{
		if (c == null || c.size() == 0)
			throw new IllegalStateException();
	}
	
	
	/**
	 * Throws an {@link IllegalStateException} with the given message
	 * if the given collection is null or is empty.
	 */
	public static void checkNotEmpty(Collection<?> c, String message)
		throws IllegalStateException
	{
		if (c == null || c.size() == 0)
			throw new IllegalStateException(message);
	}

}
