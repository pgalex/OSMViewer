package com.osmviewer.xmlDrawSettings;

import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Handler of SAX parsing events for reading draw priority
 *
 * @author preobrazhentsev
 */
public class DrawPrioritySAXParsingHandler extends DefaultHandler
{
	/**
	 * Drawing ids found while parsing draw priority order. Order of elements in
	 * array equals to nodes order in xml document
	 */
	private final ArrayList<String> parsedDrawingIds;

	/**
	 * Create with empty parsed drawing ids list
	 */
	public DrawPrioritySAXParsingHandler()
	{
		parsedDrawingIds = new ArrayList<>();
	}

	/**
	 * Get drawing ids list found while parsing.
	 *
	 * @return drawing ids list found while parsing. Order of elements in list
	 * equals to nodes order in xml document
	 */
	public List<String> getParsedDrawingIds()
	{
		return parsedDrawingIds;
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
		if (qualifiedName.compareToIgnoreCase("mapObject") == 0)
		{
			parsedDrawingIds.add(getDrawingIdFromAttributes(attributes));
		}
	}

	/**
	 * Get drawing id from draw priority xml node attributes
	 *
	 * @param attributes attributes to get drawing id from. Must be not null
	 * @return drawing id from node attributes
	 * @throws IllegalArgumentException attributes is null
	 */
	private String getDrawingIdFromAttributes(Attributes attributes) throws IllegalArgumentException
	{
		if (attributes == null)
		{
			throw new IllegalArgumentException("attributes is null");
		}

		return attributes.getValue("drawingId");
	}
}
