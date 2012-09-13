package com.xenoage.utils.graphics.font;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.xenoage.utils.base.settings.Settings;
import com.xenoage.utils.io.IO;


/**
 * Test cases for a {@link FontReplacements} class.
 *
 * @author Andreas Wenger
 */
public class FontReplacementsTest
{
	
	private Settings settings;
	
	
	@Before public void setUp()
	{
		IO.initTest();
		settings = new Settings("data/test/config/");
	}
	
	
	@Test public void testGetReplacement()
	{
		FontReplacements fr = new FontReplacements(settings);
		//"Times New Roman" is replaced by "Linux Libertine"
		assertEquals("Linux Libertine", fr.getReplacement("Times New Roman"));
		//"No Way, This is No Font" is not replaced
		assertEquals("No Way, This is No Font", fr.getReplacement("No Way, This is No Font"));
	}

}
