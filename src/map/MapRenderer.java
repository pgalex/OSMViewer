package map;

import drawingStyle.StyleViewer;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * Uses to render Map in right order
 *
 * @author pgalex
 */
public class MapRenderer
{
	/**
	 * Default background color
	 */
	private static final Color DEFAULT_BACKGROUND_COLOR = Color.GRAY;
	/**
	 * Default drawing area
	 */
	private static final Rectangle DEFAULT_DRAWING_AREA = new Rectangle(0, 0, 100, 100);
	/**
	 * Map background color
	 */
	private Color backgroundColor;
	/**
	 * Rectangle that define area where map will be drawen
	 */
	private Rectangle drawingArea;

	/**
	 * Default constructor
	 *
	 */
	public MapRenderer()
	{
		backgroundColor = DEFAULT_BACKGROUND_COLOR;
		drawingArea = DEFAULT_DRAWING_AREA;
	}

	/**
	 * Set map background color
	 *
	 * @param pBackgroundColor new background color
	 */
	public void setBackgroundColor(Color pBackgroundColor)
	{
		if (pBackgroundColor != null)
			backgroundColor = pBackgroundColor;
	}

	/**
	 * Get map background color
	 *
	 * @return background color
	 */
	public Color getBackgroundColor()
	{
		return backgroundColor;
	}

	/**
	 * Set new rectangle that define area where map will be drawen
	 *
	 * @param pDrawingArea rectangle that define area where map will be drawen
	 */
	public void setDrawingArea(Rectangle pDrawingArea)
	{
		if (pDrawingArea != null)
			drawingArea = pDrawingArea;
	}

	/**
	 * Get rectangle that define area where map will be drawen
	 *
	 * @return rectangle that define area where map will be drawen
	 */
	public Rectangle getDrawingArea()
	{
		return drawingArea;
	}

	/**
	 * Render map
	 *
	 * @param pCanvas canvas to draw map objects
	 * @param pMap map for rendering
	 * @param pStyleViewer drawing styles, uses for rendering
	 */
	public void renderMap(Graphics2D pCanvas, Map pMap, StyleViewer pStyleViewer)
	{
		if (pMap == null || pStyleViewer == null)
			return;

		pCanvas.setBackground(backgroundColor);
		pCanvas.clearRect(drawingArea.x, drawingArea.y, drawingArea.width, drawingArea.height);

		pMap.sortObjectsByDrawPriority(pStyleViewer);

		MapObjectsRendererSeparatingText objectsRenderer = new MapObjectsRendererSeparatingText(pCanvas, pStyleViewer, 0);
		pMap.acceptObjectsRenderer(objectsRenderer);

		// draw text canvas
	}
}
