package com.osmviewer.map;

import com.osmviewer.map.exceptions.FetchingErrorException;
import com.osmviewer.mapDefenitionUtilities.DefenitionTags;
import com.osmviewer.mapDefenitionUtilities.Location;
import com.osmviewer.mapDefenitionUtilities.MapBounds;
import com.osmviewer.rendering.RenderableMapObjectDrawSettings;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

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
	 * Index by latitude
	 */
	private int latitudeIndex;
	/**
	 * Index by longitude
	 */
	private int longitudeIndex;
	/**
	 * Bounds. Linked and can be computed by latitude and longitude indexes. Using
	 * like field to prevent unnessesary computing while map rendering
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
	 * Is sector already loaded (with load method), using for multithreading work
	 */
	private AtomicBoolean loaded;

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
		loaded = new AtomicBoolean(false);
	}

	/**
	 * Add all storing map objects to end of given collection
	 *
	 * @param collection collection to add map objects to
	 * @throws IllegalArgumentException collection is null
	 */
	public void addAllStoringObjectsTo(Collection<MapObject> collection) throws IllegalArgumentException
	{
		if (collection == null)
		{
			throw new IllegalArgumentException("collection is null");
		}

		if (!loaded.get())
		{
			return;
		}

		collection.addAll(objects);
	}

	/**
	 * Get count of storing objects
	 *
	 * @return count of storing objects
	 */
	public int getStoringObjectsCount()
	{
		if (loaded.get())
		{
			return objects.size();
		}
		else
		{
			return 0;
		}
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
	 * Get bounds of sector area
	 *
	 * @return bounds of sector area
	 */
	public MapBounds getBounds()
	{
		return bounds;
	}

	/**
	 * Is finished loading
	 *
	 * @return is sector finished loading
	 */
	public boolean isLoaded()
	{
		return loaded.get();
	}

	/**
	 * Load object by given map data source in sector bounds
	 *
	 * @param mapDataSource data source, using to fetch map objects, in given
	 * @param objectsDrawSettingsFinder finder of loading objects draw settings
	 * @param loadingHandler loading results handler
	 * @throws IllegalArgumentException mapDataSource, loadingHandler or
	 * objectsDrawSettingsFinder is null
	 * @throws FetchingErrorException error while loading
	 */
	public void loadObjects(final MapDataSource mapDataSource,
					RenderableMapObjectsDrawSettingsFinder objectsDrawSettingsFinder,
					final MapSectorLoadingHandler loadingHandler) throws IllegalArgumentException, FetchingErrorException
	{
		if (mapDataSource == null)
		{
			throw new IllegalArgumentException("mapDataSource is null");
		}
		if (objectsDrawSettingsFinder == null)
		{
			throw new IllegalArgumentException("objectsDrawSettingsFinder is null");
		}
		if (loadingHandler == null)
		{
			throw new IllegalArgumentException("loadingHandler is null");
		}

		loaded.set(false);
		drawSettingsFinder = objectsDrawSettingsFinder;

		final MapDataSourceFetchResultsHandler resultsHandler = this;
		Thread loadingObjectsThread = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					mapDataSource.fetchMapObjectsInArea(getBounds(), resultsHandler);
					loaded.set(true);
					loadingHandler.sectorLoaded();
				}
				catch (Exception ex)
				{
					objects.clear();
				}
			}
		});
		loadingObjectsThread.start();
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
		boolean pointsDefinesMapPoint = points.length == 1;
		if (pointsDefinesMapPoint)
		{
			createdMapObject = new MapPoint(points[0], uniqueId, tags);
		}
		else
		{
			if (MapPolygon.isLocationsCanBeUsedToCreatePolygon(points))
			{
				createdMapObject = new MapPolygon(uniqueId, tags, points);
			}
			else
			{
				if (MapLine.isLocationsCanBeUsedToCreateLine(points))
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
				createdMapObject.setDrawSettings(drawSettings);
				objects.add(createdMapObject);
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
