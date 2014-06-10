package com.xenoage.utils.gwt.font;

import com.xenoage.utils.font.FontInfo;
import com.xenoage.utils.font.TextMeasurer;
import com.xenoage.utils.font.TextMetrics;

/**
 * GWT implementation of a {@link TextMeasurer}.
 * Currently just a dummy, which returns always 0.
 * 
 * @author Andreas Wenger
 */
public class GwtTextMeasurer
	implements TextMeasurer {

	@Override public TextMetrics measure(FontInfo font, String text) {
		return new TextMetrics(0, 0, 0, 0); //GOON
	}

}
