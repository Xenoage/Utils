package com.xenoage.utils.color;

import static com.xenoage.utils.color.ColorUtils.getColor;
import static com.xenoage.utils.color.ColorUtils.getHex;
import static com.xenoage.utils.color.ColorUtils.toHTMLColor;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.xenoage.utils.color.Color;
import com.xenoage.utils.color.ColorUtils;

/**
 * Test cases for the {@link ColorUtils} class.
 * 
 * @author Andreas Wenger
 */
public class ColorUtilsTest {

	@Test public void getColorTest() {
		Color c = new Color(0, 0, 1, 0);
		assertEquals(c, getColor("#00000001"));
		c = new Color(0, 0, 1, 0xFF);
		assertEquals(c, getColor("#FF000001"));
		assertEquals(c, getColor("#000001"));
		c = new Color(0x21, 0x16, 0x0B, 0x2C);
		assertEquals(c, getColor("#2C21160B"));
		assertEquals(c, getColor("#2c21160b"));
	}

	@Test public void getHexTest() {
		Color c = new Color(0, 0, 1, 0);
		assertEquals("#00000001", getHex(c));
		c = new Color(0, 0, 1, 0xFF);
		assertEquals("#000001", getHex(c));
		c = new Color(0x21, 0x16, 0x0B, 0x2C);
		assertEquals("#2c21160b", getHex(c));
	}

	@Test public void toHTMLColorTest() {
		assertEquals("#000000", toHTMLColor(Color.black));
		assertEquals("#ffffff", toHTMLColor(Color.white));
		assertEquals("#ffffff", toHTMLColor(new Color(255, 255, 255, 200)));
		assertEquals("#ff0080", toHTMLColor(new Color(255, 0, 128, 100)));
		assertEquals("#010203", toHTMLColor(new Color(1, 2, 3, 100)));
	}

}
