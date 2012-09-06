package com.xenoage.utils.base;


/**
 * Useful methods to work with the stack frame.
 * 
 * @author Andreas Wenger
 */
public class StackUtils
{
	
	/**
	 * Gets the caller of a method. For example, if you call this method from
	 * <code>Target.target()</code>, and <code>Caller.caller()</code> was used to
	 * call the <code>target()</code> method, then the call in this <code>Caller</code>
	 * class would be returned.
	 * If unknown, null is returned.
	 */
	public static StackTraceElement getCaller()
	{
		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		if (stackTraceElements.length > 2)
			return stackTraceElements[2];
		else
			return null;
	}
	
	/**
	 * Gets the caller of the caller of a method. For example, if you call this method from
	 * <code>Target2.target2()</code>, and <code>Target1.target1()</code> was used to call this
	 * method, which was called from <code>Caller.caller()</code>, then the call in
	 * this <code>Caller</code> class would be returned.
	 * If unknown, null is returned.
	 */
	public static StackTraceElement getCallerCaller()
	{
		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		if (stackTraceElements.length > 3)
			return stackTraceElements[3];
		else
			return null;
	}

}
