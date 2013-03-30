package com.osmviewer.map;

import com.osmviewer.map.exceptions.FetchingErrorException;
import com.osmviewer.mapDefenitionUtilities.DefenitionTags;
import com.osmviewer.mapDefenitionUtilities.Location;
import com.osmviewer.mapDefenitionUtilities.MapBounds;
import com.osmviewer.rendering.RenderableMap;
import com.osmviewer.rendering.RenderableMapObjectDrawSettings;
import com.osmviewer.rendering.RenderableMapObjectsDrawPriorityComparator;
import com.osmviewer.rendering.RenderableMapObjectsVisitor;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Map loads all objects from given data source
 *
 * @author pgalex
 */
public class MapByDataSource implements RenderableMap, MapDataSourceFetchResultsHandler
{
	/**
	 * Objects on a map
	 */
	private LinkedList<MapObject> objects;

	/**
	 * Create empty
	 */
	public MapByDataSource()
	{
		objects = new LinkedList<MapObject>();
	}

	/**
	 * Load all map objects from given data source
	 *
	 * @param dataSource map objects data source to load objects from
	 * @param drawSettingsFinder draw settings finder
	 * @throws IllegalArgumentException dataSource is null; drawSettingsFinder is
	 * null
	 * @throws FetchingErrorException error while loading
	 */
	public void loadFromDataSource(MapDataSource dataSource,
					RenderableMapObjectsDrawSettingsFinder drawSettingsFinder) throws IllegalArgumentException, FetchingErrorException
	{
		if (dataSource == null)
		{
			throw new IllegalArgumentException("dataSource is null");
		}
		if (drawSettingsFinder == null)
		{
			throw new IllegalArgumentException("drawSettingsFinder is null");
		}
		
		objects.clear();
		dataSource.fetchMapObjectsInArea(new MapBounds(-90, 90, -180, 180), this);
		
		LinkedList<MapObject> objectsWithoutDrawSettings = new LinkedList<MapObject>();
		
		for (MapObject mapObject : objects)
		{
			RenderableMapObjectDrawSettings drawSettings = drawSettingsFinder.findMapObjectDrawSettings(mapObject.getDefenitionTags());
			if (drawSettings != null)
			{
				mapObject.setDrawSettings(drawSettings);
			}
			else
			{
				objectsWithoutDrawSettings.add(mapObject);
			}
		}
		
		for (MapObject objectWithoutSettings : objectsWithoutDrawSettings)
		{
			objects.remove(objectWithoutSettings);
		}
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
		if (!isMapObjectPointsCorrect(points))
		{
			throw new IllegalArgumentException("points incorrect");
		}
		
		if (points.length == 1)
		{
			objects.add(new MapPoint(points[0], uniqueId, tags));
		}
		else
		{
			if (points[0].equals(points[points.length - 1]))
			{
				objects.add(new MapPolygon(uniqueId, tags, points));
			}
			else
			{
				objects.add(new MapLine(uniqueId, tags, points));
			}
		}
	}

	/**
	 * Is map object points correct
	 *
	 * @param points poins to test
	 * @return is points null, empty or contains null
	 */
	private boolean isMapObjectPointsCorrect(Location[] points)
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
	 * Render all map objects, visible in area, with renderer. Object should be
	 * given to objectsVisitor by its draw priority
	 *
	 * @param objectsVisitor objects renderer
	 * @param area area to determine which object need give to objectsVisitor
	 * @param objectsDrawPriorityComparator comparator for sorting rendering
	 * objects by its draw priority
	 * @throws IllegalArgumentException objectsVisitor, area or
	 * objectsDrawPriorityComparator is null
	 */
	@Override
	public void renderObjectsByDrawPriority(RenderableMapObjectsVisitor objectsVisitor, MapBounds area,
					RenderableMapObjectsDrawPriorityComparator objectsDrawPriorityComparator) throws IllegalArgumentException
	{
		if (objectsVisitor == null)
		{
			throw new IllegalArgumentException("objectsVisitor is null");
		}
		if (area == null)
		{
			throw new IllegalArgumentException("area is null");
		}
		if (objectsDrawPriorityComparator == null)
		{
			throw new IllegalArgumentException("objectsDrawPriorityComparator is null");
		}

		// nothing to visit
		if (area.isZero())
		{
			return;
		}
		
		Collections.sort(objects, objectsDrawPriorityComparator);
		
		for (MapObject renderingObject : objects)
		{
			if (renderingObject.isVisibleInArea(area))
			{
				renderingObject.acceptRenderingVisitor(objectsVisitor);
			}
		}
	}
}
