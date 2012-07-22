package drawingStyles;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Drawing style of polygon (closed way)
 *
 * @author pgalex
 */
public interface PolygonDrawStyle
{
	/**
	 * Get fill color
	 *
	 * @return fill color
	 */
	public Color getFillColor();

	/**
	 * Get fill texture
	 *
	 * @return fill texture
	 */
	public BufferedImage getFillImage();

	/**
	 * Get border drawing style
	 *
	 * @return how to draw border of polygon
	 */
	public LineDrawStyle getBorderDrawStyle();
}
