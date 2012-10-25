package map.onlineMap;

import drawingStyles.StyleViewer;
import java.util.ArrayList;
import java.util.Collections;
import map.Map;
import map.MapObject;
import map.MapObjectDrawPriorityComparator;
import rendering.MapObjectsRenderer;

/**
 * Map that contains not much objects and using for online rendering
 *
 * @author pgalex
 */
public class OnlineMap implements Map
{
	/**
	 * Objects on a map
	 */
	protected ArrayList<MapObject> objects;

	/**
	 * Create empty map
	 */
	public OnlineMap()
	{
		objects = new ArrayList<MapObject>();
	}

	/**
	 * Add object to map. If object is null it will not be added
	 *
	 * @param objectToAdd object for adding
	 */
	public void addObject(MapObject objectToAdd)
	{
		if (objectToAdd == null)
		{
			return;
		}

		if (objectToAdd.getStyleIndex() != null)
		{
			objects.add(objectToAdd);
		}
	}
	
	/**
	 * Clear map
	 */
	public void clear()
	{
		objects.clear();
	}

	/**
	 * Sort all objects by draw priority
	 *
	 * @param styleViewer Style viewer to find object draw priority
	 */
	@Override
	public void sortObjectsByDrawPriority(StyleViewer styleViewer)
	{
		MapObjectDrawPriorityComparator objectsComparator = new MapObjectDrawPriorityComparator(styleViewer);
		Collections.sort(objects, objectsComparator);
	}

	/**
	 * Accept objects renderer visitor. Render every object of map
	 *
	 * @param objectsRenderer objects renderer
	 */
	@Override
	public void acceptObjectsRenderer(MapObjectsRenderer objectsRenderer)
	{
		if (objectsRenderer == null)
		{
			return;
		}

		for (int i = 0; i < objects.size(); i++)
		{
			objects.get(i).acceptRenderer(objectsRenderer);
		}
	}
}
