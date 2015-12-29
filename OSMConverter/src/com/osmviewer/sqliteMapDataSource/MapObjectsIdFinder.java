package com.osmviewer.sqliteMapDataSource;

import com.osmviewer.mapDefenitionUtilities.DefenitionTags;

/**
 * Finds id for map object
 *
 * @author preobrazhentsev
 */
public interface MapObjectsIdFinder
{
	/**
	 * Find map object id for closed way
	 *
	 * @param wayTags tags of closed way to find id of. Must be not null
	 * @return found id. Null if not found
	 * @throws IllegalArgumentException wayTags is null
	 */
	public String findClosedWayMapObjectId(DefenitionTags wayTags) throws IllegalArgumentException;

	/**
	 * Find map object id for non-closed way
	 *
	 * @param wayTags tags of non-closed way to find id of. Must be not null
	 * @return found id. Null if not found
	 * @throws IllegalArgumentException wayTags is null
	 */
	public String findNonClosedWayMapObjectId(DefenitionTags wayTags) throws IllegalArgumentException;

	/**
	 * Find map object id for node
	 *
	 * @param nodeTags tags of node to find id of. Must be not null
	 * @return found id. Null if not found
	 * @throws IllegalArgumentException nodeTags is null
	 */
	public String findNodeMapObjectId(DefenitionTags nodeTags) throws IllegalArgumentException;
}
