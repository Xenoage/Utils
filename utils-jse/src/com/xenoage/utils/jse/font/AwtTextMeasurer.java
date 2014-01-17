package com.xenoage.utils.jse.font;

import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;

import com.xenoage.utils.font.FontInfo;
import com.xenoage.utils.font.TextMeasurer;
import com.xenoage.utils.font.TextMetrics;
import com.xenoage.utils.math.Units;

/**
 * AWT implementation of a {@link TextMeasurer}.
 * 
 * @author Andreas Wenger
 */
public class AwtTextMeasurer
	implements TextMeasurer {

	@Override public TextMetrics measure(FontInfo font, String text) {
		Font awtFont = AwtFontUtils.toAwtFont(font); 
		return measure(awtFont, text);
	}
	
	public static TextMetrics measure(Font awtFont, String text) {
		TextLayout layout = new TextLayout(text, awtFont, new FontRenderContext(null, false, true));
		float ascent = Units.pxToMm(layout.getAscent(), 1);
		float descent = Units.pxToMm(layout.getDescent(), 1);
		float leading = Units.pxToMm(layout.getLeading(), 1);
		float width = Units.pxToMm((float) layout.getBounds().getWidth(), 1);
		return new TextMetrics(ascent, descent, leading, width);
	}

}
