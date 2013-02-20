package com.osmviewer.map;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.geom.impl.CoordinateArraySequence;
import com.osmviewer.mapDefenitionUtilities.DefenitionTags;
import com.osmviewer.mapDefenitionUtilities.MapBounds;
import com.osmviewer.mapDefenitionUtilities.MapPosition;
import com.osmviewer.rendering.RenderableMapObjectDrawSettings;
import com.osmviewer.rendering.RenderableMapObjectsVisitor;
import com.osmviewer.rendering.RenderableMapPolygon;

/**
 * Polygon (closed way) on a map
 *
 * @author pgalex
 */
public class MapPolygon extends MapObject implements RenderableMapPolygon
{
	/**
	 * Minimum points count that can be using for polygon
	 */
	private static final int MINIMUM_POINTS_COUNT = 4;
	/**
	 * Stores polygon. Coordinate x as latitude, y as longitude
	 */
	private Polygon polygon;

	/**
	 * Create with parameters
	 *
	 * @param polygonId global OpenStreetMap id of object
	 * @param polygonDefenitionTags Tags, describes the polygon
	 * @param polygonPoints point of polygon. Last and first point should be same
	 * @throws IllegalArgumentException polygonPoints null, contains less than 4
	 * elements, contains null elements, or last point not same as first
	 */
	public MapPolygon(long polygonId, DefenitionTags polygonDefenitionTags, MapPosition[] polygonPoints) throws IllegalArgumentException
	{
		super(polygonId, polygonDefenitionTags);

		if (!isPolygonPointsCorrect(polygonPoints))
		{
			throw new IllegalArgumentException();
		}

		GeometryFactory factory = new GeometryFactory();

		Coordinate[] polygonCoordinates = new Coordinate[polygonPoints.length];
		for (int i = 0; i < polygonPoints.length; i++)
		{
			polygonCoordinates[i] = new Coordinate(polygonPoints[i].getLatitude(), polygonPoints[i].getLongitude());
		}

		LinearRing polygonLineString = new LinearRing(new CoordinateArraySequence(polygonCoordinates), factory);
		polygon = new Polygon(polygonLineString, null, factory);
	}

	/**
	 * Determine draw prority using draw settings
	 *
	 * @return draw prority
	 * @throws NullPointerException draw settings not set
	 */
	@Override
	public int determineDrawPriotity() throws NullPointerException
	{
		RenderableMapObjectDrawSettings drawSettings = getDrawSettings();
		if (drawSettings == null)
		{
			throw new NullPointerException();
		}

		return drawSettings.getPolygonDrawPriority();
	}

	/**
	 * Is polygon points array correct
	 *
	 * @param polygonPoints points for testing
	 * @return is points array correct
	 */
	private boolean isPolygonPointsCorrect(MapPosition[] polygonPoints)
	{
		if (polygonPoints == null)
		{
			return false;
		}

		for (int i = 0; i < polygonPoints.length; i++)
		{
			if (polygonPoints[i] == null)
			{
				return false;
			}
		}

		if (polygonPoints.length < MINIMUM_POINTS_COUNT)
		{
			return false;
		}

		if (!polygonPoints[0].compareTo(polygonPoints[polygonPoints.length - 1]))
		{
			return false;
		}

		return true;
	}

	/**
	 * Get count of points
	 *
	 * @return count of points
	 */
	@Override
	public int getPointsCount()
	{
		return polygon.getNumPoints();
	}

	/**
	 * Get point with index
	 *
	 * @param index index of point to get
	 * @return point with index
	 * @throws IllegalArgumentException index is less than 0, or more than points
	 * count
	 */
	@Override
	public MapPosition getPoint(int index) throws IllegalArgumentException
	{
		if (index < 0 || index >= polygon.getNumPoints())
		{
			throw new IllegalArgumentException();
		}

		Coordinate coordinateByIndex = polygon.getCoordinates()[index];
		return new MapPosition(coordinateByIndex.x, coordinateByIndex.y);
	}

	/**
	 * Is object visible in given area
	 *
	 * @param area area to test visibility in
	 * @return is object visible in area
	 * @throws IllegalArgumentException area is null
	 */
	@Override
	public boolean isVisibleInArea(MapBounds area) throws IllegalArgumentException
	{
		if (area == null)
		{
			throw new IllegalArgumentException();
		}

		if (area.isZero())
		{
			return false;
		}

		GeometryFactory factory = new GeometryFactory();

		Coordinate[] areaCoordinates = new Coordinate[5];
		areaCoordinates[0] = new Coordinate(area.getLatitudeMinimum(), area.getLongitudeMinimum());
		areaCoordinates[1] = new Coordinate(area.getLatitudeMinimum(), area.getLongitudeMaximum());
		areaCoordinates[2] = new Coordinate(area.getLatitudeMaximum(), area.getLongitudeMaximum());
		areaCoordinates[3] = new Coordinate(area.getLatitudeMaximum(), area.getLongitudeMinimum());
		areaCoordinates[4] = new Coordinate(area.getLatitudeMinimum(), area.getLongitudeMinimum());
		LinearRing areaLinearRing = new LinearRing(new CoordinateArraySequence(areaCoordinates), factory);

		Polygon areaPolygon = new Polygon(areaLinearRing, null, factory);

		return areaPolygon.intersects(polygon);
	}

	/**
	 * Render with objects render visitor
	 *
	 * @param objectsRenderer objects renderer
	 * @throws IllegalArgumentException objectsRenderer is null
	 */
	@Override
	public void acceptRenderingVisitor(RenderableMapObjectsVisitor objectsRenderer) throws IllegalArgumentException
	{
		if (objectsRenderer == null)
		{
			throw new IllegalArgumentException();
		}

		objectsRenderer.visitPolygon(this);
	}

	/**
	 * Can this type of map object be drawen with this style
	 *
	 * @param objectDrawStyle drawing style of object
	 * @return Can this type of map object be drawen with this style
	 */
	@Override
	public boolean canBeDrawenWithStyle(RenderableMapObjectDrawSettings objectDrawStyle)
	{
		if (objectDrawStyle == null)
		{
			return false;
		}

		return objectDrawStyle.isCanBePolygon();
	}
}
