package com.osmviewer.sqliteMapDataSourceTests;

import com.osmviewer.map.MapDataSourceFetchResultsHandler;
import com.osmviewer.mapDefenitionUtilities.Location;
import java.util.ArrayList;

/**
 * Test implementation of MapDataSourceFetchResultsHandler
 *
 * @author pgalex
 */
public class TestMapDataSourceFetchResultsHandler implements MapDataSourceFetchResultsHandler
{
	public ArrayList<Long> fetchedUniqueIds;
	public ArrayList<String> fetchedDrawingIds;
	public ArrayList<Location[]> fetchedPoints;

	public TestMapDataSourceFetchResultsHandler()
	{
		fetchedUniqueIds = new ArrayList<Long>();
		fetchedDrawingIds = new ArrayList<String>();
		fetchedPoints = new ArrayList<Location[]>();
	}

	@Override
	public void takeMapObjectData(long uniqueId, String drawingId, Location[] points) throws IllegalArgumentException
	{
		fetchedUniqueIds.add(uniqueId);
		fetchedDrawingIds.add(drawingId);
		fetchedPoints.add(points);
	}
}
