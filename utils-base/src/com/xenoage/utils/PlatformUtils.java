package com.xenoage.utils;

import static com.xenoage.utils.CheckUtils.checkArgsNotNull;

import java.util.List;

import com.xenoage.utils.annotations.NonNull;
import com.xenoage.utils.font.TextMeasurer;

/**
 * General methods which implementation is platform dependent.
 * 
 * At the beginning of the startup of an application, call the
 * {@link #init(PlatformUtils)} method, using the
 * implementation of the current platform.
 * 
 * GOON: must be initialized, otherwise throw exception!
 * 
 * @author Andreas Wenger
 */
public class PlatformUtils {

	@NonNull private static PlatformUtils platformUtils = new PlatformUtils();
	
	/**
	 * Gets the current PlatformUtils.
	 */
	public static PlatformUtils platformUtils() {
		return platformUtils;
	}
	
	/**
	 * Initializes this class with the given platform-specific implementation.
	 */
	public static void init(PlatformUtils platformUtils) {
		checkArgsNotNull(platformUtils);
		PlatformUtils.platformUtils = platformUtils;
	}
	
	/**
	 * Gets the current stack trace.
	 * If this platform is not able to retrieve a stack trace, null is returned.
	 */
	public List<StackTraceElement> getCurrentStackTrace() {
		return null;
	}
	
	/**
	 * Gets the stack trace of the given Throwable as a string.
	 * If this platform is not able to retrieve it, null is returned.
	 */
	public String getStackTraceString(Throwable throwable) {
		return null;
	}
	
	/**
	 * Gets the caller of a method. If unknown, null is returned.
	 * @param stackTrace  The stack trace, or null if unknown (then, null is returned)
	 * @param level       The level of the caller: 1 = the caller, 2 = the caller of the caller, ...
	 *                    For example, when A.a() calls B.b(), and in B.b() you want to know who
	 *                    called B.b(), use getCaller(1).
	 */
	public StackTraceElement getCaller(int level) {
		return null;
	}
	
	/**
	 * Returns the {@link TextMeasurer}.
	 * If this platform is not able to retrieve it, null is returned.
	 */
	public TextMeasurer getTextMeasurer() {
		return null;
	}
	
}
