package com.xenoage.utils.callback;


/**
 * Callback methods for an asynchronous call.
 * 
 * @author Andreas Wenger
 */
public interface AsyncCallback<T> {

	/**
	 * This method is called when the operation was successful.
	 * @param data  the resulting data
	 */
	public void onSuccess(T data);
	
	/**
	 * This method is called when the operation was not successful.
	 * @param ex  details about the error
	 */
	public void onFailure(Exception ex);
	
}
