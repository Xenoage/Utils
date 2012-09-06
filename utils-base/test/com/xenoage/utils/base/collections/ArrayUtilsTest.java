package com.xenoage.utils.base.collections;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import com.xenoage.utils.base.collections.ArrayUtils;




/**
 * Test cases for the ArrayTools class.
 *
 * @author Andreas Wenger
 */
public class ArrayUtilsTest
{
	
	@Test public void copyTest()
	{
		float[] a1 = new float[1000];
		for (int i = 0; i < a1.length; i++)
			a1[i] = i;
		float[] a2 = ArrayUtils.copy(a1);
		assertArrayEquals(a1, a2, 0);
	}
	

  @Test public void convertToArrayTest()
  {
    ArrayList<Integer> al;
    int[] a;
    //null array
    al = null;
    a = ArrayUtils.toIntArray(al);
    assertNotNull(a);
    assertEquals(0, a.length);
    //empty array
    al = new ArrayList<Integer>();
    a = ArrayUtils.toIntArray(al);
    assertEquals(0, a.length);
    //filled array
    int arraySize = 100;
    for (int i = 0; i < arraySize; i++)
      al.add(i);
    a = ArrayUtils.toIntArray(al);
    for (int i = 0; i < arraySize; i++)
      assertEquals(i, a[i]);
  }
  
}
