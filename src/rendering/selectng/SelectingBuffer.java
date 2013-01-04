package rendering.selectng;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import map.MapObject;

/**
 * Stores interperation of objects, drawen on target canvas, specialized for
 * selection, using to find object under cursor
 *
 * @author pgalex
 */
public class SelectingBuffer
{
	/**
	 * Selecting objects link with objects, drawen on target canvas
	 */
	private ArrayList<SelectingObject> selectingObjects;

	/**
	 * Create empty selecting buffer
	 */
	public SelectingBuffer()
	{
		selectingObjects = new ArrayList<SelectingObject>();
	}

	/**
	 * Find map objects associated with selecting objects under point
	 *
	 * @param point point for finding map objects
	 * @return objects under point sorted by selecting priority. More draw
	 * priority - less index
	 * @throws IllegalArgumentException point is null
	 */
	public MapObject[] findObjectsAtPoint(Point2D point) throws IllegalArgumentException
	{
		if (point == null)
		{
			throw new IllegalArgumentException();
		}

		ArrayList<SelectingObject> selectingObjecteAtPoint = new ArrayList<SelectingObject>();

		for (SelectingObject selectingObject : selectingObjects)
		{
			if (selectingObject.isHitsByPoint(point))
			{
				selectingObjecteAtPoint.add(selectingObject);
			}
		}

		Collections.sort(selectingObjecteAtPoint, new SelectingObjectsDrawPriorityComparator());

		MapObject[] mapObjectsAtPoint = new MapObject[selectingObjecteAtPoint.size()];
		for (int i = 0; i < selectingObjecteAtPoint.size(); i++)
		{
			mapObjectsAtPoint[i] = selectingObjecteAtPoint.get(i).getAssociatedMapObject();
		}
		return mapObjectsAtPoint;
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
