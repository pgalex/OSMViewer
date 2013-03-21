package com.osmviewer.map;

import com.osmviewer.mapDefenitionUtilities.MapBounds;

/**
 * Source of map objects
 *
 * @author pgalex
 */
public interface MapDataSource
{
	/**
	 * Fetch map objects exists in area, and send them to fetchResultsHandler
	 *
	 * @param area area on map, deteriming what map objects need to fetch
	 * @param fetchResultsHandler handler of fetch results
	 * @throws IllegalArgumentException area is null, fetchResultsHandler is null
	 */
	public void fetchMapObjectsInArea(MapBounds area, MapDataSourceFetchResultsHandler fetchResultsHandler) throws IllegalArgumentException;
}
