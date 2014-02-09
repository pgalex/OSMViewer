package com.osmviewer.rendering;

import com.osmviewer.mapDefenitionUtilities.Location;

/**
 * Map object that can be rendered
 *
 * @author pgalex
 */
public interface RenderableMapObject
{
	public String getDrawingId();
	public int getPointsCount();
	public Location getPoint(int pointIndex)throws IllegalArgumentException;
}
