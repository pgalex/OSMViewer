package com.osmviewer.xmlDrawSettings;

import com.osmviewer.mapDefenitionUtilities.DefenitionTags;
import com.osmviewer.mapDefenitionUtilities.Tag;
import com.osmviewer.sqliteMapDataSource.MapObjectsIdFinder;

/**
 * Finds map object id synchronized with xml description file
 *
 * @author preobrazhentsev
 */
public class XmlMapObjectsIdFinder implements MapObjectsIdFinder
{
	/**
	 * Find map object id for closed way
	 *
	 * @param wayTags tags of closed way to find map object id of. Must be not
	 * null
	 * @return found map object id. Null if not found
	 * @throws IllegalArgumentException wayTags is null
	 */
	@Override
	public String findClosedWayMapObjectId(DefenitionTags wayTags) throws IllegalArgumentException
	{
		if (wayTags == null)
		{
			throw new IllegalArgumentException("wayTags is null");
		}

		if (wayTags.contains(new Tag("natural", "wood")))
		{
			return "natural-wood-closed-way";
		}

		return null;
	}

	/**
	 * Find map object id for non-closed way
	 *
	 * @param wayTags tags of non-closed way to find map object id of. Must be not
	 * null
	 * @return found id. Null if not found
	 * @throws IllegalArgumentException wayTags is null
	 */
	@Override
	public String findNonClosedWayMapObjectId(DefenitionTags wayTags) throws IllegalArgumentException
	{
		if (wayTags == null)
		{
			throw new IllegalArgumentException("wayTags is null");
		}

		return null;
	}

	/**
	 * Find map object id for node
	 *
	 * @param nodeTags tags of node to find map object id of. Must be not null
	 * @return found id. Null if not found
	 * @throws IllegalArgumentException nodeTags is null
	 */
	@Override
	public String findNodeMapObjectId(DefenitionTags nodeTags) throws IllegalArgumentException
	{
		if (nodeTags == null)
		{
			throw new IllegalArgumentException("nodeTags is null");
		}

		if (nodeTags.contains(new Tag("place", "village")))
		{
			return "place-village-node";
		}

		return null;
	}
}
