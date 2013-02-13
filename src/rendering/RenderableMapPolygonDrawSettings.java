package rendering;

import drawingStyles.LineDrawSettings;
import java.awt.Paint;

/**
 * Draw settings of renderable map polygon
 *
 * @author pgalex
 */
public interface RenderableMapPolygonDrawSettings
{
	/**
	 * Is need to draw inner part of polygon
	 *
	 * @return is need to draw inner part
	 */
	public boolean isDrawInnerPart();

	/**
	 * Is need to draw border of polgyon
	 *
	 * @return is need to draw border
	 */
	public boolean isDrawBorder();

	/**
	 * Get paint for drawing inner part of polygon
	 *
	 * @return paint for drawing polygon
	 */
	public Paint getPaint();

	/**
	 * Get border drawing settings
	 *
	 * @return how to draw border of polygon
	 */
	public LineDrawSettings getBorderDrawSettings();
}
