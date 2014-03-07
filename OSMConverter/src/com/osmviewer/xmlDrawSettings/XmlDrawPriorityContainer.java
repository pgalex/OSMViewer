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
 * Contains draw priority values for each drawing id, by reading it from xml
 * file
 *
 * @author preobrazhentsev
 */
public class XmlDrawPriorityContainer
{
	/**
	 * Drawing ids sorted in draw order
	 */
	private final ArrayList<String> drawingIdsInDrawOrder;

	/**
	 * Create empty
	 */
	public XmlDrawPriorityContainer()
	{
		drawingIdsInDrawOrder = new ArrayList<>();
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

		drawingIdsInDrawOrder.clear();

		try
		{
			SAXParserFactory parserFactory = SAXParserFactory.newInstance();
			SAXParser parser = parserFactory.newSAXParser();

			DrawPrioritySAXParsingHandler parsingHandler = new DrawPrioritySAXParsingHandler();
			parser.parse(input, parsingHandler);
			drawingIdsInDrawOrder.addAll(parsingHandler.getParsedDrawingIds());
		}
		catch (IOException | ParserConfigurationException | SAXException ex)
		{
			throw new ParsingXmlErrorException(ex);
		}
	}

	/**
	 * Get draw priority by drawing id
	 *
	 * @param drawingId drawing id to get draw priority by. Must be not null
	 * @return draw priority for given drawing id. Null if not found
	 * @throws IllegalArgumentException drawingId is null
	 */
	public Integer getDrawPriorityOf(String drawingId) throws IllegalArgumentException
	{
		if (drawingId == null)
		{
			throw new IllegalArgumentException("drawingId is null");
		}

		int indexInDrawOrder = drawingIdsInDrawOrder.indexOf(drawingId);
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
