package rendering.selectng;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import map.MapObject;

/**
 * Stores interperation of objects, drawen on target canvas, specialized for
 * selection (using to find object at point on target canvas)
 *
 * @author pgalex
 */
public class SelectingBuffer
{
	/**
	 * Selecting objects of buffer
	 */
	private ArrayList<SelectingObject> selectingObjects;

	/**
	 * Create empty
	 */
	public SelectingBuffer()
	{
		selectingObjects = new ArrayList<SelectingObject>();
	}

	/**
	 * Find map objects associated with buffer's selecting objects, hits by point
	 *
	 * @param point point for finding map objects at it
	 * @return map objects at point, sorted by draw priority (more draw priority -
	 * less index). Empty if no objects found
	 * @throws IllegalArgumentException point is null
	 */
	public MapObject[] findObjectsAtPoint(Point2D point) throws IllegalArgumentException
	{
		if (point == null)
		{
			throw new IllegalArgumentException();
		}

		ArrayList<SelectingObject> selectingObjectsAtPoint = findSelectingObjectsAtPoint(point);

		MapObject[] mapObjectsAtPoint = new MapObject[selectingObjectsAtPoint.size()];
		for (int i = 0; i < selectingObjectsAtPoint.size(); i++)
		{
			mapObjectsAtPoint[i] = selectingObjectsAtPoint.get(i).getAssociatedMapObject();
		}
		return mapObjectsAtPoint;
	}

	/**
	 * Find selecting objects of buffer at point
	 *
	 * @param point point for finding selecting objects at
	 * @return selecting objects of buffer, hits by point. Empty if no objects
	 * found. Sorted by associated map objects draw priority
	 * @throws IllegalArgumentException point is null
	 */
	private ArrayList<SelectingObject> findSelectingObjectsAtPoint(Point2D point) throws IllegalArgumentException
	{
		if (point == null)
		{
			throw new IllegalArgumentException();
		}

		ArrayList<SelectingObject> selectingObjectsAtPoint = new ArrayList<SelectingObject>();
		for (SelectingObject selectingObject : selectingObjects)
		{
			if (selectingObject.isHitsByPoint(point))
			{
				selectingObjectsAtPoint.add(selectingObject);
			}
		}
		Collections.sort(selectingObjectsAtPoint, new SelectingObjectsDrawPriorityComparator());

		return selectingObjectsAtPoint;
	}

	/**
	 * Add selecting object to buffer
	 *
	 * @param selectingObjectToAdd adding selecting object
	 * @throws IllegalArgumentException selectingObjectToAdd is null
	 */
	public void addSelectingObject(SelectingObject selectingObjectToAdd) throws IllegalArgumentException
	{
		if (selectingObjectToAdd == null)
		{
			throw new IllegalArgumentException();
		}

		selectingObjects.add(selectingObjectToAdd);
	}

	/**
	 * Remove all selecting objects
	 */
	public void clear()
	{
		selectingObjects.clear();
	}
}
