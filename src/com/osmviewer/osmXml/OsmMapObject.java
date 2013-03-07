package com.osmviewer.osmXml;

import java.util.ArrayList;

/**
 * Some object of osm map. Common part of nodes and ways
 *
 * @author pgalex
 */
public class OsmMapObject
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
	 * Create with default values
	 */
	public OsmMapObject()
	{
		id = 0;
		tags = new ArrayList<OsmTag>();
	}

	/**
	 * Set unique global osm id of object
	 *
	 * @param idToSet Unique global osm id of object
	 */
	public void setId(long idToSet)
	{
		id = idToSet;
	}

	/**
	 * Get unique global osm id of object
	 *
	 * @return Unique global osm id of object
	 */
	public long getId()
	{
		return id;
	}

	/**
	 * Set object tags
	 *
	 * @param tagsToSet Object tags
	 */
	public void setTags(ArrayList<OsmTag> tagsToSet)
	{
		tags = tagsToSet;
	}

	/**
	 * Get object tags
	 *
	 * @return Object tags
	 */
	public ArrayList<OsmTag> getTags()
	{
		return tags;
	}
	
	public void addTag(OsmTag tagToAdd)
	{
		tags.add(tagToAdd);
	}
}
