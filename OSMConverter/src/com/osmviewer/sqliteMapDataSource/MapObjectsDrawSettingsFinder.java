package com.osmviewer.sqliteMapDataSource;

/**
 * Finds draw settings of map objects
 *
 * @author preobrazhentsev
 */
public interface MapObjectsDrawSettingsFinder
{
	// todo create class for map object id
	// change drawing id to map object id
	public int findMapObjectMinimumVisibleScaleLevel(String mapObjectId) throws IllegalArgumentException;
	public int findMapObjectMaximumVisibleScaleLevel(String mapObjectId) throws IllegalArgumentException;
}
