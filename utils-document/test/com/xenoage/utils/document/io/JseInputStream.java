package com.xenoage.utils.document.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * Based on file in the utils-jse project.
 * 
 * TIDY: move all tests in own test project! Then this class is not needed any more.
 * 
 * @author Andreas Wenger
 */
public class JseInputStream
	extends InputStream
	implements com.xenoage.utils.io.InputStream {

	private InputStream jseInputStream = null;
	private com.xenoage.utils.io.InputStream genInputStream = null;


	public JseInputStream(InputStream jseInputStream) {
		this.jseInputStream = jseInputStream;
	}

	public JseInputStream(com.xenoage.utils.io.InputStream genInputStream) {
		this.genInputStream = genInputStream;
	}

	@Override public int read()
		throws IOException {
		if (jseInputStream != null)
			return jseInputStream.read();
		else
			return genInputStream.read();
	}

	@Override public void close() {
		if (jseInputStream != null) {
			try {
				jseInputStream.close();
			} catch (IOException e) {
				//ignore
			}
		}
		else {
			genInputStream.close();
		}
	}
	
	

}
