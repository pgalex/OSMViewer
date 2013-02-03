package rendering.selectng;

import java.awt.geom.Point2D;
import map.MapObject;
import rendering.RenderableMapObject;

/**
 * Interpretation of one map object, using for selection,
 *
 * @author pgalex
 */
public abstract class SelectingObject
{
	/**
	 * Associated object of map. Interpretation of which selecting object is
	 */
	private RenderableMapObject associatedMapObject;
	/**
	 * Draw priority of associated map object
	 */
	private int associatedMapObjectDrawPriority;

	/**
	 * Create selection object associated with map object
	 *
	 * @param associatedObject associated object of map
	 * @param associatedObjectDrawPriority draw priority of associated map object
	 * @throws IllegalArgumentException associatedObject is null
	 */
	public SelectingObject(RenderableMapObject associatedObject, int associatedObjectDrawPriority) throws IllegalArgumentException
	{
		if (associatedObject == null)
		{
			throw new IllegalArgumentException();
		}

		associatedMapObject = associatedObject;
		associatedMapObjectDrawPriority = associatedObjectDrawPriority;
	}

	/**
	 * Get associated map object
	 *
	 * @return associated map object
	 */
	public RenderableMapObject getAssociatedMapObject()
	{
		return associatedMapObject;
	}

	/**
	 * Get draw priority of associated map object
	 *
	 * @return draw priority of associated map object
	 */
	public int getAssociatedObjectDrawPriority()
	{
		return associatedMapObjectDrawPriority;
	}

	/**
	 * Is selecting object hit by point
	 *
	 * @param point point to test hit
	 * @return is selecting object hit by point
	 * @throws IllegalArgumentException point is null
	 */
	public abstract boolean isHitsByPoint(Point2D point) throws IllegalArgumentException;
}
