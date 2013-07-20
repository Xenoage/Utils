package com.xenoage.utils.document.io;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import com.xenoage.utils.document.Notes;


/**
 * This class writes {@link Notes} to a text file.
 * 
 * @author Andreas Wenger
 */
public class NotesOutput
	implements FileOutput<Notes> {

	
	@Override public void write(Notes document, OutputStream stream, String filePath)
		throws IOException {
		PrintWriter writer = new PrintWriter(stream);
		for (String line : document.getLines())
			writer.println(line);
		writer.flush();
	}

	
	@Override public boolean isFilePathRequired(Notes document) {
		return false;
	}

}
