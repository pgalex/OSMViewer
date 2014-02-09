package com.osmviewer.rendering;

import com.osmviewer.mapDefenitionUtilities.Location;

/**
 * Map object that can be rendered
 *
 * @author pgalex
 */
public interface RenderableMapObject
{
	/**
	 * Get drawing id
	 *
	 * @return drawing id(help to find how to draw map object). Must be not null
	 */
	public String getDrawingId();

	/**
	 * Get count of points, defining map object position
	 *
	 * @return count of points, defining map object position
	 */
	public int getPointsCount();

	/**
	 * Get point, defining map object position, by index
	 *
	 * @param pointIndex point index. Must be from 0 to count of points
	 * @return point, defining map object position
	 * @throws IllegalArgumentException pointIndex is out of bounds
	 */
	public Location getPoint(int pointIndex) throws IllegalArgumentException;
}
