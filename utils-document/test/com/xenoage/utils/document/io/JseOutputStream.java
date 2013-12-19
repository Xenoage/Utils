package com.xenoage.utils.document.io;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Based on file in the utils-jse project.
 * 
 * @author Andreas Wenger
 */
public class JseOutputStream
	extends OutputStream
	implements com.xenoage.utils.io.OutputStream {

	private OutputStream jseOutputStream = null;
	private com.xenoage.utils.io.OutputStream genOutputStream = null;


	public JseOutputStream(OutputStream jseOutputStream) {
		this.jseOutputStream = jseOutputStream;
	}

	public JseOutputStream(com.xenoage.utils.io.OutputStream genOutputStream) {
		this.genOutputStream = genOutputStream;
	}

	@Override public void write(int b)
		throws IOException {
		if (jseOutputStream != null)
			jseOutputStream.write(b);
		else
			genOutputStream.write(b);
	}

	@Override public void close() {
		if (jseOutputStream != null) {
			try {
				jseOutputStream.close();
			} catch (IOException e) {
				//ignore
			}
		}
		else {
			genOutputStream.close();
		}
	}
}
