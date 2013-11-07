package com.xenoage.utils.document;

import static com.xenoage.utils.kernel.Range.range;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

import com.xenoage.utils.document.command.CommandPerformer;
import com.xenoage.utils.document.io.NotesFormats;

/**
 * Class for testing the {@link Document} interface.
 * It contains just a list of text notes.
 * 
 * @author Andreas Wenger
 */
public class Notes
	implements Document {

	@Getter private List<String> lines = new ArrayList<String>();

	@Getter private CommandPerformer commandPerformer = new CommandPerformer(this);
	@Getter private NotesFormats supportedFormats = new NotesFormats();


	@Override public String toString() {
		StringBuilder ret = new StringBuilder();
		for (int i : range(lines)) {
			ret.append(lines.get(i));
			if (i < lines.size() - 1)
				ret.append(";");
		}
		return ret.toString();
	}

}
