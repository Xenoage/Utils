package com.xenoage.utils.swing.io;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Wrapper for a Java SE {@link OutputStream}.
 * 
 * @author Andreas Wenger
 */
public class JseOutputStream
	extends OutputStream
	implements com.xenoage.utils.io.generic.OutputStream {
	
	private OutputStream jseOutputStream = null;
	private com.xenoage.utils.io.generic.OutputStream genOutputStream = null;
	
	public JseOutputStream(OutputStream jseOutputStream) {
		this.jseOutputStream = jseOutputStream;
	}
	
	public JseOutputStream(com.xenoage.utils.io.generic.OutputStream genOutputStream) {
		this.genOutputStream = genOutputStream;
	}

	@Override public void write(int b)
		throws IOException {
		if (jseOutputStream != null)
			jseOutputStream.write(b);
		else
			genOutputStream.write(b);
	}

}
