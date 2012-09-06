package com.xenoage.utils.lang;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import com.xenoage.utils.io.TestIO;


/**
 * Test cases for the {@link Language} class.
 *
 * @author Andreas Wenger
 */
public class LanguageTest
{
	
	public enum TestVocabulary
		implements VocID
	{
		Anything, TestVocabulary_TestVoc, TestVocabulary_TestVoc2, NotExisting;
		
		@Override public String getProjectID()
		{
			return "testproject";
		}
		
		@Override public String getID()
		{
			return this.toString();
		}
	}
	
	
	@Before public void setUp()
	{
		TestIO.initWithSharedDir();
	}

  
  /**
   * Tests the get(String)-method.
   */
  @Test public void get1()
  	throws Exception
  {
    Language l = new Language("data/test/lang", "testlang");
    assertEquals("This is a test vocabulary.", l.get(TestVocabulary.TestVocabulary_TestVoc));
    assertNull(l.getWithNull(TestVocabulary.NotExisting));
  }
  
  
  /**
   * Tests the get(String, String[])-method.
   */
  @Test public void get2()
  	throws Exception
  {
  	Language l = new Language("data/test/lang", "testlang");
    String[] tokens = new String[]{"stupid", "- haha", "crazy"};
    assertEquals("This (stupid) text has some crazy tokens in it - haha.",
      l.get(TestVocabulary.TestVocabulary_TestVoc2, tokens));
  }
  
  
}
