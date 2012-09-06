package com.xenoage.utils.base.zip;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.xenoage.utils.base.zip.ZipUtils;
import com.xenoage.utils.io.FileUtils;


/**
 * Test cases for the {@link ZipUtils} class.
 * 
 * @author Andreas Wenger
 */
public class ZipUtilsTest
{
	
	File dir = new File(FileUtils.getTempFolder(), getClass().getName() + ".extractAllTest");
	
	
	@Before public void setUp()
	{
		FileUtils.deleteDirectory(dir);
		dir.mkdir();
	}
	
	
	@Test public void extractAllTest()
		throws IOException
	{
		ZipUtils.extractAll(new FileInputStream("data/test/zip/album.zip"), dir);
		assertTrue(new File(dir, "META-INF/container.xml").exists());
		assertTrue(new File(dir, "BeetAnGeSample.xml").exists());
		assertTrue(new File(dir, "BrahWiMeSample.mxl").exists());
		assertTrue(new File(dir, "SchbAvMaSample.xml").exists());
	}
	
	
	@After public void cleanUp()
	{
		FileUtils.deleteDirectory(dir);
	}

}
