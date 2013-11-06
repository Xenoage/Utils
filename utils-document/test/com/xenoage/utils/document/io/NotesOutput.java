package com.xenoage.utils.document.io;

import java.io.IOException;
import java.io.PrintWriter;

import com.xenoage.utils.document.Notes;
import com.xenoage.utils.io.generic.OutputStream;


/**
 * This class writes {@link Notes} to a text file.
 * 
 * @author Andreas Wenger
 */
public class NotesOutput
	implements FileOutput<Notes> {

	
	@Override public void write(Notes document, OutputStream stream, String filePath)
		throws IOException {
		PrintWriter writer = new PrintWriter(new JseOutputStream(stream));
		for (String line : document.getLines())
			writer.println(line);
		writer.flush();
		writer.close();
	}

	
	@Override public boolean isFilePathRequired(Notes document) {
		return false;
	}

}
