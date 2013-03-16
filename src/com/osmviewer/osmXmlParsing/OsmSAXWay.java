package com.osmviewer.osmXmlParsing;

import com.osmviewer.osmXml.OsmTag;
import com.osmviewer.osmXml.OsmWay;
import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * Osm way for sax parsing
 *
 * @author preobrazhentsev
 */
public class OsmSAXWay implements OsmWay
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
	 * Ids of nodes of way
	 */
	private ArrayList<Long> nodesIds;

	/**
	 * Create by parsing xml tag attributes
	 *
	 * @param attributes attributes of way xml tag
	 * @throws IllegalArgumentException attributes is null
	 * @throws SAXException error while parsing
	 */
	public OsmSAXWay(Attributes attributes) throws IllegalArgumentException, SAXException
	{
		if (attributes == null)
		{
			throw new IllegalArgumentException("attributes is null");
		}

		tags = new ArrayList<OsmTag>();
		nodesIds = new ArrayList<Long>();

		try
		{
			id = Long.valueOf(attributes.getValue("id"));
		}
		catch (Exception ex)
		{
			throw new SAXException(ex);
		}
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
	 * Get count of nodes ids
	 *
	 * @return count of nodes ids
	 */
	@Override
	public int getNodesIdsCount()
	{
		return nodesIds.size();
	}

	/**
	 * Get node id by index
	 *
	 * @param index index of node id
	 * @return node id by index
	 * @throws IllegalArgumentException index is out of bounds
	 */
	@Override
	public long getNodeId(int index) throws IllegalArgumentException
	{
		if (index < 0 || index >= nodesIds.size())
		{
			throw new IllegalArgumentException("index is out of bounds");
		}

		return nodesIds.get(index);
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

	/**
	 * Add id of node of way
	 *
	 * @param nodeIdToAdd id of way's node
	 */
	public void addNodeId(long nodeIdToAdd)
	{
		nodesIds.add(nodeIdToAdd);
	}
}
