package map.rendering;

import java.awt.geom.Point2D;
import map.MapPosition;

/**
 * Interface of object that using to convert coordinates
 *
 * @author pgalex
 */
public interface CoordinatesConverter
{
	/**
	 * Convert point in geographics coordinates on a map to point on drawing
	 * canvas (using current scale and view position)
	 *
	 * @param pPositionOnMap position of point on a map
	 * @return position of point on drawing canvas
	 */
	public Point2D goegraphicsToCanvas(MapPosition pPositionOnMap);
}
