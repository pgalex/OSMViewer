package com.osmviewer.osmXmlSAXParsing;

import com.osmviewer.osmXml.OsmXmlParsingResultsHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * Handler of parsing creating osm map object
 *
 * @author pgalex
 */
public interface ParsingObjectCreator
{
	/**
	 * Name of xml tag of osm tag
	 */
	public static final String TAG_XML_TAG_NAME = "tag";

	/**
	 * End creating osm map object and send it to handler
	 *
	 * @param handler handler, taking created osm map object
	 * @throws IllegalArgumentException handler is null
	 */
	public void sendCreatedObjectToHandler(OsmXmlParsingResultsHandler handler) throws IllegalArgumentException;

	/**
	 * Receive notification of the start of an element.
	 *
	 * @param uri The Namespace URI, or the empty string if the element has no
	 * Namespace URI or if Namespace processing is not being performed.
	 * @param localName The local name (without prefix), or the empty string if
	 * Namespace processing is not being performed.
	 * @param qualifiedName The qualified name (with prefix), or the empty string
	 * if qualified names are not available.
	 * @param attributes The attributes attached to the element. If there are no
	 * attributes, it shall be an empty Attributes object.
	 * @throws SAXException error while element parsing
	 */
	public void startElement(String uri, String localName, String qualifiedName, Attributes attributes) throws SAXException;

	/**
	 * Receive notification of the end of an element.
	 *
	 * <p>By default, do nothing. Application writers may override this method in
	 * a subclass to take specific actions at the end of each element (such as
	 * finalising a tree node or writing output to a file).</p>
	 *
	 * @param uri The Namespace URI, or the empty string if the element has no
	 * Namespace URI or if Namespace processing is not being performed.
	 * @param localName The local name (without prefix), or the empty string if
	 * Namespace processing is not being performed.
	 * @param qualifiedName The qualified name (with prefix), or the empty string
	 * if qualified names are not available.
	 * @exception SAXException error while parsing end of element
	 */
	public void endElement(String uri, String localName, String qualifiedName) throws SAXException;

	/**
	 * Is element name end for creating osm map object
	 *
	 * @param qualifiedElementName xml element qualified name
	 * @return is element name end for creating
	 * @throws IllegalArgumentException qualifiedElementName is null
	 */
	public boolean isEndCreatingElementName(String qualifiedElementName) throws IllegalArgumentException;
}
