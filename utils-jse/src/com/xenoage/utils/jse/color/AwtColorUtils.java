package com.xenoage.utils.jse.color;

import java.awt.Color;

import com.xenoage.utils.color.ColorInfo;

/**
 * Useful methods for working with AWT colors.
 * 
 * @author Andreas Wenger
 */
public class AwtColorUtils {

	/**
	 * Creates a new {@link ColorInfo}.
	 * @param color      the AWT color
	 */
	public static ColorInfo createColorInfo(Color color) {
		return new ColorInfo(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
	}

	/**
	 * Gets the AWT {@link Color} from the given {@link ColorInfo}.
	 */
	public static Color createColor(ColorInfo colorInfo) {
		return new Color(colorInfo.r, colorInfo.g, colorInfo.b, colorInfo.a);
	}

}
