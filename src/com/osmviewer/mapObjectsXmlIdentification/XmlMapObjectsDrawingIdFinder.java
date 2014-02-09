package com.osmviewer.mapObjectsXmlIdentification;

import com.osmviewer.mapDefenitionUtilities.DefenitionTags;
import com.osmviewer.mapDefenitionUtilities.Tag;
import com.osmviewer.sqliteMapDataSource.MapObjectsDrawingIdFinder;

/**
 * Finds drawing id synchronized with xml description file
 *
 * @author preobrazhentsev
 */
public class XmlMapObjectsDrawingIdFinder implements MapObjectsDrawingIdFinder
{
	/**
	 * Find drawing id for closed way
	 *
	 * @param wayTags tags of closed way to find drawing id of. Must be not null
	 * @return found drawing id. Null if not found
	 * @throws IllegalArgumentException wayTags is null
	 */
	@Override
	public String findClosedWayDrawingId(DefenitionTags wayTags) throws IllegalArgumentException
	{
		if (wayTags == null)
		{
			throw new IllegalArgumentException("wayTags is null");
		}

		if (wayTags.contains(new Tag("natural", "wood")))
		{
			return "natural-wood";
		}

		return null;
	}

	/**
	 * Find drawing id for non-closed way
	 *
	 * @param wayTags tags of non-closed way to find drawing id of. Must be not null
	 * @return found drawing id. Null if not found
	 * @throws IllegalArgumentException wayTags is null
	 */
	@Override
	public String findNonClosedDrawingId(DefenitionTags wayTags) throws IllegalArgumentException
	{
		if (wayTags == null)
		{
			throw new IllegalArgumentException("wayTags is null");
		}

		return null;
	}

	/**
	 * Find drawing id for node
	 *
	 * @param nodeTags tags of node to find drawing id of. Must be not null
	 * @return found drawing id. Null if not found
	 * @throws IllegalArgumentException nodeTags is null
	 */
	@Override
	public String findNodeDrawingId(DefenitionTags nodeTags) throws IllegalArgumentException
	{
		if (nodeTags == null)
		{
			throw new IllegalArgumentException("nodeTags is null");
		}

		return null;
	}
}
