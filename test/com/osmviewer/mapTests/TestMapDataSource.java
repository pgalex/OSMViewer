package com.osmviewer.mapTests;

import com.osmviewer.map.MapDataSource;
import com.osmviewer.map.MapDataSourceFetchResultsHandler;
import com.osmviewer.map.exceptions.FetchingErrorException;
import com.osmviewer.mapDefenitionUtilities.Location;
import com.osmviewer.mapDefenitionUtilities.MapBounds;
import java.util.ArrayList;

/**
 * Test realization of MapDataSource
 *
 * @author preobrazhentsev
 */
public class TestMapDataSource implements MapDataSource
{
	public ArrayList<Integer> uniquesIds;
	public ArrayList<String> drawingIds;
	public ArrayList<Location[]> points;

	public TestMapDataSource()
	{
		uniquesIds = new ArrayList<>();
		drawingIds = new ArrayList<>();
		points = new ArrayList<>();
	}

	@Override
	public void fetchMapObjectsInArea(MapBounds area, MapDataSourceFetchResultsHandler fetchResultsHandler) throws IllegalArgumentException, FetchingErrorException
	{
		for (int i = 0; i < uniquesIds.size(); i++)
		{
			fetchResultsHandler.takeMapObjectData(uniquesIds.get(i), drawingIds.get(i), points.get(i));

		}
	}

}
