package com.osmviewer.osmXmlSAXParsing;

import com.osmviewer.osmXml.OsmXmlParsingResultsHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * SAX .osm xml map data parser handler
 *
 * @author preobrazhentsev
 */
public class SAXOsmXmlParserHandler extends DefaultHandler
{
	/**
	 * Currently working(handling parsing events) parsing osm object creator
	 */
	private ParsingObjectCreator currentlyObjectCreator;
	/**
	 * Handler, taking created while parsing osm map objects
	 */
	private OsmXmlParsingResultsHandler osmParsingHandler;

	public SAXOsmXmlParserHandler(OsmXmlParsingResultsHandler handler)
	{
		currentlyObjectCreator = null;
		osmParsingHandler = handler;
	}

	/**
	 * Receive notification of the beginning of the document.
	 *
	 * @throws SAXException error while begining parsing
	 */
	@Override
	public void startDocument() throws SAXException
	{
		currentlyObjectCreator = null;
	}

	/**
	 * Receive notification of the end of the document.
	 *
	 * @throws SAXException error while ending parsing
	 */
	@Override
	public void endDocument() throws SAXException
	{
		currentlyObjectCreator = null;
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
		if (currentlyObjectCreator != null)
		{
			currentlyObjectCreator.startElement(uri, localName, qualifiedName, attributes);
		}
		else
		{
			if (qualifiedName.compareToIgnoreCase(NodeParsingObjectCreator.NODE_XML_TAG_NAME) == 0)
			{
				currentlyObjectCreator = new NodeParsingObjectCreator(attributes);
			}
			else if (qualifiedName.compareToIgnoreCase(WayParsingObjectCreator.WAY_XML_TAG_NAME) == 0)
			{
				currentlyObjectCreator = new WayParsingObjectCreator(attributes);
			}
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
		if (currentlyObjectCreator != null)
		{
			if (currentlyObjectCreator.isEndCreatingElementName(qualifiedName))
			{
				currentlyObjectCreator.sendCreatedObjectToHandler(osmParsingHandler);
				currentlyObjectCreator = null;
			}
			else
			{
				currentlyObjectCreator.endElement(uri, localName, qualifiedName);
			}
		}
	}

	@Override
	public void warning(SAXParseException e) throws SAXException
	{
		throw new SAXException(e);
	}

	@Override
	public void error(SAXParseException e) throws SAXException
	{
		throw new SAXException(e);
	}

	@Override
	public void fatalError(SAXParseException e) throws SAXException
	{
		throw new SAXException(e);
	}
}
