package com.osmviewer.map;

import com.osmviewer.map.exceptions.FetchingErrorException;
import com.osmviewer.mapDefenitionUtilities.MapBounds;
import com.osmviewer.rendering.RenderableMap;
import com.osmviewer.rendering.RenderableMapObjectsDrawPriorityComparator;
import com.osmviewer.rendering.RenderableMapObjectsVisitor;
import java.util.LinkedList;

/**
 * Map, storing and processings map objects by sectors
 *
 * @author pgalex
 */
public class SectoredMap implements RenderableMap
{
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
				MapSector newSector = new MapSector(latitudeIndex, longitudeIndex);
				newSector.loadObjects(mapDataSource, objectsDrawSettingsFinder);
				sectors.add(newSector);
			}
		}
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
			storingObjectsCount += mapSector.getObjectsCount();
		}

		return storingObjectsCount;
	}

	/**
	 * Accept visitor for all map objects visible in area. Object should be given
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

		for (MapSector mapSector : sectors)
		{
			if (renderingArea.intersects(mapSector.getBounds()))
			{
				mapSector.renderObjectsByDrawPriority(objectsRenderingVisitor, renderingArea, objectsDrawPriorityComparator);
			}
		}
	}
}