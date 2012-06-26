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
	 * Get rectangle of map around view position that currently displayed. Size of
	 * rectangle determines by target canvas drawing area size
	 *
	 * @return view area
	 */
	public MapBounds getViewArea()
	{
		Point2D viewPositionMetric = geoToMetric(viewPosition);
		Point2D viewPositionOnCanvas = new Point2D.Double(viewPositionMetric.getX() * ScalesArray.getScaleByScaleLevel(scaleLevel, viewPosition.getLatitude()),
						viewPositionMetric.getY() * ScalesArray.getScaleByScaleLevel(scaleLevel, viewPosition.getLatitude()));

		Point2D viewAreaTopLeftOnCanvas = new Point2D.Double(viewPositionOnCanvas.getX() - targetCanvasDrawingArea.getWidth() / 2,
						viewPositionOnCanvas.getY() - targetCanvasDrawingArea.getHeight() / 2);
		Point2D viewAreaTopLeftMetric = new Point2D.Double(viewAreaTopLeftOnCanvas.getX() / ScalesArray.getScaleByScaleLevel(scaleLevel, viewPosition.getLatitude()),
						viewAreaTopLeftOnCanvas.getY() / ScalesArray.getScaleByScaleLevel(scaleLevel, viewPosition.getLatitude()));

		MapPosition viewAreaTopLeft = metricToGeo(viewAreaTopLeftMetric);


		Point2D viewAreaRightBottomOnCanvas = new Point2D.Double(viewPositionOnCanvas.getX() + targetCanvasDrawingArea.getWidth() / 2,
						viewPositionOnCanvas.getY() + targetCanvasDrawingArea.getHeight() / 2);
		Point2D viewAreaRightBottomMetric = new Point2D.Double(viewAreaRightBottomOnCanvas.getX() / ScalesArray.getScaleByScaleLevel(scaleLevel, viewPosition.getLatitude()),
						viewAreaRightBottomOnCanvas.getY() / ScalesArray.getScaleByScaleLevel(scaleLevel, viewPosition.getLatitude()));

		MapPosition viewAreaRightBottom = metricToGeo(viewAreaRightBottomMetric);

		return new MapBounds(viewAreaTopLeft.getLatitude(),
						viewAreaRightBottom.getLatitude(),
						viewAreaTopLeft.getLongitude(),
						viewAreaRightBottom.getLongitude());
	}

	/**
	 * Convert geographics (spheric) coordinates to metric
	 *
	 * @param pPositionOnMap
	 * @return point in metric coordinates
	 */
	private Point2D geoToMetric(MapPosition pPositionOnMap)
	{
		double x = pPositionOnMap.getLongitude() * 20037508.34 / 180.0;
		double y = Math.log(Math.tan((90.0 + pPositionOnMap.getLatitude()) * Math.PI / 360.0)) / (Math.PI / 180.0);
		y = y * 20037508.34 / 180.0;

		return new Point2D.Double(x, y);
	}

	/**
	 * Convert metric coordinates to geographics
	 *
	 * @param pPoint point with metric coordinates
	 * @return point with geographics coordinates
	 */
	private MapPosition metricToGeo(Point2D pPoint)
	{
		double lon = pPoint.getX() / 20037508.34 * 180.0;
		double p = Math.pow(Math.E, pPoint.getY() * Math.PI / 20037508.34);
		double lat = Math.atan(p) * 360.0 / Math.PI - 90.0;

		return new MapPosition(lat, lon);
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
		MapObjectsRendererSeparatingText objectsRenderer = new MapObjectsRendererSeparatingText(pCanvas, pStyleViewer, 0);
		pMap.acceptObjectsRenderer(objectsRenderer);

		// draw text canvas
	}
}
