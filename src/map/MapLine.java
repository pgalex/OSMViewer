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
 * Line (non closed way) on a map
 *
 * @author pgalex
 */
public class MapLine extends MapObjectByPoints
{
	/**
	 * Minimum points count that can be using for line
	 */
	private static final int MINIMUM_POINTS_COUNT = 2;

	/**
	 * Create with parameters
	 *
	 * @param lineId global OpenStreetMap id of object
	 * @param lineDefenitionTags Tags, describes the line
	 * @param linePoints points of line
	 * @throws IllegalArgumentException linePoints contains less than 2 elements
	 */
	public MapLine(long lineId, DefenitionTags lineDefenitionTags, MapPosition[] linePoints) throws IllegalArgumentException
	{
		super(lineId, lineDefenitionTags, linePoints);

		if (linePoints.length < MINIMUM_POINTS_COUNT)
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

		Coordinate[] lineCoordinates = new Coordinate[getPointsCount()];
		for (int i = 0; i < getPointsCount(); i++)
		{
			MapPosition point = getPoint(i);
			lineCoordinates[i] = new Coordinate(point.getLatitude(), point.getLongitude());
		}

		LineString lineString = new LineString(new CoordinateArraySequence(lineCoordinates), factory);

		Coordinate[] areaCoordinates = new Coordinate[5];
		areaCoordinates[0] = new Coordinate(area.getLatitudeMinimum(), area.getLongitudeMinimum());
		areaCoordinates[1] = new Coordinate(area.getLatitudeMinimum(), area.getLongitudeMaximum());
		areaCoordinates[2] = new Coordinate(area.getLatitudeMaximum(), area.getLongitudeMaximum());
		areaCoordinates[3] = new Coordinate(area.getLatitudeMaximum(), area.getLongitudeMinimum());
		areaCoordinates[4] = new Coordinate(area.getLatitudeMinimum(), area.getLongitudeMinimum());
		LinearRing areaLinearRing = new LinearRing(new CoordinateArraySequence(areaCoordinates), factory);
		
		Polygon areaPolygon = new Polygon(areaLinearRing, null, factory);

		return areaPolygon.intersects(lineString);
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

		objectsRenderer.renderLine(this);
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

		return objectDrawStyle.isCanBeLine();
	}
}
