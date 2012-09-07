package map.rendering;

import drawingStyles.MapDrawSettings;
import drawingStyles.StyleViewer;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
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
	private static final Rectangle DEFAULT_DRAWING_AREA = new Rectangle(50, 50, 150, 150);
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
	 * Create renderer
	 *
	 * @param renderingMinimumScaleLevel Minimun scale level
	 * @param renderingMaximumScaleLevel Maximum scale level
	 * @param startScaleLevel scale level that will be set after creating
	 */
	public MapRenderer(int renderingMinimumScaleLevel, int renderingMaximumScaleLevel, int startScaleLevel)
	{
		targetCanvasDrawingArea = DEFAULT_DRAWING_AREA;
		viewPosition = new MapPosition();

		scaleLevel = startScaleLevel;
		minimumScaleLevel = renderingMinimumScaleLevel;
		maximumScaleLevel = renderingMaximumScaleLevel;
	}

	/**
	 * Set new rectangle that define area where map will be drawen (on destenation
	 * graphics)
	 *
	 * @param drawingAreaToSet rectangle that define area where map will be drawen
	 */
	public void setTargetCanvasDrawingArea(Rectangle drawingAreaToSet)
	{
		if (drawingAreaToSet != null)
		{
			targetCanvasDrawingArea = drawingAreaToSet;
		}
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
	 * @param viewPositionToSet New view position. Center point of area on a map that
	 * will be drawen
	 */
	public void setViewPosition(MapPosition viewPositionToSet)
	{
		if (viewPositionToSet != null)
		{
			viewPosition = viewPositionToSet;
		}
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
	 * @param scaleLevelToSet new scale level
	 */
	public void setScaleLevel(int scaleLevelToSet)
	{
		if (scaleLevelToSet < minimumScaleLevel || scaleLevelToSet > maximumScaleLevel)
		{
			return;
		}

		scaleLevel = scaleLevelToSet;
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
		MapPosition positionOfTopLeft = canvasToGeographics(new Point2D.Double(targetCanvasDrawingArea.getMinX(), targetCanvasDrawingArea.getMinY()));
		MapPosition positionOfBottomRight = canvasToGeographics(new Point2D.Double(targetCanvasDrawingArea.getMaxX(), targetCanvasDrawingArea.getMaxY()));

		return new MapBounds(positionOfTopLeft.getLatitude(), positionOfBottomRight.getLatitude(),
						positionOfTopLeft.getLongitude(), positionOfBottomRight.getLongitude());
	}

	/**
	 * Render map
	 *
	 * @param targetCanvas canvas to draw map
	 * @param mapToRender map for rendering
	 * @param styleViewer drawing styles, uses for rendering
	 */
	public void renderMap(Graphics2D targetCanvas, Map mapToRender, StyleViewer styleViewer)
	{
		if (mapToRender == null || styleViewer == null || targetCanvas == null)
		{
			return;
		}
		
		setupRenderingHints(targetCanvas);

		MapDrawSettings mapDrawingSettings = styleViewer.getMapDrawSettings();
		targetCanvas.setBackground(mapDrawingSettings.getMapBackgroundColor().getColor());
		targetCanvas.clearRect(targetCanvasDrawingArea.x, targetCanvasDrawingArea.y, targetCanvasDrawingArea.width, targetCanvasDrawingArea.height);

		mapToRender.sortObjectsByDrawPriority(styleViewer);
		MapObjectsRendererSeparatingText objectsRenderer = new MapObjectsRendererSeparatingText(targetCanvas, styleViewer, this, scaleLevel);
		mapToRender.acceptObjectsRenderer(objectsRenderer);

		// draw text canvas
	}

	/**
	 * Set graphics rendering hints for map drawing
	 *
	 * @param canvas canvas using for setup
	 */
	private void setupRenderingHints(Graphics2D canvas)
	{
		if(canvas==null)
		{
			return;
		}
		
		canvas.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		canvas.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		canvas.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	}

	/**
	 * Convert point in geographics coordinates on a map to point on drawing
	 * canvas (using current scale and view position)
	 *
	 * @param positionOnMap position of point on a map
	 * @return position of point on drawing canvas
	 */
	@Override
	public Point2D goegraphicsToCanvas(MapPosition positionOnMap)
	{
		Point2D pointInMeractor = MercatorSphericProjection.geographicsToMercator(positionOnMap,
						ScalesArray.getScaleByScaleLevel(scaleLevel));

		Point2D viewInMercator = MercatorSphericProjection.geographicsToMercator(viewPosition,
						ScalesArray.getScaleByScaleLevel(scaleLevel));

		double pointInCanvasX = (pointInMeractor.getX() - viewInMercator.getX()) + targetCanvasDrawingArea.getCenterX();
		double pointInCanvasY = (viewInMercator.getY() - pointInMeractor.getY()) + targetCanvasDrawingArea.getCenterY();

		return new Point2D.Double(pointInCanvasX, pointInCanvasY);
	}

	/**
	 * Convert point on drawing canvas to point on a map, using current scale and
	 * view position
	 *
	 * @param positionOnCanvas position of point on drawing canvas
	 * @return position of point on map
	 */
	@Override
	public MapPosition canvasToGeographics(Point2D positionOnCanvas)
	{
		Point2D viewInMercator = MercatorSphericProjection.geographicsToMercator(viewPosition,
						ScalesArray.getScaleByScaleLevel(scaleLevel));

		double pointInMercatorX = positionOnCanvas.getX() + viewInMercator.getX() - targetCanvasDrawingArea.getCenterX();
		double pointInMercatorY = viewInMercator.getY() - positionOnCanvas.getY() + targetCanvasDrawingArea.getCenterY();

		return MercatorSphericProjection.mercatorToGeographics(new Point2D.Double(pointInMercatorX, pointInMercatorY),
						ScalesArray.getScaleByScaleLevel(scaleLevel));
	}
}
