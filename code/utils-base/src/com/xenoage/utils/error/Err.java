package com.xenoage.utils.error;

import static com.xenoage.utils.base.NullUtils.throwNullArg;

import com.xenoage.utils.base.annotations.NeverNull;
import com.xenoage.utils.log.Report;


/**
 * This class manages a single error handler for
 * non-verbose and quick access.
 *
 * @author Andreas Wenger
 */
public class Err
{
	
	private static ErrorProcessing instance = new NoErrorProcessing();
	
	
	/**
	 * Sets the current global {@link ErrorProcessing} instance.
	 */
	public static void init(@NeverNull ErrorProcessing errorProcessing)
	{
		throwNullArg(errorProcessing);
		instance = errorProcessing;
	}
	
	
	/**
	 * Gets the current global {@link ErrorProcessing} instance.
	 */
	public static ErrorProcessing getErrorProcessing()
	{
		return instance;
	}
	
	
	/**
	 * Handles the given error report, dependent on the registered {@link ErrorProcessing}.
	 */
	public static void handle(Report report)
	{
		instance.report(report);
	}

}
