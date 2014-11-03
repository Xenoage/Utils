package com.xenoage.utils.jse.lang;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.xenoage.utils.lang.Lang;
import com.xenoage.utils.lang.VocID;

/**
 * This class provides access to {@link Lang} language packs
 * via the {@link ResourceBundle} interface.
 * 
 * The keys are simply String encoded {@link VocID} values,
 * with some special additions:
 * <ul>
 *   <li>if the resource ID ends with ":", {@link Lang#getLabel(VocID)} is used</li>
 *   <li>if the resource ID ends with "...", {@link Lang#getWithEllipsis(VocID)} is used</li>
 * </ul>
 * 
 * @author Andreas Wenger
 */
public class LangResourceBundle
	extends ResourceBundle {

	private Map<String, VocID> vocIDs;
	private Enumeration<String> vocIDStrings;


	/**
	 * Creates a new {@link LangResourceBundle} for the given
	 * vocabulary keys.
	 */
	public LangResourceBundle(VocID[] vocIDs) {
		this.vocIDs = new HashMap<String, VocID>();
		for (VocID vocID : vocIDs) {
			this.vocIDs.put(vocID.toString(), vocID);
		}
		this.vocIDStrings = Collections.enumeration(this.vocIDs.keySet());
	}

	@Override protected Object handleGetObject(String key) {
		if (key.endsWith(":")) {
			//read as label (usually the text, followed by ":")
			VocID vocID = vocIDs.get(key.substring(0, key.length() - 1));
			if (vocID == null)
				return null;
			return Lang.getLabel(vocID);
		}
		else if (key.endsWith("...")) {
			//read with ellipsis (text followed by "...")
			VocID vocID = vocIDs.get(key.substring(0, key.length() - 3));
			if (vocID == null)
				return null;
			return Lang.getWithEllipsis(vocID);
		}
		else {
			//normal case
			VocID vocID = vocIDs.get(key);
			if (vocID == null)
				return null;
			return Lang.get(vocID);
		}
	}

	@Override public Enumeration<String> getKeys() {
		return vocIDStrings;
	}

	//overridden to support special cases
	@Override public boolean containsKey(String key) {
		if (key.endsWith(":"))
			key = key.substring(0, key.length() - 1);
		else if (key.endsWith("..."))
			key = key.substring(0, key.length() - 3);
		return super.containsKey(key);
	}

}
