package com.osmviewer.map;

import com.osmviewer.map.exceptions.FetchingErrorException;
import com.osmviewer.mapDefenitionUtilities.DefenitionTags;
import com.osmviewer.mapDefenitionUtilities.Location;
import com.osmviewer.mapDefenitionUtilities.MapBounds;
import java.util.LinkedList;

/**
 * Sector of sectored map
 *
 * @author pgalex
 */
public class MapSector implements MapDataSourceFetchResultsHandler
{
	/**
	 * Sector area size by latitude
	 */
	public static double LATITUDE_SIZE = 0.5;
	/**
	 * Sector area size by longitude
	 */
	public static double LONGITUDE_SIZE = 0.5;
	/**
	 * Index of sector by latitude
	 */
	private int latitudeIndex;
	/**
	 * Index of sector by longitude
	 */
	private int longitudeIndex;
	/**
	 * Objects in sector
	 */
	private LinkedList<MapObject> objects;

	/**
	 * Create with bounds
	 *
	 * @param sectorLatitudeIndex index of sector by latitude
	 * @param sectorLongitudeIndex index of sector by longitude
	 */
	public MapSector(int sectorLatitudeIndex, int sectorLongitudeIndex)
	{
		objects = new LinkedList<MapObject>();
		
		latitudeIndex = sectorLatitudeIndex;
		longitudeIndex = sectorLongitudeIndex;
	}

	/**
	 * Load object by given map data source int sector bounds
	 *
	 * @param mapDataSource data source, using to fetch map objects, in given
	 * @throws IllegalArgumentException mapDataSource is null
	 * @throws FetchingErrorException error while loading
	 */
	public void loadObjects(MapDataSource mapDataSource) throws IllegalArgumentException, FetchingErrorException
	{
		if (mapDataSource == null)
		{
			throw new IllegalArgumentException("mapDataSource is null");
		}

		mapDataSource.fetchMapObjectsInArea(getBounds(), this);
	}

	/**
	 * Get bounds of sector area
	 *
	 * @return bounds of sector area
	 */
	public MapBounds getBounds()
	{
		return new MapBounds(latitudeIndex * LATITUDE_SIZE, latitudeIndex * LATITUDE_SIZE + LATITUDE_SIZE,
						longitudeIndex * LONGITUDE_SIZE, longitudeIndex * LONGITUDE_SIZE + LONGITUDE_SIZE);
	}

	/**
	 * Take and process data of fetched map object
	 *
	 * @param uniqueId unique openstreetmap id of fetched map objects
	 * @param tags tags of fetched map object
	 * @param points points on map, defining map object
	 * @throws IllegalArgumentException tags is null; points is null, empty or
	 * contains null
	 */
	@Override
	public void takeMapObjectData(long uniqueId, DefenitionTags tags, Location[] points) throws IllegalArgumentException
	{
		if (tags == null)
		{
			throw new IllegalArgumentException("tags is null");
		}
		if (!isMapObjectPointsCorrent(points))
		{
			throw new IllegalArgumentException("points incorrent");
		}

		if (points.length == 1)
		{
			objects.add(new MapPoint(points[0], uniqueId, tags));
		}
		else
		{
			if (points[0].equals(points[points.length - 1]))
			{
				if (points.length >= 3)
				{
					objects.add(new MapPolygon(uniqueId, tags, points));
				}
			}
			else
			{
				if (points.length >= 2)
				{
					objects.add(new MapLine(uniqueId, tags, points));
				}
			}
		}
	}

	/**
	 * Is map object points corrent
	 *
	 * @param points points to test
	 * @return is points not null, not empty, not contains null
	 */
	private static boolean isMapObjectPointsCorrent(Location[] points)
	{
		if (points == null)
		{
			return false;
		}
		if (points.length == 0)
		{
			return false;
		}
		for (int i = 0; i < points.length; i++)
		{
			if (points[i] == null)
			{
				return false;
			}
		}

		return true;
	}

	/**
	 * Get count of storing objects
	 *
	 * @return count of storing objects
	 */
	public int getObjectsCount()
	{
		return objects.size();
	}

	/**
	 * Find latitude index of sector for latitude value
	 *
	 * @param latitude latitude to find index by
	 * @return found sector latitude index
	 */
	public static int findSectorLatitudeIndex(double latitude)
	{
		return (int) Math.floor(latitude / LATITUDE_SIZE);
	}

	/**
	 * Find longitude index of sector for longitude value
	 *
	 * @param longitude longitude to find index by
	 * @return found sector longitude index
	 */
	public static int findSectorLongitudeIndex(double longitude)
	{
		return (int) Math.floor(longitude / LATITUDE_SIZE);
	}
}
