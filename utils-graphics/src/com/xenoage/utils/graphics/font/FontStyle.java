package com.xenoage.utils.graphics.font;

import java.util.EnumSet;


/**
 * Enumeration of different font styles like bold or italic.
 * 
 * In earlier versions, an {@link EnumSet} was used. It turned out,
 * that it doesn't work on Android 2.
 * 
 * @author Andreas Wenger
 */
public class FontStyle
{
	
	public static final int Bold = 1<<0;
	public static final int Italic = 1<<1;
	public static final int Underline = 1<<2;
	public static final int Strikethrough = 1<<3;
	
	public static final FontStyle normal = new FontStyle(0);
	
	private final int style;
	
	
	/* Move into JavaSE utils project
	public static FontStyle fromFont(Font font)
	{
		int style = 0;
		if (font.isBold()) style |= Bold;
		if (font.isItalic()) style |= Italic;
		return new FontStyle(style);
	} */
	
	
	public static FontStyle create(int... flags)
	{
		int style = 0;
		for (int flag : flags)
			style |= flag;
		return new FontStyle(style);
	}
	
	
	private FontStyle(int style)
	{
		this.style = style;
	}
	
	
	/**
	 * Sets the given flag, like Bold, to true.
	 */
	public FontStyle with(int flag)
	{
		return with(flag, true);
	}
	
	
	/**
	 * Sets the given flag, like Bold, to the given value.
	 */
	public FontStyle with(int flag, boolean value)
	{
		int style = this.style;
		if (value)
			style |= flag;
		else
			style &= ~flag;
		return new FontStyle(style);
	}
	
	
	public boolean isSet(int flag)
	{
		return (style & flag) > 0;
	}
	
}
