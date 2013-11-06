package com.xenoage.utils.document.io;

import java.io.IOException;

import com.xenoage.utils.document.Document;
import com.xenoage.utils.io.generic.OutputStream;


/**
 * This is the interface for all classes that allow the creation of a file
 * from some document data.
 * 
 * There may be a MusicXML writer and a MIDI writer for example.
 *
 * @author Andreas Wenger
 */
public interface FileOutput<T extends Document>
{
	
  /**
   * Writes the given document data to the given file.
   * @param doc       the data of any type. Incompatible types will throw an {@link IOException}.
   * @param stream    the output stream, which should be used most of the time. Must not be null.
   * @param filePath  the file path, which should be given in addition to the stream.
   *                  It is useful for some formats, which create multiple files,
   *                  like a range of PNG files, and need the file path to generate additional
   *                  file paths. If required but not given, an {@link IOException}
   *                  is thrown. Use {@link #isFilePathRequired(Document)} to find out if the
   *                  file path is required or not.
   */
  public void write(T document, OutputStream stream, String filePath)
    throws IOException;
  
  
  /**
   * Returns true, if this class requires the file path as a parameter for writing
   * the given document. See {@link #write(Document, OutputStream, String)}.
   */
  public boolean isFilePathRequired(T document);
  

}
