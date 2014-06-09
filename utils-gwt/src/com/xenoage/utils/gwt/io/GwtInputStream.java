package com.xenoage.utils.gwt.io;

import java.io.IOException;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.xenoage.utils.io.InputStream;
import com.xenoage.utils.io.InputStreamCallback;

/**
 * {@link InputStream} implementation of GWT.
 * 
 * Do not forget to call the {@link #open(InputStreamCallback)} method
 * before using the input stream, since this class loads the file
 * content asynchronously.
 * 
 * Currently, this input stream only works with text files.
 * 
 * @author Andreas Wenger
 */
public class GwtInputStream
	implements InputStream {

	private String file = null;
	public String data = null; //TODO: not public!!
	private ByteArrayInputStream stream = null;
	
	
	/**
	 * Opens the given file. It is immediately read and the method
	 * returns not before an error occurs or before the file
	 * is completely read.
	 * @param file  the path of the file, e.g. "path/to/file.txt"
	 */
	public GwtInputStream(String file) {
		this.file = file;
	}
	
	@Override public void open(final InputStreamCallback callback) {
		data = null;
		try {
			new RequestBuilder(RequestBuilder.GET, file).sendRequest("", new RequestCallback() {

				@Override public void onResponseReceived(Request req, Response resp) {
					try {
						data = resp.getText();
						stream = new ByteArrayInputStream(data.getBytes("UTF-8"));
						callback.inputStreamOpened(true, null);
					} catch (Exception ex) {
						callback.inputStreamOpened(false, new IOException(ex));
					}
				}

				@Override public void onError(Request res, Throwable throwable) {
					callback.inputStreamOpened(false, new IOException(throwable));
				}
			});
		} catch (RequestException ex) {
			callback.inputStreamOpened(false, new IOException(ex));
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
