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
			MapBounds pointsBounds = findPointsBounds(storingPoints.get(i));
			if (pointsBounds.getLatitudeMinimum() <= area.getLatitudeMaximum()
							&& pointsBounds.getLatitudeMaximum() >= area.getLatitudeMinimum()
							&& pointsBounds.getLongitudeMinimum() <= area.getLongitudeMaximum()
							&& pointsBounds.getLongitudeMaximum() >= area.getLongitudeMinimum())
			{
				fetchResultsHandler.takeMapObjectData(storingIds.get(i), storingTags.get(i), storingPoints.get(i));
			}
		}
	}

	private MapBounds findPointsBounds(Location[] points)
	{

		double minLatitude = points[0].getLatitude();
		double minLongitude = points[0].getLongitude();
		double maxLatitude = points[0].getLatitude();
		double maxLongitude = points[0].getLatitude();

		for (int i = 0; i < points.length; i++)
		{
			Location mapPosition = points[i];
			if (mapPosition.getLatitude() < minLatitude)
			{
				minLatitude = mapPosition.getLatitude();
			}
			if (mapPosition.getLongitude() < minLongitude)
			{
				minLongitude = mapPosition.getLongitude();
			}
			if (mapPosition.getLatitude() > maxLatitude)
			{
				maxLatitude = mapPosition.getLatitude();
			}
			if (mapPosition.getLongitude() > maxLongitude)
			{
				maxLongitude = mapPosition.getLongitude();
			}
		}

		return new MapBounds(minLatitude, maxLatitude, minLongitude, maxLongitude);
	}
}
