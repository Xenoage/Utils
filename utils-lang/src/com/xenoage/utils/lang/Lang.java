package com.xenoage.utils.lang;

import static com.xenoage.utils.error.Err.handle;
import static com.xenoage.utils.log.Report.fatal;
import static com.xenoage.utils.log.Report.warning;

import java.util.ArrayList;

import com.xenoage.utils.base.collections.WeakList;
import com.xenoage.utils.base.iterators.It;
import com.xenoage.utils.kernel.Tuple2;


/**
 * This class manages a single language pack for
 * non-verbose and quick access.
 *
 * @author Andreas Wenger
 */
public class Lang
{

	public static final String defaultLangPath = "data/lang";

	private static Language currentLanguage = null;

	private static WeakList<LanguageComponent> languageComponents = new WeakList<LanguageComponent>();

	private static ArrayList<Tuple2<String, String>> tokens = new ArrayList<Tuple2<String, String>>();


	/**
	 * Loads the given language pack from the default directory.
	 * If this fails, it is tried to load the English language pack.
	 * If this fails too, a fatal error is thrown.
	 * @param id    id of the language pack
	 */
	public static void loadLanguage(String id)
	{
		loadLanguage(defaultLangPath, id, true);
	}


	/**
	 * Loads the given language pack from the given directory.
	 * If this fails, it is tried to load the English language pack.
	 * If this fails too, a fatal error is thrown.
	 * @param path     path to the language pack directory (without trailing slash)
	 * @param id       id of the language pack
	 * @param warning  report warning if requested language pack could not be loaded
	 *                  and the English pack is loaded instead
	 */
	public static void loadLanguage(String path, String id, boolean warning)
	{
		try {
			currentLanguage = Language.read(path, id);
		} catch (Exception ex) {
			//loading language pack failed
			if (warning)
				handle(warning("The language \"" + id
					+ "\" could not be loaded. Loading English pack instead...", ex));
			//try to load English one
			try {
				currentLanguage = Language.read(path, "en");
			} catch (Exception ex2) {
				handle(fatal("The language \"" + id + "\" could not be loaded.", ex2));
			}
		}
		updateLanguageComponents();
	}
	

	/**
	 * Gets the ID of the current language.
	 */
	public static String getCurrentLanguageID()
	{
		return currentLanguage.getID();
	}


	/**
	 * Gets an entry from the language pack.
	 * If no value is found, null is returned.
	 */
	public static String getWithNull(VocID id)
	{
		if (currentLanguage == null)
			return null;
		return currentLanguage.getWithNull(id);
	}


	/**
	 * Gets an entry from the language pack.
	 * If no value is found, the id is given back as a String
	 * (because it's more useful for the user than an empty string).
	 */
	public static String get(VocID id)
	{
		if (id == null)
			return "";
		String s = getWithNull(id);
		return (s != null ? s : id.getDefaultValue());
	}
	
	
	/**
	 * Gets an entry from the language pack as a label text.
	 * By default this is the text with a trailing colon (":").
	 */
	public static String getLabel(VocID id)
	{
		return get(id) + ":";
	}
	
	
	/**
	 * Gets an entry from the language pack with a trailing "...".
	 */
	public static String getWithEllipsis(VocID id)
	{
		return get(id) + "...";
	}


	/**
	 * Gets an entry from the language pack.
	 * If no value is found, the id is given back as a String
	 * (because it's more useful for the user
	 * than an empty string).
	 * The tokens {1}, {2}, ... {n} are replaced
	 * by the given Strings.
	 */
	public static String get(VocID id, String... replacements)
	{
		String ret = get(id);
		//search for {n}-tokens and replace them
		for (int i = 0; i < replacements.length; i++) {
			ret = ret.replaceAll("\\{" + (i + 1) + "\\}", replacements[i]);
		}
		return ret;
	}


	/**
	 * Register the given {@link LanguageComponent}.
	 * Every time the language is changed, the <code>languageChanged()</code>
	 * method of all registered components is called.
	 * 
	 * Unregistering is not necessary. This class stores only weak
	 * references of the components, so they can be removed by the
	 * garbage collector when they are not used any more.
	 */
	public static void registerComponent(LanguageComponent component)
	{
		languageComponents.add(component);
	}


	/**
	 * Unregister the all LanguageComponents.
	 */
	static void unregisterAllComponents()
	{
		languageComponents.clear();
	}


	/**
	 * Update all registered language components.
	 */
	static void updateLanguageComponents()
	{
		for (LanguageComponent component : languageComponents.getAll()) {
			component.languageChanged();
		}
	}


	/**
	 * Gets the number of registered language components.
	 */
	static int getLanguageComponentsCount()
	{
		return languageComponents.getAll().size();
	}


	/**
	 * Register a token for replacement, e.g. "{app.name}" by "Xenoage WhatEver".
	 */
	public static void registerToken(String symbol, String value)
	{
		tokens.add(new Tuple2<String, String>(symbol, value));
	}


	/**
	 * Gets an iterator over the tokens for replacement.
	 */
	static It<Tuple2<String, String>> getTokens()
	{
		return new It<Tuple2<String, String>>(tokens);
	}


}