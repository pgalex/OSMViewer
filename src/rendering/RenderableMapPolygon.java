package rendering;

import MapDefenitionUtilities.MapPosition;

/**
 * Polygon-like map object that can be rendered
 *
 * @author pgalex
 */
public interface RenderableMapPolygon extends RenderableMapObject
{
	/**
	 * Get count of polygon points
	 *
	 * @return count of line points
	 */
	public int getPointsCount();

	/**
	 * Get polygon point with index
	 *
	 * @param index index of point to get
	 * @return point with index
	 * @throws IllegalArgumentException index is less than 0, or more than points
	 * count
	 */
	public MapPosition getPoint(int index) throws IllegalArgumentException;
}
