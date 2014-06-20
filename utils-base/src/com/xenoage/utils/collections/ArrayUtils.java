package com.xenoage.utils.collections;

import static com.xenoage.utils.kernel.Range.range;

import java.util.Collection;
import java.util.List;

/**
 * Some useful functions for arrays.
 *
 * @author Andreas Wenger
 */
public class ArrayUtils {

	/**
	 * Converts the given {@link Collection} of <code>int</code>s
	 * into an <code>int</code> array.
	 * @param a  the collection, which may not contain null.
	 *           if null, an empty array is returned.
	 */
	public static int[] toIntArray(Collection<Integer> a) {
		if (a == null)
			return new int[0];
		int[] ret = new int[a.size()];
		int i = 0;
		for (int t : a) {
			ret[i] = t;
			i++;
		}
		return ret;
	}

	/**
	 * Converts the given {@link Collection} of <code>String</code>s
	 * into an <code>String</code> array.
	 * @param l  the collection, which may not contain null.
	 *           if null, an empty array is returned.
	 */
	public static String[] toStringArray(List<String> l) {
		if (l == null)
			return new String[0];
		String[] ret = new String[l.size()];
		int i = 0;
		for (String o : l) {
			ret[i] = o;
			i++;
		}
		return ret;
	}

	/**
	 * Creates a copy of the given <code>float</code> array.
	 */
	public static float[] copy(float[] a) {
		float[] ret = new float[a.length];
		for (int i = 0; i < a.length; i++)
			ret[i] = a[i];
		return ret;
	}

	/**
	 * Gets the index of the given element within the given array.
	 * @return  the index, or null if not found
	 */
	public static <T> int indexOf(T[] a, T e) {
		for (int i : range(a)) {
			if ((a[i] == null && e == null) || a[i].equals(e)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Returns the sum of the given <code>float</code> array.
	 */
	public static float sum(float[] a) {
		float ret = 0;
		for (int i = 0; i < a.length; i++)
			ret += a[i];
		return ret;
	}

	/**
	 * Returns true, if the given array contains the given object
	 * (same reference, not same contents).
	 */
	public static boolean containsRef(Object[] a, Object o) {
		for (Object e : a)
			if (e == o)
				return true;
		return false;
	}

	/**
	 * Returns true, if the given array contains a <code>null</code> element.
	 */
	public static boolean containsNull(Object[] a) {
		return containsRef(a, null);
	}

	/**
	 * Returns true, if the given array contains only <code>null</code> elements.
	 */
	public static <T> boolean containsOnlyNull(T[] c) {
		for (Object e : c)
			if (e != null)
				return false;
		return true;
	}

	/**
	 * Returns the entry with the lowest value.
	 */
	public static <T extends Comparable<T>> T min(T[] c) {
		T ret = null;
		if (c.length > 0) {
			ret = c[0];
			for (T t : c)
				if (t.compareTo(ret) < 0)
					ret = t;
		}
		return ret;
	}

	/**
	 * Returns the given array as a human-readable comma-separated String,
	 * e.g. <code>[0,1,2]</code> as <code>"0, 1, 2"</code>.
	 */
	public static <T> String toString(T[] c) {
		StringBuilder s = new StringBuilder();
		if (c.length > 0) {
			for (int i : range(0, c.length - 2))
				s.append(c[i] + ", ");
			s.append(c[c.length - 1]);
		}
		return s.toString();
	}

}
