package com.xenoage.utils;

import java.util.List;

/**
 * Useful methods to work with the stack frame.
 * 
 * @author Andreas Wenger
 */
public class StackUtils {

	/**
	 * Gets the caller of a method. If unknown, null is returned.
	 * @param stackTrace  the stack trace, or null if unknown (then, null is returned)
	 * @param level       the level of the caller: 1 = the caller, 2 = the caller of the caller, ...
	 */
	public static StackTraceElement getCaller(List<StackTraceElement> stackTrace, int level) {
		if (stackTrace == null)
			return null;
		if (stackTrace.size() > 1 + level)
			return stackTrace.get(1 + level);
		else
			return null;
	}

}
