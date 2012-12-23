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
public class MapLine extends MapObject
{
	/**
	 * Minimum points count that can be using for lineString
	 */
	private static final int MINIMUM_POINTS_COUNT = 2;
	/**
	 * Stores line. Coordinate x as latitude, y as longitude
	 */
	private LineString lineString;

	/**
	 * Create with parameters
	 *
	 * @param lineId global OpenStreetMap id of object
	 * @param lineDefenitionTags Tags, describes the lineString
	 * @param linePoints points of lineString
	 * @throws IllegalArgumentException linePoints, null, contains less than 2
	 * elements or contains null elements
	 */
	public MapLine(long lineId, DefenitionTags lineDefenitionTags, MapPosition[] linePoints) throws IllegalArgumentException
	{
		super(lineId, lineDefenitionTags);

		if (!isLinePointsCorrect(linePoints))
		{
			throw new IllegalArgumentException();
		}

		GeometryFactory factory = new GeometryFactory();

		Coordinate[] lineCoordinates = new Coordinate[linePoints.length];
		for (int i = 0; i < linePoints.length; i++)
		{
			lineCoordinates[i] = new Coordinate(linePoints[i].getLatitude(), linePoints[i].getLongitude());
		}

		lineString = new LineString(new CoordinateArraySequence(lineCoordinates), factory);
	}

	/**
	 * Is lineString points array correct
	 *
	 * @param linePoints points for testing
	 * @return is points array correct
	 */
	private boolean isLinePointsCorrect(MapPosition[] linePoints)
	{
		if (linePoints == null)
		{
			return false;
		}

		if (linePoints.length == 0)
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
	 * Get count of points
	 *
	 * @return count of points
	 */
	public int getPointsCount()
	{
		return lineString.getNumPoints();
	}

	/**
	 * Get point with index
	 *
	 * @param index index of point to get
	 * @return point with index
	 * @throws IllegalArgumentException index is less than 0, or more than points
	 * count
	 */
	public MapPosition getPoint(int index) throws IllegalArgumentException
	{
		if (index < 0 || index >= lineString.getNumPoints())
		{
			throw new IllegalArgumentException();
		}

		Coordinate coordinateByIndex = lineString.getCoordinateN(index);
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

		return areaPolygon.intersects(lineString);
	}

	/**
	 * Render with objects render visitor
	 *
	 * @param objectsRenderer objects renderer
	 * @throws IllegalArgumentException objectsRenderer is null
	 */
	@Override
	public void acceptRenderer(MapObjectsRenderer objectsRenderer) throws IllegalArgumentException
	{
		if (objectsRenderer == null)
		{
			throw new IllegalArgumentException();
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
