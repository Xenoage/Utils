package com.xenoage.utils.jse.log;

import static com.xenoage.utils.jse.io.DesktopIO.desktopIO;
import static com.xenoage.utils.log.Log.log;
import static com.xenoage.utils.log.Report.remark;
import static com.xenoage.utils.log.Report.warning;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.xenoage.utils.PlatformUtils;
import com.xenoage.utils.jse.JsePlatformUtils;
import com.xenoage.utils.jse.io.DesktopIO;
import com.xenoage.utils.jse.io.JseFileUtils;
import com.xenoage.utils.log.Log;

/**
 * Tests for {@link DesktopLogProcessing}.
 *
 * @author Andreas Wenger
 */
public class DesktopLogProcessingTest {

	private String logFilename;


	@Before public void setUp() {
		PlatformUtils.init(JsePlatformUtils.instance);
		DesktopIO.initTest();
		logFilename = "data/test/temp.log";
	}

	@Test public void testLogging() {
		desktopIO().deleteFile(logFilename, true);

		//create log file
		Log.init(new DesktopLogProcessing(logFilename, "test"));
		String message = "This is a message";
		log(remark(message));
		for (int i = 0; i < 100; i++)
			log(remark("Another row (" + i + ")"));
		String warning = "This is a message";
		log(warning(warning));

		//check logfile
		assertTrue(desktopIO().existsFile(logFilename));
		String logText = JseFileUtils.readFile(desktopIO().findFile(logFilename));
		assertNotNull(logText);
		assertTrue(logText.contains(message));
		assertTrue(logText.contains(warning));
	}

	@After public void cleanUp() {
		Log.close();
		//desktopIO().deleteFile(logFilename, true);
	}

}
