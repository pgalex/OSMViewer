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
}
