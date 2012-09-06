package com.xenoage.utils.base.lang;


/**
 * This interface must be implemented by all enums that contain
 * vocabulary IDs.
 * 
 * There must be a method <code>getProjectID</code> to identify
 * the project to which the vocabulary belongs (like "viewer",
 * or "general") and a method <code>getID</code> that returns the ID
 * as a String needed to index the vocabulary in XML files
 * (like "Error_UnknownValue").
 * 
 * @author Andreas Wenger
 */
public interface VocID
{
	
	
	/**
	 * Gets the ID of the project.
	 */
	public String getProjectID();
	
	
	/**
	 * Gets the ID of the vocabulary as a String.
	 */
	public String getID();
	

}
