package com.osmviewer.osmXmlParsing;

import com.osmviewer.osmXml.OsmXmlParsingResultsHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * Handler of sax parsing, creating osm way
 *
 * @author pgalex
 */
public class WayParsingObjectCreator implements ParsingObjectCreator
{
	/**
	 * Xml tag of way
	 */
	public static final String WAY_XML_TAG_NAME = "way";
	/**
	 * Xml tag of node id of way
	 */
	private static final String WAY_NODE_ID_TAG_NAME = "nd";
	/**
	 * Osm way, creating while parsing
	 */
	private OsmSAXWay creatingWay;

	/**
	 * Create way parsing creator, gets way parameters from attributes of xml way
	 * tag
	 *
	 * @param wayAttributes attributes of way xml tag
	 * @throws IllegalArgumentException attributes is null
	 * @throws SAXException error while parsing attributes
	 */
	public WayParsingObjectCreator(Attributes wayAttributes) throws IllegalArgumentException, SAXException
	{
		if (wayAttributes == null)
		{
			throw new IllegalArgumentException("wayAttributes is null");
		}

		creatingWay = new OsmSAXWay(wayAttributes);
	}

	/**
	 * End creating osm map object and send it to handler
	 *
	 * @param handler handler, taking created osm map object
	 * @throws IllegalArgumentException handler is null
	 */
	@Override
	public void sendCreatedObjectToHandler(OsmXmlParsingResultsHandler handler) throws IllegalArgumentException
	{
		if (handler == null)
		{
			throw new IllegalArgumentException("handler is null");
		}

		handler.takeWay(creatingWay);
	}

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
	@Override
	public void startElement(String uri, String localName, String qualifiedName, Attributes attributes) throws SAXException
	{
		if (qualifiedName.compareToIgnoreCase(ParsingObjectCreator.TAG_XML_TAG_NAME) == 0)
		{
			creatingWay.addTag(new OsmSAXTag(attributes));
		}
		else if (qualifiedName.compareToIgnoreCase(WAY_NODE_ID_TAG_NAME) == 0)
		{
			creatingWay.addNodeId(Long.valueOf(attributes.getValue("ref")));
		}
	}

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
	@Override
	public void endElement(String uri, String localName, String qualifiedName) throws SAXException
	{
	}

	/**
	 * Is element name end for creating osm map object
	 *
	 * @param qualifiedElementName xml element qualified name
	 * @return is element name end for creating
	 * @throws IllegalArgumentException qualifiedElementName is null
	 */
	@Override
	public boolean isEndCreatingElementName(String qualifiedElementName) throws IllegalArgumentException
	{
		if (qualifiedElementName == null)
		{
			throw new IllegalArgumentException("qualifiedElementName is null");
		}

		return qualifiedElementName.compareToIgnoreCase(WAY_XML_TAG_NAME) == 0;
	}
}
