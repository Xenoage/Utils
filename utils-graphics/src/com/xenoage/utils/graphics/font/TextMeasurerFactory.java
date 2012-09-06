package com.xenoage.utils.graphics.font;



/**
 * This class provides a factory for creating {@link TextMeasurer}s.
 * 
 * @author Andreas Wenger
 */
public interface TextMeasurerFactory
{

	public TextMeasurer textMeasurer(FontInfo font, String text);

}
