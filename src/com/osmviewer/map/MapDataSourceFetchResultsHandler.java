package com.osmviewer.map;

import com.osmviewer.mapDefenitionUtilities.Location;

/**
 * Handler of fetching results of map data source
 *
 * @author pgalex
 */
public interface MapDataSourceFetchResultsHandler
{
	/**
	 * Take and process data of fetched map object
	 *
	 * @param uniqueId unique object identifier
	 * @param drawingId map object drawing identifier. Must be not null
	 * @param points points on map, defining map object position. Must be not null, not empty, not contains null
	 * @throws IllegalArgumentException drawingId is null; points is null, empty or
	 * contains null
	 */
	public void takeMapObjectData(long uniqueId, String drawingId, Location[] points) throws IllegalArgumentException;
}
