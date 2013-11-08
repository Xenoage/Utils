package com.xenoage.utils.jse;

import static com.xenoage.utils.collections.CollectionUtils.alist;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

import com.xenoage.utils.PlatformUtils;
import com.xenoage.utils.font.TextMeasurer;
import com.xenoage.utils.jse.font.AwtTextMeasurer;

/**
 * Java SE specific {@link PlatformUtils} implementation.
 * 
 * @author Andreas Wenger
 */
public class JsePlatformUtils
	extends PlatformUtils {

	public static final JsePlatformUtils instance = new JsePlatformUtils();

	private AwtTextMeasurer textMeasurer = new AwtTextMeasurer();


	@Override public List<StackTraceElement> getCurrentStackTrace() {
		return alist(Thread.currentThread().getStackTrace());
	}

	@Override public String getStackTraceString(Throwable throwable) {
		final Writer result = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(result);
		throwable.printStackTrace(printWriter);
		return result.toString();
	}

	@Override public StackTraceElement getCaller(int level) {
		List<StackTraceElement> stackTrace = getCurrentStackTrace();
		if (stackTrace.size() > 2 + level)
			return stackTrace.get(2 + level);
		else
			return null;
	}

	@Override public TextMeasurer getTextMeasurer() {
		return textMeasurer;
	}

}
