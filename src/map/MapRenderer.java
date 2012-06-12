package map;

import drawingStyle.MapDrawingSettings;
import drawingStyle.StyleViewer;
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
	 * Default drawing area
	 */
	private static final Rectangle DEFAULT_DRAWING_AREA = new Rectangle(0, 0, 100, 100);
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
		drawingArea = DEFAULT_DRAWING_AREA;
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
		
		MapDrawingSettings mapDrawingSettings = pStyleViewer.getMapDrawingSettings();
		pCanvas.setBackground(mapDrawingSettings.getMapBackgroundColor().getColor());
		
		pCanvas.clearRect(drawingArea.x, drawingArea.y, drawingArea.width, drawingArea.height);

		pMap.sortObjectsByDrawPriority(pStyleViewer);

		MapObjectsRendererSeparatingText objectsRenderer = new MapObjectsRendererSeparatingText(pCanvas, pStyleViewer, 0);
		pMap.acceptObjectsRenderer(objectsRenderer);

		// draw text canvas
	}
}
