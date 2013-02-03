package rendering;

import map.MapPosition;

/**
 * Line-like map object that can be render
 *
 * @author pgalex
 */
public interface RenderableMapLine extends RenderableMapObject
{
	/**
	 * Get count of line points
	 *
	 * @return count of line points
	 */
	public int getPointsCount();

	/**
	 * Get line point with index
	 *
	 * @param index index of point to get
	 * @return point with index
	 * @throws IllegalArgumentException index is less than 0, or more than points
	 * count
	 */
	public MapPosition getPoint(int index) throws IllegalArgumentException;
}
