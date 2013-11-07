package com.xenoage.utils.font;

import com.xenoage.utils.annotations.Const;

/**
 * Enumeration of different font styles like bold or italic.
 * 
 * @author Andreas Wenger
 */
@Const public class FontStyle {

	public static final int Bold = 1 << 0;
	public static final int Italic = 1 << 1;
	public static final int Underline = 1 << 2;
	public static final int Strikethrough = 1 << 3;

	public static final FontStyle normal = new FontStyle(0);

	private final int style;

	
	public static FontStyle fontStyle(int... flags) {
		int style = 0;
		for (int flag : flags)
			style |= flag;
		if (style == 0)
			return normal;
		else
			return new FontStyle(style);
	}

	private FontStyle(int style) {
		this.style = style;
	}

	/**
	 * Sets the given flag, like Bold, to true.
	 */
	public FontStyle with(int flag) {
		return with(flag, true);
	}

	/**
	 * Sets the given flag, like Bold, to the given value.
	 */
	public FontStyle with(int flag, boolean value) {
		int style = this.style;
		if (value)
			style |= flag;
		else
			style &= ~flag;
		return new FontStyle(style);
	}

	/**
	 * Returns true, when the given flag is set.
	 */
	public boolean isSet(int flag) {
		return (style & flag) > 0;
	}

}
