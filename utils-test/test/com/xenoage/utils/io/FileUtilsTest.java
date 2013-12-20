package com.xenoage.utils.io;

import static org.junit.Assert.*;

import org.junit.Test;

import com.xenoage.utils.kernel.Tuple2;

/**
 * Test cases for the {@link FileUtils} class.
 *
 * @author Andreas Wenger
 */
public class FileUtilsTest {

	@Test public void splitDirectoryAndFilename() {
		//test "1/2/3.pdf"
		String s = "1/2/3.pdf";
		Tuple2<String, String> res = FileUtils.splitDirectoryAndFilename(s);
		assertEquals("1/2", res.get1());
		assertEquals("3.pdf", res.get2());
		//test "4.xml"
		s = "4.xml";
		res = FileUtils.splitDirectoryAndFilename(s);
		assertEquals("", res.get1());
		assertEquals("4.xml", res.get2());
	}

}
