package com.xenoage.utils.color;

import static com.xenoage.utils.math.MathUtils.clamp;

/**
 * A platform independent way to store a color.
 * All values are between 0 and 255.
 * 
 * @author Andreas Wenger
 */
public class ColorInfo {

	public static final ColorInfo black = new ColorInfo(0, 0, 0);
	public static final ColorInfo blue = new ColorInfo(0, 0, 255);
	public static final ColorInfo gray = new ColorInfo(128, 128, 128);
	public static final ColorInfo green = new ColorInfo(0, 255, 0);
	public static final ColorInfo lightGray = new ColorInfo(192, 192, 192);
	public static final ColorInfo red = new ColorInfo(255, 0, 0);
	public static final ColorInfo white = new ColorInfo(255, 255, 255);
	public static final ColorInfo yellow = new ColorInfo(255, 255, 0);

	public final int r, g, b, a;


	public ColorInfo(int r, int g, int b, int a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	public ColorInfo(int r, int g, int b) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = 255;
	}

	/**
	 * Adds the given value to the red, green and blue color. The values
	 * are clamped to the range between 0 and 255. The given summand may also be negative
	 * to remove light.
	 */
	public ColorInfo addWhite(int summand) {
		return new ColorInfo(clamp(r + summand, 0, 255), clamp(g + summand, 0, 255),
			clamp(b + summand, 0, 255));
	}

	@Override public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + a;
		result = prime * result + b;
		result = prime * result + g;
		result = prime * result + r;
		return result;
	}

	@Override public boolean equals(Object o) {
		if (this == o)
			return true;
		else if (o == null)
			return false;
		else if (o instanceof ColorInfo) {
			ColorInfo c = (ColorInfo) o;
			return c.r == r && c.b == b && c.g == g && c.a == a;
		}
		else {
			return super.equals(o);
		}
	}

}
