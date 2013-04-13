package com.osmviewer.map;

import com.osmviewer.map.exceptions.FetchingErrorException;
import com.osmviewer.mapDefenitionUtilities.DefenitionTags;
import com.osmviewer.mapDefenitionUtilities.Location;
import com.osmviewer.mapDefenitionUtilities.MapBounds;
import com.osmviewer.rendering.RenderableMapObjectDrawSettings;
import java.util.LinkedList;
import java.util.List;

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
	public static double LATITUDE_SIZE = 0.05;
	/**
	 * Sector area size by longitude
	 */
	public static double LONGITUDE_SIZE = 0.05;
	/**
	 * Index of sector by latitude
	 */
	private int latitudeIndex;
	/**
	 * Index of sector by longitude
	 */
	private int longitudeIndex;
	/**
	 * Sector bounds. Linked and can be computed by latitude and longitude
	 * indexes. Using like field to prevent unnessesary computing while map
	 * rendering
	 */
	private MapBounds bounds;
	/**
	 * Objects in sector
	 */
	private LinkedList<MapObject> objects;
	/**
	 * Finder, using to find draw settings while loading objects
	 */
	private RenderableMapObjectsDrawSettingsFinder drawSettingsFinder;

	/**
	 * Create empty
	 *
	 * @param sectorLatitudeIndex index of sector by latitude
	 * @param sectorLongitudeIndex index of sector by longitude
	 */
	public MapSector(int sectorLatitudeIndex, int sectorLongitudeIndex)
	{
		objects = new LinkedList<MapObject>();
		drawSettingsFinder = null;

		latitudeIndex = sectorLatitudeIndex;
		longitudeIndex = sectorLongitudeIndex;

		bounds = new MapBounds(latitudeIndex * LATITUDE_SIZE, latitudeIndex * LATITUDE_SIZE + LATITUDE_SIZE,
						longitudeIndex * LONGITUDE_SIZE, longitudeIndex * LONGITUDE_SIZE + LONGITUDE_SIZE);
	}

	public List<MapObject> getObjects()
	{
		return objects;
	}

	/**
	 * Get latitude index
	 *
	 * @return index by latitude
	 */
	public int getLatitudeIndex()
	{
		return latitudeIndex;
	}

	/**
	 * Get longitude index
	 *
	 * @return index by longitude
	 */
	public int getLongitudeIndex()
	{
		return longitudeIndex;
	}

	/**
	 * Load object by given map data source int sector bounds
	 *
	 * @param mapDataSource data source, using to fetch map objects, in given
	 * @param objectsDrawSettingsFinder finder of loading objects draw settings
	 * @throws IllegalArgumentException mapDataSource or objectsDrawSettingsFinder
	 * is null
	 * @throws FetchingErrorException error while loading
	 */
	public void loadObjects(MapDataSource mapDataSource, RenderableMapObjectsDrawSettingsFinder objectsDrawSettingsFinder) throws IllegalArgumentException, FetchingErrorException
	{
		if (mapDataSource == null)
		{
			throw new IllegalArgumentException("mapDataSource is null");
		}
		if (objectsDrawSettingsFinder == null)
		{
			throw new IllegalArgumentException("objectsDrawSettingsFinder is null");
		}

		drawSettingsFinder = objectsDrawSettingsFinder;
		mapDataSource.fetchMapObjectsInArea(getBounds(), this);
	}

	/**
	 * Get bounds of sector area
	 *
	 * @return bounds of sector area
	 */
	public MapBounds getBounds()
	{
		return bounds;
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

		MapObject createdMapObject = null;

		if (points.length == 1)
		{
			createdMapObject = new MapPoint(points[0], uniqueId, tags);
		}
		else
		{
			if (points[0].equals(points[points.length - 1]))
			{
				if (points.length >= 3)
				{
					createdMapObject = new MapPolygon(uniqueId, tags, points);
				}
			}
			else
			{
				if (points.length >= 2)
				{
					createdMapObject = new MapLine(uniqueId, tags, points);
				}
			}
		}

		if (createdMapObject != null)
		{
			RenderableMapObjectDrawSettings drawSettings = drawSettingsFinder.findMapObjectDrawSettings(createdMapObject.getDefenitionTags());
			if (drawSettings != null)
			{
				if (createdMapObject.isVisibleInArea(getBounds()))
				{
					createdMapObject.setDrawSettings(drawSettings);
					objects.add(createdMapObject);
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
