package onlineMapTests;

import java.util.ArrayList;
import map.MapObject;
import map.onlineMap.OnlineMap;

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
