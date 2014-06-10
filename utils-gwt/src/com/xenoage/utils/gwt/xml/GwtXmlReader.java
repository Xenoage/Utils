package com.xenoage.utils.gwt.xml;

import com.google.gwt.xml.client.DOMException;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.XMLParser;
import com.xenoage.utils.xml.XmlDataException;
import com.xenoage.utils.xml.XmlException;
import com.xenoage.utils.xml.XmlReader;

/**
 * GWT implementation of an {@link XmlReader},
 * based on GWT's {@link XMLParser} and {@link Document}.
 * 
 * @author Andreas Wenger
 */
public class GwtXmlReader
	extends XmlReader {
	
	private Document doc;
	private Node currentNode;
	private boolean currentElementChildrenRead = false;
	
	public GwtXmlReader(String xmlData) {
		try {
			this.doc = XMLParser.parse(xmlData);
			this.currentNode = doc;
		} catch (DOMException ex) {
			throw new XmlException(ex);
		}
	}

	@Override public String getElementName() {
		return currentNode.getNodeName();
	}

	@Override public String getText() {
		return currentNode.getNodeValue();
	}

	@Override public int getAttributeCount() {
		return currentNode.getAttributes().getLength();
	}

	@Override public String getAttributeName(int index) {
		return currentNode.getAttributes().item(index).getNodeName();
	}

	@Override public String getAttribute(int index) {
		return currentNode.getAttributes().item(index).getNodeValue();
	}

	@Override public boolean openNextChildElement() {
		//if the current node has a child element, open it
		if (currentNode.getChildNodes().getLength() > 0) {
			currentNode = currentNode.getFirstChild();
			return true;
		}
		//else if the current node has a following sibling, open it
		else if (currentNode.getNextSibling() != null) {
			currentNode = currentNode.getNextSibling();
			return false;
		}
		return false;
	}

	@Override public void closeElement() {
		currentNode = currentNode.getParentNode();
	}

	@Override public XmlDataException dataException() {
		throw new XmlDataException("");
	}

	@Override public XmlDataException dataException(String message) {
		throw new XmlDataException(message);
	}

	@Override public int getLine() {
		return -1;
	}

	@Override public void close() {
		currentNode = null;
		doc = null;
	}

}
