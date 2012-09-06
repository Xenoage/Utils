package com.xenoage.utils.log;



/**
 * Interface for a logging class.
 *
 * @author Andreas Wenger
 */
public interface LogProcessing
{


  /**
   * Writes a message to the log.
   */
  public void log(Report report);


  /**
   * Flushes and closes the log file.
   */
  public void close();


  /**
   * Gets the filename of the log file.
   */
  public String getLogFilename();
  
}
