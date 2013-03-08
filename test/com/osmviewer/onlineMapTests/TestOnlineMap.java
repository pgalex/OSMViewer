package com.osmviewer.onlineMapTests;

import java.util.ArrayList;
import com.osmviewer.map.MapObject;
import com.osmviewer.map.onlineMap.Map;

/**
 * Uses to get access to private section of Map, for testing
 *
 * @author pgalex
 */
public class TestOnlineMap extends Map
{
	/**
	 * Get stored map objects
	 *
	 * @return array of stored map objects
	 */
	public ArrayList<MapObject> getObjects()
	{
		return objects;
	}
}
