package com.osmviewer.sqliteMapDataSourceTests;

import com.osmviewer.mapDefenitionUtilities.DefenitionTags;
import com.osmviewer.sqliteMapDataSource.MapObjectsIdFinder;

/**
 * Implementation of MapObjectsIdFinder for testing
 *
 * @author preobrazhentsev
 */
public class MapObjectsIdFinderFake implements MapObjectsIdFinder
{
	@Override
	public String findClosedWayMapObjectId(DefenitionTags polygonTags) throws IllegalArgumentException
	{
		return "";
	}

	@Override
	public String findNonClosedWayMapObjectId(DefenitionTags lineTags) throws IllegalArgumentException
	{
		return "";
	}

	@Override
	public String findNodeMapObjectId(DefenitionTags pointTags) throws IllegalArgumentException
	{
		return "";
	}
}
