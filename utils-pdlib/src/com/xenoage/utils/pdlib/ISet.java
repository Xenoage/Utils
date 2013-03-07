package com.xenoage.utils.pdlib;

import static com.xenoage.utils.pdlib.It.it;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;


/**
 * Immutable closeable set.
 * 
 * This is just a wrapper around an {@link HashSet}. At the beginning,
 * the set is unclosed and can be written like a normal {@link HashSet}.
 * After the {@link #close()} method is called, all calls to write methods
 * will throw an {@link IllegalStateException}.
 * 
 * An {@link ISet} is "branched" when it is created based on an existing pdlib {@link Set}.
 * In this case it shares its data memory, until the first write
 * operation is performed. At this point, a full copy of the data memory is made.
 * Thus, if the set is not modified later, branching the set is very fast.
 * 
 * As long as the class is used as an {@link ISet}, there are no compiler
 * warnings for the write methods. As soon as it is used as a {@link Set},
 * compiler warnings for the write methods will show up.
 * 
 * This class is a pragmatic combination of the idea of functional data structures
 * like {@link PSet} and an efficient implementation like {@link HashSet}.
 * 
 * @author Andreas Wenger
 */
public class ISet<T>
	implements Set<T>
{
	
	private java.util.Set<T> set;
	private boolean closed = false;
	private boolean sharedMemory = false; //true, when set is shared with another instance


	/**
	 * Creates an empty and unclosed {@link ISet}.
	 */
	public ISet()
	{
		this(true);
	}
	
	
	/**
	 * Creates an empty and unclosed {@link ISet} with the
	 * given initial capacity.
	 */
	public ISet(int initialCapacity)
	{
		set = new HashSet<T>(initialCapacity);
	}
	
	
	/**
	 * Creates an unclosed {@link ISet} based on the given mutable
	 * mutableCollection. A shallow copy of the given collection is created.
	 */
	public ISet(Collection<T> mutableCollection)
	{
		set = new HashSet<T>(mutableCollection);
	}


	private ISet(boolean init)
	{
		if (init)
			set = new HashSet<T>();
		else
			set = null;
	}


	/**
	 * Creates an empty and unclosed {@link ISet}.
	 */
	public static <T2> ISet<T2> iset()
	{
		return new ISet<T2>();
	}


	/**
	 * Creates an empty and unclosed {@link ISet} with the
	 * given initial capacity.
	 */
	public static <T2> ISet<T2> iset(int initialCapacity)
	{
		return new ISet<T2>(initialCapacity);
	}
	
	
	/**
	 * Creates an unclosed {@link ISet} based on the given mutable
	 * collection. A shallow copy of the given collection is created.
	 */
	public static <T2> ISet<T2> iset(Collection<T2> mutableCollection)
	{
		return new ISet<T2>(mutableCollection);
	}
	
	
	/**
	 * Creates an unclosed {@link ISet} based on the given {@link Set} as a branch.
	 * This means, that the new set shares the data of the given set instance.
	 * The memory is shared until the new set receives the first write operation.
	 */
	public static <T2> ISet<T2> ivec(ISet<T2> s)
	{
		ISet<T2> ret = new ISet<T2>(false);
		ret.sharedMemory = true;
		if (s instanceof ISet)
			ret.set = ((ISet<T2>)s).set; //avoid a stack of redirections. use set directly
		else
			ret.set = s; //no choice, we must use the public interface
		return ret;
	}


	/**
	 * Creates an unclosed {@link ISet} with the given items.
	 */
	@SafeVarargs public static <T2> ISet<T2> ivec(T2... data)
	{
		HashSet<T2> set = new HashSet<T2>(data.length);
		for (T2 o : data)
			set.add(o);
		return new ISet<T2>(set);
	}


	/**
	 * Closes the vector. All future calls to write methods will fail.
	 * Returns this vector for convenience.
	 */
	public ISet<T> close()
	{
		closed = true;
		return this;
	}


	private void requestWrite()
	{
		//if closed, further write operations are forbidden
		if (closed)
			throw new IllegalStateException("set is closed");
		//if shared memory is used, create full copy instead
		if (sharedMemory) {
			set = new HashSet<T>(set);
			sharedMemory = false;
		}
	}


	@Override public int size()
	{
		return set.size();
	}


	@Override public boolean isEmpty()
	{
		return set.isEmpty();
	}


	@Override public boolean contains(Object o)
	{
		return set.contains(o);
	}


	@Override public Iterator<T> iterator()
	{
		if (closed)
			return it(set);
		else
			return set.iterator();
	}


	@Override public Object[] toArray()
	{
		return set.toArray();
	}


	@Override public <T2> T2[] toArray(T2[] a)
	{
		return set.toArray(a);
	}


	@Override public boolean containsAll(Collection<?> c)
	{
		return set.containsAll(c);
	}


	@Override public boolean add(T e)
	{
		requestWrite();
		return set.add(e);
	}


	@Override public boolean remove(Object o)
	{
		requestWrite();
		return set.remove(o);
	}


	@Override public boolean addAll(Collection<? extends T> c)
	{
		requestWrite();
		return set.addAll(c);
	}


	@Override public boolean retainAll(Collection<?> c)
	{
		requestWrite();
		return set.retainAll(c);
	}


	@Override public boolean removeAll(Collection<?> c)
	{
		requestWrite();
		return set.removeAll(c);
	}


	@Override public void clear()
	{
		requestWrite();
		set.clear();
	}

}
