package com.xenoage.utils.gwt;

import static com.xenoage.utils.collections.CollectionUtils.alist;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

import com.xenoage.utils.PlatformUtils;
import com.xenoage.utils.font.TextMeasurer;
import com.xenoage.utils.io.FilesystemInput;
import com.xenoage.utils.io.InputStream;
import com.xenoage.utils.io.OutputStream;
import com.xenoage.utils.io.ZipReader;
import com.xenoage.utils.jse.font.AwtTextMeasurer;
import com.xenoage.utils.jse.io.DesktopIO;
import com.xenoage.utils.jse.io.JseInputStream;
import com.xenoage.utils.jse.io.JseOutputStream;
import com.xenoage.utils.jse.io.JseZipReader;
import com.xenoage.utils.jse.xml.JseXmlReader;
import com.xenoage.utils.jse.xml.JseXmlWriter;
import com.xenoage.utils.xml.XmlReader;
import com.xenoage.utils.xml.XmlWriter;

/**
 * Google Web Toolkit specific {@link PlatformUtils} implementation.
 * 
 * @author Andreas Wenger
 */
public class GwtPlatformUtils
	extends PlatformUtils {

	private static GwtPlatformUtils instance = null;

	private GwtIO gwtIO = null;
	
	/**
	 * Initializes the {@link PlatformUtils} class for usage within a
	 * GWT environment (using an instance of {@link GwtPlatformUtils}).
	 */
	public static void init() {
		instance = new GwtPlatformUtils();
		instance.gwtIO = new GwtIO();
		PlatformUtils.init(instance);
	}
	
	/**
	 * Gets the {@link GwtIO} instance.
	 * If the {@link GwtPlatformUtils} are not initialized yet,
	 * they are initialized.
	 */
	public static GwtIO gwtIO() {
		if (instance == null)
			init();
		return instance.gwtIO;
	}

	/**
	 * Unsupported in GWT. Returns null.
	 */
	@Override public List<StackTraceElement> getCurrentStackTrace() {
		return null;
	}

	/**
	 * Unsupported in GWT. Returns null.
	 */
	@Override public String getStackTraceString(Throwable throwable) {
		return null;
	}

	/**
	 * Unsupported in GWT. Returns null.
	 */
	@Override public StackTraceElement getCaller(int level) {
		return null;
	}

	@Override public TextMeasurer getTextMeasurer() {
		return textMeasurer;
	}
	
	@Override public FilesystemInput getFilesystemInput() {
		return gwtIO;
	}

	@Override public InputStream openFile(String filePath)
		throws IOException {
		return gwtIO().openFile(filePath);
	}

	@Override public XmlReader createXmlReader(InputStream inputStream) {
		return new GwtXmlReader(new GwtInputStream(inputStream));
	}

	/**
	 * Unsupported in GWT. Returns null.
	 */
	@Override public XmlWriter createXmlWriter(OutputStream outputStream) {
		return null;
	}

	/**
	 * Unsupported in GWT. Returns null.
	 */
	@Override public ZipReader createZipReader(InputStream inputStream) {
		return null;
	}

}
