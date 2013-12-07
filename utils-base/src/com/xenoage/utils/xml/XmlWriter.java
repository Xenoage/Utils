package com.xenoage.utils.xml;



/**
 * Platform independent base class of a XML stream writer,
 * inspired by the StAX Cursor API.
 * 
 * Implementations for different platforms (Java SE, Android, GWT, ...)
 * are provided in the corresponding platform libraries.
 * 
 * @author Andreas Wenger
 */
public abstract class XmlWriter {
	
	/**
   * Writes an element start tag with the given local name.
   */
  public abstract void writeElementStart(String name);
  
  /**
   * Writes an element end tag.
   */
  public abstract void writeElementEnd();
  
  /**
   * Writes an empty element with the given local name.
   */
  public abstract void writeElementEmpty(String name);
	
  /**
   * Writes an element with the given local name and text content.
   * If the given text is null, no text content is written.
   */
  public abstract void writeElementText(String name, String text);
  
  /**
   * Writes an element with the given local name and text content.
   * If the given text is null, no text content is written.
   * If the text is not null, it is converted to a {@link String}
   * using {@link Object#toString()}.
   */
  public void writeElementText(String name, Object text) {
  	String s = (text != null ? text.toString() : null);
  	writeElementText(name, s);
  }
  
	/**
   * Writes an attribute with the given local name and value at this position.
   * Nothing is written if the given value is null.
   */
  public void writeAttribute(String name, String value) {
  	if (value == null)
  		return;
  	writeAttributeInternal(name, value);
  }
  
  /**
   * Writes an attribute with the given local name and value at this position.
   */
  protected abstract void writeAttributeInternal(String name, String value);
  
  /**
   * Writes an attribute with the given local name and value at this position.
   * Nothing is written if the given value is null.
   * The value is converted to a {@link String} using {@link Object#toString()}.
   */
  public void writeAttribute(String name, Object value) {
  	if (value == null)
  		return;
  	writeAttributeInternal(name, value.toString());
  }

}
