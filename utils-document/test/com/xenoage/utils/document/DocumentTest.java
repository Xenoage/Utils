package com.xenoage.utils.document;

import static com.xenoage.utils.kernel.Range.range;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.xenoage.utils.document.command.LineAdd;
import com.xenoage.utils.document.command.LineRemove;


/**
 * Tests for the document framework, based on the test data class {@link Notes}.
 * 
 * @author Andreas Wenger
 */
public class DocumentTest {
	
	
	private Notes createTestNotes() {
		Notes notes = new Notes();
		//add 5 lines
		for (int i : range(1, 5))
			notes.getLines().add("" + i);
		assertEquals("1;2;3;4;5", notes.toString());
		return notes;
	}
	
	
	@Test public void testCommands() {
		Notes notes = createTestNotes();
		//remove the second line
		notes.getCommandPerformer().execute(new LineRemove(notes, 1));
		assertEquals("1;3;4;5", notes.toString());
		//add a line
		notes.getCommandPerformer().execute(new LineAdd(notes, "6"));
		assertEquals("1;3;4;5;6", notes.toString());
		//undo last step
		notes.getCommandPerformer().undo();
		assertEquals("1;3;4;5", notes.toString());
		//redo last undo
		notes.getCommandPerformer().redo();
		assertEquals("1;3;4;5;6", notes.toString());
		//undo last two steps
		notes.getCommandPerformer().undo();
		notes.getCommandPerformer().undo();
		assertEquals("1;2;3;4;5", notes.toString());
	}
	
	
	@Test public void testIO() {
		Notes notes = createTestNotes();
		//GOON
	}

}
