package com.osmviewer.map;

import com.osmviewer.mapDefenitionUtilities.DefenitionTags;
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
	 * @param uniqueId unique openstreetmap id of fetched map objects
	 * @param tags tags of fetched map object
	 * @param points points on map, defining map object position
	 * @throws IllegalArgumentException tags is null; points is null, empty or
	 * contains null
	 */
	public void takeMapObjectData(long uniqueId, DefenitionTags tags, Location[] points) throws IllegalArgumentException;
}
