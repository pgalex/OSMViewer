package com.osmviewer.map;

import com.osmviewer.mapDefenitionUtilities.DefenitionTags;

/**
 * Map objects defined by several points, equals to openstreetmap way. Using to
 * defing unique id, comes from openstreetmap, sharing among way-like map
 * objects
 *
 * @author pgalex
 */
public abstract class MapWay extends MapObject
{
	/**
	 * ID of way, comes from OpenStreetMap
	 */
	private long id;

	/**
	 * Initialize with parameters
	 *
	 * @param wayId unique id of way from openstreetmap
	 * @param objectDefenitionTags defenition tags of way
	 * @throws IllegalArgumentException objectDefenitionTags incorrect
	 */
	public MapWay(long wayId, DefenitionTags objectDefenitionTags) throws IllegalArgumentException
	{
		super(objectDefenitionTags);

		id = wayId;
	}

	/**
	 * Get ID
	 *
	 * @return ID of object, comes from OpenStreetMap
	 */
	public long getId()
	{
		return id;
	}

	/**
	 * Is equals to objects.
	 *
	 * @param objectToCompare object for comparing
	 * @return is equals to given objects. way-like objects is equal to other
	 * way-like if thier ids equals, and to equals to other types of map objects
	 */
	@Override
	public boolean equals(Object objectToCompare)
	{
		if (objectToCompare instanceof MapWay)
		{
			MapWay comparingWay = (MapWay) objectToCompare;
			return comparingWay.id == id;
		}
		else
		{
			return false;
		}
	}

	/**
	 * Get hash code
	 *
	 * @return hash code
	 */
	@Override
	public int hashCode()
	{
		int hash = 3;
		hash = 97 * hash + (int) (this.id ^ (this.id >>> 32));
		return hash;
	}
}
