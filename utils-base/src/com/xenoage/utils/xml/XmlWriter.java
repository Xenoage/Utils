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
  public abstract void writeStartElement(String name);
  
  /**
   * Writes an element end tag.
   */
  public abstract void writeEndElement();
	
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
