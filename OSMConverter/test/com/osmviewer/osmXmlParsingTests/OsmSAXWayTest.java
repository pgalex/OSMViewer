package com.osmviewer.osmXmlParsingTests;

import com.osmviewer.osmXmlSAXParsing.OsmSAXWay;
import java.util.TreeMap;
import org.junit.Test;
import static org.junit.Assert.*;
import org.xml.sax.SAXException;

/**
 * Tests of OsmSAXWay class
 *
 * @author pgalex
 */
public class OsmSAXWayTest
{
	private final Long ID = new Long(123456789);
	private TreeMap<String, String> testAttributesMap;

	public OsmSAXWayTest()
	{
		testAttributesMap = new TreeMap<String, String>();
		testAttributesMap.put("id", ID.toString());
	}

	/**
	 * Test creating by null attributes
	 *
	 * @throws SAXException
	 */
	@Test
	public void parsingNullAttributesTest() throws SAXException
	{
		try
		{
			OsmSAXWay osmSAXWay = new OsmSAXWay(null);
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

		OsmSAXWay osmSAXWay = new OsmSAXWay(testAttributes);

		assertEquals((long) ID, osmSAXWay.getId());
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
		OsmSAXWay osmSAXWay = new OsmSAXWay(testAttributes);
		try
		{
			osmSAXWay.addTag(null);
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
		OsmSAXWay osmSAXWay = new OsmSAXWay(testAttributes);

		TestOsmTag addingTag = new TestOsmTag();
		osmSAXWay.addTag(addingTag);
		assertEquals(1, osmSAXWay.getTagsCount());
		assertEquals(addingTag, osmSAXWay.getTag(0));
	}

	/**
	 * Test getting tag by index less than bounds
	 *
	 *
	 * @throws SAXException
	 */
	@Test
	public void gettingTagByIndexLessThanBoundsTest() throws SAXException
	{
		TestAttributes testAttributes = new TestAttributes();
		testAttributes.attributesData = testAttributesMap;
		OsmSAXWay osmSAXWay = new OsmSAXWay(testAttributes);
		osmSAXWay.addTag(new TestOsmTag());
		try
		{
			osmSAXWay.getTag(-1);
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
	public void gettingTagByIndexMoreThanBoundsTest() throws SAXException
	{
		TestAttributes testAttributes = new TestAttributes();
		testAttributes.attributesData = testAttributesMap;
		OsmSAXWay osmSAXWay = new OsmSAXWay(testAttributes);
		osmSAXWay.addTag(new TestOsmTag());
		try
		{
			osmSAXWay.getTag(osmSAXWay.getTagsCount() + 1);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test getting node id by index less than bounds
	 *
	 *
	 * @throws SAXException
	 */
	@Test
	public void gettingNodeIdByIndexLessThanBoundsTest() throws SAXException
	{
		TestAttributes testAttributes = new TestAttributes();
		testAttributes.attributesData = testAttributesMap;
		OsmSAXWay osmSAXWay = new OsmSAXWay(testAttributes);
		osmSAXWay.addNodeId(10);
		try
		{
			osmSAXWay.getNodeId(-1);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test getting node id by index more than bounds
	 *
	 *
	 * @throws SAXException
	 */
	@Test
	public void gettingNodeIdByIndexMoreThanBoundsTest() throws SAXException
	{
		TestAttributes testAttributes = new TestAttributes();
		testAttributes.attributesData = testAttributesMap;
		OsmSAXWay osmSAXWay = new OsmSAXWay(testAttributes);
		osmSAXWay.addNodeId(10);
		try
		{
			osmSAXWay.getNodeId(osmSAXWay.getNodesIdsCount() + 1);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test getting node id normal work
	 *
	 *
	 * @throws SAXException
	 */
	@Test
	public void gettingNodeIdNormalWorkTest() throws SAXException
	{
		TestAttributes testAttributes = new TestAttributes();
		testAttributes.attributesData = testAttributesMap;
		OsmSAXWay osmSAXWay = new OsmSAXWay(testAttributes);
		osmSAXWay.addNodeId(11);
		assertEquals(11, osmSAXWay.getNodeId(0));
	}

	@Test
	public void isClosedEmptyWayTest() throws SAXException
	{
		TestAttributes testAttributes = new TestAttributes();
		testAttributes.attributesData = testAttributesMap;
		OsmSAXWay osmSAXWay = new OsmSAXWay(testAttributes);
		try
		{
			osmSAXWay.isClosed();
			fail();
		}
		catch (IllegalStateException ex)
		{
			// ok
		}
	}

	@Test
	public void isClosedNotEnoughNodesTest() throws SAXException
	{
		TestAttributes testAttributes = new TestAttributes();
		testAttributes.attributesData = testAttributesMap;
		OsmSAXWay osmSAXWay = new OsmSAXWay(testAttributes);
		osmSAXWay.addNodeId(13);
		osmSAXWay.addNodeId(14);
		osmSAXWay.addNodeId(13);
		assertFalse(osmSAXWay.isClosed());
	}

	@Test
	public void isClosedFirstLastNodesEqualsTest() throws SAXException
	{
		TestAttributes testAttributes = new TestAttributes();
		testAttributes.attributesData = testAttributesMap;
		OsmSAXWay osmSAXWay = new OsmSAXWay(testAttributes);
		osmSAXWay.addNodeId(13);
		osmSAXWay.addNodeId(14);
		osmSAXWay.addNodeId(15);
		osmSAXWay.addNodeId(13);
		osmSAXWay.addNodeId(16);
		osmSAXWay.addNodeId(13);
		assertTrue(osmSAXWay.isClosed());
	}

	@Test
	public void isClosedFirstLastNodesNotEqualsTest() throws SAXException
	{
		TestAttributes testAttributes = new TestAttributes();
		testAttributes.attributesData = testAttributesMap;
		OsmSAXWay osmSAXWay = new OsmSAXWay(testAttributes);
		osmSAXWay.addNodeId(13);
		osmSAXWay.addNodeId(14);
		osmSAXWay.addNodeId(15);
		osmSAXWay.addNodeId(13);
		osmSAXWay.addNodeId(16);
		osmSAXWay.addNodeId(17);
		assertFalse(osmSAXWay.isClosed());
	}
}