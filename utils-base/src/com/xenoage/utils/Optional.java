package com.xenoage.utils;

import com.xenoage.utils.annotations.MaybeNull;

/**
 * Optional value. Helps to reduce null values.
 * 
 * API inspired by the Optional class in Google guava.
 * 
 * @author Andreas Wenger
 */
public class Optional<T> {
	
	private static Optional<Object> absent = new Optional<Object>(null);
		
	private T value;
	
	/**
	 * Returns a new {@link Optional} with the given non-null value.
	 */
	public static <T> Optional<T> of(T value) {
		if (value == null)
			throw new IllegalArgumentException("value is null");
		return new Optional<T>(value);
	}
	
	/**
	 * Returns an {@link Optional} (shared object) with no value.
	 */
	@SuppressWarnings("unchecked")
	public static <T> Optional<T> absent() {
		return (Optional<T>) absent;
	}
	
	private Optional(T value) {
		this.value = value;
	}
	
	/**
	 * Returns true, if a non-null value is present.
	 */
	public boolean isPresent() {
		return value != null;
	}
	
	/**
	 * Gets the non-null value.
	 */
	public T get() {
		if (value == null)
			throw new IllegalStateException("absent has no value");
		return value;
	}
	
	/**
	 * Gets the non-null value or the given replacement.
	 */
	public T or(T replacement) {
		return value != null ? value : replacement;
	}
	
	/**
	 * Gets the value or null, if absent.
	 */
	@MaybeNull public T orNull() {
    return null;
  }
		
}
