package com.osmviewer.osmXmlSAXParsing;

import com.osmviewer.osmXml.OsmXmlParser;
import com.osmviewer.osmXml.OsmXmlParsingResultsHandler;
import com.osmviewer.osmXml.exceptions.ParsingOsmErrorException;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

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
	private SAXOsmXmlParserHandler saxParsingHandler;

	/**
	 * Create parser
	 */
	public SAXOsmXmlParser()
	{
		saxParsingHandler = null;
	}

	/**
	 * Parse openstreetmap xml map data from stream
	 *
	 * @param input input osm xml data stream
	 * @param handler handler, taking parsing results
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

		saxParsingHandler = new SAXOsmXmlParserHandler(handler);
		try
		{
			SAXParserFactory parserFactory = SAXParserFactory.newInstance();
			SAXParser parser = parserFactory.newSAXParser();

			parser.parse(input, saxParsingHandler);
		}
		catch (IOException | ParserConfigurationException | SAXException ex)
		{
			throw new ParsingOsmErrorException(ex);
		}
	}
}
