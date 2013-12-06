package com.xenoage.utils.xml;


/**
 * Runtime exception for wrong data in an XML document.
 * 
 * @author Andreas Wenger
 */
public abstract class XmlDataException
	extends RuntimeException {

	public XmlDataException(String message) {
		super(message);
	}

}
