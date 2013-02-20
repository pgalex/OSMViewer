package com.osmviewer.rendering;

import java.awt.BasicStroke;
import java.awt.Color;

/**
 * Draw settings of renderable map line
 *
 * @author pgalex
 */
public interface RenderableMapLineDrawSettings
{
	/**
	 * Get line color
	 *
	 * @return line color
	 */
	public Color getColor();

	/**
	 * Get line width
	 *
	 * @return line width
	 */
	public float getWidth();

	/**
	 * Get stroke for line drawing
	 *
	 * @return stroke for line drawing
	 */
	public BasicStroke getStroke();
}
