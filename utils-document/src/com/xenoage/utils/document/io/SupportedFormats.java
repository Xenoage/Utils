package com.xenoage.utils.document.io;

import static com.xenoage.utils.pdlib.IVector.ivec;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

import com.xenoage.utils.document.Document;
import com.xenoage.utils.pdlib.IVector;
import com.xenoage.utils.pdlib.Vector;


/**
 * This base class provides a list of all formats which can be used
 * for loading or saving a document.
 * 
 * @author Andreas Wenger
 */
public abstract class SupportedFormats<T extends Document>
{
	
	/** The list of supported formats. */
	@Getter List<FileFormat<T>> formats = new ArrayList<FileFormat<T>>();
	
	
	/**
	 * Gets a list of the supported formats for reading.
	 */
	public Vector<FileFormat<T>> getReadFormats()
	{
		IVector<FileFormat<T>> ret = ivec();
		for (FileFormat<T> f : formats)
			if (f.getInput() != null)
				ret.add(f);
		return ret.close();
	}
	
	
	/**
	 * Gets a list of the supported formats for reading.
	 */
	public Vector<FileFormat<T>> getWriteFormats()
	{
		IVector<FileFormat<T>> ret = ivec();
		for (FileFormat<T> f : formats)
		{
			if (f.getOutput() != null)
				ret.add(f);
		}
		return ret.close();
	}
	
	
	/**
	 * Gets the default format for reading files.
	 */
	public abstract FileFormat<T> getReadDefaultFormat();
	
	
	/**
	 * Gets the default format for writing files.
	 */
	public abstract FileFormat<T> getWriteDefaultFormat();
	
	
	/**
	 * Gets the format with the given ID.
	 */
	public FileFormat<T> getByID(String id)
	{
		for (FileFormat<T> f : formats)
			if (f.getId().equals(id))
				return f;
		throw new IllegalArgumentException("Format with ID \"" + id + "\" does not exist");
	}
	

}
