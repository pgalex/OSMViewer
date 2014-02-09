package com.osmviewer.rendering;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

/**
 * Drawing tool using Graphics2D capabilities for drawing
 *
 * @author preobrazhentsev
 */
public class Graphics2DDrawingTool implements DrawingTool
{
	/**
	 * Graphics using for drawing
	 */
	private Graphics2D graphics;

	/**
	 * Create with graphics
	 *
	 * @param graphics graphics where to draw. Must be not null
	 * @throws IllegalArgumentException graphics is null
	 */
	public Graphics2DDrawingTool(Graphics2D graphics) throws IllegalArgumentException
	{
		if (graphics == null)
		{
			throw new IllegalArgumentException("graphics is null");
		}

		this.graphics = graphics;
	}

	/**
	 * Draw polygon filled with given color
	 *
	 * @param polygonToDraw drawing polygon. Must be not null
	 * @param polygonColor color to fill polygon inner part with. Must be not null
	 * @throws IllegalArgumentException polygonToDraw is null, polygonColor is
	 * null
	 */
	@Override
	public void drawColorFilledPolygon(Polygon polygonToDraw, Color polygonColor) throws IllegalArgumentException
	{
		if (polygonToDraw == null)
		{
			throw new IllegalArgumentException("polygonToDraw is null");
		}
		if (polygonColor == null)
		{
			throw new IllegalArgumentException("polygonColor is null");
		}

		graphics.setPaint(polygonColor);
		graphics.fillPolygon(polygonToDraw);
	}
}
