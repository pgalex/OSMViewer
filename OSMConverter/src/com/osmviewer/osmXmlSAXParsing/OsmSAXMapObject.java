package com.osmviewer.osmXmlSAXParsing;

import com.osmviewer.osmXml.OsmTag;
import java.util.ArrayList;
import java.util.List;

/**
 * SAX Parsed osm map object
 *
 * @author pgalex
 */
public abstract class OsmSAXMapObject
{
	/**
	 * Unique global osm id
	 */
	private long id;
	/**
	 * Tags
	 */
	private List<OsmTag> tags;

	/**
	 * Create with zero id and empty tags
	 */
	public OsmSAXMapObject()
	{
		id = 0;
		tags = new ArrayList<OsmTag>();
	}

	/**
	 * Get unique global osm id
	 *
	 * @return Unique global osm
	 */
	public long getId()
	{
		return id;
	}

	/**
	 * Set new global unique osm id
	 *
	 * @param idToSet new global unique osm id
	 */
	public void setId(long idToSet)
	{
		id = idToSet;
	}

	/**
	 * Get tags count
	 *
	 * @return tags count
	 */
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
