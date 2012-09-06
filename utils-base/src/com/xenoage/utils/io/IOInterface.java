package com.xenoage.utils.io;

import java.io.*;
import java.util.Date;
import java.util.Set;


/**
 * Interface for a input/output class.
 *
 * @author Andreas Wenger
 */
public interface IOInterface
{

  
  /**
   * Returns true, when the given data file exists,
   * otherwise false.
   */
  public boolean existsDataFile(String filepath);
  
  
  /**
   * Returns true, when the given data directory exists,
   * otherwise false.
   */
  public boolean existsDataDirectory(String directory);
  
  
  /**
   * Gets the modification date of the given data file,
   * or null, if the date is unavailable.
   */
  public Date getDataFileModificationDate(String filepath);
  
  
  /**
   * Opens and returns an input stream for the data file with
   * the given relative path.
   */
  public InputStream openInputStream(String filepath)
    throws IOException;
  
  
  /**
   * Opens and returns an input stream for the data file with
   * the given absolute or relative path. The path is guaranteed to
   * be untouched (no automatic rerouting to home directory or something
   * like that).
   */
  public InputStream openInputStreamPreservePath(String filepath)
    throws IOException;
  
  
  /**
   * Opens and returns an output stream for the data file with
   * the given relative path.
   */
  public OutputStream openOutputStream(String filepath)
    throws IOException;
  
  
  /**
   * Removes the data file with the given relative path.
   * @param system  if true, not only the user's private settings file is deleted
   *                but also the system data file
   */
  public void deleteDataFile(String filepath, boolean system);
  
  
  /**
   * Finds and returns the data files in the given directory.
   */
  public Set<String> listDataFiles(String directory)
    throws IOException;
  
  
  /**
   * Finds and returns the data files in the given directory
   * matching the given filename filter.
   */
  public Set<String> listDataFiles(String directory, FilenameFilter filter)
    throws IOException;
  
  
  /**
   * Finds and returns the data directories in the given directory.
   */
  public Set<String> listDataDirectories(String directory)
    throws IOException;
  
}
