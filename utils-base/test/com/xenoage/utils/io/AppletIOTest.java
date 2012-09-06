package com.xenoage.utils.io;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.xenoage.utils.io.AppletIO;
import com.xenoage.utils.io.FileUtils;


/**
 * Test cases for the AppletIO class.
 *
 * @author Andreas Wenger
 */
public class AppletIOTest
{
  
  AppletIO io;
  HashSet<String> dataFiles;
  
  
  @Before public void setUp()
  	throws IOException
  {
    dataFiles = new HashSet<String>();
    dataFiles.add("hallo.txt");
    dataFiles.add("dir1/hallo.xml");
    dataFiles.add("dir1/ordner/");
    dataFiles.add("dir1/buffer.png");
    dataFiles.add("dir 1/noch ne datei");
    dataFiles.add("dir1/dir2/hallo.txt");
    io = new AppletIO(new URL("http://localhost"), dataFiles);
  }
  
  
  @Test public void existsDataFile()
  {
    for (String dataFile : dataFiles)
    {
      assertTrue(io.existsDataFile(dataFile));
    }
    assertFalse(io.existsDataFile("dir1/hallo.Txt"));
    assertFalse(io.existsDataFile(""));
    assertFalse(io.existsDataFile("dir 1/noch ne datei.png"));
    assertFalse(io.existsDataFile("dir1/dir2/oje"));
  }
  
  
  @Test public void listDataFiles()
  {
    try
    {
      //empty folder
      Set<String> files = io.listDataFiles("dir1/ordner/");
      assertEquals(0, files.size());
      //folder with 2 files
      files = io.listDataFiles("dir1");
      assertEquals(2, files.size());
      assertTrue(files.contains("hallo.xml"));
      assertTrue(files.contains("buffer.png"));
      //folder with 2 files, filtered
      files = io.listDataFiles("dir1/", FileUtils.getXMLFilter());
      assertEquals(1, files.size());
      assertTrue(files.contains("hallo.xml"));
    }
    catch (Exception ex)
    {
      fail();
    }
  }

}
