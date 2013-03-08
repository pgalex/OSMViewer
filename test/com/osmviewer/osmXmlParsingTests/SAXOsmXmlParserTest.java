package com.osmviewer.osmXmlParsingTests;

import com.osmviewer.osmXml.exceptions.ParsingOsmErrorException;
import com.osmviewer.osmXmlParsing.SAXOsmXmlParser;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of SAXOsmXmlParser class
 *
 * @author pgalex
 */
public class SAXOsmXmlParserTest
{
	/**
	 * Test parsing normal work
	 *
	 * @throws FileNotFoundException
	 * @throws IllegalArgumentException
	 * @throws ParsingOsmErrorException
	 */
	@Test
	public void parsingNormalWorkTest() throws FileNotFoundException, IllegalArgumentException, ParsingOsmErrorException
	{
		SAXOsmXmlParser parser = new SAXOsmXmlParser();
		TestOsmXmlParsingResultsHandler resultsHandler = new TestOsmXmlParsingResultsHandler();
		parser.parse(new FileInputStream(new File("test/supportFiles/testmap.osm")), resultsHandler);

		assertEquals(4, resultsHandler.nodes.size());

		assertEquals(123456789, resultsHandler.nodes.get(0).getId());
		assertEquals(55.55, resultsHandler.nodes.get(0).getLatitude(), 0.001);
		assertEquals(38.38, resultsHandler.nodes.get(0).getLongitude(), 0.001);

		assertEquals(1233435413, resultsHandler.nodes.get(1).getId());
		assertEquals(55.2090925, resultsHandler.nodes.get(1).getLatitude(), 0.00001);
		assertEquals(38.6016506, resultsHandler.nodes.get(1).getLongitude(), 0.00001);

		assertEquals(1233435417, resultsHandler.nodes.get(2).getId());
		assertEquals(55.2086321, resultsHandler.nodes.get(2).getLatitude(), 0.00001);
		assertEquals(38.6015777, resultsHandler.nodes.get(2).getLongitude(), 0.00001);

		assertEquals(1233435465, resultsHandler.nodes.get(3).getId());
		assertEquals(55.2048968, resultsHandler.nodes.get(3).getLatitude(), 0.00001);
		assertEquals(38.6050110, resultsHandler.nodes.get(3).getLongitude(), 0.00001);
	}
}