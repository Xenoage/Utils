package com.xenoage.utils.jse;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import com.xenoage.utils.document.io.FileFormat;
import com.xenoage.utils.jse.io.JseFile;
import com.xenoage.utils.jse.settings.Settings;

/**
 * Useful methods for working with a {@link JFileChooser}.
 * 
 * @author Andreas Wenger
 */
public class JFileChooserUtil {

	/**
	 * Removes all file filters.
	 */
	public static void clearFileFilters(JFileChooser fileChooser) {
		while (fileChooser.getChoosableFileFilters().length > 0)
			fileChooser.removeChoosableFileFilter(fileChooser.getChoosableFileFilters()[0]);
	}

	/**
	 * Creates a Swing {@link FileFilter} for the given {@link FileFormat}.
	 */
	public static FileFilter createFileFilter(final FileFormat<?> fileFormat) {
		FileFilter ret = new FileFilter() {

			@Override public String getDescription() {
				return fileFormat.getFilterDescription();
			}

			@Override public boolean accept(File f) {
				return fileFormat.isAccepted(new JseFile(f));
			}
		};
		return ret;
	}

	/**
	 * Sets the given {@link JFileChooser} to the location that has been saved
	 * as the last directory within the {@link Settings}, if available.
	 */
	public static void setDirFromSettings(JFileChooser fileChooser) {
		setDirFromSettings(fileChooser, new File("files"));
	}

	/**
	 * Sets the given {@link JFileChooser} to the location that has been saved
	 * as the last directory within the {@link Settings}, if available. Otherwise, the given default directory is set.
	 */
	public static void setDirFromSettings(JFileChooser fileChooser, File defaultDir) {
		String lastDocumentDir = Settings.getInstance().getSetting("lastdocumentdirectory", "paths");
		if (lastDocumentDir != null) {
			fileChooser.setCurrentDirectory(new File(lastDocumentDir));
		}
		else {
			fileChooser.setCurrentDirectory(defaultDir);
		}
	}

	/**
	 * Saves the directory of the given {@link JFileChooser}
	 * as the last directory to the {@link Settings}.
	 */
	public static void rememberDir(JFileChooser fileChooser) {
		Settings.getInstance().saveSetting("lastdocumentdirectory", "paths",
			fileChooser.getCurrentDirectory().toString());
	}

}
