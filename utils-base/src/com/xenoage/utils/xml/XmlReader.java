package com.xenoage.utils.xml;

import com.xenoage.utils.Parser;
import com.xenoage.utils.annotations.Unneeded;

/**
 * Platform independent base class of a XML stream reader,
 * inspired by the StAX Cursor API.
 * 
 * Implementations for different platforms (Java SE, Android, GWT, ...)
 * are provided in the corresponding platform libraries.
 * 
 * @author Andreas Wenger
 */
public abstract class XmlReader {

	/**
	 * Gets the name of this element.
	 */
	public abstract String getElementName();
	
	/**
	 * Gets the complete text content of this element,
	 * or null if there is none.
	 * This method only works correct if called at the start of an element.
	 */
	public abstract String getText();

	/**
	 * Gets the number of attributes at this element.
	 */
	public abstract int getAttributeCount();

	/**
	 * Gets the local name of the attribute at the given index at this element.
	 */
	public abstract String getAttributeName(int index);

	/**
	 * Gets the string value of the attribute at the given index at this element.
	 */
	public abstract String getAttributeString(int index);

	/**
	 * Gets all attributes at this element.
	 */
	public XmlAttribute[] getAttributes() {
		XmlAttribute[] ret = new XmlAttribute[getAttributeCount()];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = new XmlAttribute(getAttributeName(i), getAttributeString(i));
		}
		return ret;
	}

	/**
	 * Gets the value of the attribute with the given name at this element, or null if not found.
	 * This method is convenient, but slower than iterating through the attributes on the caller side.
	 * For each call, the whole list of attributes has to be checked.
	 */
	public String getAttributeString(String name) {
		for (int i = 0; i < getAttributeCount(); i++) {
			if (getAttributeName(i).equals(name))
				return getAttributeString(i);
		}
		return null;
	}

	/**
	 * Gets the value of the attribute with the given name at this element
	 * as a int value, or null if not found.
	 * See {@link #getAttributeValue(String)} for a notice about performance.
	 */
	public Integer getAttributeInt(String name) {
		String v = getAttributeString(name);
		if (v == null)
			return null;
		return Parser.parseIntegerNull(v);
	}

	/**
	 * Gets the value of the attribute with the given name at this element
	 * as a float value, or null if not found.
	 * See {@link #getAttributeValue(String)} for a notice about performance.
	 */
	public Float getAttributeFloat(String name) {
		String v = getAttributeString(name);
		if (v == null)
			return null;
		return Parser.parseFloatNull(v);
	}

	/**
	 * Moves forward to the next start of an child element and returns true.
	 * If there is none (i.e. if the end of the current element is seen before), false is returned.
	 */
	public abstract boolean openNextChildElement();
	
	/**
	 * Moves forward to the next start of an child element with the given local name and returns true.
	 * Child elements with other names are skipped.
	 * If there is none (i.e. if the end of the current element is seen before), false is returned.
	 */
	@Unneeded public abstract boolean openNextChildElement(String name);
	
	/**
	 * Closes the current element, i.e. moves forward to the end of the current element.
	 * If the current element has child elements, they are skipped.
	 */
	public abstract void closeElement();

	/**
	 * Throws a {@link XmlDataException} at the current position.
	 */
	public abstract void throwDataException()
		throws XmlDataException;

	/**
	 * Throws a {@link XmlDataException} at the current position,
	 * using the additional detail message.
	 */
	public abstract void throwDataException(String message)
		throws XmlDataException;

}
