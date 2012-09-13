package com.xenoage.utils.pdlib;


/**
 * Interface for immutable maps.
 * 
 * Deprecated annotations are used to warn the programmer of calling
 * unsupported methods.
 * 
 * @author Andreas Wenger
 */
public interface Map<K, V>
	extends java.util.Map<K, V>
{

	@Deprecated @Override public V put(K key, V value);


	@Deprecated @Override public V remove(Object key);


	@Deprecated @Override public void putAll(java.util.Map<? extends K, ? extends V> m);


	@Deprecated @Override public void clear();

}
