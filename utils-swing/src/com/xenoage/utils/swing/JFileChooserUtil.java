package com.xenoage.utils.swing;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import com.xenoage.utils.base.settings.Settings;
import com.xenoage.utils.io.FileFormat;


/**
 * Useful methods for working with a {@link JFileChooser}.
 * 
 * @author Andreas Wenger
 */
public class JFileChooserUtil
{
	
	/**
	 * Creates a Swing {@link FileFilter} for the given {@link FileFormat}.
	 */
	public static FileFilter createFileFilter(final FileFormat fileFormat)
	{
		FileFilter ret = new FileFilter()
		{

			@Override public String getDescription()
			{
				return fileFormat.getFilterDescription();
			}


			@Override public boolean accept(File f)
			{
				return fileFormat.isAccepted(f);
			}
		};
		return ret;
	}
	
	
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
