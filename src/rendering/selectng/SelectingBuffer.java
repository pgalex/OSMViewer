package rendering.selectng;

import java.awt.geom.Point2D;
import java.util.ArrayList;
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
	 * @return objects under point
	 * @throws IllegalArgumentException point is null
	 */
	public MapObject[] findObjectsAtPoint(Point2D point) throws IllegalArgumentException
	{
		if (point == null)
		{
			throw new IllegalArgumentException();
		}
		
		ArrayList<MapObject> objectsAtPoint = new ArrayList<MapObject>();
		
		for (SelectingObject selectingObject : selectingObjects)
		{
			if (selectingObject.isHitsByPoint(point))
			{
				objectsAtPoint.add(selectingObject.getAssociatedMapObject());
			}
		}
		
		return (MapObject[]) objectsAtPoint.toArray();
	}
}
