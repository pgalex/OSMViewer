package com.osmviewer.osmXml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * Tag of osm xml
 *
 * @author pgalex
 */
public class OsmTag
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
	 * Create tag with parameters
	 *
	 * @param tagKey Key of tag
	 * @param tagValue Value of tag
	 */
	public OsmTag(String tagKey, String tagValue)
	{
		key = tagKey;
		value = tagValue;
	}

	/**
	 * Create tag from xml attributes
	 *
	 * @param attributes attributes of xml tag using to create tag
	 * @throws IllegalArgumentException attributes is null
	 * @throws SAXException can not create tag from given attributes
	 */
	public OsmTag(Attributes attributes) throws IllegalArgumentException, SAXException
	{
		if (attributes == null)
		{
			throw new IllegalArgumentException();
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
	 * Set key of tag
	 *
	 * @param keyToSet key of tag
	 */
	public void setKey(String keyToSet)
	{
		key = keyToSet;
	}

	/**
	 * Get key of tag
	 *
	 * @return key of tag
	 */
	public String getKey()
	{
		return key;
	}

	/**
	 * Set value of tag
	 *
	 * @param valueToSet value of tag
	 */
	public void setValue(String valueToSet)
	{
		value = valueToSet;
	}

	/**
	 * Get value of tag
	 *
	 * @return value of tag
	 */
	public String getValue()
	{
		return value;
	}
}
