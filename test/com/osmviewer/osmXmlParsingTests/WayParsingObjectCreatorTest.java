package com.osmviewer.osmXmlParsingTests;

import com.osmviewer.osmXmlParsing.WayParsingObjectCreator;
import java.util.TreeMap;
import org.junit.Test;
import static org.junit.Assert.*;
import org.xml.sax.SAXException;

/**
 * Tests of WayParsingObjectCreator class
 *
 * @author pgalex
 */
public class WayParsingObjectCreatorTest
{
	private final Long ID = new Long(123456789);
	private TreeMap<String, String> testAttributesMap;

	public WayParsingObjectCreatorTest()
	{
		testAttributesMap = new TreeMap<String, String>();
		testAttributesMap.put("id", ID.toString());
	}

	/**
	 * Test creating with null attributes
	 *
	 * @throws SAXException
	 */
	@Test
	public void creatingWithNullAttributesTest() throws SAXException
	{
		try
		{
			WayParsingObjectCreator wayCreator = new WayParsingObjectCreator(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test parsing attributes of way xml tag
	 *
	 * @throws SAXException
	 */
	@Test
	public void parsingAttributesTest() throws SAXException
	{
		TestAttributes testAttributes = new TestAttributes();
		testAttributes.attributesData = testAttributesMap;
		WayParsingObjectCreator wayParsingObjectCreator = new WayParsingObjectCreator(testAttributes);

		TestOsmXmlParsingResultsHandler testResultsHandler = new TestOsmXmlParsingResultsHandler();
		wayParsingObjectCreator.sendCreatedObjectToHandler(testResultsHandler);

		assertEquals(1, testResultsHandler.ways.size());
		assertEquals((long) ID, testResultsHandler.ways.get(0).getId());
	}
}