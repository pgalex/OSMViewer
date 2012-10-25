package rendering;

import java.awt.geom.Point2D;
import map.MapPosition;

/**
 * Interface of object that using to convert coordinates in current view
 * position
 *
 * @author pgalex
 */
public interface CoordinatesConverter
{
	/**
	 * Convert point in geographics coordinates on a map to point on drawing
	 * canvas (using current scale and view position)
	 *
	 * @param positionOnMap position of point on a map
	 * @return position of point on drawing canvas
	 */
	public Point2D goegraphicsToCanvas(MapPosition positionOnMap);

	/**
	 * Convert point on drawing canvas to point on a map, using current scale and
	 * view position
	 *
	 * @param positionOnCanvas position of point on drawing canvas
	 * @return position of point on map
	 */
	public MapPosition canvasToGeographics(Point2D positionOnCanvas);
}
