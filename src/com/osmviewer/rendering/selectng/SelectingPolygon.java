package com.osmviewer.rendering.selectng;

import java.awt.Polygon;
import java.awt.geom.Point2D;
import com.osmviewer.rendering.RenderableMapObject;

/**
 * Interpretation of polygon-like map object using for selection
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
	 * @param associatedObjectDrawPriority draw priority of associated map object
	 * @param polygonForSelection polygon, determining selection polygon
	 * @throws IllegalArgumentException associatedObject or polygonForSelection is
	 * null
	 */
	public SelectingPolygon(RenderableMapObject associatedObject, int associatedObjectDrawPriority,
					Polygon polygonForSelection) throws IllegalArgumentException
	{
		super(associatedObject, associatedObjectDrawPriority);

		if (polygonForSelection == null)
		{
			throw new IllegalArgumentException("polygonForSelection is null");
		}

		// mb test correction points of polygon
		polygon = polygonForSelection;
	}

	/**
	 * Is hits by point
	 *
	 * @param point point to test hit
	 * @return is selecting polygon hit by point
	 * @throws IllegalArgumentException point is null
	 */
	@Override
	public boolean isHitsByPoint(Point2D point) throws IllegalArgumentException
	{
		if (point == null)
		{
			throw new IllegalArgumentException("point is null");
		}

		return polygon.contains(point);
	}
}
