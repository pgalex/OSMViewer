package com.osmviewer.osmXmlSAXParsing;

import com.osmviewer.osmXml.OsmXmlParsingResultsHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * Handler of osm xml parsing, creating osm node
 *
 * @author pgalex
 */
public class NodeParsingObjectCreator implements ParsingObjectCreator
{
	/**
	 * Xml tag of node
	 */
	public static final String NODE_XML_TAG_NAME = "node";
	/**
	 * Creating osm node
	 */
	private OsmSAXNode creatingNode;

	/**
	 * Create node parsing creator, taking node parameters from node xml tag
	 * attributes
	 *
	 * @param nodeAttributes node xml tag attributes
	 * @throws IllegalArgumentException nodeAttributes is null
	 * @throws SAXException error while parsing attributes
	 */
	public NodeParsingObjectCreator(Attributes nodeAttributes) throws IllegalArgumentException, SAXException
	{
		if (nodeAttributes == null)
		{
			throw new IllegalArgumentException("nodeAttributes is null");
		}

		creatingNode = new OsmSAXNode(nodeAttributes);
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

		handler.takeNode(creatingNode);
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
			creatingNode.addTag(new OsmSAXTag(attributes));
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
	 * Is element name means end of creating osm map object
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

		return qualifiedElementName.compareToIgnoreCase(NODE_XML_TAG_NAME) == 0;
	}
}
