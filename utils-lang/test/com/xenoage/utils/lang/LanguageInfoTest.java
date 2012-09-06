package com.xenoage.utils.lang;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.xenoage.utils.io.IO;
import com.xenoage.utils.io.TestIO;


/**
 * Test cases for a {@link LanguageInfo}.
 *
 * @author Andreas Wenger
 */
public class LanguageInfoTest
{

	private List<LanguageInfo> langs;


	@Before public void setUp()
		throws Exception
	{
		TestIO.initWithSharedDir();
		langs = LanguageInfo.getAvailableLanguages(Lang.defaultLangPath);
		assertTrue("There must be at least one language pack!", langs.size() > 0);
	}


	@Test public void getAvailableLanguages()
		throws IOException
	{
		//all folders from language pack folder must be returned
		Set<String> folders = IO.listDataDirectories(Lang.defaultLangPath);
		assertEquals(folders.size(), langs.size());
		for (String folder : folders) {
			boolean found = false;
			for (LanguageInfo lang : langs) {
				if (lang.getID().equals(folder)) {
					found = true;
					break;
				}
			}
			assertTrue(found);
		}
	}


	@Test public void getLangInformation()
	{
		String errorMsg = "international name must be set";
		for (LanguageInfo lang : langs) {
			assertNotNull(errorMsg, lang.getInternationalName());
		}
	}


	@Test public void getFlag16()
	{
		String errorMsg = "flag16 must be set, because there is a flag16.png";
		for (LanguageInfo lang : langs) {
			if (new File(Lang.defaultLangPath + "/" + lang.getID() + "/flag16.png").exists())
				assertNotNull(errorMsg, lang.getFlag16());
		}
	}

}
