package com.xenoage.utils.lang;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.xenoage.utils.io.TestIO;
import com.xenoage.utils.lang.LanguageTest.TestVocabulary;


/**
 * Test cases for the {@link Lang} class.
 *
 * @author Andreas Wenger
 */
public class LangTest
{
	
	
	@Before public void setUp()
	{
		TestIO.initWithSharedDir();
	}
  
	
  @Test public void testCreateUnknownLanguage()
  {
		Lang.unregisterAllComponents();
    Lang.loadLanguage(Lang.defaultLangPath, "thiswillbeunknown", false);
    assertEquals("Unknown language: Must load English!", "en", Lang.getCurrentLanguageID());
  }
  
  
  /**
   * Tests if at least one language pack can be found.
   */
  @Test public void testLanguagePacks()
  	throws Exception
  {
    List<LanguageInfo> langs = LanguageInfo.getAvailableLanguages(Lang.defaultLangPath);
    assertTrue("There must be at least one language pack!", langs.size() > 0);
    for (int i = 0; i < langs.size(); i++)
    {
      Lang.loadLanguage(langs.get(i).getID());
      assertNotNull("Null result in language " + Lang.getCurrentLanguageID(),
        Lang.get(TestVocabulary.Anything));
    }
  }

  
  /**
   * Tests the get(String)-method.
   */
  @Test public void get1()
  {
    Lang.loadLanguage("data/test/lang", "testlang", false);
    assertEquals("This is a test vocabulary.", Lang.get(TestVocabulary.TestVocabulary_TestVoc));
    assertNull(Lang.getWithNull(TestVocabulary.NotExisting));
  }
  
  
  /**
   * Tests the get(String, String[])-method.
   */
  @Test public void get2()
  {
    Lang.loadLanguage("data/test/lang", "testlang", false);
    String[] tokens = new String[]{"stupid", "- haha", "crazy"};
    assertEquals("This (stupid) text has some crazy tokens in it - haha.",
      Lang.get(TestVocabulary.TestVocabulary_TestVoc2, tokens));
  }
  
  
  /**
   * Test the registering and implicit unregistering of {@link LanguageComponent}s.
   */
  @Test public void testLanguageComponents()
  {
  	//create and add components
  	Lang.unregisterAllComponents();
  	LanguageComponentMock c1 = new LanguageComponentMock();
  	LanguageComponentMock c2 = new LanguageComponentMock();
  	Lang.registerComponent(c1);
  	Lang.registerComponent(c2);
  	assertEquals(2, Lang.getLanguageComponentsCount());
  	//update 1
  	Lang.updateLanguageComponents();
  	assertEquals(1, c1.languageChangedCounter);
  	assertEquals(1, c1.languageChangedCounter);
  	//update 2
  	Lang.updateLanguageComponents();
  	assertEquals(2, c1.languageChangedCounter);
  	assertEquals(2, c1.languageChangedCounter);
  	//delete component 1
  	c1 = null;
  	System.gc(); //ok, this no guarantee... can we test it in another way?
  	assertEquals(1, Lang.getLanguageComponentsCount());
  	//update 3
  	Lang.updateLanguageComponents();
  	assertEquals(3, c2.languageChangedCounter);
  	//delete other component
  	Lang.unregisterAllComponents();
  	assertEquals(0, Lang.getLanguageComponentsCount());
  }
  
  
}
