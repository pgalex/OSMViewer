package com.osmviewer.osmXmlSAXParsing;

import com.osmviewer.osmXml.OsmTag;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * Tag of osm xml for sax parsing
 *
 * @author pgalex
 */
public class OsmSAXTag implements OsmTag
{
	/**
	 * Key of tag
	 */
	private String key;
	/**
	 * Value of tag
	 */
	private String value;

	/**
	 * Create tag from xml attributes
	 *
	 * @param attributes attributes of xml tag using to create tag
	 * @throws IllegalArgumentException attributes is null
	 * @throws SAXException can not create tag from given attributes
	 */
	public OsmSAXTag(Attributes attributes) throws IllegalArgumentException, SAXException
	{
		if (attributes == null)
		{
			throw new IllegalArgumentException("attributes is null");
		}

		try
		{
			key = attributes.getValue("k");
			value = attributes.getValue("v");
		}
		catch (Exception ex)
		{
			throw new SAXException(ex);
		}
	}

	/**
	 * Get key
	 *
	 * @return key of tag
	 */
	@Override
	public String getKey()
	{
		return key;
	}

	/**
	 * Get value
	 *
	 * @return value of tag
	 */
	@Override
	public String getValue()
	{
		return value;
	}
}
