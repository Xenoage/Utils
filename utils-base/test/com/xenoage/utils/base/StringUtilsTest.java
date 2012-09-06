package com.xenoage.utils.base;

import static com.xenoage.utils.base.StringUtils.concatenate;
import static com.xenoage.utils.base.StringUtils.trimRight;
import static com.xenoage.utils.base.collections.CollectionUtils.alist;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;


/**
 * Test cases for a {@link StringUtils} class.
 *
 * @author Andreas Wenger
 */
public class StringUtilsTest
{

  
  @Test public void trimRightTest()
  {
  	assertEquals("abc", trimRight("abc"));
    assertEquals("abc", trimRight("abc "));
    assertEquals("abc", trimRight("abc    "));
    assertEquals("a b c", trimRight("a b c"));
    assertEquals("a  b c", trimRight("a  b c    "));
  }
  
  
  @Test public void concatenateTest()
  {
  	String separator = ":";
  	assertEquals("", concatenate(new ArrayList<String>(), separator));
  	assertEquals("1", concatenate(alist("1"), separator));
  	assertEquals("1:2", concatenate(alist("1", "2"), separator));
  	assertEquals("1:2:3", concatenate(alist("1", "2", "3"), separator));
  }
  
}
