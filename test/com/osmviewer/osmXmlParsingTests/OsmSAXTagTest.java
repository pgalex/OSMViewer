package com.osmviewer.osmXmlParsingTests;

import com.osmviewer.osmXmlSAXParsing.OsmSAXTag;
import java.util.TreeMap;
import org.junit.Test;
import static org.junit.Assert.*;
import org.xml.sax.SAXException;

/**
 *
 * @author pgalex
 */
public class OsmSAXTagTest
{
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
			OsmSAXTag osmSAXTag = new OsmSAXTag(null);
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
		testAttributes.attributesData = new TreeMap<String, String>();

		final String KEY = "key1";
		final String VALUE = "value";
		testAttributes.attributesData.put("k", KEY);
		testAttributes.attributesData.put("v", VALUE);
		OsmSAXTag osmSAXTag = new OsmSAXTag(testAttributes);
		assertEquals(KEY, osmSAXTag.getKey());
		assertEquals(VALUE, osmSAXTag.getValue());
	}
}