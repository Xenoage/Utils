package com.xenoage.utils.io;

import java.io.File;


/**
 * General information on a file format.
 * 
 * @author Andreas Wenger
 */
public final class FileFormat
{
	
	/** A unique ID of the format, like "MP3". */
	public final String id;
	/** The name of the format, like "MPEG Audio Layer III". */
	public final String name;
	/** The default extension, like ".mp3" or ".xml". */
	public final String defaultExtension;
	/** More supported extensions, like ".mid" or ".xml". */
	public final String[] otherExtensions;
	

	public FileFormat(String id, String name, String defaultExtension)
	{
		this.id = id;
		this.name = name;
		this.defaultExtension = defaultExtension;
		this.otherExtensions = new String[0];
	}
	
	
	public FileFormat(String id, String name,
		String defaultExtension, String... otherExtensions)
	{
		this.id = id;
		this.name = name;
		this.defaultExtension = defaultExtension;
		this.otherExtensions = otherExtensions;
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
