package com.xenoage.utils;

import com.xenoage.utils.jse.JsePlatformUtils;
import com.xenoage.utils.jse.io.DesktopIO;

/**
 * Bootstrap loader for {@link PlatformUtils}, called automatically
 * by {@link PlatformUtils#platformUtils()}.
 * 
 * @author Andreas Wenger
 */
public class PlatformUtilsInit {
	
	public static void init() {
		PlatformUtils.init(new JsePlatformUtils());
		DesktopIO.init("utils-test");
	}

}
