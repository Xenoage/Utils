package com.xenoage.utils.jse.color;

import com.xenoage.utils.color.Color;


/**
 * Useful methods for working with AWT colors.
 * 
 * @author Andreas Wenger
 */
public class AwtColorUtils {

	/**
	 * Gets the {@link Color} from the given AWT color.
	 * @param awtColor  the AWT color
	 */
	public static Color fromAwtColor(java.awt.Color awtColor) {
		return new Color(awtColor.getRed(), awtColor.getGreen(), awtColor.getBlue(), awtColor.getAlpha());
	}

	/**
	 * Gets the AWT color from the given {@link Color}.
	 */
	public static java.awt.Color toAwtColor(Color color) {
		return new java.awt.Color(color.r, color.g, color.b, color.a);
	}
	
	/**
	 * Returns a color based on the given color, using the given alpha value.
	 */
	public static java.awt.Color withAlpha(java.awt.Color color, int alpha) {
		return new java.awt.Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
	}

}
