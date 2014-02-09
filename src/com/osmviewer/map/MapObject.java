package com.osmviewer.map;

import com.osmviewer.mapDefenitionUtilities.Location;
import com.osmviewer.rendering.RenderableMapObject;

/**
 * Object on a map
 *
 * @author pgalex
 */
public class MapObject implements RenderableMapObject
{
	/**
	 * Identifier, helps to find kind of map object and how to draw them
	 */
	private String drawingId;
	/**
	 * Points defining map object position
	 */
	private Location[] points;

	/**
	 * Initialize with parameters
	 *
	 * @param drawingId map object drawing id. Help to find kind of map object and
	 * how to draw them. Must be not null
	 * @param points points, defining map object position. Must be not null, not
	 * empty, not contains null
	 * @throws IllegalArgumentException identifier is null
	 */
	public MapObject(String drawingId, Location[] points) throws IllegalArgumentException
	{
		if (drawingId == null)
		{
			throw new IllegalArgumentException("identifier is null");
		}
		if (!isPointsCorrect(points))
		{
			throw new IllegalArgumentException("points incorrect");
		}

		this.drawingId = drawingId;
		this.points = points;
	}

	/**
	 * Is given points of map object correct
	 *
	 * @param pointsToTest testing points
	 * @return is given points of map object correct
	 */
	private boolean isPointsCorrect(Location[] pointsToTest)
	{
		if (pointsToTest == null)
		{
			return false;
		}
		if (pointsToTest.length == 0)
		{
			return false;
		}
		for (Location point : pointsToTest)
		{
			if (point == null)
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * Get drawing id
	 *
	 * @return drawing id(help to find how to draw map object). Must be not null
	 */
	@Override
	public String getDrawingId()
	{
		return drawingId;
	}

	/**
	 * Get count of points, defining map object position
	 *
	 * @return count of points, defining map object position
	 */
	@Override
	public int getPointsCount()
	{
		return points.length;
	}

	/**
	 * Get point by index
	 *
	 * @param pointIndex point index. Must be from 0 to count of points
	 * @return point, defining map object position
	 * @throws IllegalArgumentException pointIndex is out of bounds
	 */
	@Override
	public Location getPoint(int pointIndex) throws IllegalArgumentException
	{
		if (pointIndex < 0 || pointIndex >= points.length)
		{
			throw new IllegalArgumentException("pointIndex out of bounds");
		}
		
		return points[pointIndex];
	}
}
