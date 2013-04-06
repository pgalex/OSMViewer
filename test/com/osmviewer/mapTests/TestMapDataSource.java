package com.osmviewer.mapTests;

import com.osmviewer.map.MapDataSource;
import com.osmviewer.map.MapDataSourceFetchResultsHandler;
import com.osmviewer.map.exceptions.FetchingErrorException;
import com.osmviewer.mapDefenitionUtilities.DefenitionTags;
import com.osmviewer.mapDefenitionUtilities.Location;
import com.osmviewer.mapDefenitionUtilities.MapBounds;
import java.util.ArrayList;

/**
 * Test implementation of MapDataSource
 *
 * @author pgalex
 */
public class TestMapDataSource implements MapDataSource
{
	public ArrayList<Long> storingIds;
	public ArrayList<DefenitionTags> storingTags;
	public ArrayList<Location[]> storingPoints;

	public TestMapDataSource()
	{
		storingIds = new ArrayList<Long>();
		storingTags = new ArrayList<DefenitionTags>();
		storingPoints = new ArrayList<Location[]>();
	}

	@Override
	public void fetchMapObjectsInArea(MapBounds area, MapDataSourceFetchResultsHandler fetchResultsHandler) throws IllegalArgumentException, FetchingErrorException
	{
		for (int i = 0; i < storingIds.size(); i++)
		{
			fetchResultsHandler.takeMapObjectData(storingIds.get(i), storingTags.get(i), storingPoints.get(i));
		}
	}
}
