package com.xenoage.utils.collections;

import static com.xenoage.utils.collections.CollectionUtils.map;

import java.util.Map;

import com.xenoage.utils.annotations.MaybeNull;
import com.xenoage.utils.annotations.NonNull;

/**
 * A bidirectional map, with constant lookup time in both directions.
 * A default value for non-existent entries may be provided, otherwise it is null.
 * 
 * @author Andreas Wenger
 */
public class BiMap<T1, T2> {
	
	private Map<T1, T2> map1 = map();
	private Map<T2, T1> map2 = map();
	private T1 defaultValue1 = null;
	private T2 defaultValue2 = null;
	
	public static <T1, T2> BiMap<T1, T2> biMap() {
		return new BiMap<T1, T2>();
	}
	
	public int size() {
		return map1.size();
	}

	public boolean isEmpty() {
		return map1.isEmpty();
	}
	
	@MaybeNull public T1 get1(@MaybeNull T2 value2) {
		if (value2 == null)
			return defaultValue1;
		T1 ret = map2.get(value2);
		return (ret != null ? ret : defaultValue1);
	}

	@MaybeNull public T2 get2(@MaybeNull T1 value1) {
		if (value1 == null)
			return defaultValue2;
		T2 ret = map1.get(value1);
		return (ret != null ? ret : defaultValue2);
	}

	public void put(@NonNull T1 value1, @NonNull T2 value2) {
		removeOldValues(value1, value2);
		map1.put(value1, value2);
		map2.put(value2, value1);
	}

	private void removeOldValues(T1 value1, T2 value2) {
		T1 oldValue1 = map2.get(value2);
		if (oldValue1 != null)
			map1.remove(oldValue1);
		T2 oldValue2 = map1.get(value1);
		if (oldValue2 != null)
			map2.remove(oldValue2);
	}
	
	public T1 getDefaultValue1() {
		return defaultValue1;
	}

	public void setDefaultValue1(T1 defaultValue1) {
		this.defaultValue1 = defaultValue1;
	}

	public T2 getDefaultValue2() {
		return defaultValue2;
	}

	public void setDefaultValue2(T2 defaultValue2) {
		this.defaultValue2 = defaultValue2;
	}

}
