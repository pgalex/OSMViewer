package onlineMap;

import drawingStyle.StyleViewer;
import java.util.ArrayList;
import java.util.Collections;
import map.Map;
import map.MapObject;
import map.MapObjectDrawPriorityComparator;
import map.exceptions.MapObjectIsNullException;

/**
 * Map that contains not much objects and using for online rendering
 *
 * @author pgalex
 */
public class OnlineMap implements Map
{
	/**
	 * Objects of map
	 */
	private ArrayList<MapObject> objects;

	/**
	 * Default constructor
	 */
	public OnlineMap()
	{
		objects = new ArrayList<MapObject>();
	}

	/**
	 * Add object to map
	 *
	 * @param pObject object for adding
	 * @throws MapObjectIsNullException adding object is null
	 */
	public void addObject(MapObject pObject) throws MapObjectIsNullException
	{
		if (pObject == null)
			throw new MapObjectIsNullException();

		objects.add(pObject);
	}

	/**
	 * Sort all objects by draw priority
	 *
	 * @param pStyleViewer Style viewer to find object draw priority
	 */
	@Override
	public void sortObjectByDrawPriority(StyleViewer pStyleViewer)
	{
		MapObjectDrawPriorityComparator objectsComparator = new MapObjectDrawPriorityComparator(pStyleViewer);
		Collections.sort(objects, objectsComparator);
	}
}
