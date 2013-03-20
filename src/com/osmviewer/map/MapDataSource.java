package com.osmviewer.map;

import com.osmviewer.mapDefenitionUtilities.MapBounds;
import java.util.List;

/**
 * Source of map objects
 *
 * @author pgalex
 */
public interface MapDataSource
{
	/**
	 * Add all map objects, exists in area to array
	 *
	 * @param area area on map, determining what objects need to get
	 * @param arrayToFill fill map objects array
	 * @throws IllegalArgumentException area is null, arrayToFill is null
	 */
	public void addMapObjectsInAreaToArray(MapBounds area, List<MapObject> arrayToFill) throws IllegalArgumentException;
}
