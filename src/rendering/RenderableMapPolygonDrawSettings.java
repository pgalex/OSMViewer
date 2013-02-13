package rendering;

import drawingStyles.StandartLineDrawSettings;
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
	 * Find border drawing settings
	 *
	 * @return draw settings of polygon border
	 */
	public RenderableMapLineDrawSettings findBorderDrawSettings();
}
