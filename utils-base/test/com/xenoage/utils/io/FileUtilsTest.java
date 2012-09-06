package com.xenoage.utils.io;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import com.xenoage.utils.io.FileUtils;
import com.xenoage.utils.kernel.Tuple2;


/**
 * Test cases for the {@link FileUtils} class.
 *
 * @author Andreas Wenger
 */
public class FileUtilsTest
{

  @Test public void getNameWithoutExt()
  {
    //gets the filename without extension of this file
    assertEquals("FileToolsTest",
      FileUtils.getNameWithoutExt(new File(
        "test/com.xenoage.zong/util/FileToolsTest.java")));
  }
  
  
  @Test public void splitDirectoryAndFilename()
  {
  	//test "1/2/3.pdf"
  	String s = "1" + File.separator + "2" + File.separator + "3.pdf";
  	Tuple2<String, String> res = FileUtils.splitDirectoryAndFilename(s);
  	assertEquals("1" + File.separator + "2", res.get1());
  	assertEquals("3.pdf", res.get2());
  	//test "4.xml"
  	s = "4.xml";
  	res = FileUtils.splitDirectoryAndFilename(s);
  	assertEquals("", res.get1());
  	assertEquals("4.xml", res.get2());
  }

  
}