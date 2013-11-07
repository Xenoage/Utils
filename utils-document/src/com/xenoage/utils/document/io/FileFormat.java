package com.xenoage.utils.document.io;

import com.xenoage.utils.document.Document;
import com.xenoage.utils.io.File;

import lombok.AllArgsConstructor;
import lombok.Data;


/**
 * General information on a file format for a document.
 * 
 * @author Andreas Wenger
 */
@Data @AllArgsConstructor public final class FileFormat<T extends Document>
{
	
	/** A unique ID of the format, like "MP3". */
	private final String id;
	/** The name of the format, like "MPEG Audio Layer III". */
	private final String name;
	/** The default extension, like ".mp3" or ".xml". */
	private final String defaultExtension;
	/** More supported extensions, like ".mid" or ".xml". */
	private final String[] otherExtensions;
	/** Reader for this file format, or null if unsupported. */
	private final FileInput<T> input;
	/** Writer for this file format, or null if unsupported. */
	private final FileOutput<T> output;
	
	
	/**
	 * Creates a new {@link FileFormat} with no input and output class.
	 */
	public FileFormat(String id, String name, String defaultExtension, String... otherExtensions) {
		this.id = id;
		this.name = name;
		this.defaultExtension = defaultExtension;
		this.otherExtensions = otherExtensions;
		this.input = null;
		this.output = null;
	}
	
	
	/**
	 * Gets the file filter description of the format. By default, this
	 * is the name of the format with the default extensions and
	 * other extensions in parentheses, like "MPEG Audio Layer III (.mp3, .mpeg)".
	 */
	public String getFilterDescription()
	{
		StringBuilder ret = new StringBuilder(name);
		ret.append(" (");
		ret.append(defaultExtension);
		for (String ext : otherExtensions) {
			ret.append(", ");
			ret.append(ext);
		}
		ret.append(")");
		return ret.toString();
	}


	/**
	 * Returns true, if a file filter dialog would accept the given file.
	 * By default, directories and files ending with the default extension
	 * (case insensitive) are accepted.
	 */
	public boolean isAccepted(File file)
	{
		String name = file.getName().toLowerCase();
		return file.isDirectory() || name.endsWith(defaultExtension);
	}

}
