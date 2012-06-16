package onlineMap;

import drawingStyles.StyleViewer;
import java.util.ArrayList;
import java.util.Collections;
import map.Map;
import map.MapObject;
import map.MapObjectDrawPriorityComparator;
import map.rendering.MapObjectsRenderer;

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
	 * Default constructor
	 */
	public OnlineMap()
	{
		objects = new ArrayList<MapObject>();
	}

	/**
	 * Add object to map. If object is null it will not be added
	 *
	 * @param pObject object for adding
	 */
	public void addObject(MapObject pObject)
	{
		if (pObject == null)
			return;

		if (pObject.getStyleIndex() != null)
			objects.add(pObject);
	}

	/**
	 * Sort all objects by draw priority
	 *
	 * @param pStyleViewer Style viewer to find object draw priority
	 */
	@Override
	public void sortObjectsByDrawPriority(StyleViewer pStyleViewer)
	{
		MapObjectDrawPriorityComparator objectsComparator = new MapObjectDrawPriorityComparator(pStyleViewer);
		Collections.sort(objects, objectsComparator);
	}

	/**
	 * Accept objects renderer visitor. Render every object of map
	 *
	 * @param pObjectsRenderer objects renderer
	 */
	@Override
	public void acceptObjectsRenderer(MapObjectsRenderer pObjectsRenderer)
	{
		if (pObjectsRenderer == null)
			return;

		for (int i = 0; i < objects.size(); i++)
			objects.get(i).acceptRenderer(pObjectsRenderer);
	}
}
