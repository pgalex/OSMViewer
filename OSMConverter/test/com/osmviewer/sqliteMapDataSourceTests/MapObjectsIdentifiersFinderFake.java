package com.osmviewer.sqliteMapDataSourceTests;

import com.osmviewer.mapDefenitionUtilities.DefenitionTags;
import com.osmviewer.sqliteMapDataSource.MapObjectsDrawingIdFinder;

/**
 * Implementation of MapObjectsIdentifiersFinder for testing
 *
 * @author preobrazhentsev
 */
public class MapObjectsIdentifiersFinderFake implements MapObjectsDrawingIdFinder
{
	@Override
	public String findClosedWayDrawingId(DefenitionTags polygonTags) throws IllegalArgumentException
	{
		return "";
	}

	@Override
	public String findNonClosedWayDrawingId(DefenitionTags lineTags) throws IllegalArgumentException
	{
		return "";
	}

	@Override
	public String findNodeDrawingId(DefenitionTags pointTags) throws IllegalArgumentException
	{
		return "";
	}
}
