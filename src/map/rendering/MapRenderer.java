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
	 * openstreetmap like scale on each scale level in meters per pixel in equator
	 */
	private static final double scaleByScaleLevel[] =
	{
		1.0 / 111000000.0,
		1.0 / 55000000,
		1.0 / 28000000,
		1.0 / 14000000,
		1.0 / 7000000,
		1.0 / 3000000,
		1.0 / 2000000,
		1.0 / 867.000,
		1.0 / 433.000,
		1.0 / 217.000,
		1.0 / 108.000,
		1.0 / 54.000,
		1.0 / 27.000,
		1.0 / 14.000,
		1.0 / 6.771,
		1.0 / 3.385,
		1.0 / 1.693
	};
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
	 * Current scale level
	 */
	private int scaleLevel;
	/**
	 * Minimun scale level
	 */
	private int minimumScaleLevel;
	/**
	 * Maximum scale level
	 */
	private int maximumScaleLevel;

	/**
	 * Constructor
	 *
	 *
	 * @param pMinimumScaleLevel Minimun scale level
	 * @param pMaximumScaleLevel Maximum scale level
	 */
	public MapRenderer(int pMinimumScaleLevel, int pMaximumScaleLevel)
	{
		targetCanvasDrawingArea = DEFAULT_DRAWING_AREA;
		viewPosition = new MapPosition();
		scaleLevel = 0;
		minimumScaleLevel = pMinimumScaleLevel;
		maximumScaleLevel = pMaximumScaleLevel;
	}

	/**
	 * Get scale by scale level.
	 *
	 * @param pScaleLevel scale level.
	 * @param pLatitude latitude where need find scale
	 * @return scale by scale level in meters per pixel. If scale level more than
	 * maximum of less than minimum will be return value on each bound
	 */
	public static double getScaleByScaleLevel(int pScaleLevel, double pLatitude)
	{
		// scaleOnScaleLevel[normalizeScaleLevel]*cos(pLatitude)
		return 0;
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
	 * Set new scale level
	 *
	 * @param pScaleLevel new scale level
	 */
	public void setScaleLevel(int pScaleLevel)
	{
		if (pScaleLevel < minimumScaleLevel || pScaleLevel > maximumScaleLevel)
			return;

		scaleLevel = pScaleLevel;
	}

	/**
	 * Get current scale level
	 *
	 * @return currect scale level
	 */
	public int getScaleLevel()
	{
		return scaleLevel;
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
