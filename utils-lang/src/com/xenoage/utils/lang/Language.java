package com.xenoage.utils.lang;

import static com.xenoage.utils.base.NullUtils.notNull;
import static com.xenoage.utils.log.Log.log;
import static com.xenoage.utils.log.Report.remark;
import static com.xenoage.utils.log.Report.warning;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.xenoage.utils.io.FileUtils;
import com.xenoage.utils.io.IO;
import com.xenoage.utils.kernel.Tuple2;
import com.xenoage.utils.xml.XMLReader;


/**
 * Class for a language pack.
 * 
 * When a language is loaded, all .xml files from the given directory
 * are read and all values are stored.
 * 
 * Then, they can be queried using the <code>get</code> methods.
 * 
 * For non-verbose and quick access to a language pack
 * in the program, use the {@link Lang} class instead of this class.
 *
 * @author Andreas Wenger
 */
public class Language
{

	private static class ProjectLanguage
	{

		//maps vocabulary id to translated text
		public HashMap<String, String> entries = new HashMap<String, String>();
	}

	private final String id;
	//maps project id to project language
	private HashMap<String, ProjectLanguage> projects = new HashMap<String, ProjectLanguage>();


	/**
	 * Loads a language pack from the directory <code>langPath + id</code>.
	 * All ".voc" files in this directory are loaded.
	 * If the language pack can not be loaded, an {@link IOException} is thrown.
	 * @param path  path to the language pack directory (without trailing slash)
	 * @param id    id of the language pack
	 */
	public Language(String path, String id)
		throws IOException
	{
		this.id = id;

		log(remark("Loading language pack \"" + id + "\"..."));

		addVocabularyFrom(path);
	}


	/**
	 * Adds vocabulary from the given folder to this language.
	 * @param path  e.g. "../morevoc/" to load vocabulary from "../morevoc/{currentlanguage}/".
	 */
	public void addVocabularyFrom(String path)
		throws IOException
	{
		log(remark("Loading vocabulary for language pack \"" + id + "\" from folder \""
			+ path + "\"..."));

		//locate vocabulary files
		Set<String> langFiles = IO.listDataFiles(path + "/" + id, FileUtils.getXMLFilter());
		langFiles.remove("id.xml");

		//load entries
		int entriesCount = 0;
		int entriesOverwrittenCount = 0;
		String currentFile = "";
		for (String langFile : langFiles) {
			currentFile = new File(langFile).getName();
			log(remark("Reading language file \"" + currentFile + "\"..."));
			Document doc = null;
			doc = XMLReader.readFile(IO.openInputStream(path + "/" + id + "/" + currentFile));

			//check root element
			Element root = XMLReader.root(doc);
			if (!root.getNodeName().equals("vocabulary")) {
				//no vocabulary file. continue.
				log(warning("No vocabulary file."));
				continue;
			}

			//read project
			String projectID = root.getAttribute("project");
			if (projects.get(projectID) == null) {
				projects.put(projectID, new ProjectLanguage());
			}
			ProjectLanguage projLang = projects.get(projectID);

			//read package
			String packageName = "";
			if (root.getAttribute("package").length() > 0) {
				packageName = root.getAttribute("package") + "_";
			}

			//read vocabulary data
			List<Element> eEntries = XMLReader.elements(root, "voc");
			for (int i = 0; i < eEntries.size(); i++) {
				Element e = (Element) eEntries.get(i);
				String eID = packageName + XMLReader.attributeNotNull(e, "key");
				String eValue = XMLReader.attributeNotNull(e, "value");
				eValue = eValue.replaceAll("\\\\n", "\n");
				String oldValue = projLang.entries.put(eID, eValue);
				if (oldValue == null)
					entriesCount++;
				else {
					log(warning("Overwritten entry: " + eID));
					entriesOverwrittenCount++;
				}
			}
		}
		log(remark("Language pack loaded. Entries: " + entriesCount
			+ ". Overwritten entries: " + entriesOverwrittenCount));

		//replace all tokens
		for (String projectID : projects.keySet()) {
			ProjectLanguage projLang = projects.get(projectID);
			for (String key : projLang.entries.keySet()) {
				String value = projLang.entries.get(key);
				if (value.indexOf("{") > -1) {
					projLang.entries.put(key, replaceTokens(projectID, value));
				}
			}
		}
	}


	/**
	 * Gets the ID of this language.
	 */
	public String getID()
	{
		return id;
	}


	/**
	 * Gets an entry from the language pack.
	 * If no value is found, null is returned.
	 */
	public String getWithNull(VocID id)
	{
		if (id == null)
			return null;
		ProjectLanguage projLang = projects.get(id.getProjectID());
		if (projLang != null)
			return projLang.entries.get(id.getID());
		else
			return null;
	}


	/**
	 * Gets an entry from the language pack.
	 * If no value is found, the id is given back as a String
	 * (because it's more useful for the user
	 * than an empty string).
	 */
	public String get(VocID id)
	{
		return notNull(getWithNull(id), id.toString());
	}


	/**
	 * Gets an entry from the language pack.
	 * If no value is found, null is returned.
	 */
	public String getWithNull(String projectID, String id)
	{
		ProjectLanguage projLang = projects.get(projectID);
		if (projLang != null)
			return projLang.entries.get(id);
		else
			return null;
	}


	/**
	 * Gets an entry from the language pack.
	 * If no value is found, the id is given back as a String
	 * (because it's more useful for the user
	 * than an empty string).
	 */
	private String get(String projectID, String id)
	{
		return notNull(getWithNull(projectID, id), id);
	}


	/**
	 * Gets an entry from the language pack.
	 * If no value is found, the id is given back as a String
	 * (because it's more useful for the user
	 * than an empty string).
	 * The tokens {1}, {2}, ... {n} are replaced
	 * by the given Strings.
	 */
	public String get(VocID id, String... replacements)
	{
		String ret = get(id);
		//search for {n}-tokens and replace them
		for (int i = 0; i < replacements.length; i++) {
			ret = ret.replaceAll("\\{" + (i + 1) + "\\}", replacements[i]);
		}
		return ret;
	}


	/**
	 * Replaces the following tokens in the given String and returns the result:
	 * <ul>
	 * 	<li>All registered tokens from the {@link Lang} class</li>
	 * 	<li>{voc:xyz}: Inserts vocabulary with ID "xyz" from the same project</li>
	 * </ul>
	 */
	private String replaceTokens(String projectID, String s)
	{
		String ret = s;
		for (Tuple2<String, String> t : Lang.getTokens()) {
			ret = ret.replace(t.get1(), t.get2());
		}
		int pos;
		while ((pos = ret.indexOf("{voc:")) > -1) {
			int pos2 = ret.indexOf("}", pos + 1);
			if (pos2 > -1) {
				String id = ret.substring(pos + 5, pos2);
				ret = ret.replaceFirst("\\{voc:" + id + "\\}", get(projectID, id));
			} else {
				ret = ret.replaceFirst("\\{voc:", "");
			}
		}
		return ret;
	}


	/**
	 * Gets all registered project IDs.
	 */
	public Set<String> getAllProjectIDs()
	{
		return projects.keySet();
	}


	/**
	 * Gets all vocabulary keys for the given project, or null
	 * if the project is undefined.
	 */
	public Set<String> getAllKeys(String projectID)
	{
		ProjectLanguage projLang = projects.get(projectID);
		if (projLang != null)
			return projLang.entries.keySet();
		else
			return null;
	}


}
