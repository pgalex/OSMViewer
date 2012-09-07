package drawingStyles;

import java.awt.image.BufferedImage;

/**
 * Drawing style of object if its point (one node)
 *
 * @author pgalex
 */
public interface PointDrawStyle
{
	/**
	 * Get icon
	 *
	 * @return icon of point. Null if there is no icon
	 */
	public BufferedImage getIcon();
}
