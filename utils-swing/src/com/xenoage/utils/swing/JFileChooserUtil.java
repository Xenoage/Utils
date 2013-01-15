package com.xenoage.utils.swing;

import java.io.File;

import javax.swing.JFileChooser;

import com.xenoage.utils.base.settings.Settings;


/**
 * Useful methods for working with a {@link JFileChooser}.
 * 
 * @author Andreas Wenger
 */
public class JFileChooserUtil
{
	
	/**
	 * Sets the given {@link JFileChooser} to the location that has been saved
	 * as the last directory within the {@link Settings}, if available.
	 */
	public static void setDirFromSettings(JFileChooser fileChooser)
	{
		String lastDocumentDir = Settings.getInstance().getSetting("lastdocumentdirectory", "paths");
		if (lastDocumentDir != null) {
			fileChooser.setCurrentDirectory(new File(lastDocumentDir));
		} else {
			fileChooser.setCurrentDirectory(new File("files"));
		}
	}
	
	
	/**
	 * Saves the directory of the given {@link JFileChooser}
	 * as the last directory to the {@link Settings}.
	 */
	public static void rememberDir(JFileChooser fileChooser)
	{
		Settings.getInstance().saveSetting("lastdocumentdirectory", "paths",
			fileChooser.getCurrentDirectory().toString());
	}

}
