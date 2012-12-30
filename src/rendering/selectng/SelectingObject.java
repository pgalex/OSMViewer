package rendering.selectng;

import java.awt.geom.Point2D;
import map.MapObject;

/**
 * Interpretation for selection of one map object, drawen on canvas,
 *
 * @author pgalex
 */
public abstract class SelectingObject
{
	/**
	 * Associated object of map. Interpretation of which selecting object is
	 */
	private MapObject associatedMapObject;

	/**
	 * Create selection object for map object
	 *
	 * @param associatedObject associated object of map
	 * @throws IllegalArgumentException associatedObject is null
	 */
	public SelectingObject(MapObject associatedObject) throws IllegalArgumentException
	{
		if (associatedObject == null)
		{
			throw new IllegalArgumentException();
		}

		associatedMapObject = associatedObject;
	}

	/**
	 * Get associated object of map
	 *
	 * @return associated object
	 */
	public MapObject getAssociatedMapObject()
	{
		return associatedMapObject;
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
