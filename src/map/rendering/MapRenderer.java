package map.rendering;

import drawingStyles.MapDrawingSettings;
import drawingStyles.StyleViewer;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import map.Map;
import map.MapPosition;

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
	private Rectangle targetCanvasDrawingArea;
	/**
	 * Current view position. Center point of area on a map that will be drawen
	 */
	private MapPosition viewPosition;

	/**
	 * Default constructor
	 *
	 */
	public MapRenderer()
	{
		targetCanvasDrawingArea = DEFAULT_DRAWING_AREA;
		viewPosition = new MapPosition();
	}

	/**
	 * Set new rectangle that define area where map will be drawen (on destenation
	 * graphics)
	 *
	 * @param pDrawingArea rectangle that define area where map will be drawen
	 */
	public void setTargetCanvasDrawingArea(Rectangle pDrawingArea)
	{
		if (pDrawingArea != null)
			targetCanvasDrawingArea = pDrawingArea;
	}

	/**
	 * Get rectangle that define area where map will be drawen
	 *
	 * @return rectangle that define area where map will be drawen
	 */
	public Rectangle getTargetCanvasDrawingArea()
	{
		return targetCanvasDrawingArea;
	}

	/**
	 * Set new view positoin
	 *
	 * @param pViewPosition New view position. Center point of area on a map that
	 * will be drawen
	 */
	public void setViewPosition(MapPosition pViewPosition)
	{
		if (pViewPosition != null)
			viewPosition = pViewPosition;
	}

	/**
	 * Get view position
	 *
	 * @return current view position
	 */
	public MapPosition getViewPosition()
	{
		return viewPosition;
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
		if (pMap == null || pStyleViewer == null || pCanvas == null)
			return;

		MapDrawingSettings mapDrawingSettings = pStyleViewer.getMapDrawingSettings();
		pCanvas.setBackground(mapDrawingSettings.getMapBackgroundColor().getColor());
		pCanvas.clearRect(targetCanvasDrawingArea.x, targetCanvasDrawingArea.y, targetCanvasDrawingArea.width, targetCanvasDrawingArea.height);

		pMap.sortObjectsByDrawPriority(pStyleViewer);
		MapObjectsRendererSeparatingText objectsRenderer = new MapObjectsRendererSeparatingText(pCanvas, pStyleViewer, 0);
		pMap.acceptObjectsRenderer(objectsRenderer);

		// draw text canvas
	}
}
