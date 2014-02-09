package com.osmviewer.rendering;

import java.awt.Color;
import java.awt.Polygon;

/**
 *
 * @author preobrazhentsev
 */
public interface DrawingTool
{
	/**
	 * Draw polygon filled with given color
	 *
	 * @param polygonToDraw drawing polygon. Must be not null
	 * @param polygonColor color to fill polygon inner part with. Must be not null
	 * @throws IllegalArgumentException polygonToDraw is null, polygonColor is
	 * null
	 */
	public void drawColorFilledPolygon(Polygon polygonToDraw, Color polygonColor) throws IllegalArgumentException;
}
