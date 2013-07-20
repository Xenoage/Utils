package com.xenoage.utils.document.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.xenoage.utils.base.exceptions.InvalidFormatException;
import com.xenoage.utils.document.Notes;


/**
 * This class read {@link Notes} from a text file.
 * 
 * @author Andreas Wenger
 */
public class NotesInput
	implements FileInput<Notes> {

	
	@Override public Notes read(InputStream stream, String filePath)
		throws InvalidFormatException, IOException {
		List<String> lines = new ArrayList<String>();
		BufferedReader in = new BufferedReader(new InputStreamReader(stream));
		String line = null;
		while((line = in.readLine()) != null)
			lines.add(line);
		Notes ret = new Notes();
		ret.getLines().addAll(lines);
		return ret;
	}

}
