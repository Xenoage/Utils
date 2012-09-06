package com.xenoage.utils.log;

import static com.xenoage.utils.log.Level.Remark;

import java.io.PrintStream;
import java.text.DateFormat;
import java.util.Date;


/**
 * Logging class for desktop applications.
 * 
 * @author Andreas Wenger
 */
public class AppLogProcessing
  implements LogProcessing
{
  
  public static final String FILENAME_DEFAULT = "data/app.log";
  
  private static String logFileName;
  private static PrintStream writer = null;
  
  
  /**
   * Initialize the logging class.
   */
  public AppLogProcessing(String appNameAndVersion)
  {
  	this(FILENAME_DEFAULT, appNameAndVersion);
  }

  
  /**
   * Initialize the logging class.
   */
  public AppLogProcessing(String logFileName, String appNameAndVersion)
  {
    try
    {
      AppLogProcessing.logFileName = logFileName;
      writer = new PrintStream(logFileName);
      //start message
      println(Remark, "Logging started for: " + appNameAndVersion);
      //os
      println(Remark, "Operating system: " + System.getProperty("os.name") + ", " +
        System.getProperty("os.version"));
      //java version
      println(Remark, "Java version: " + System.getProperty("java.version"));
    }
    catch (Exception ex)
    {
      throw new RuntimeException(ex);
    }
  }
  
  
  @Override public void log(Report report)
	{
		println(report.level, report.toString());
	}


  @Override public void close()
  {
    println(Remark, "Logging closed");
    writer.close();
  }


  /**
   * Gets the filename of the log file.
   */
  @Override public String getLogFilename()
  {
    return logFileName;
  }
  
  
  /**
   * Returns the current time in the format HH:MM:SS.
   */
  private static String time()
  {
    DateFormat df = DateFormat.getTimeInstance(DateFormat.MEDIUM);
    return df.format(new Date());
  }
  
  
  /**
   * Prints the current time, the given log level and message
   * into the log file and starts a new line. The buffer is immediately
   * flushed.
   */
  private void println(Level level, String s)
  {
  	writer.println(time() + " " + level.getFixedString() + " " + s);
  	writer.flush();
  }
  

}
