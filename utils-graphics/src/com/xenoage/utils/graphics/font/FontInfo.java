package com.xenoage.utils.graphics.font;

import static com.xenoage.utils.base.NullUtils.notNull;
import static com.xenoage.utils.pdlib.PVector.pvec;

import com.xenoage.utils.base.annotations.MaybeEmpty;
import com.xenoage.utils.base.annotations.MaybeNull;
import com.xenoage.utils.base.annotations.NeverEmpty;
import com.xenoage.utils.base.annotations.NeverNull;
import com.xenoage.utils.pdlib.PVector;


/**
 * Information about a font.
 * 
 * This class allows to save multiple
 * font names/families and allows the attributes to be unset.
 * 
 * It is independent of a specific API like AWT or Android,
 * and can thus be used on each device. The factories in
 * the subpackages can be used to turn instances of this
 * class in real API-specific fonts.
 * 
 * @author Andreas Wenger
 */
public final class FontInfo
{
	
	private final PVector<String> families;
	private final Float size;
	private final FontStyle style;
	
	private static final PVector<String> defaultFamilies =
		pvec("Times New Roman", "Linux Libertine", "Times");
	private static final float defaultSize = 12;
	private static final FontStyle defaultFontStyle = FontStyle.normal;
	public static final FontInfo defaultValue = new FontInfo((String) null, null, null);

	
	public FontInfo(@MaybeNull @MaybeEmpty PVector<String> families,
		@MaybeNull Float size, @MaybeNull FontStyle style)
	{
		this.families = (families != null && families.size() > 0 ? families : null);
		this.size = size;
		this.style = style;
	}
	
	
	public FontInfo(@MaybeNull String family, @MaybeNull Float size, @MaybeNull FontStyle style)
	{
		this(family != null ? pvec(family) : null, size, style);
	}

	
	/** The list of families. The first entry is the preferred font, the alternative fonts
	 * can be found at the following entries. */
	@NeverEmpty public PVector<String> getFamilies()
	{
		return notNull(families, defaultFamilies);
	}

	
	/** The size of the font in pt. */
	public float getSize()
	{
		return notNull(size, defaultSize);
	}

	/** The style of the font */
	@NeverNull public FontStyle getStyle()
	{
		return notNull(style, defaultFontStyle);
	}
	
	
	@MaybeNull public PVector<String> getFamiliesNull()
	{
		return families;
	}

	
	@MaybeNull public Float getSizeNull()
	{
		return size;
	}


	@MaybeNull public FontStyle getStyleNull()
	{
		return style;
	}


	@Override public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((families == null) ? 0 : families.hashCode());
		result = prime * result + ((size == null) ? 0 : size.hashCode());
		result = prime * result + ((style == null) ? 0 : style.hashCode());
		return result;
	}


	@Override public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FontInfo other = (FontInfo) obj;
		if (families == null)
		{
			if (other.families != null)
				return false;
		}
		else if (!families.equals(other.families))
			return false;
		if (size == null)
		{
			if (other.size != null)
				return false;
		}
		else if (!size.equals(other.size))
			return false;
		if (style == null)
		{
			if (other.style != null)
				return false;
		}
		else if (!style.equals(other.style))
			return false;
		return true;
	}
	

}
