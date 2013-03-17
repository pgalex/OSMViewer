package com.osmviewer.osmXmlParsingTests;

import com.osmviewer.osmXmlSAXParsing.NodeParsingObjectCreator;
import java.util.Map;
import java.util.TreeMap;
import org.junit.Test;
import static org.junit.Assert.*;
import org.xml.sax.SAXException;

/**
 * Tests of NodeParsingObjectCreator class
 *
 * @author pgalex
 */
public class NodeParsingObjectCreatorTest
{
	private Map<String, String> testNodeAttributesMap;

	public NodeParsingObjectCreatorTest()
	{
		testNodeAttributesMap = new TreeMap<String, String>();
		testNodeAttributesMap.put("id", "123456789");
		testNodeAttributesMap.put("lat", "55.2090925");
		testNodeAttributesMap.put("lon", "34.2090925");
	}

	/**
	 * Test creating with attributes
	 *
	 * @throws SAXException
	 */
	@Test
	public void parsingAttributesTest() throws SAXException
	{
		TestAttributes testNodeAttributes = new TestAttributes();
		testNodeAttributes.attributesData = testNodeAttributesMap;
		NodeParsingObjectCreator nodeCreator = new NodeParsingObjectCreator(testNodeAttributes);

		TestOsmXmlParsingResultsHandler parsingHandler = new TestOsmXmlParsingResultsHandler();
		nodeCreator.sendCreatedObjectToHandler(parsingHandler);

		assertEquals((long) Long.valueOf(testNodeAttributesMap.get("id")), (long) parsingHandler.nodes.get(0).getId());
		assertEquals((double) Double.valueOf(testNodeAttributesMap.get("lat")), (double) parsingHandler.nodes.get(0).getLatitude(), 0.001);
		assertEquals((double) Double.valueOf(testNodeAttributesMap.get("lon")), (double) parsingHandler.nodes.get(0).getLongitude(), 0.001);
	}

	/**
	 * Test adding and parsing tags
	 *
	 * @throws SAXException
	 */
	@Test
	public void addingTagsTest() throws SAXException
	{
		TestAttributes testNodeAttributes = new TestAttributes();
		testNodeAttributes.attributesData = testNodeAttributesMap;
		NodeParsingObjectCreator nodeCreator = new NodeParsingObjectCreator(testNodeAttributes);


		TestAttributes tagAttributes = new TestAttributes();
		tagAttributes.attributesData = new TreeMap<String, String>();
		final String KEY = "key1";
		final String VALUE = "value";
		tagAttributes.attributesData.put("k", KEY);
		tagAttributes.attributesData.put("v", VALUE);

		nodeCreator.startElement("", "", "tag", tagAttributes);


		TestOsmXmlParsingResultsHandler parsingHandler = new TestOsmXmlParsingResultsHandler();
		nodeCreator.sendCreatedObjectToHandler(parsingHandler);

		assertEquals(1, parsingHandler.nodes.get(0).getTagsCount());
		assertEquals(KEY, parsingHandler.nodes.get(0).getTag(0).getKey());
		assertEquals(VALUE, parsingHandler.nodes.get(0).getTag(0).getValue());
	}

	/**
	 * Test send creating object to null handler
	 *
	 * @throws SAXException
	 */
	@Test
	public void sendCreatedObjectToNullHandlerTest() throws SAXException
	{
		try
		{
			TestAttributes testNodeAttributes = new TestAttributes();
			testNodeAttributes.attributesData = testNodeAttributesMap;
			NodeParsingObjectCreator nodeCreator = new NodeParsingObjectCreator(testNodeAttributes);
			nodeCreator.sendCreatedObjectToHandler(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test is creating element with null name
	 *
	 * @throws SAXException
	 */
	@Test
	public void isEndCreatingElementWithNullNameTest() throws SAXException
	{
		try
		{
			TestAttributes testNodeAttributes = new TestAttributes();
			testNodeAttributes.attributesData = testNodeAttributesMap;
			NodeParsingObjectCreator nodeCreator = new NodeParsingObjectCreator(testNodeAttributes);
			nodeCreator.isEndCreatingElementName(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test is creating element name normal work
	 *
	 * @throws IllegalArgumentException
	 * @throws SAXException
	 */
	@Test
	public void isEndCreatingElementNameNormalWorkTest() throws IllegalArgumentException, SAXException
	{
		TestAttributes testNodeAttributes = new TestAttributes();
		testNodeAttributes.attributesData = testNodeAttributesMap;
		NodeParsingObjectCreator nodeCreator = new NodeParsingObjectCreator(testNodeAttributes);
		assertTrue(nodeCreator.isEndCreatingElementName("node"));
		assertTrue(nodeCreator.isEndCreatingElementName("Node"));
		assertFalse(nodeCreator.isEndCreatingElementName("way"));
	}
}