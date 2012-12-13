package rendering;

import drawingStyles.MapDrawSettings;
import drawingStyles.StyleViewer;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
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
	 * Minimun rendering scale level
	 */
	private int minimumScaleLevel;
	/**
	 * Maximum rendering scale level
	 */
	private int maximumScaleLevel;

	/**
	 * Create renderer
	 *
	 * @param renderingMinimumScaleLevel Minimun scale level
	 * @param renderingMaximumScaleLevel Maximum scale level
	 * @param startScaleLevel scale level that will be set as current after
	 * creating
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
	 * @throws IllegalArgumentException drawingAreaToSet is null
	 */
	public void setTargetCanvasDrawingArea(Rectangle drawingAreaToSet) throws IllegalArgumentException
	{
		if (drawingAreaToSet == null)
		{
			throw new IllegalArgumentException();
		}

		targetCanvasDrawingArea = drawingAreaToSet;
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
	 * @param viewPositionToSet New view position. Center point of area on a map
	 * that will be drawen
	 * @throws IllegalArgumentException viewPositionToSet is null
	 */
	public void setViewPosition(MapPosition viewPositionToSet) throws IllegalArgumentException
	{
		if (viewPositionToSet == null)
		{
			throw new IllegalArgumentException();
		}

		viewPosition = viewPositionToSet;
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
	 * Set new rendering scale level
	 *
	 * @param scaleLevelToSet new scale level
	 * @throws IllegalArgumentException scaleLevelToSet is out of bounds
	 */
	public void setScaleLevel(int scaleLevelToSet) throws IllegalArgumentException
	{
		if (scaleLevelToSet < minimumScaleLevel || scaleLevelToSet > maximumScaleLevel)
		{
			throw new IllegalArgumentException();
		}

		scaleLevel = scaleLevelToSet;
	}

	/**
	 * Get current scale level
	 *
	 * @return current scale level
	 */
	public int getScaleLevel()
	{
		return scaleLevel;
	}

	/**
	 * Get area on map around view position that currently displayed. Size of area
	 * determines by target canvas drawing area size
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
	 * @throws IllegalArgumentException targetCanvas, mapToRender or styleViewer
	 * is null
	 */
	public void renderMap(Map mapToRender, Graphics2D targetCanvas,
					StyleViewer styleViewer) throws IllegalArgumentException
	{
		if (mapToRender == null)
		{
			throw new IllegalArgumentException();
		}
		if (targetCanvas == null)
		{
			throw new IllegalArgumentException();
		}
		if (styleViewer == null)
		{
			throw new IllegalArgumentException();
		}

		setupRenderingHints(targetCanvas);

		MapDrawSettings mapDrawSettings = styleViewer.getMapDrawSettings();
		targetCanvas.setBackground(mapDrawSettings.getMapBackgroundColor());

		targetCanvas.clearRect(targetCanvasDrawingArea.x, targetCanvasDrawingArea.y,
						targetCanvasDrawingArea.width, targetCanvasDrawingArea.height);

		mapToRender.sortObjectsByDrawPriority(styleViewer);

		BufferedImage textCanvasImage = new BufferedImage(targetCanvasDrawingArea.width, targetCanvasDrawingArea.height,
						BufferedImage.TYPE_INT_ARGB);
		Graphics2D textCanvasGraphics = textCanvasImage.createGraphics();
		setupRenderingHints(textCanvasGraphics);

		MapObjectsRendererSeparatingText objectsRenderer = new MapObjectsRendererSeparatingText(targetCanvas,
						textCanvasGraphics, styleViewer, this, scaleLevel);
		mapToRender.rendersObjectInArea(objectsRenderer, getViewArea());

		targetCanvas.drawImage(textCanvasImage, 0, 0, null);
	}

	/**
	 * Set graphics rendering hints for map drawing
	 *
	 * @param canvas canvas using for setup
	 */
	private void setupRenderingHints(Graphics2D canvas) throws IllegalArgumentException
	{
		if (canvas == null)
		{
			throw new IllegalArgumentException();
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
	 * @throws IllegalArgumentException positionOnMap is null
	 */
	@Override
	public Point2D goegraphicsToCanvas(MapPosition positionOnMap) throws IllegalArgumentException
	{
		if (positionOnMap == null)
		{
			throw new IllegalArgumentException();
		}

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
	 * @throws IllegalArgumentException positionOnCanvas is null
	 */
	@Override
	public MapPosition canvasToGeographics(Point2D positionOnCanvas) throws IllegalArgumentException
	{
		if (positionOnCanvas == null)
		{
			throw new IllegalArgumentException();
		}

		Point2D viewInMercator = MercatorSphericProjection.geographicsToMercator(viewPosition,
						ScalesArray.getScaleByScaleLevel(scaleLevel));

		double pointInMercatorX = positionOnCanvas.getX() + viewInMercator.getX() - targetCanvasDrawingArea.getCenterX();
		double pointInMercatorY = viewInMercator.getY() - positionOnCanvas.getY() + targetCanvasDrawingArea.getCenterY();

		return MercatorSphericProjection.mercatorToGeographics(new Point2D.Double(pointInMercatorX, pointInMercatorY),
						ScalesArray.getScaleByScaleLevel(scaleLevel));
	}
}
