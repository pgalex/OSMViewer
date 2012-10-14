package drawingStyles;

import java.awt.Color;
import java.awt.Paint;
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
	public LineDrawSettings getBorderDrawStyle();

	/**
	 * Get paint for drawing filled polygon
	 *
	 * @return paint for drawing polygon. Solid paint if fill image is null, and
	 * texture paint if fill image not null
	 */
	public Paint getPaint();
}
