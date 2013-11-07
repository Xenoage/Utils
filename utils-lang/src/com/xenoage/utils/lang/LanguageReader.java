package com.xenoage.utils.lang;

import static com.xenoage.utils.base.NullUtils.notNull;
import static com.xenoage.utils.base.collections.CollectionUtils.map;
import static com.xenoage.utils.jse.io.FileUtils.getPOFilter;
import static com.xenoage.utils.jse.io.FileUtils.getXMLFilter;
import static com.xenoage.utils.jse.io.FileUtils.orFilter;
import static com.xenoage.utils.log.Log.log;
import static com.xenoage.utils.log.Report.remark;
import static com.xenoage.utils.log.Report.warning;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.xenoage.utils.jse.io.IO;
import com.xenoage.utils.jse.xml.XMLReader;
import com.xenoage.utils.kernel.Tuple2;


/**
 * This class reads a {@link Language} from a given directory.
 * The directory may contain .xml files or GNU gettext .po files.
 * 
 * The format of the XML files is simple:
 * 
 * <pre>{@code <vocabulary>
 *   <voc key="About" value="Über"/>
 *   ...
 * </vocabulary> }</pre>
 * 
 * The format of the PO files is like in the
 * <a href="http://www.gnu.org/software/gettext/manual/html_node/PO-Files.html">documentation</a>,
 * but requires the usage of <code>msgctxt</code> for each entry, which is used as the
 * vocabulary key. The <code>msgid</code> is ignored and is only useful for human translators.
 * 
 * @author Andreas Wenger
 */
public class LanguageReader
{
	
	/**
	 * Creates a {@link Language} from all .xml and .po files in the folder <code>basePath/id</code>.
	 */
	public static Language read(String id, String basePath)
		throws IOException
	{
		log(remark("Loading language pack \"" + id + "\" from folder \"" + basePath + "\"..."));

		//locate vocabulary files
		Set<String> langFiles = IO.listDataFiles(basePath + "/" + id,
			orFilter(getXMLFilter(), getPOFilter()));
		langFiles.remove("id.xml");

		//load entries
		HashMap<String, String> entries = map();
		int entriesCount = 0;
		int entriesOverwrittenCount = 0;
		String currentFile = "";
		for (String langFile : langFiles) {
			currentFile = new File(langFile).getName();
			
			//read XML or PO file
			String filePath = basePath + "/" + id + "/" + currentFile;
			HashMap<String, String> fileEntries = null;
			if (langFile.endsWith(".po")) {
				log(remark("Reading PO language file \"" + currentFile + "\""));
				fileEntries = readPO(filePath);
			} else {
				log(remark("Reading XML language file \"" + currentFile + "\""));
				fileEntries = readXML(filePath);
			}
				
			//insert vocabulary data
			for (Entry<String, String> fileEntry : fileEntries.entrySet()) {
				String oldValue = entries.put(fileEntry.getKey(), fileEntry.getValue());
				if (oldValue == null)
					entriesCount++;
				else {
					log(warning("Overwritten entry: " + fileEntry.getKey()));
					entriesOverwrittenCount++;
				}
			}
		}
		log(remark("Language pack loaded. Entries: " + entriesCount
			+ ". Overwritten entries: " + entriesOverwrittenCount));

		//replace all tokens
		for (String key : entries.keySet()) {
			String value = entries.get(key);
			if (value.indexOf("{") > -1) {
				entries.put(key, replaceTokens(value, entries));
			}
		}
		
		return new Language(id, entries);
	}
	
	
	/**
	 * Returns all key-value pairs from the given XML language file.
	 */
	private static HashMap<String, String> readXML(String filePath)
		throws IOException
	{
		Document doc = null;
		doc = XMLReader.readFile(IO.openInputStream(filePath));

		//check root element
		Element root = XMLReader.root(doc);
		if (!root.getNodeName().equals("vocabulary")) {
			//no vocabulary file.
			log(warning("No vocabulary file."));
			return null;
		}
		
		//read vocabulary data
		HashMap<String, String> entries = map();
		List<Element> eEntries = XMLReader.elements(root, "voc");
		for (int i = 0; i < eEntries.size(); i++) {
			Element e = eEntries.get(i);
			String eID = XMLReader.attributeNotNull(e, "key");
			String eValue = XMLReader.attributeNotNull(e, "value");
			eValue = eValue.replaceAll("\\\\n", "\n");
			entries.put(eID, eValue);
		}
		
		return entries;
	}
	
	
	/**
	 * Returns all key-value pairs from the given PO language file.
	 */
	private static HashMap<String, String> readPO(String filePath)
		throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(IO.openInputStream(filePath)));
		
		//read vocabulary data
		HashMap<String, String> entries = map();
		String line, key = null, original = null, value = null;
		while ((line = br.readLine()) != null) {
			line = line.trim();
			if (line.startsWith("#") || line.length() == 0) {
				//finish entry, if one is open
				if (key != null && value != null)
					entries.put(key, value);
				key = null;
				original = null;
				value = null;
			} else {
				//begin or extend current entry
				if (line.startsWith("msgctxt "))
					key = val(line.substring("msgctxt ".length()));
				else if (line.startsWith("msgid "))
					original = val(line.substring("msgid ".length()));
				else if (line.startsWith("msgstr "))
					value = val(line.substring("msgstr ".length()));
				else if (line.startsWith("\"")) {
					String l = val(line); //another text line which belongs to the previous one
					if (value != null)
						value += l;
					else if (original != null)
						original += l;
					else if (key != null)
						key += l;
				}
			}
		}
		//finish entry, if one is open
		if (key != null && value != null)
			entries.put(key, value);
		
		return entries;
	}
	
	
	/**
	 * Removes quotes at the beginning and at the end, if there,
	 * and unescapes special characters.
	 */
	private static String val(String s)
	{
		s = s.trim();
		if (s.startsWith("\"") && s.endsWith("\""))
			s = s.substring(1, s.length() - 1);
		s = s.replaceAll("\\\"", "\"");
		s = s.replaceAll("\\\\n", "\n");
		return s;
	}
	
	
	/**
	 * Replaces the following tokens in the given String and returns the result:
	 * <ul>
	 * 	<li>All registered tokens from the {@link Lang} class</li>
	 * 	<li>{voc:xyz}: Inserts vocabulary with ID "xyz"</li>
	 * </ul>
	 */
	private static String replaceTokens(String s, Map<String, String> entries)
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
				ret = ret.replaceFirst("\\{voc:" + id + "\\}", notNull(entries.get(id), "?"));
			} else {
				ret = ret.replaceFirst("\\{voc:", "");
			}
		}
		return ret;
	}

}
