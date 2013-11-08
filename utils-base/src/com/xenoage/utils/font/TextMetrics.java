package com.xenoage.utils.font;

/**
 * Metrics of text measured with a {@link TextMeasurer}.
 * 
 * @author Andreas Wenger
 */
public class TextMetrics {
	
	private float ascent, descent, leading, width;
	

	public TextMetrics(float ascent, float descent, float leading, float width) {
		this.ascent = ascent;
		this.descent = descent;
		this.leading = leading;
		this.width = width;
	}

	/**
	 * Gets the ascent of this font in mm.
	 */
	public float getAscent() {
		return ascent;
	}

	/**
	 * Gets the descent of this font in mm.
	 */
	public float getDescent() {
		return descent;
	}

	/**
	 * Gets the leading of this font in mm.
	 */
	public float getLeading() {
		return leading;
	}

	/**
	 * Measure the width of this text in mm.
	 */
	public float getWidth()  {
		return width;
	}

}
