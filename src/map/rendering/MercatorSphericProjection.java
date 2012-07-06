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
	 * @param pPositionOnMap position of point on a map
	 * @param pScale scale using in converting
	 * @return position of point in mercator projection
	 */
	public static Point2D geographicsToMercator(MapPosition pPositionOnMap, double pScale)
	{
		double c = pScale * EARTH_EQUATOR_RADIUS_IN_METERS;

		double x = c * Math.toRadians(pPositionOnMap.getLongitude());
		double y = c * Math.log(Math.tan(Math.PI / 4 + Math.toRadians(pPositionOnMap.getLatitude()) / 2));

		return new Point2D.Double(x, y);
	}

	/**
	 * Convert point in mercator projection coordinates to point on map in
	 * geographics coordinates
	 *
	 * @param pPointInMercator point in mercator projection coordinates
	 * @param pScale scale using in converting
	 * @return position of point in mercator projection
	 */
	public static MapPosition mercatorToGeographics(Point2D pPointInMercator, double pScale)
	{
		double c = pScale * EARTH_EQUATOR_RADIUS_IN_METERS;

		double longitude = Math.toDegrees(pPointInMercator.getX() / c);
		double latitude = Math.toDegrees(2 * Math.atan(Math.pow(Math.E, pPointInMercator.getY() / c)) - Math.PI / 2);

		return new MapPosition(latitude, longitude);
	}
}
