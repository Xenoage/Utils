package com.xenoage.utils.pdlib;

import java.util.Collection;


/**
 * Interface for immutable sets.
 * 
 * Deprecated annotations are used to warn the programmer of calling
 * unsupported methods.
 * 
 * @author Andreas Wenger
 */
public interface Set<E>
	extends java.util.Set<E>
{


	@Deprecated @Override public boolean add(E e);


	@Deprecated @Override public boolean remove(Object o);


	@Deprecated @Override public boolean addAll(Collection<? extends E> c);


	@Deprecated @Override public boolean retainAll(Collection<?> c);


	@Deprecated @Override public boolean removeAll(Collection<?> c);


	@Deprecated @Override public void clear();

}
