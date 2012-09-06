package com.xenoage.utils.swing.font;

import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;

import com.xenoage.utils.graphics.Units;
import com.xenoage.utils.graphics.font.FontInfo;
import com.xenoage.utils.graphics.font.TextMeasurer;


/**
 * This class provides a method to calculate the width
 * of a given String with a given font.
 * 
 * @author Andreas Wenger
 * @author Uli Teschemacher
 */
public class AWTTextMeasurer
	implements TextMeasurer
{
	
	private TextLayout layout;
	
	
	/**
	 * Creates a measurer for the given {@link FontInfo}.
	 */
	public AWTTextMeasurer(FontInfo font, String text)
	{
		init(font, text);
	}
	
	
	/**
	 * Creates a measurer for the given {@link FontInfo}.
	 */
	public AWTTextMeasurer(Font font, String text)
	{
		layout = new TextLayout(text, font,
			new FontRenderContext(null, false, true));
	}
	
	
	@Override public void init(FontInfo font, String text)
	{
		layout = new TextLayout(text, AWTFontUtils.createFont(font),
			new FontRenderContext(null, false, true));
	}
	
	
	/**
	 * Gets the ascent of this font in mm.
	 */
	@Override public float getAscent()
	{
		return Units.pxToMm(layout.getAscent(), 1);
	}
	
	
	/**
	 * Gets the descent of this font in mm.
	 */
	@Override public float getDescent()
	{
		return Units.pxToMm(layout.getDescent(), 1);
	}
	
	
	/**
	 * Gets the leading of this font in mm.
	 */
	@Override public float getLeading()
	{
		return Units.pxToMm(layout.getLeading(), 1);
	}
	
	
	/**
	 * Measure the width of this text in mm.
	 */
	@Override public float getWidth()
	{
		return Units.pxToMm((float)layout.getBounds().getWidth(), 1);
	}

	
}
