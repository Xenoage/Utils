package com.xenoage.utils.math;

/**
 * Some useful methods with random numbers.
 * 
 * @author Andreas Wenger
 */
public class RandomUtils {

	public static int randomInt(int min, int max) {
		return min + (int) (Math.random() * (max - min));
	}

}
