package com.xenoage.utils.pdlib;

import static com.xenoage.utils.pdlib.It.it;
import static com.xenoage.utils.pdlib.ListIt.listIt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


/**
 * Immutable closeable vector.
 * 
 * This is just a wrapper around an {@link ArrayList}. At the beginning,
 * the vector is unclosed and can be written like a normal {@link ArrayList}.
 * After the {@link #close()} method is called, all calls to write methods
 * will throw an {@link IllegalStateException}.
 * 
 * An {@link IVector} is "branched" when it is created based on an existing {@link Vector}.
 * In this case it shares its array memory, until the first write
 * operation is performed. At this point, a full copy of the array memory is made.
 * Thus, if the vector is not modified later, branching the vector is very fast.
 * 
 * As long as the class is used as a {@link IVector}, there are no compiler
 * warnings for the write methods. As soon as it is used as a {@link Vector},
 * compiler warnings for the write methods will show up.
 * 
 * This class is a pragmatic combination of the idea of functional data structures
 * like {@link PVector} and an efficient implementation like {@link ArrayList}.
 * 
 * @author Andreas Wenger
 */
public final class IVector<T>
	implements Vector<T>
{

	private List<T> array;
	private boolean closed = false;
	private boolean sharedMemory = false; //true, when array is shared with another instance


	public IVector()
	{
		this(true);
	}


	private IVector(int initialCapacity)
	{
		array = new ArrayList<T>(initialCapacity);
	}


	private IVector(Collection<T> c)
	{
		array = new ArrayList<T>(c);
	}
	
	
	private IVector(boolean init)
	{
		if (init)
			array = new ArrayList<T>();
		else
			array = null;
	}


	public static <T2> IVector<T2> ivec()
	{
		return new IVector<T2>();
	}


	public static <T2> IVector<T2> ivec(int initialCapacity)
	{
		return new IVector<T2>(initialCapacity);
	}
	
	
	/**
	 * Creates a new {@link IVector} based on the given {@link Vector} as a branch.
	 * This means, that the new vector shares the data of the given vector instance.
	 * The memory is shared until the new vector receives the first write operation.
	 */
	@SuppressWarnings("unchecked") public static <T2> IVector<T2> ivec(Vector<T2> c)
	{
		IVector<T2> ret = new IVector<T2>(false);
		ret.sharedMemory = true;
		if (c instanceof IVector)
			ret.array = ((IVector)c).array; //avoid a stack of redirections. use array directly
		else
			ret.array = c; //no choice, we must use the public interface
		return ret;
	}


	public static <T2> IVector<T2> ivec(Collection<T2> c)
	{
		return new IVector<T2>(c);
	}


	@SafeVarargs public static <T2> IVector<T2> ivec(T2... data)
	{
		ArrayList<T2> array = new ArrayList<T2>(data.length);
		for (T2 o : data)
			array.add(o);
		return new IVector<T2>(array);
	}


	public static <T> IVector<T> ivecInit(T valueForAll, int size)
	{
		IVector<T> ret = new IVector<T>(size);
		for (int i = 0; i < size; i++)
			ret.add(valueForAll);
		return ret;
	}
	

	public static <T2> Vector<T2> vec(Collection<T2> data)
	{
		return ivec(data).close();
	}


	@SafeVarargs public static <T2> Vector<T2> vec(T2... data)
	{
		return ivec(data).close();
	}


	/**
	 * Closes the vector. All future calls to write methods will fail.
	 * Returns this vector for convenience.
	 */
	public IVector<T> close()
	{
		closed = true;
		return this;
	}


	private void requestWrite()
	{
		//if closed, further write operations are forbidded
		if (closed)
			throw new IllegalStateException("vector is closed");
		//if shared memory is used, create full copy instead
		if (sharedMemory) {
			array = new ArrayList<T>(array);
			sharedMemory = false;
		}
	}


	@Override public T getFirst()
	{
		return array.get(0);
	}


	@Override public T getLast()
	{
		return array.get(array.size() - 1);
	}


	@Override public boolean add(T e)
	{
		requestWrite();
		array.add(e);
		return true;
	}


	@Override public void add(int index, T element)
	{
		requestWrite();
		array.add(index, element);
	}


	@Override public boolean addAll(Collection<? extends T> c)
	{
		requestWrite();
		return array.addAll(c);
	}


	@Override public boolean addAll(int index, Collection<? extends T> c)
	{
		requestWrite();
		return array.addAll(index, c);
	}


	@Override public void clear()
	{
		requestWrite();
		array.clear();
	}


	@Override public boolean contains(Object o)
	{
		return array.contains(o);
	}


	@Override public boolean containsAll(Collection<?> c)
	{
		return array.containsAll(c);
	}


	@Override public T get(int index)
	{
		return array.get(index);
	}


	@Override public int indexOf(Object o)
	{
		return array.indexOf(o);
	}


	@Override public boolean isEmpty()
	{
		return array.isEmpty();
	}


	@Override public Iterator<T> iterator()
	{
		if (closed)
			return it(array);
		else
			return array.iterator();
	}


	@Override public int lastIndexOf(Object o)
	{
		return array.lastIndexOf(o);
	}


	@Override public ListIterator<T> listIterator()
	{
		if (closed)
			return listIt(array.listIterator());
		else
			return array.listIterator();
	}


	@Override public ListIterator<T> listIterator(int index)
	{
		throw new UnsupportedOperationException();
	}


	@Override public boolean remove(Object o)
	{
		requestWrite();
		return array.remove(o);
	}


	@Override public T remove(int index)
	{
		requestWrite();
		return array.remove(index);
	}


	@Override public boolean removeAll(Collection<?> c)
	{
		requestWrite();
		return array.retainAll(c);
	}


	@Override public boolean retainAll(Collection<?> c)
	{
		requestWrite();
		return array.retainAll(c);
	}


	@Override public T set(int index, T element)
	{
		requestWrite();
		return array.set(index, element);
	}


	@Override public int size()
	{
		return array.size();
	}


	@Override public Vector<T> subList(int fromIndex, int toIndex)
	{
		return ivec(array.subList(fromIndex, toIndex)).close();
	}


	@Override public Object[] toArray()
	{
		return array.toArray();
	}


	@Override public <T2> T2[] toArray(T2[] a)
	{
		return array.toArray(a);
	}


	/**
	 * Returns true, if the given collection has the same values as this one,
	 * otherwise false.
	 */
	@Override public boolean equals(Object o)
	{
		return array.equals(o);
	}


	@Override public int hashCode()
	{
		return array.hashCode();
	}


	@Override public String toString()
	{
		return "{size:" + array.size() + ", data:" + array.toString() + "}";
	}


}
