package com.osmviewer.osmXmlSAXParsing;

import com.osmviewer.osmXml.OsmNode;
import com.osmviewer.osmXml.OsmTag;
import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * Osm xml node (one point)
 *
 * @author preobrazhentsev
 */
public class OsmSAXNode implements OsmNode
{
	/**
	 * Unique global osm id of object
	 */
	private long id;
	/**
	 * Object tags
	 */
	private ArrayList<OsmTag> tags;
	/**
	 * Position - latitude
	 */
	private double latitude;
	/**
	 * Position - longitude
	 */
	private double longitude;

	/**
	 * Creating node by xml attributes
	 *
	 * @param attributes node xml tag attributes
	 * @throws IllegalArgumentException attributes is null
	 * @throws SAXException error while parsing attributes
	 */
	public OsmSAXNode(Attributes attributes) throws IllegalArgumentException, SAXException
	{
		if (attributes == null)
		{
			throw new IllegalArgumentException("attributes is null");
		}

		tags = new ArrayList<OsmTag>();

		try
		{
			id = Long.valueOf(attributes.getValue("id"));
			latitude = Double.valueOf(attributes.getValue("lat"));
			longitude = Double.valueOf(attributes.getValue("lon"));
		}
		catch (Exception ex)
		{
			throw new SAXException(ex);
		}
	}

	/**
	 * Get node position latitude
	 *
	 * @return position latitude
	 */
	@Override
	public double getLatitude()
	{
		return latitude;
	}

	/**
	 * Get node position longitude
	 *
	 * @return position longitude
	 */
	@Override
	public double getLongitude()
	{
		return longitude;
	}

	/**
	 * Get unique global osm id of object
	 *
	 * @return Unique global osm id of object
	 */
	@Override
	public long getId()
	{
		return id;
	}

	/**
	 * Get object tags count
	 *
	 * @return tags count
	 */
	@Override
	public int getTagsCount()
	{
		return tags.size();
	}

	/**
	 * Get tag by index
	 *
	 * @param index index of tag
	 * @return tag by index
	 * @throws IllegalArgumentException index is out of bounds
	 */
	@Override
	public OsmTag getTag(int index) throws IllegalArgumentException
	{
		if (index < 0 || index >= tags.size())
		{
			throw new IllegalArgumentException("index is out of bounds");
		}

		return tags.get(index);
	}

	/**
	 * Add tag
	 *
	 * @param tagToAdd adding tag
	 * @throws IllegalArgumentException tagToAdd is null
	 */
	public void addTag(OsmTag tagToAdd) throws IllegalArgumentException
	{
		if (tagToAdd == null)
		{
			throw new IllegalArgumentException("tagToAdd is null");
		}

		tags.add(tagToAdd);
	}
}
