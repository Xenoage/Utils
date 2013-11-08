package com.xenoage.utils.jse.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * Wrapper for a Java SE {@link InputStream}.
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

}
