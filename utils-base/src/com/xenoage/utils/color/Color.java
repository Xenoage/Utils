package com.xenoage.utils.color;

import static com.xenoage.utils.math.MathUtils.clamp;

/**
 * A platform independent way to store a color.
 * All values are between 0 and 255.
 * 
 * @author Andreas Wenger
 */
public class Color {

	public static final Color black = new Color(0, 0, 0);
	public static final Color blue = new Color(0, 0, 255);
	public static final Color gray = new Color(128, 128, 128);
	public static final Color green = new Color(0, 255, 0);
	public static final Color lightGray = new Color(192, 192, 192);
	public static final Color red = new Color(255, 0, 0);
	public static final Color white = new Color(255, 255, 255);
	public static final Color yellow = new Color(255, 255, 0);

	public final int r, g, b, a;


	public Color(int r, int g, int b, int a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	public Color(int r, int g, int b) {
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
	public Color addWhite(int summand) {
		return new Color(clamp(r + summand, 0, 255), clamp(g + summand, 0, 255), clamp(b + summand, 0,
			255));
	}

	public int getRGB() {
		return ((a & 0xFF) << 24) | ((r & 0xFF) << 16) | ((g & 0xFF) << 8) | ((b & 0xFF) << 0);
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
		else if (o instanceof Color) {
			Color c = (Color) o;
			return c.r == r && c.b == b && c.g == g && c.a == a;
		}
		else {
			return super.equals(o);
		}
	}

}
