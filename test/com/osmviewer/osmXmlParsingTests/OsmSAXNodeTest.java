package com.osmviewer.osmXmlParsingTests;

import com.osmviewer.osmXmlParsing.OsmSAXNode;
import java.util.TreeMap;
import org.junit.Test;
import static org.junit.Assert.*;
import org.xml.sax.SAXException;

/**
 * Tests of OsmSAXNode class
 *
 * @author pgalex
 */
public class OsmSAXNodeTest
{
	private final Long ID = new Long(123456789);
	private final Double LAT = new Double(35.123213);
	private final Double LON = new Double(-12.123);
	private TreeMap<String, String> testAttributesMap;

	public OsmSAXNodeTest()
	{
		testAttributesMap = new TreeMap<String, String>();
		testAttributesMap.put("id", ID.toString());
		testAttributesMap.put("lat", LAT.toString());
		testAttributesMap.put("lon", LON.toString());
	}

	/**
	 * Test creating by parsing null attributes
	 *
	 * @throws SAXException
	 */
	@Test
	public void parsingNullAttributesTest() throws SAXException
	{
		try
		{
			OsmSAXNode osmSAXNode = new OsmSAXNode(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test creating by parsing attributes normal work
	 *
	 * @throws SAXException
	 */
	@Test
	public void parsingAttributesNormalWorkTest() throws SAXException
	{

		TestAttributes testAttributes = new TestAttributes();
		testAttributes.attributesData = testAttributesMap;

		OsmSAXNode osmSAXNode = new OsmSAXNode(testAttributes);
		assertEquals((long) ID, osmSAXNode.getId());
		assertEquals((double) LAT, osmSAXNode.getLatitude(), 0.0001);
		assertEquals((double) LON, osmSAXNode.getLongitude(), 0.0001);
	}

	/**
	 * Test adding null tag
	 *
	 *
	 * @throws SAXException
	 */
	@Test
	public void addingNullTagTest() throws SAXException
	{
		TestAttributes testAttributes = new TestAttributes();
		testAttributes.attributesData = testAttributesMap;
		OsmSAXNode osmSAXNode = new OsmSAXNode(testAttributes);
		try
		{
			osmSAXNode.addTag(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test adding tags normal work
	 *
	 *
	 * @throws SAXException
	 */
	@Test
	public void addingTagsNormalWorkTest() throws SAXException
	{
		TestAttributes testAttributes = new TestAttributes();
		testAttributes.attributesData = testAttributesMap;
		OsmSAXNode osmSAXNode = new OsmSAXNode(testAttributes);

		TestOsmTag addingTag = new TestOsmTag();
		osmSAXNode.addTag(addingTag);
		assertEquals(1, osmSAXNode.getTagsCount());
		assertEquals(addingTag, osmSAXNode.getTag(0));
	}

	/**
	 * Test getting tag by index less than bounds
	 *
	 *
	 * @throws SAXException
	 */
	@Test
	public void gettingByIndexLessThanBoundsTest() throws SAXException
	{
		TestAttributes testAttributes = new TestAttributes();
		testAttributes.attributesData = testAttributesMap;
		OsmSAXNode osmSAXNode = new OsmSAXNode(testAttributes);
		osmSAXNode.addTag(new TestOsmTag());
		try
		{
			osmSAXNode.getTag(-1);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test getting tag by index more than bounds
	 *
	 *
	 * @throws SAXException
	 */
	@Test
	public void gettingByIndexMoreThanBoundsTest() throws SAXException
	{
		TestAttributes testAttributes = new TestAttributes();
		testAttributes.attributesData = testAttributesMap;
		OsmSAXNode osmSAXNode = new OsmSAXNode(testAttributes);
		osmSAXNode.addTag(new TestOsmTag());
		try
		{
			osmSAXNode.getTag(osmSAXNode.getTagsCount() + 1);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}
}