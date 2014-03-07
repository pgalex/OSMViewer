package com.osmviewer.sqliteMapDataSource;

import com.osmviewer.mapDefenitionUtilities.DefenitionTags;

/**
 * Finds drawing id for map object
 *
 * @author preobrazhentsev
 */
public interface MapObjectsDrawingIdFinder
{
	/**
	 * Find drawing id for closed way
	 *
	 * @param wayTags tags of closed way to find id of. Must be not null
	 * @return found id. Null if not found
	 * @throws IllegalArgumentException wayTags is null
	 */
	public String findClosedWayDrawingId(DefenitionTags wayTags) throws IllegalArgumentException;

	/**
	 * Find drawing id for non-closed way
	 *
	 * @param wayTags tags of non-closed way to find id of. Must be not null
	 * @return found id. Null if not found
	 * @throws IllegalArgumentException wayTags is null
	 */
	public String findNonClosedWayDrawingId(DefenitionTags wayTags) throws IllegalArgumentException;

	/**
	 * Find drawing id for node
	 *
	 * @param nodeTags tags of node to find id of. Must be not null
	 * @return found id. Null if not found
	 * @throws IllegalArgumentException nodeTags is null
	 */
	public String findNodeDrawingId(DefenitionTags nodeTags) throws IllegalArgumentException;
}
