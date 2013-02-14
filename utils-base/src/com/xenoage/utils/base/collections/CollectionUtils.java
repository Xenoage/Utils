package com.xenoage.utils.base.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;


/**
 * Useful methods for working with collections.
 * 
 * @author Andreas Wenger
 */
public final class CollectionUtils
{
	
	
	/**
	 * Creates a new empty {@link ArrayList} with the inferred type.
	 */
	public static <T> ArrayList<T> alist()
	{
		return new ArrayList<T>();
	}
	
	
	/**
	 * Creates a new empty {@link ArrayList} with the inferred type
	 * using the given capacity.
	 */
	public static <T> ArrayList<T> alist(int initialCapacity)
	{
		return new ArrayList<T>(initialCapacity);
	}
	
	
	@SafeVarargs public static <T> ArrayList<T> alist(T... vals)
	{
		ArrayList<T> ret = new ArrayList<T>();
		for (T v : vals)
			ret.add(v);
		return ret;
	}
	
	
	public static <T> ArrayList<T> alistInit(T value, int size)
	{
		ArrayList<T> ret = new ArrayList<T>(size);
		for (int i = 0; i < size; i++)
			ret.add(value);
		return ret;
	}
	
	
	/**
	 * Creates a new empty {@link HashMap} with the inferred type.
	 */
	public static <T1, T2> HashMap<T1, T2> map()
	{
		return new HashMap<T1, T2>();
	}
	
	
	/**
	 * Creates a new empty {@link LinkedList} with the inferred type.
	 */
	public static <T> LinkedList<T> llist()
	{
		return new LinkedList<T>();
	}
	
	
	@SafeVarargs public static <T> LinkedList<T> llist(T... vals)
	{
		LinkedList<T> ret = new LinkedList<T>();
		for (T v : vals)
			ret.add(v);
		return ret;
	}
	
	
	@SafeVarargs public static <T> Set<T> set(T... vals)
	{
		HashSet<T> ret = new HashSet<T>();
		for (T v : vals)
			ret.add(v);
		return ret;
	}
	
	
	/**
	 * Returns the first maximum value of the given collection.
	 * If the collection is empty, null is returned.
	 */
	public static <T extends Comparable<T>> T getMax(Collection<T> vals)
	{
		return getExtremum(vals, 1);
	}
	
	
	/**
	 * Returns the first minimum value of the given collection.
	 * If the collection is empty, null is returned.
	 */
	public static <T extends Comparable<T>> T getMin(Collection<T> vals)
	{
		return getExtremum(vals, -1);
	}
	
	
	private static <T extends Comparable<T>> T getExtremum(Collection<T> vals, int comparator)
	{
		if (vals.size() == 0)
			return null;
		Iterator<T> it = vals.iterator();
		T ret = it.next();
		while (it.hasNext())
		{
			T v = it.next();
			if (v.compareTo(ret) * comparator > 0)
				ret = v;
		}
		return ret;
	}
	
	
	/**
   * Returns true, if the given collection contains the given object
   * (same reference, not same contents).
   */
  public static boolean contains(Collection<?> c, Object o)
  {
    for (Object e : c)
      if (e == o) return true;
    return false;
  }
  
  
  /**
   * Returns true, if the given collection contains a <code>null</code> element.
   */
  public static boolean containsNull(Collection<?> c)
  {
    return contains(c, null);
  }

}
