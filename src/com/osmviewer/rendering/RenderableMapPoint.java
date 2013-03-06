package com.osmviewer.rendering;

import com.osmviewer.mapDefenitionUtilities.Location;

/**
 * Point-like map object that can be render
 *
 * @author pgalex
 */
public interface RenderableMapPoint extends RenderableMapObject
{
	/**
	 * Get position on a map
	 *
	 * @return position on a map
	 */
	public Location getPosition();
}
