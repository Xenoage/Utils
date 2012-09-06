package com.xenoage.utils.graphics.font;


/**
 * This class provides a method to measure Strings.
 * Normally, the {@link AWTTextMeasurer} implementation will
 * be used. For devices that don't support AWT (like Android),
 * there may be different implementations.
 * 
 * @author Andreas Wenger
 */
public interface TextMeasurer
{


	/**
	 * Initializes a {@link TextMeasurer} for the given {@link FontInfo} and text.
	 */
	public void init(FontInfo font, String text);


	/**
	 * Gets the ascent of this font in mm.
	 */
	public float getAscent();


	/**
	 * Gets the descent of this font in mm.
	 */
	public float getDescent();


	/**
	 * Gets the leading of this font in mm.
	 */
	public float getLeading();


	/**
	 * Measure the width of this text in mm.
	 */
	public float getWidth();


}
