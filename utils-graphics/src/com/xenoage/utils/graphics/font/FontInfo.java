package com.xenoage.utils.graphics.font;

import static com.xenoage.utils.base.NullUtils.notNull;
import static com.xenoage.utils.base.collections.CollectionUtils.alist;
import static com.xenoage.utils.pdlib.IVector.ivec;

import java.util.List;

import com.xenoage.utils.base.annotations.MaybeEmpty;
import com.xenoage.utils.base.annotations.MaybeNull;
import com.xenoage.utils.base.annotations.NonEmpty;
import com.xenoage.utils.base.annotations.NonNull;
import com.xenoage.utils.pdlib.IVector;


/**
 * Information about a font.
 * 
 * This class allows to save multiple font names/families and allows the attributes to be unset.
 * 
 * It is independent of a specific API like AWT or Android, and can thus be used on each device. The factories in the
 * subpackages can be used to turn instances of this class in real API-specific fonts.
 * 
 * @author Andreas Wenger
 */
public final class FontInfo {

	private final List<String> families;
	private final Float size;
	private final FontStyle style;

	private static final List<String> defaultFamilies = alist("Times New Roman", "Linux Libertine", "Times");
	private static final float defaultSize = 12;
	private static final FontStyle defaultFontStyle = FontStyle.normal;
	public static final FontInfo defaultValue = new FontInfo((String) null, null, null);


	/**
	 * Creates a new {@link FontInfo} with the given attributes.
	 */
	public FontInfo(@MaybeNull @MaybeEmpty IVector<String> families, @MaybeNull Float size,
		@MaybeNull FontStyle style) {
		this.families = (families != null && families.size() > 0 ? families : null);
		this.size = size;
		this.style = style;
	}


	/**
	 * Creates a new {@link FontInfo} with the given attributes.
	 */
	public FontInfo(@MaybeNull String family, @MaybeNull Float size, @MaybeNull FontStyle style) {
		this(family != null ? ivec(family).close() : null, size, style);
	}


	/** 
	 * The list of families, or the default families if unset.
	 * The first entry is the preferred font, the alternative fonts can be found at the following entries.
	 */
	@NonEmpty public List<String> getFamilies() {
		return notNull(families, defaultFamilies);
	}


	/**
	 * The size of the font in pt, or the default size if unset.
	 */
	public float getSize() {
		return notNull(size, defaultSize);
	}


	/**
	 * The style of the font, or the default style if unset.
	 */
	@NonNull public FontStyle getStyle() {
		return notNull(style, defaultFontStyle);
	}


	/**
	 * The list of families, or null if unset.
	 */
	@MaybeNull public List<String> getFamiliesOrNull() {
		return families;
	}


	/**
	 * The size of the font in pt, or null if unset.
	 */
	@MaybeNull public Float getSizeOrNull() {
		return size;
	}


	/**
	 * The style of the font, or null if unset.
	 */
	@MaybeNull public FontStyle getStyleOrNull() {
		return style;
	}


	@Override public int hashCode() { //auto-generated
		final int prime = 31;
		int result = 1;
		result = prime * result + ((families == null) ? 0 : families.hashCode());
		result = prime * result + ((size == null) ? 0 : size.hashCode());
		result = prime * result + ((style == null) ? 0 : style.hashCode());
		return result;
	}


	@Override public boolean equals(Object obj) { //auto-generated
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FontInfo other = (FontInfo) obj;
		if (families == null) {
			if (other.families != null)
				return false;
		} else if (!families.equals(other.families))
			return false;
		if (size == null) {
			if (other.size != null)
				return false;
		} else if (!size.equals(other.size))
			return false;
		if (style == null) {
			if (other.style != null)
				return false;
		} else if (!style.equals(other.style))
			return false;
		return true;
	}


}
