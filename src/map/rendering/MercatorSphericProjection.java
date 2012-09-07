package map.rendering;

import java.awt.geom.Point2D;
import map.MapPosition;

/**
 * Realization of mercator spheric projection
 *
 * @author pgalex
 */
public class MercatorSphericProjection
{
	/**
	 * Earth radius using in converting coordinates
	 */
	private static final double EARTH_EQUATOR_RADIUS_IN_METERS = 6378137.0;

	/**
	 * Convert point in geographics coordinates to point in mercator projection
	 *
	 * @param positionOnMap position of point on a map
	 * @param scale scale using in converting
	 * @return position of point in mercator projection
	 */
	public static Point2D geographicsToMercator(MapPosition positionOnMap, double scale)
	{
		double c = scale * EARTH_EQUATOR_RADIUS_IN_METERS;

		double x = c * Math.toRadians(positionOnMap.getLongitude());
		double y = c * Math.log(Math.tan(Math.PI / 4 + Math.toRadians(positionOnMap.getLatitude()) / 2));

		return new Point2D.Double(x, y);
	}

	/**
	 * Convert point in mercator projection coordinates to point on map in
	 * geographics coordinates
	 *
	 * @param pointInMercator point in mercator projection coordinates
	 * @param scale scale using in converting
	 * @return position of point in mercator projection
	 */
	public static MapPosition mercatorToGeographics(Point2D pointInMercator, double scale)
	{
		double c = scale * EARTH_EQUATOR_RADIUS_IN_METERS;

		double longitude = Math.toDegrees(pointInMercator.getX() / c);
		double latitude = Math.toDegrees(2 * Math.atan(Math.pow(Math.E, pointInMercator.getY() / c)) - Math.PI / 2);

		return new MapPosition(latitude, longitude);
	}
}
