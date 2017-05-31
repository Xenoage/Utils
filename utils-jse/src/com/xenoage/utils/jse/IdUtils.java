package com.xenoage.utils.jse;

import java.util.UUID;

/**
 * Generate IDs.
 *
 * @author Andreas Wenger
 */
public class IdUtils {

	public static String uuid() {
		return UUID.randomUUID().toString();
	}

}
