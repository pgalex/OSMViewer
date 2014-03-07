package com.osmviewer.osmXmlParsingTests;

import com.osmviewer.osmXmlSAXParsing.WayParsingObjectCreator;
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
	
	/**
	 * Test adding and parsing tags
	 *
	 * @throws SAXException
	 */
	@Test
	public void addingTagsTest() throws SAXException
	{
		TestAttributes testAttributes = new TestAttributes();
		testAttributes.attributesData = testAttributesMap;
		WayParsingObjectCreator wayCreator = new WayParsingObjectCreator(testAttributes);


		TestAttributes tagAttributes = new TestAttributes();
		tagAttributes.attributesData = new TreeMap<String, String>();
		final String KEY = "key1";
		final String VALUE = "value";
		tagAttributes.attributesData.put("k", KEY);
		tagAttributes.attributesData.put("v", VALUE);

		wayCreator.startElement("", "", "tag", tagAttributes);


		TestOsmXmlParsingResultsHandler parsingHandler = new TestOsmXmlParsingResultsHandler();
		wayCreator.sendCreatedObjectToHandler(parsingHandler);

		assertEquals(1, parsingHandler.ways.get(0).getTagsCount());
		assertEquals(KEY, parsingHandler.ways.get(0).getTag(0).getKey());
		assertEquals(VALUE, parsingHandler.ways.get(0).getTag(0).getValue());
	}
	
	/**
	 * Test adding and parsing nodes ids
	 *
	 * @throws SAXException
	 */
	@Test
	public void addingNodesIdsTest() throws SAXException
	{
		TestAttributes testAttributes = new TestAttributes();
		testAttributes.attributesData = testAttributesMap;
		WayParsingObjectCreator wayCreator = new WayParsingObjectCreator(testAttributes);
		
		TestAttributes nodeIdAttributes = new TestAttributes();
		nodeIdAttributes.attributesData = new TreeMap<String, String>();
		final Long REF = new Long(12345678);
		nodeIdAttributes.attributesData.put("ref", REF.toString());
		
		wayCreator.startElement("", "", "nd", nodeIdAttributes);
		
		TestOsmXmlParsingResultsHandler parsingHandler = new TestOsmXmlParsingResultsHandler();
		wayCreator.sendCreatedObjectToHandler(parsingHandler);
		
		assertEquals(1, parsingHandler.ways.get(0).getNodesIdsCount());
		assertEquals((long)REF, parsingHandler.ways.get(0).getNodeId(0));
	}
}