package drawingStyles;

import java.awt.BasicStroke;
import java.awt.Color;

/**
 * Drawing style of line (closed way)
 *
 * @author pgalex
 */
public interface LineDrawStyle
{
	/**
	 * Get color
	 *
	 * @return color of line
	 */
	public Color getColor();

	/**
	 * Get width
	 *
	 * @return get width of line
	 */
	public float getWidth();

	/**
	 * Get pattern
	 *
	 * @return pattern for line drawing
	 */
	public float[] getPattern();

	/**
	 * Get stroke for line drawing
	 *
	 * @return stroke for line drawing
	 */
	public BasicStroke getStroke();
}
