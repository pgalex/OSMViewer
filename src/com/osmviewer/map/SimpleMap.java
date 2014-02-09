package com.osmviewer.map;

import com.osmviewer.map.exceptions.FetchingErrorException;
import com.osmviewer.mapDefenitionUtilities.Location;
import com.osmviewer.mapDefenitionUtilities.MapBounds;
import com.osmviewer.rendering.RenderableMap;
import com.osmviewer.rendering.MapObjectsRenderer;
import com.osmviewer.rendering.RenderableMapObjectsDrawPriorityComparator;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Stores map objects
 *
 * @author preobrazhentsev
 */
public class SimpleMap implements MapDataSourceFetchResultsHandler, RenderableMap
{
	/**
	 * Objects of map
	 */
	private final ArrayList<MapObject> mapObjects;

	/**
	 * Create empty
	 */
	public SimpleMap()
	{
		mapObjects = new ArrayList<>();
	}

	/**
	 * Load map objects in area with given map data source
	 *
	 * @param area area, determining which objects need to load. Must be not null
	 * @param mapDataSource map data source using for loading. Must be not null
	 * @throws IllegalArgumentException area is null, mapDataSource is null
	 *
	 * @throws FetchingErrorException error while loading
	 */
	public void loadObjectsInArea(MapBounds area, MapDataSource mapDataSource) throws IllegalArgumentException, FetchingErrorException
	{
		if (area == null)
		{
			throw new IllegalArgumentException("area is null");
		}
		if (mapDataSource == null)
		{
			throw new IllegalArgumentException("mapDataSource is null");
		}

		mapObjects.clear();
		if (!area.isZero())
		{
			mapDataSource.fetchMapObjectsInArea(area, this);
		}
	}

	/**
	 * Take and process data of fetched map object
	 *
	 * @param uniqueId unique object identifier
	 * @param drawingId map object drawing identifier. Must be not null
	 * @param points points on map, defining map object position. Must be not
	 * null, not empty, not contains null
	 * @throws IllegalArgumentException drawingId is null; points is null, empty
	 * or contains null
	 */
	@Override
	public void takeMapObjectData(long uniqueId, String drawingId, Location[] points) throws IllegalArgumentException
	{
		if (drawingId == null)
		{
			throw new IllegalArgumentException("drawingId is null");
		}
		if (!isMapObjectPointsCorrect(points))
		{
			throw new IllegalArgumentException("points incorrect");
		}

		mapObjects.add(new MapObject(drawingId, points));
	}

	/**
	 * Is given map object points array correct
	 *
	 * @param pointsToTest map object points array
	 * @return Is given map object points array correct
	 */
	private boolean isMapObjectPointsCorrect(Location[] pointsToTest)
	{
		if (pointsToTest == null)
		{
			return false;
		}
		if (pointsToTest.length == 0)
		{
			return false;
		}
		for (Location point : pointsToTest)
		{
			if (point == null)
			{
				return false;
			}
		}

		return true;
	}

	/**
	 * Render storing map objects, that visible in area, with given renderer
	 *
	 * @param mapObjectsRenderer objects renderer. Must be not null
	 * @param renderingArea area to determine which object need to render. Must be
	 * not null
	 * @param objectsDrawPriorityComparator comparator for sorting rendering
	 * objects by its draw priority. Must ne not null
	 * @throws IllegalArgumentException mapObjectsRenderer, renderingArea or
	 * objectsDrawPriorityComparator is null
	 */
	@Override
	public void renderObjectsByDrawPriority(MapObjectsRenderer mapObjectsRenderer,
					MapBounds renderingArea, RenderableMapObjectsDrawPriorityComparator objectsDrawPriorityComparator) throws IllegalArgumentException
	{
		if (mapObjectsRenderer == null)
		{
			throw new IllegalArgumentException("mapObjectsRenderer is null");
		}
		if (renderingArea == null)
		{
			throw new IllegalArgumentException("renderingArea is null");
		}
		if (objectsDrawPriorityComparator == null)
		{
			throw new IllegalArgumentException("objectsDrawPriorityComparator is null");
		}
		
		Collections.sort(mapObjects, objectsDrawPriorityComparator);
		// todo  is Visisble in area

		for (MapObject mapObject : mapObjects)
		{
			mapObjectsRenderer.renderMapObject(mapObject);
		}
	}

	/**
	 * Get count of storing map objects
	 *
	 * @return count of storing map objects
	 */
	public int getStoringObjectsCount()
	{
		return mapObjects.size();
	}
}
