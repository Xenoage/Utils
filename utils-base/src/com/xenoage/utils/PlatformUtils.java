package com.xenoage.utils;

import static com.xenoage.utils.CheckUtils.checkArgsNotNull;

import java.io.IOException;
import java.util.List;

import com.xenoage.utils.annotations.NonNull;
import com.xenoage.utils.font.TextMeasurer;
import com.xenoage.utils.io.FilesystemInput;
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
 * implementation of the current platform. If this is not done,
 * this class will try to load the class <code>{@value #bootstrapClassName}</code>
 * and call its <code>init</code> method. If not successfull, an
 * {@link IllegalStateException} is thrown.
 * 
 * @author Andreas Wenger
 */
public abstract class PlatformUtils {

	public static final String bootstrapClassName = "com.xenoage.utils.PlatformUtilsInit";
	
	private static PlatformUtils platformUtils = null;
	
	/**
	 * Gets the current {@link PlatformUtils}.
	 */
	@NonNull public static PlatformUtils platformUtils() {
		if (platformUtils == null) {
			//try to load helper class to init this class
			try {
				Class<?> cls = Class.forName(bootstrapClassName);
				cls.getMethod("init").invoke(null);
			} catch (Exception ex) {
			}
			//successfull?
			if (platformUtils == null) {
				throw new IllegalStateException(PlatformUtils.class.getName() + " not initialized");
			}
		}
		return platformUtils;
	}
	
	/**
	 * Initializes this class with the given platform-specific implementation.
	 * Do not call this method directly. Use the <code>init</code> methods of the specific implementations.
	 */
	public static void init(PlatformUtils platformUtils) {
		checkArgsNotNull(platformUtils);
		PlatformUtils.platformUtils = platformUtils;
	}
	
	/**
	 * Gets the current stack trace.
	 * If this platform is not able to retrieve a stack trace, null is returned.
	 */
	public abstract List<StackTraceElement> getCurrentStackTrace();
	
	/**
	 * Gets the stack trace of the given Throwable as a string.
	 * If this platform is not able to retrieve it, null is returned.
	 */
	public abstract String getStackTraceString(Throwable throwable);
	
	/**
	 * Gets the caller of a method. If unknown, null is returned.
	 * @param stackTrace  The stack trace, or null if unknown (then, null is returned)
	 * @param level       The level of the caller: 1 = the caller, 2 = the caller of the caller, ...
	 *                    For example, when A.a() calls B.b(), and in B.b() you want to know who
	 *                    called B.b(), use getCaller(1).
	 */
	public abstract StackTraceElement getCaller(int level);
	
	/**
	 * Returns the {@link TextMeasurer}.
	 */
	@NonNull public abstract TextMeasurer getTextMeasurer();
	
	/**
	 * Gets the input filesystem for this platform.
	 */
	@NonNull public abstract FilesystemInput getFilesystemInput();
	
	/**
	 * Convenience method for opening an {@link InputStream} for the file at the given relative path.
	 * If this platform is not able to retrieve it, e.g. because it does not exist, null is returned.
	 */
	@NonNull public abstract InputStream openFile(String filePath)
		throws IOException;
	
	/**
	 * Returns an {@link XmlReader} for the given {@link InputStream} for this platform.
	 */
	@NonNull public abstract XmlReader createXmlReader(InputStream inputStream);
	
	/**
	 * Returns an {@link XmlWriter} for the given {@link OutputStream} for this platform.
	 * If this platform is not able to retrieve it, null is returned.
	 */
	@NonNull public abstract XmlWriter createXmlWriter(OutputStream outputStream);
	
	/**
	 * Returns an {@link ZipReader} for the given {@link InputStream} for this platform.
	 * If this platform is not able to retrieve it, e.g. because it does not exist, null is returned.
	 */
	@NonNull public abstract ZipReader createZipReader(InputStream inputStream)
		throws IOException;
	
}
