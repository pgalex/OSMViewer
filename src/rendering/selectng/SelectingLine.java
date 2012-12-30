package rendering.selectng;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.impl.CoordinateArraySequence;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import map.MapObject;

/**
 * Interpretation of line-like object using for selecting
 *
 * @author pgalex
 */
public class SelectingLine extends SelectingObject
{
	/**
	 * Minimum points count that can be using for line
	 */
	private static final int MINIMUM_POINTS_COUNT = 2;
	/**
	 * Line minimum width
	 */
	private static final int MINIMUM_WIDTH = 1;
	/**
	 * Stores line
	 */
	private LineString lineString;
	/**
	 * Width of line
	 */
	private int lineWidth;

	/**
	 * Create selecting line, associated with map object
	 *
	 * @param associatedObject associated object of map
	 * @param selectingLinePoints points of line
	 * @param selectingLineWidth line width
	 * @throws IllegalArgumentException associatedObject is null or
	 * selectingLinePoints null, contains less than 2 elements, or contains null
	 * elements; or selectingLineWidth less than 1
	 */
	public SelectingLine(MapObject associatedObject, Point2D[] selectingLinePoints,
					int selectingLineWidth) throws IllegalArgumentException
	{
		super(associatedObject);

		if (!isLinePointsCorrect(selectingLinePoints))
		{
			throw new IllegalArgumentException();
		}
		if (selectingLineWidth < MINIMUM_WIDTH)
		{
			throw new IllegalArgumentException();
		}

		GeometryFactory factory = new GeometryFactory();
		Coordinate[] lineCoordinates = new Coordinate[selectingLinePoints.length];
		for (int i = 0; i < selectingLinePoints.length; i++)
		{
			lineCoordinates[i] = new Coordinate(selectingLinePoints[i].getX(), selectingLinePoints[i].getY());
		}
		lineString = new LineString(new CoordinateArraySequence(lineCoordinates), factory);

		lineWidth = selectingLineWidth;
	}

	/**
	 * Is line points array correct
	 *
	 * @param linePoints points for testing
	 * @return is points array correct
	 */
	private boolean isLinePointsCorrect(Point2D[] linePoints)
	{
		if (linePoints == null)
		{
			return false;
		}

		for (int i = 0; i < linePoints.length; i++)
		{
			if (linePoints[i] == null)
			{
				return false;
			}
		}

		if (linePoints.length < MINIMUM_POINTS_COUNT)
		{
			return false;
		}

		return true;
	}

	/**
	 * Is selecting line hit by point
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

		GeometryFactory factory = new GeometryFactory();
		Coordinate[] hitPointCoordinates = new Coordinate[1];
		hitPointCoordinates[0] = new Coordinate(point.getX(), point.getY());
		Point hitPoint = new Point(new CoordinateArraySequence(hitPointCoordinates), factory);

		double distanceToPoint = lineString.distance(hitPoint);
		
		return distanceToPoint <= lineWidth;
	}
}
