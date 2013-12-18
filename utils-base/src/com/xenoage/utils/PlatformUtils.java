package com.xenoage.utils;

import static com.xenoage.utils.CheckUtils.checkArgsNotNull;

import java.io.IOException;
import java.util.List;

import com.xenoage.utils.annotations.NonNull;
import com.xenoage.utils.font.TextMeasurer;
import com.xenoage.utils.io.InputStream;
import com.xenoage.utils.io.OutputStream;
import com.xenoage.utils.io.ZipReader;
import com.xenoage.utils.xml.XmlReader;
import com.xenoage.utils.xml.XmlWriter;

/**
 * General methods which implementation is platform dependent.
 * 
 * At the beginning of the startup of an application, call the
 * {@link #init(PlatformUtils)} method, using the
 * implementation of the current platform.
 * 
 * @author Andreas Wenger
 */
public abstract class PlatformUtils {

	@NonNull private static PlatformUtils platformUtils = null;
	
	/**
	 * Gets the current PlatformUtils.
	 */
	public static PlatformUtils platformUtils() {
		if (platformUtils == null)
			throw new IllegalStateException(PlatformUtils.class.getName() + " not initialized");
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
	
	/**
	 * Opens an {@link InputStream} for the file at the given relative path.
	 * If this platform is not able to retrieve it, null is returned.
	 */
	public InputStream openInputStream(String filePath)
		throws IOException {
		return null;
	}
	
	/**
	 * Returns an {@link XmlReader} for the given {@link InputStream} for this platform.
	 * If this platform is not able to retrieve it, null is returned.
	 */
	public XmlReader createXmlReader(InputStream inputStream) {
		return null;
	}
	
	/**
	 * Returns an {@link XmlWriter} for the given {@link OutputStream} for this platform.
	 * If this platform is not able to retrieve it, null is returned.
	 */
	public XmlWriter createXmlWriter(OutputStream outputStream) {
		return null;
	}
	
	/**
	 * Returns an {@link ZipReader} for the given {@link InputStream} for this platform.
	 * If this platform is not able to retrieve it, null is returned.
	 */
	public ZipReader createZipReader(InputStream inputStream)
		throws IOException {
		return null;
	}
	
}
