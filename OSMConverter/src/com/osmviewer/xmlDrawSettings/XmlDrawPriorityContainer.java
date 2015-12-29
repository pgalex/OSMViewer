package com.osmviewer.xmlDrawSettings;

import com.osmviewer.xmlDrawSettings.exceptions.ParsingXmlErrorException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

/**
 * Contains draw priority values for each map object id, by reading it from xml
 * file
 *
 * @author preobrazhentsev
 */
public class XmlDrawPriorityContainer
{
	/**
	 * Map objects ids sorted in draw order
	 */
	private final ArrayList<String> mapObjectsIdsInDrawOrder;

	/**
	 * Create empty
	 */
	public XmlDrawPriorityContainer()
	{
		mapObjectsIdsInDrawOrder = new ArrayList<>();
	}

	/**
	 * Parse draw priority from input stream
	 *
	 * @param input input stream to read draw priority from. Must be not null
	 * @throws IllegalArgumentException fileName is null
	 * @throws ParsingXmlErrorException error while parsing
	 */
	public void readFromStream(InputStream input) throws IllegalArgumentException, ParsingXmlErrorException
	{
		if (input == null)
		{
			throw new IllegalArgumentException("input is null");
		}

		mapObjectsIdsInDrawOrder.clear();

		try
		{
			SAXParserFactory parserFactory = SAXParserFactory.newInstance();
			SAXParser parser = parserFactory.newSAXParser();

			DrawPrioritySAXParsingHandler parsingHandler = new DrawPrioritySAXParsingHandler();
			parser.parse(input, parsingHandler);
			mapObjectsIdsInDrawOrder.addAll(parsingHandler.getParsedMapObjectsIds());
		}
		catch (IOException | ParserConfigurationException | SAXException ex)
		{
			throw new ParsingXmlErrorException(ex);
		}
	}

	/**
	 * Get draw priority by map object id
	 *
	 * @param mapObjectId map object id to get draw priority by. Must be not null
	 * @return draw priority for given drawing id. Null if not found
	 * @throws IllegalArgumentException mapObjectId is null
	 */
	public Integer getDrawPriorityOf(String mapObjectId) throws IllegalArgumentException
	{
		if (mapObjectId == null)
		{
			throw new IllegalArgumentException("mapObjectId is null");
		}

		int indexInDrawOrder = mapObjectsIdsInDrawOrder.indexOf(mapObjectId);
		if (indexInDrawOrder != -1)
		{
			return indexInDrawOrder;
		}
		else
		{
			return null;
		}
	}
}
