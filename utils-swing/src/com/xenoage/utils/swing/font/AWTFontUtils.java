package com.xenoage.utils.swing.font;

import static com.xenoage.utils.pdlib.PVector.pvec;

import java.awt.Font;

import com.xenoage.utils.graphics.font.FontInfo;
import com.xenoage.utils.graphics.font.FontReplacements;
import com.xenoage.utils.graphics.font.FontStyle;
import com.xenoage.utils.pdlib.PVector;


/**
 * Useful methods for working with AWT fonts.
 * 
 * @author Andreas Wenger
 */
public class AWTFontUtils
{
	
	private static final String defaultFamily = "Serif"; //should always be available in AWT
	
	
	/**
	 * Creates a new {@link FontInfo}.
	 * @param font      the AWT font
	 */
	public static FontInfo createFontInfo(Font font)
	{
		PVector<String> families = pvec(font.getFamily());
		FontStyle style = FontStyle.normal;
		if (font.isBold())
			style = style.with(FontStyle.Bold, true);
		if (font.isItalic())
			style = style.with(FontStyle.Italic, true);
		return new FontInfo(families, font.getSize2D(), style);
	}
	
	
	/**
	 * Gets the {@link Font} that matches best to the values of the
	 * given {@link FontInfo} object.
	 */
	public static Font createFont(FontInfo fontInfo)
	{
		//find an appropriate family:
		//go through all families, until a known family is found. if no family
		//is found, look for replacements. If also not found, take the base font family.
		String fontFamily = null;
		for (String family : fontInfo.getFamilies())
		{
			if (FontUtils.getInstance().isFontFamilySupported(family))
			{
				fontFamily = family;
				break;
			}
		}
		if (fontFamily == null)
		{
			for (String family : fontInfo.getFamilies())
			{
				String replacement = FontReplacements.getInstance().getReplacement(family);
				if (replacement != family && FontUtils.getInstance().isFontFamilySupported(replacement))
				{
					fontFamily = replacement;
					break;
				}
			}
		}
		if (fontFamily == null)
		{
			fontFamily = defaultFamily;
		}
		
		//size
		float fontSize = fontInfo.getSize();
		
		//style
		FontStyle style = fontInfo.getStyle();
		int fontStyle = Font.PLAIN;
		fontStyle |= (style.isSet(FontStyle.Bold) ? Font.BOLD : 0);
		fontStyle |= (style.isSet(FontStyle.Italic) ? Font.ITALIC : 0);
		
		return new Font(fontFamily, fontStyle, Math.round(fontSize));
	}

}
