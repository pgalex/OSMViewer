package com.osmviewer.osmXmlSAXParsing;

import com.osmviewer.osmXml.OsmWay;
import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * Osm way for sax parsing
 *
 * @author preobrazhentsev
 */
public class OsmSAXWay extends OsmSAXMapObject implements OsmWay
{
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
		super();

		if (attributes == null)
		{
			throw new IllegalArgumentException("attributes is null");
		}

		nodesIds = new ArrayList<Long>();

		try
		{
			setId(Long.valueOf(attributes.getValue("id")));
		}
		catch (Exception ex)
		{
			throw new SAXException(ex);
		}
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
	 * Add id of node of way
	 *
	 * @param nodeIdToAdd id of way's node
	 */
	public void addNodeId(long nodeIdToAdd)
	{
		nodesIds.add(nodeIdToAdd);
	}
}
