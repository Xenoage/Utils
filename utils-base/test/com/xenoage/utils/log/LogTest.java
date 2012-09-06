package com.xenoage.utils.log;

import static com.xenoage.utils.log.Log.log;
import static com.xenoage.utils.log.Report.remark;
import static com.xenoage.utils.log.Report.warning;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.xenoage.utils.io.FileUtils;
import com.xenoage.utils.io.IO;
import com.xenoage.utils.log.AppLogProcessing;
import com.xenoage.utils.log.Log;


/**
 * Test cases for a Log.
 *
 * @author Andreas Wenger
 */
public class LogTest
{
  
  private String logFilename;
  
  
  @Before public void setUp()
  {
  	IO.initTest();
    logFilename = "data/test/temp.log";
  }
  

  @Test public void testLogging()
  {
    IO.deleteDataFile(logFilename, true);
    
    //create log file
    Log.init(new AppLogProcessing(logFilename, "test"));
    String message = "This is a message";
    log(remark(message));
    for (int i = 0; i < 100; i++)
    	log(remark("Another row (" + i + ")"));
    String warning = "This is a message";
    log(warning(warning));
    
    //check logfile
    assertTrue(IO.existsDataFile(logFilename));
    String logText = FileUtils.readFile(logFilename);
    assertNotNull(logText);
    assertTrue(logText.contains(message));
    assertTrue(logText.contains(warning));
  }
  
  
  @After public void cleanUp()
  {
    Log.close();
    IO.deleteDataFile(logFilename, true);
  }
  
}
