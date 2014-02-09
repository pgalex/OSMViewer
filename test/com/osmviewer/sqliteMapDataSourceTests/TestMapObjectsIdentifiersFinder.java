package com.osmviewer.sqliteMapDataSourceTests;

import com.osmviewer.mapDefenitionUtilities.DefenitionTags;
import com.osmviewer.sqliteMapDataSource.MapObjectsDrawingIdFinder;

/**
 * Test implementation of MapObjectsIdentifiersFinder
 *
 * @author preobrazhentsev
 */
public class TestMapObjectsIdentifiersFinder implements MapObjectsDrawingIdFinder
{
	@Override
	public String findClosedWayDrawingId(DefenitionTags polygonTags) throws IllegalArgumentException
	{
		return "";
	}

	@Override
	public String findNonClosedDrawingId(DefenitionTags lineTags) throws IllegalArgumentException
	{
		return "";
	}

	@Override
	public String findNodeDrawingId(DefenitionTags pointTags) throws IllegalArgumentException
	{
		return "";
	}
}
