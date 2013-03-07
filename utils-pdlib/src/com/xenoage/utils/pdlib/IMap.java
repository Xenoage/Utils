package com.xenoage.utils.pdlib;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;


/**
 * Immutable closeable map.
 * 
 * This is just a wrapper around an {@link HashMap}. At the beginning,
 * the map is unclosed and can be written like a normal {@link HashMap}.
 * After the {@link #close()} method is called, all calls to write methods
 * will throw an {@link IllegalStateException}.
 * 
 * An {@link IMap} is "branched" when it is created based on an existing pdlib {@link Map}.
 * In this case it shares its data memory, until the first write
 * operation is performed. At this point, a full copy of the data memory is made.
 * Thus, if the map is not modified later, branching the map is very fast.
 * 
 * As long as the class is used as an {@link IMap}, there are no compiler
 * warnings for the write methods. As soon as it is used as a {@link Map},
 * compiler warnings for the write methods will show up.
 * 
 * This class is a pragmatic combination of the idea of functional data structures
 * like {@link PMap} and an efficient implementation like {@link HashMap}.
 * 
 * @author Andreas Wenger
 */
public final class IMap<K, V>
	implements Map<K, V>
{

	private java.util.Map<K, V> map;
	private boolean closed = false;
	private boolean sharedMemory = false; //true, when map is shared with another instance
	
	
	/**
	 * Creates an empty and unclosed {@link IMap}.
	 */
	public IMap()
	{
		this(true);
	}


	/**
	 * Creates an unclosed {@link IMap} based on the given mutable
	 * map. A shallow copy of the given map is created.
	 */
	public IMap(java.util.Map<K, V> mutableMap)
	{
		map = new HashMap<K, V>(mutableMap);
	}
	
	
	private IMap(boolean init)
	{
		if (init)
			map = new HashMap<K, V>();
		else
			map = null;
	}


	/**
	 * Creates an empty and unclosed {@link IMap}.
	 */
	public static <K2, V2> IMap<K2, V2> imap()
	{
		return new IMap<K2, V2>();
	}
	
	
	/**
	 * Creates an unclosed {@link IMap} based on the given mutable
	 * map. A shallow copy of the given map is created.
	 */
	public static <K2, V2> IMap<K2, V2> imap(java.util.Map<K2, V2> mutableMap)
	{
		return new IMap<K2, V2>(mutableMap);
	}
	
	
	/**
	 * Creates a new {@link IMap} based on the given {@link Map} as a branch.
	 * This means, that the new map shares the data of the given map instance.
	 * The memory is shared until the new map receives the first write operation.
	 */
	public static <K2, V2> IMap<K2, V2> imap(Map<K2, V2> m)
	{
		IMap<K2, V2> ret = new IMap<K2, V2>(false);
		ret.sharedMemory = true;
		if (m instanceof Map)
			ret.map = ((IMap<K2, V2>)m).map; //avoid a stack of redirections. use map directly
		else
			ret.map = m; //no choice, we must use the public interface
		return ret;
	}
	
	
	/**
	 * Closes the map. All future calls to write methods will fail.
	 * Returns this map for convenience.
	 */
	public IMap<K, V> close()
	{
		closed = true;
		return this;
	}


	private void requestWrite()
	{
		//if closed, further write operations are forbidden
		if (closed)
			throw new IllegalStateException("map is closed");
		//if shared memory is used, create full copy instead
		if (sharedMemory) {
			map = new HashMap<K, V>(map);
			sharedMemory = false;
		}
	}
	
	
	@Override public int size()
	{
		return map.size();
	}
	
	
	@Override public boolean isEmpty()
	{
		return map.isEmpty();
	}
	
	
	@Override public boolean containsKey(Object key)
	{
		return map.containsKey(key);
	}
	
	
	@Override public boolean containsValue(Object value)
	{
		return map.containsValue(value);
	}
	
	
	@Override public V get(Object key)
	{
		return map.get(key);
	}
	
	
	@Override public Set<K> keySet()
	{
		return map.keySet();
	}
	
	
	@Override public Collection<V> values()
	{
		return map.values();
	}
	
	
	@Override public Set<java.util.Map.Entry<K, V>> entrySet()
	{
		return map.entrySet();
	}
	
	
	@Override public V put(K key, V value)
	{
		requestWrite();
		return map.put(key, value);
	}
	
	
	@Override public V remove(Object key)
	{
		requestWrite();
		return map.remove(key);
	}
	
	
	@Override public void putAll(java.util.Map<? extends K, ? extends V> m)
	{
		requestWrite();
		map.putAll(m);
	}
	
	
	@Override public void clear()
	{
		requestWrite();
		map.clear();
	}

}
