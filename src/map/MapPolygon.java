package map;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.geom.impl.CoordinateArraySequence;
import drawingStyles.DefenitionTags;
import drawingStyles.MapObjectDrawSettings;

/**
 * Polygon (closed way) on a map
 *
 * @author pgalex
 */
public class MapPolygon extends MapObjectByPoints
{
	/**
	 * Minimum points count that can be using for polygon
	 */
	private static final int MINIMUM_POINTS_COUNT = 4;

	/**
	 * Create with parameters
	 *
	 * @param polygonId global OpenStreetMap id of object
	 * @param polygonDefenitionTags Tags, describes the polygon
	 * @param polygonPoints point of polygon. Last and first point should be same
	 * @throws IllegalArgumentException polygonPointsless contains less than 4
	 * elements
	 */
	public MapPolygon(long polygonId, DefenitionTags polygonDefenitionTags, MapPosition[] polygonPoints) throws IllegalArgumentException
	{
		super(polygonId, polygonDefenitionTags, polygonPoints);

		if (polygonPoints.length < MINIMUM_POINTS_COUNT)
		{
			throw new IllegalArgumentException();
		}
		
		if (!polygonPoints[0].compareTo(polygonPoints[polygonPoints.length - 1]))
		{
			throw new IllegalArgumentException();
		}
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

		Coordinate[] polygonCoordinates = new Coordinate[getPointsCount()];
		for (int i = 0; i < getPointsCount(); i++)
		{
			MapPosition point = getPoint(i);
			polygonCoordinates[i] = new Coordinate(point.getLatitude(), point.getLongitude());
		}

		LinearRing polygonLineString = new LinearRing(new CoordinateArraySequence(polygonCoordinates), factory);
		Polygon polygon = new Polygon(polygonLineString, null, factory);

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
	 */
	@Override
	public void acceptRenderer(MapObjectsRenderer objectsRenderer)
	{
		if (objectsRenderer == null)
		{
			return;
		}

		objectsRenderer.renderPolygon(this);
	}

	/**
	 * Can this type of map object be drawen with this style
	 *
	 * @param objectDrawStyle drawing style of object
	 * @return Can this type of map object be drawen with this style
	 */
	@Override
	protected boolean canBeDrawenWithStyle(MapObjectDrawSettings objectDrawStyle)
	{
		if (objectDrawStyle == null)
		{
			return false;
		}

		return objectDrawStyle.isCanBePolygon();
	}
}
