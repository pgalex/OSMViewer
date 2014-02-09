package com.osmviewer.map;

import com.osmviewer.mapDefenitionUtilities.Location;
import com.osmviewer.rendering.RenderableMapObject;

/**
 * Some object on a map
 *
 * @author pgalex
 */
public class MapObject implements RenderableMapObject
{
	/**
	 * Identifier help to find kind of map object and how to draw them
	 */
	private String drawingId;
	/**
	 * Points defining map object position
	 */
	private Location[] points;

	/**
	 * Initialize with parameters
	 *
	 * @param identifier map object id. Describes kind of map object. Must be not
	 * null
	 * @param points points, defining map object position. Must be not null, not
	 * empty, not contains null
	 * @throws IllegalArgumentException identifier is null
	 */
	public MapObject(String identifier, Location[] points) throws IllegalArgumentException
	{
		if (identifier == null)
		{
			throw new IllegalArgumentException("identifier is null");
		}
		if (!isPointsCorrect(points))
		{
			throw new IllegalArgumentException("points incorrect");
		}
		this.drawingId = identifier;
		this.points = points;
	}

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

		for (int i = 0; i < pointsToTest.length; i++)
		{
			if (pointsToTest[i] == null)
			{
				return false;
			}
		}

		return true;
	}

	@Override
	public String getDrawingId()
	{
		return drawingId;
	}

	@Override
	public int getPointsCount()
	{
		return points.length;
	}

	@Override
	public Location getPoint(int pointIndex) throws IllegalArgumentException
	{
		return points[pointIndex];
	}
}
