package com.osmviewer.map;

import com.osmviewer.map.exceptions.FetchingErrorException;
import com.osmviewer.mapDefenitionUtilities.MapBounds;
import com.osmviewer.rendering.RenderableMap;
import com.osmviewer.rendering.RenderableMapObjectsDrawPriorityComparator;
import com.osmviewer.rendering.RenderableMapObjectsVisitor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Map, storing and processings map objects by sectors. Map area separating by
 * sectors with defined latitude and longitude size
 *
 * @author pgalex
 */
public class SectoredMap implements RenderableMap
{
	/**
	 * Maximum count of storing invisible sectors
	 */
	private int MAXIMUM_INVISIBLE_SECTOR_COUNT = 5;
	/**
	 * Sectors of map
	 */
	private LinkedList<MapSector> sectors;

	/**
	 * Create empty
	 */
	public SectoredMap()
	{
		sectors = new LinkedList<MapSector>();
	}

	/**
	 * Load map objects in area with given map data source
	 *
	 * @param area area, determining which objects need to load
	 * @param mapDataSource map data source using for loading
	 * @param objectsDrawSettingsFinder loading objects draw settings finder
	 * @throws IllegalArgumentException area is null, mapDataSource is null,
	 * objectsDrawSettingsFinder is null
	 *
	 * @throws FetchingErrorException error while loading
	 */
	public void loadObjectsInArea(MapBounds area, MapDataSource mapDataSource,
					RenderableMapObjectsDrawSettingsFinder objectsDrawSettingsFinder) throws IllegalArgumentException, FetchingErrorException
	{
		if (area == null)
		{
			throw new IllegalArgumentException("area is null");
		}
		if (mapDataSource == null)
		{
			throw new IllegalArgumentException("mapDataSource is null");
		}

		if (area.isZero())
		{
			sectors.clear();
			return;
		}

		int minLatitudeSectorIndex = MapSector.findSectorLatitudeIndex(area.getLatitudeMinimum());
		int minLongitudeSectorIndex = MapSector.findSectorLongitudeIndex(area.getLongitudeMinimum());

		int maxLatitudeSectorIndex = MapSector.findSectorLatitudeIndex(area.getLatitudeMaximum());
		int maxLongitudeSectorIndex = MapSector.findSectorLongitudeIndex(area.getLongitudeMaximum());

		for (int latitudeIndex = minLatitudeSectorIndex; latitudeIndex <= maxLatitudeSectorIndex; latitudeIndex++)
		{
			for (int longitudeIndex = minLongitudeSectorIndex; longitudeIndex <= maxLongitudeSectorIndex; longitudeIndex++)
			{
				if (isExistsSectorWithIndexes(latitudeIndex, longitudeIndex))
				{
					continue;
				}
				MapSector newSector = new MapSector(latitudeIndex, longitudeIndex);
				newSector.loadObjects(mapDataSource, objectsDrawSettingsFinder);
				sectors.add(newSector);
			}
		}

		keepAndRemoveInvisibleSectors(area);
	}

	/**
	 * Keep MAXIMUM_INVISIBLE_SECTOR_COUNT invisible in area sectors, and remove
	 * other
	 *
	 * @param area area to determine sectors visiblity in
	 * @throws IllegalArgumentException area is null
	 */
	private void keepAndRemoveInvisibleSectors(MapBounds area) throws IllegalArgumentException
	{
		if (area == null)
		{
			throw new IllegalArgumentException("area is null");
		}

		ArrayList<MapSector> invisibleSectors = new ArrayList<MapSector>();
		for (MapSector mapSector : sectors)
		{
			if (!mapSector.getBounds().intersects(area))
			{
				invisibleSectors.add(mapSector);
			}
		}

		for (int i = MAXIMUM_INVISIBLE_SECTOR_COUNT; i < invisibleSectors.size(); i++)
		{
			sectors.remove(invisibleSectors.get(i));
		}
	}

	/**
	 * Is exists in aleardy loaded sectors sector with given indexes
	 *
	 * @param sectorLatitudeIndex index of sector by latitude
	 * @param sectorLongitudeIndex index of sector by longitude
	 * @return is aleardy exists sector with given indexes
	 */
	private boolean isExistsSectorWithIndexes(int sectorLatitudeIndex, int sectorLongitudeIndex)
	{
		for (MapSector mapSector : sectors)
		{
			if (mapSector.getLatitudeIndex() == sectorLatitudeIndex
							&& mapSector.getLongitudeIndex() == sectorLongitudeIndex)
			{
				return true;
			}
		}

		return false;
	}

	/**
	 * Get count of storing objects
	 *
	 * @return count of storing objects"
	 */
	public int getObjectsCount()
	{
		int storingObjectsCount = 0;

		for (MapSector mapSector : sectors)
		{
			storingObjectsCount += mapSector.getStoringObjectsCount();
		}

		return storingObjectsCount;
	}

	/**
	 * Accept rendering visitor for all map objects visible in area. Object should be given
	 * to objectsVisitor by its draw priority
	 *
	 * @param objectsRenderingVisitor objects renderer
	 * @param renderingArea area to determine which object need give to
	 * objectsVisitor
	 * @param objectsDrawPriorityComparator comparator for sorting rendering
	 * objects by its draw priority
	 * @throws IllegalArgumentException objectsVisitor, area or
	 * objectsDrawPriorityComparator is null
	 */
	@Override
	public void renderObjectsByDrawPriority(RenderableMapObjectsVisitor objectsRenderingVisitor, MapBounds renderingArea, RenderableMapObjectsDrawPriorityComparator objectsDrawPriorityComparator) throws IllegalArgumentException
	{
		if (objectsRenderingVisitor == null)
		{
			throw new IllegalArgumentException("objectsRenderingVisitor is null");
		}
		if (renderingArea == null)
		{
			throw new IllegalArgumentException("renderingArea is null");
		}
		if (objectsDrawPriorityComparator == null)
		{
			throw new IllegalArgumentException("objectsDrawPriorityComparator is null");
		}

		if (renderingArea.isZero())
		{
			return;
		}

		// определить сектора, которые следует нарисовать
		// добавить в массив объекты этих секторов с учетом повторов
		// сортировать массив
		// нарисовать объекты

		ArrayList<MapSector> renderedSectors = new ArrayList<MapSector>();
		LinkedList<MapObject> objectsToRender = new LinkedList<MapObject>();
		for (MapSector renderingSector : sectors)
		{
			if (renderingArea.intersects(renderingSector.getBounds()))
			{
				ArrayList<MapObject> renderingSectorObjects = new ArrayList<MapObject>(renderingSector.getStoringObjectsCount());
				renderingSector.addAllStoringObjectsToList(renderingSectorObjects);
				for (MapObject mapObject : renderingSectorObjects)
				{
					boolean objectAlreadyRendered = false;
					for (int i = 0; i < renderedSectors.size(); i++)
					{
						if (mapObject.isVisibleInArea(renderedSectors.get(i).getBounds()))
						{
							objectAlreadyRendered = true;
						}
					}
					if (!objectAlreadyRendered)
					{
						objectsToRender.add(mapObject);
					}
				}

				renderedSectors.add(renderingSector);
			}
		}

		Collections.sort(objectsToRender, objectsDrawPriorityComparator);

		for (MapObject mapObject : objectsToRender)
		{
			if (mapObject.isVisibleInArea(renderingArea))
			{
				mapObject.acceptRenderingVisitor(objectsRenderingVisitor);
			}
		}
	}
}
