package com.osmviewer.rendering.selectng;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.impl.CoordinateArraySequence;
import java.awt.geom.Point2D;
import com.osmviewer.rendering.RenderableMapObject;

/**
 * Interpretation of line-like map object using for selecting
 *
 * @author pgalex
 */
public class SelectingLine extends SelectingObject
{
	/**
	 * Minimum line points count
	 */
	private static final int MINIMUM_POINTS_COUNT = 2;
	/**
	 * Minimum line width
	 */
	private static final int MINIMUM_WIDTH = 1;
	/**
	 * Stores line
	 */
	private LineString lineString;
	/**
	 * Width of line
	 */
	private double lineWidth;

	/**
	 * Create selecting line, associated with map object
	 *
	 * @param associatedObject associated object of map
	 * @param associatedObjectDrawPriority draw priority of associated map object
	 * @param selectingLinePoints points of line
	 * @param selectingLineWidth line width
	 * @throws IllegalArgumentException associatedObject is null;
	 * selectingLinePoints null, contains less than 2 elements or contains null
	 * elements; or selectingLineWidth less than 1
	 */
	public SelectingLine(RenderableMapObject associatedObject, int associatedObjectDrawPriority, Point2D[] selectingLinePoints,
					double selectingLineWidth) throws IllegalArgumentException
	{
		super(associatedObject, associatedObjectDrawPriority);

		if (!isLinePointsCorrect(selectingLinePoints))
		{
			throw new IllegalArgumentException("selectingLinePoints incorrect");
		}
		if (selectingLineWidth < MINIMUM_WIDTH)
		{
			throw new IllegalArgumentException("selectingLineWidth less than minimum valid");
		}

		lineString = createLineStringByPoints(selectingLinePoints);
		lineWidth = selectingLineWidth;
	}

	/**
	 * Create line string by points array
	 *
	 * @param lineStringPoints points to create line string by
	 * @return line string, created by given points
	 * @throws IllegalArgumentException lineStringPoints null, contains less than
	 * 2 elements or contains null elements
	 */
	private LineString createLineStringByPoints(Point2D[] lineStringPoints) throws IllegalArgumentException
	{
		if (!isLinePointsCorrect(lineStringPoints))
		{
			throw new IllegalArgumentException("lineStringPoints incorrect");
		}

		GeometryFactory factory = new GeometryFactory();
		Coordinate[] lineCoordinates = new Coordinate[lineStringPoints.length];
		for (int i = 0; i < lineStringPoints.length; i++)
		{
			lineCoordinates[i] = new Coordinate(lineStringPoints[i].getX(), lineStringPoints[i].getY());
		}
		return new LineString(new CoordinateArraySequence(lineCoordinates), factory);
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
	 * Is hits by point
	 *
	 * @param point point to test hit
	 * @return is selecting line hits by point
	 * @throws IllegalArgumentException point is null
	 */
	@Override
	public boolean isHitsByPoint(Point2D point) throws IllegalArgumentException
	{
		if (point == null)
		{
			throw new IllegalArgumentException("point is null");
		}

		GeometryFactory factory = new GeometryFactory();
		Coordinate[] hitPointCoordinates = new Coordinate[1];
		hitPointCoordinates[0] = new Coordinate(point.getX(), point.getY());
		Point hitPoint = new Point(new CoordinateArraySequence(hitPointCoordinates), factory);

		double distanceToPoint = lineString.distance(hitPoint);
		double lineWidthDiv2 = lineWidth / 2;
		if (lineWidthDiv2 < 1)
		{
			lineWidthDiv2 = 1;
		}

		return distanceToPoint <= lineWidthDiv2;
	}
}
