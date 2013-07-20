package com.xenoage.utils.document.io;

import java.io.IOException;
import java.io.InputStream;

import com.xenoage.utils.base.annotations.MaybeNull;
import com.xenoage.utils.base.exceptions.InvalidFormatException;
import com.xenoage.utils.document.Document;


/**
 * This is the interface for all classes that allow the creation
 * of some document data from a file.
 * 
 * There may be a MusicXML reader and a MIDI reader for example.
 *
 * @author Andreas Wenger
 */
public interface FileInput<T extends Document>
{
  
  /**
   * Creates document data from the document behind the given input stream.
   * If the file path is known too, it can be given, otherwise it is null.
   */
  public T read(InputStream stream, @MaybeNull String filePath)
    throws InvalidFormatException, IOException;
  

}
