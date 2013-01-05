package rendering.selectng;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import map.MapObject;

/**
 * Interpretation of rectangle-like object using for selection
 *
 * @author pgalex
 */
public class SelectingRectangle extends SelectingObject
{
	/**
	 * Stores rectangle
	 */
	private Rectangle2D rectangle;

	/**
	 * Create selecting rectangle, associated with map object
	 *
	 * @param associatedObject associated object of map
	 * @param associatedObjectDrawPriority draw priority of associated map object
	 * @param rectangleForSelection rectangle, determines selecting rectangle
	 * @throws IllegalArgumentException associatedObject, rectangleForSelection is
	 * null
	 */
	public SelectingRectangle(MapObject associatedObject, int associatedObjectDrawPriority,
					Rectangle2D rectangleForSelection) throws IllegalArgumentException
	{
		super(associatedObject, associatedObjectDrawPriority);

		if (rectangleForSelection == null)
		{
			throw new IllegalArgumentException();
		}

		rectangle = rectangleForSelection;
	}

	/**
	 * Is selecting rectangle hit by point
	 *
	 * @param point point to test hit
	 * @return is selecting object hit by point
	 * @throws IllegalArgumentException point is null
	 */
	@Override
	public boolean isHitsByPoint(Point2D point) throws IllegalArgumentException
	{
		if (point == null)
		{
			throw new IllegalArgumentException();
		}

		return rectangle.contains(point);
	}
}