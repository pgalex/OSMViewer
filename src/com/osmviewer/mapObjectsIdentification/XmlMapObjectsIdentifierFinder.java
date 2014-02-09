package com.osmviewer.mapObjectsIdentification;

import com.osmviewer.mapDefenitionUtilities.DefenitionTags;
import com.osmviewer.mapDefenitionUtilities.Tag;
import com.osmviewer.sqliteMapDataSource.MapObjectsIdentifierFinder;

/**
 * Finds id map object synchronized with xml description file
 *
 * @author preobrazhentsev
 */
public class XmlMapObjectsIdentifierFinder implements MapObjectsIdentifierFinder
{
	/**
	 * Find id for closed way
	 *
	 * @param wayTags tags of closed way to find id of. Must be not null
	 * @return found id. Null if not found
	 * @throws IllegalArgumentException wayTags is null
	 */
	@Override
	public String findClosedWayIdentifier(DefenitionTags wayTags) throws IllegalArgumentException
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
	 * Find id for non-closed way
	 *
	 * @param wayTags tags of non-closed way to find id of. Must be not null
	 * @return found id. Null if not found
	 * @throws IllegalArgumentException wayTags is null
	 */
	@Override
	public String findNonClosedIdentifier(DefenitionTags wayTags) throws IllegalArgumentException
	{
		if (wayTags == null)
		{
			throw new IllegalArgumentException("wayTags is null");
		}

		return null;
	}

	/**
	 * Find id for node
	 *
	 * @param nodeTags tags of node to find id of. Must be not null
	 * @return found id. Null if not found
	 * @throws IllegalArgumentException nodeTags is null
	 */
	@Override
	public String findNodeIdentifier(DefenitionTags nodeTags) throws IllegalArgumentException
	{
		if (nodeTags == null)
		{
			throw new IllegalArgumentException("nodeTags is null");
		}

		return null;
	}
}
