package map.onlineMap;

import java.util.ArrayList;
import java.util.Collections;
import map.MapBounds;
import map.MapObject;
import rendering.MapObjectDrawPriorityComparator;
import rendering.RenderableMap;
import rendering.RenderableMapObjectsVisitor;

/**
 * Map that contains not much objects and using for online rendering
 *
 * @author pgalex
 */
public class OnlineMap implements RenderableMap
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
	 * Render all map objects, visible in area, with renderer. Object should be
	 * given to objectsVisitor by its draw priority
	 *
	 * @param objectsVisitor objects renderer
	 * @param area area to determine which object need give to objectsVisitor
	 * @param objectsDrawPriorityComparator comparator for sorting rendering
	 * objects by its draw priority
	 * @throws IllegalArgumentException objectsVisitor, area or
	 * objectsDrawPriorityComparator is null
	 */
	@Override
	public void renderObjectsByDrawPriority(RenderableMapObjectsVisitor objectsVisitor, MapBounds area,
					MapObjectDrawPriorityComparator objectsDrawPriorityComparator) throws IllegalArgumentException
	{
		if (objectsVisitor == null)
		{
			throw new IllegalArgumentException();
		}
		if (area == null)
		{
			throw new IllegalArgumentException();
		}
		if (objectsDrawPriorityComparator == null)
		{
			throw new IllegalArgumentException();
		}

		// nothing to visit
		if (area.isZero())
		{
			return;
		}

		Collections.sort(objects, objectsDrawPriorityComparator);

		for (int i = 0; i < objects.size(); i++)
		{
			MapObject renderingObject = objects.get(i);
			if (renderingObject.isVisibleInArea(area))
			{
				renderingObject.acceptRenderer(objectsVisitor);
			}
		}
	}
}
