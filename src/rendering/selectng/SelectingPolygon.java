package rendering.selectng;

import java.awt.Polygon;
import java.awt.geom.Point2D;
import map.MapObject;

/**
 * Interpretation of polygon-like object using for selection
 *
 * @author pgalex
 */
public class SelectingPolygon extends SelectingObject
{
	/**
	 * Stores polygon points
	 */
	private Polygon polygon;

	/**
	 * Create selecting polygon, associated with map object
	 *
	 * @param associatedObject associated object of map
	 * @param polygonForSelection polygon, determining selection polygon
	 * @throws IllegalArgumentException associatedObject or polygonForSelection is
	 * null
	 */
	public SelectingPolygon(MapObject associatedObject,
					Polygon polygonForSelection) throws IllegalArgumentException
	{
		super(associatedObject);

		if (polygonForSelection == null)
		{
			throw new IllegalArgumentException();
		}

		// mb test correction points of polygon
		polygon = polygonForSelection;
	}

	/**
	 * Is selecting polygon hit by point
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

		return polygon.contains(point);
	}
}
