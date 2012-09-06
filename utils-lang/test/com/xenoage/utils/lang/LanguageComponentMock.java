package com.xenoage.utils.lang;

import com.xenoage.utils.lang.LanguageComponent;


/**
 * An simple {@link LanguageComponent} for testing.
 * 
 * @author Andreas Wenger
 */
public class LanguageComponentMock
	implements LanguageComponent
{
	
	public int languageChangedCounter = 0;
	

	@Override public void languageChanged()
	{
		languageChangedCounter++;
	}

}
