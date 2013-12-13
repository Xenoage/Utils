package com.xenoage.utils.jse.xml;

import java.io.InputStream;

import javax.xml.stream.Location;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.xenoage.utils.xml.XmlDataException;
import com.xenoage.utils.xml.XmlException;
import com.xenoage.utils.xml.XmlReader;

/**
 * Java SE implementation of an {@link XmlReader},
 * based on a {@link XMLStreamReader}.
 * 
 * @author Andreas Wenger
 */
public class JseXmlReader
	extends XmlReader {
	
	private XMLStreamReader reader;
	
	public JseXmlReader(InputStream in) {
		XMLInputFactory input = XMLInputFactory.newInstance();
		try {
			reader = input.createXMLStreamReader(in);
		} catch (XMLStreamException ex) {
			throw new XmlException(ex);
		}
	}

	@Override public String getElementName() {
		try {
			return reader.getLocalName();
		} catch (IllegalStateException ex) {
			throw new XmlException(ex);
		}
	}

	@Override public String getText() {
		try {
			while (reader.hasNext()) {
				int event = reader.next();
				if (event == XMLStreamConstants.CHARACTERS)
					break;
				else if (event == XMLStreamConstants.END_ELEMENT)
					return null;
			}
			return reader.getText();
		} catch (Exception ex) {
			throw new XmlException(ex);
		}
	}

	@Override public int getAttributeCount() {
		try {
			return reader.getAttributeCount();
		} catch (IllegalStateException ex) {
			throw new XmlException(ex);
		}
	}

	@Override public String getAttributeName(int index) {
		try {
			return reader.getAttributeLocalName(index);
		} catch (IllegalStateException ex) {
			throw new XmlException(ex);
		}
	}

	@Override public String getAttribute(int index) {
		try {
			return reader.getAttributeValue(index);
		} catch (IllegalStateException ex) {
			throw new XmlException(ex);
		}
	}

	@Override public boolean openNextChildElement() {
		try {
			while (reader.hasNext()) {
				int event = reader.next();
				if (event == XMLStreamConstants.START_ELEMENT)
					return true;
				else if (event == XMLStreamConstants.END_ELEMENT)
					return false;
			}
			return false;
		} catch (XMLStreamException ex) {
			throw new XmlException(ex);
		}
	}

	@Override public void closeElement() {
		try {
			while (reader.hasNext()) {
				int event = reader.next();
				if (event == XMLStreamConstants.END_ELEMENT)
					return;
			}
		} catch (XMLStreamException ex) {
			throw new XmlException(ex);
		}
	}
	
	private String getLocation() {
		Location location = reader.getLocation();
		return "line " + location.getLineNumber() + ", column " + location.getColumnNumber();
	}

	@Override public XmlDataException dataException() {
		throw new XmlDataException("(at " + getLocation() + ")");
	}

	@Override public XmlDataException dataException(String message) {
		throw new XmlDataException(message + " (at " + getLocation() + ")");
	}

}
