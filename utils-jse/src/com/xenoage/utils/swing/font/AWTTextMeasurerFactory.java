package com.xenoage.utils.swing.font;

import com.xenoage.utils.graphics.font.FontInfo;
import com.xenoage.utils.graphics.font.TextMeasurerFactory;


/**
 * This class provides a factory for creating {@link AWTTextMeasurer}s.
 * 
 * @author Andreas Wenger
 */
public final class AWTTextMeasurerFactory
	implements TextMeasurerFactory
{
	
	@Override public AWTTextMeasurer textMeasurer(FontInfo font, String text)
	{
		return new AWTTextMeasurer(font, text);
	}

}
