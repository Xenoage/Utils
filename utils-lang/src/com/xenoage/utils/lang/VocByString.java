package com.xenoage.utils.lang;



/**
 * Vocabulary IDs for commands.
 * 
 * @author Andreas Wenger
 */
public class VocByString
	implements VocID
{
	
	private final String projectID;
	private final String id;
	
	
	/**
	 * Creates a new {@link VocID} using the given project ID (like "viewer") and
	 * vocabulary ID (like "Error_Unknown").
	 */
	public static VocByString voc(String projectID, String vocID)
	{
		return new VocByString(projectID, vocID);
	}
	
	
	/**
	 * Creates a new {@link VocID} using the given project id, package name
	 * and vocabulary name, like "viewer", "Error" and "Unknown".
	 */
	public static VocByString voc(String projectID, String packageName, String vocName)
	{
		return new VocByString(projectID, packageName + "_" + vocName);
	}
	
	
	/**
	 * Creates a new {@link VocID} using the given vocabulary ID (like "Error_Unknown").
	 * The project is set to "general".
	 */
	public static VocByString voc(String vocID)
	{
		return new VocByString("general", vocID);
	}
	
	
	private VocByString(String projectID, String vocID)
	{
		this.projectID = projectID;
		this.id = vocID;
	}
	
	
	/**
	 * Gets the ID of the project.
	 */
	@Override public String getProjectID()
	{
		return projectID;
	}

	
	/**
	 * Gets the ID of the vocabulary as a String.
	 */
	@Override public String getID()
	{
		return id;
	}
	
	
	@Override public String toString()
	{
		return projectID + "."  + id;
	}


}
