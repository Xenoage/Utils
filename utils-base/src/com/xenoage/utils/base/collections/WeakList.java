package com.xenoage.utils.base.collections;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedList;


/**
 * A list of weakly referenced objects (by {@link WeakReference}.
 * 
 * Each time the list is queried or when an object is added or removed,
 * the list is cleaned up, i.e. null references are removed.
 *
 * This class is thread-safe.
 *
 * @author Andreas Wenger
 */
public class WeakList<T>
{
	
	private LinkedList<WeakReference<T>> list = new LinkedList<WeakReference<T>>();
	
	
	/**
	 * Gets a copy of the elements in the list.
	 * The returned list does not contain null elements.
	 */
	public synchronized ArrayList<T> getAll()
	{
		clean();
		ArrayList<T> ret = new ArrayList<T>();
		for (WeakReference<T> e : list) {
			T t = e.get();
			if (t != null)
				ret.add(t);
		}
		return ret;
	}
	
	
	/**
	 * Adds the given element, if it is not null and if it is not already
	 * in the list.
	 */
	public synchronized void add(T element)
	{
		clean();
		if (element == null)
			return;
		for (WeakReference<T> e : list)
			if (e.get().equals(element))
				return;
		list.add(new WeakReference<T>(element));
	}
	
	
	/**
	 * Removes the given element from the list.
	 */
	public synchronized void remove(T element)
	{
		clean();
		if (element == null)
			return;
		for (WeakReference<T> e : list) {
			if (e.get().equals(element)) {
				list.remove(e);
				return;
			}
		}
	}
	
	
	/**
	 * Removes all elements from the list.
	 */
	public synchronized void clear()
	{
		list.clear();
	}
	
	
	private synchronized void clean()
	{
		for (WeakReference<T> e : list) {
			if (e.get() == null)
				list.remove(e);
		}
	}
	

}
