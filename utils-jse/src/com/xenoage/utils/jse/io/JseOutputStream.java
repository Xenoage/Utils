package com.xenoage.utils.jse.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Wrapper for a Java SE {@link OutputStream}.
 * 
 * It can be used in two ways: By wrapping a Java stream to a
 * Xenoage stream and vice versa.
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
	
	public JseOutputStream(File file)
		throws FileNotFoundException {
		this.jseOutputStream = new FileOutputStream(file);
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
