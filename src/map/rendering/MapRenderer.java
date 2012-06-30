package map.rendering;

import drawingStyles.MapDrawingSettings;
import drawingStyles.StyleViewer;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import map.Map;
import map.MapBounds;
import map.MapPosition;

/**
 * Uses to render Map. Defines order of rendering and container rendring
 * parameters
 *
 * @author pgalex
 */
public class MapRenderer implements CoordinatesConverter
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
	 * @param pStartScaleLevel scale level that will be set after creating
	 */
	public MapRenderer(int pMinimumScaleLevel, int pMaximumScaleLevel, int pStartScaleLevel)
	{
		targetCanvasDrawingArea = DEFAULT_DRAWING_AREA;
		viewPosition = new MapPosition();

		scaleLevel = pStartScaleLevel;
		minimumScaleLevel = pMinimumScaleLevel;
		maximumScaleLevel = pMaximumScaleLevel;
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
	 * Get rectangle that define area where map will be drawen (on destenation
	 * graphics)
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
	 * @param pCanvas canvas to draw map
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
		MapObjectsRendererSeparatingText objectsRenderer = new MapObjectsRendererSeparatingText(pCanvas, pStyleViewer, this);
		pMap.acceptObjectsRenderer(objectsRenderer);

		// draw text canvas
	}

	/**
	 * Get rectangle of map around view position that currently displayed. Size of
	 * rectangle determines by target canvas drawing area size
	 *
	 * @return view area
	 */
	public MapBounds getViewArea()
	{
		return new MapBounds(55.1423, 55.2336, 38.5189, 38.7067);
	}

	/**
	 * Convert point in geographics coordinates on a map to point on drawing
	 * canvas (using current scale and view position)
	 *
	 * @param pPositionOnMap position of point on a map
	 * @return position of point on drawing canvas
	 */
	@Override
	public Point2D goegraphicsToCanvas(MapPosition pPositionOnMap)
	{
		if (pPositionOnMap == null)
			return null;

		double pointMercatorX = Mercator.mercX(pPositionOnMap.getLongitude());
		double pointMercatorY = Mercator.mercY(pPositionOnMap.getLatitude());
		double pointScale = ScalesArray.getScaleByScaleLevel(scaleLevel, pPositionOnMap.getLatitude());

		double viewMercatorX = Mercator.mercX(viewPosition.getLongitude());
		double viewMercatorY = Mercator.mercY(viewPosition.getLatitude());
		double viewScale = ScalesArray.getScaleByScaleLevel(scaleLevel, pPositionOnMap.getLatitude());

		double pointCanvasX = (pointMercatorX * pointScale - viewMercatorX * viewScale) + targetCanvasDrawingArea.getCenterX();
		double pointCanvasY = (viewMercatorY * viewScale - pointMercatorY * pointScale) + targetCanvasDrawingArea.getCenterY();

		return new Point2D.Double(pointCanvasX, pointCanvasY);
	}
}
