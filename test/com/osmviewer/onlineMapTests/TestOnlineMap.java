package com.osmviewer.onlineMapTests;

import java.util.ArrayList;
import com.osmviewer.map.MapObject;
import com.osmviewer.map.onlineMap.OnlineMap;

/**
 * Uses to get access to private section of OnlineMap, for testing
 *
 * @author pgalex
 */
public class TestOnlineMap extends OnlineMap
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
