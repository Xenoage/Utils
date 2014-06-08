package com.xenoage.utils.gwt.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.xenoage.utils.io.InputStream;

/**
 * {@link InputStream} implementation of GWT.
 * 
 * It works on the client side by using a {@link RequestBuilder},
 * but the methods are blocking. For network applications, this is
 * bad practice in general, but to comply to the interface, the methods
 * have to be blocking. Maybe this can be optimized in the future.
 * 
 * Currently, this input stream only works with text files.
 * 
 * @author Andreas Wenger
 */
public class GwtInputStream
	implements InputStream {

	public String data = null; //TODO: not public!!
	private ByteArrayInputStream stream = null;
	
	
	/**
	 * Opens the given file. It is immediately read and the method
	 * returns not before an error occurs or before the file
	 * is completely read.
	 * @param file  the path of the file, e.g. "path/to/file.txt"
	 */
	public GwtInputStream(String file)
		throws FileNotFoundException {
		data = null;
		try {
			new RequestBuilder(RequestBuilder.GET, file).sendRequest("", new RequestCallback() {

				@Override public void onResponseReceived(Request req, Response resp) {
					data = resp.getText();
				}

				@Override public void onError(Request res, Throwable throwable) {
					data = "ERROR"; //TODO
				}
			});
		} catch (RequestException ex) {
			data = "ERROR"; //TODO
		}
		
		//blocking - DIRTY
		while (data == null);
		//TODO: error detection
		try {
			stream = new ByteArrayInputStream(data.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			data = "ERROR"; //TODO
		}
	}

	@Override public int read()
		throws IOException {
		return stream.read();
	}

	@Override public int read(byte[] b)
		throws IOException {
		return stream.read(b, 0, b.length);
	}

	@Override public int read(byte[] b, int off, int len)
		throws IOException {
		return stream.read(b, off, len);
	}

	@Override public void close() {
		data = null;
		stream  = null;
	}

}
