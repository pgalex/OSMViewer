package com.osmviewer.sqliteMapDataSourceTests;

import com.osmviewer.mapDefenitionUtilities.DefenitionTags;
import com.osmviewer.sqliteMapDataSource.MapObjectsIdentifierFinder;

/**
 * Test implementation of MapObjectsIdentifiersFinder
 *
 * @author preobrazhentsev
 */
public class TestMapObjectsIdentifiersFinder implements MapObjectsIdentifierFinder
{
	@Override
	public String findClosedWayIdentifier(DefenitionTags polygonTags) throws IllegalArgumentException
	{
		return "";
	}

	@Override
	public String findNonClosedIdentifier(DefenitionTags lineTags) throws IllegalArgumentException
	{
		return "";
	}

	@Override
	public String findNodeIdentifier(DefenitionTags pointTags) throws IllegalArgumentException
	{
		return "";
	}
}
