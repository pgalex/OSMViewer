package com.osmviewer.osmXmlParsing;

import com.osmviewer.osmXml.OsmXmlParser;
import com.osmviewer.osmXml.OsmXmlParsingResultsHandler;
import com.osmviewer.osmXml.exceptions.ParsingOsmErrorException;
import java.io.InputStream;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * SAX .osm xml map data parser
 *
 * @author pgalex
 */
public class SAXOsmXmlParser implements OsmXmlParser
{
	/**
	 * Handler of SAX parsing
	 */
	private SAXOsmXmlParserHandler saxHandler;

	/**
	 * Create parser
	 */
	public SAXOsmXmlParser()
	{
		saxHandler = null;
	}

	/**
	 * Parse openstreetmap xml map data from stream
	 *
	 * @param input input osm xml data stream
	 * @param handler handler, taking results of parsing
	 * @throws IllegalArgumentException input or handler is null
	 * @throws ParsingOsmErrorException error while parsing data from input
	 */
	@Override
	public void parse(InputStream input, OsmXmlParsingResultsHandler handler) throws IllegalArgumentException, ParsingOsmErrorException
	{
		if (input == null)
		{
			throw new IllegalArgumentException("input is null");
		}
		if (handler == null)
		{
			throw new IllegalArgumentException("handler is null");
		}

		saxHandler = new SAXOsmXmlParserHandler(handler);
		try
		{
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();

			parser.parse(input, saxHandler);
		}
		catch (Exception ex)
		{
			throw new ParsingOsmErrorException(ex);
		}
	}
}
