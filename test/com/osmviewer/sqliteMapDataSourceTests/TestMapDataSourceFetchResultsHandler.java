package com.osmviewer.sqliteMapDataSourceTests;

import com.osmviewer.map.MapDataSourceFetchResultsHandler;
import com.osmviewer.mapDefenitionUtilities.DefenitionTags;
import com.osmviewer.mapDefenitionUtilities.Location;
import java.util.ArrayList;

/**
 * Test implementation of MapDataSourceFetchResultsHandler
 *
 * @author pgalex
 */
public class TestMapDataSourceFetchResultsHandler implements MapDataSourceFetchResultsHandler
{
	public ArrayList<Long> fetchedIds;
	public ArrayList<DefenitionTags> fetchedTags;
	public ArrayList<Location[]> fetchedPoints;
	
	public TestMapDataSourceFetchResultsHandler()
	{
		fetchedIds = new ArrayList<Long>();
		fetchedTags = new ArrayList<DefenitionTags>();
		fetchedPoints = new ArrayList<Location[]>();
	}
	
	@Override
	public void takeMapObjectData(long uniqueId, DefenitionTags tags, Location[] points) throws IllegalArgumentException
	{
		fetchedIds.add(uniqueId);
		fetchedTags.add(tags);
		fetchedPoints.add(points);
	}
}
