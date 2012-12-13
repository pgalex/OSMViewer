package map.onlineMap;

import drawingStyles.StyleViewer;
import java.util.ArrayList;
import java.util.Collections;
import map.Map;
import map.MapBounds;
import map.MapObject;
import map.MapObjectDrawPriorityComparator;
import map.MapObjectsRenderer;

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
	 * Render all map objects, visible in area, with renderer
	 *
	 * @param objectsRenderer objects renderer
	 * @param renderingArea area, using to determine map objects that need to draw
	 * @throws IllegalArgumentException objectsRenderer or renderingArea is null
	 */
	@Override
	public void rendersObjectInArea(MapObjectsRenderer objectsRenderer,
					MapBounds renderingArea) throws IllegalArgumentException
	{
		if (objectsRenderer == null)
		{
			throw new IllegalArgumentException();
		}
		if (renderingArea == null)
		{
			throw new IllegalArgumentException();
		}

		// nothing to draw
		if (renderingArea.isZero())
		{
			return;
		}

		for (int i = 0; i < objects.size(); i++)
		{
			MapObject renderingObject = objects.get(i);
			if (renderingObject.isVisibleInArea(renderingArea))
			{
				renderingObject.acceptRenderer(objectsRenderer);
			}
		}
	}
}
