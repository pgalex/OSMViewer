package com.osmviewer.osmXmlParsingTests;

import com.osmviewer.osmXmlParsing.NodeParsingObjectCreator;
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
		assertEquals((long) Long.valueOf(testNodeAttributesMap.get("id")), (long) nodeCreator.getCreatingNode().getId());
		assertEquals((double) Double.valueOf(testNodeAttributesMap.get("lat")), (double) nodeCreator.getCreatingNode().getLatitude(), 0.001);
		assertEquals((double) Double.valueOf(testNodeAttributesMap.get("lon")), (double) nodeCreator.getCreatingNode().getLongitude(), 0.001);
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