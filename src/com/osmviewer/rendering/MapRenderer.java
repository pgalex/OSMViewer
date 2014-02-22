package com.osmviewer.rendering;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import com.osmviewer.mapDefenitionUtilities.MapBounds;
import com.osmviewer.mapDefenitionUtilities.Location;
import com.osmviewer.xmlDrawSettings.XmlDrawSettingsContainer;
import com.osmviewer.rendering.selectng.SelectingBuffer;

/**
 * Map renderer. Defines order of map rendering and contains rendring parameters
 *
 * @author pgalex
 */
public class MapRenderer implements CoordinatesConverter
{
	/**
	 * Default target canvas drawing area
	 */
	private static final Rectangle DEFAULT_DRAWING_AREA = new Rectangle(50, 50, 150, 150);
	/**
	 * Rectangle on target canvas that define area where map will be drawen
	 */
	private Rectangle targetCanvasDrawingArea;
	/**
	 * Current view position. Center point of rendering area of a map
	 */
	private Location viewPosition;
	/**
	 * Current rendering scale level
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
	 * Buffer, contains intpertation of drawen objects using for finding objects
	 * under point, for selecting
	 */
	private SelectingBuffer selectingBuffer;

	/**
	 * Create map renderer
	 *
	 * @param minimumScaleLevel minimun scale level
	 * @param maximumScaleLevel maximum scale level
	 * @param startScaleLevel scale level that will be set as current after
	 * creating
	 * @throws IllegalArgumentException minimumScaleLevel more than
	 * maximumScaleLevel, or startScaleLevel out of minimum/maximum bounds
	 */
	public MapRenderer(int minimumScaleLevel, int maximumScaleLevel,
					int startScaleLevel) throws IllegalArgumentException
	{
		if (minimumScaleLevel > maximumScaleLevel)
		{
			throw new IllegalArgumentException("minimumScaleLevel is more than renderingMaximumScaleLevel");
		}
		if (startScaleLevel < minimumScaleLevel || startScaleLevel > maximumScaleLevel)
		{
			throw new IllegalArgumentException("startScaleLevel is out of bounds");
		}

		this.targetCanvasDrawingArea = DEFAULT_DRAWING_AREA;
		this.viewPosition = new Location();

		this.scaleLevel = startScaleLevel;
		this.minimumScaleLevel = minimumScaleLevel;
		this.maximumScaleLevel = maximumScaleLevel;

		this.selectingBuffer = new SelectingBuffer();
	}

	/**
	 * Set rectangle that define area where map will be drawen (on destenation
	 * graphics)
	 *
	 * @param drawingAreaToSet rectangle that define area of target canvas where
	 * map will be drawen
	 * @throws IllegalArgumentException drawingAreaToSet is null
	 */
	public void setTargetCanvasDrawingArea(Rectangle drawingAreaToSet) throws IllegalArgumentException
	{
		if (drawingAreaToSet == null)
		{
			throw new IllegalArgumentException("drawingAreaToSet is null");
		}

		targetCanvasDrawingArea = drawingAreaToSet;
	}

	/**
	 * Get rectangle that define area where map drawen (on destenation graphics)
	 *
	 * @return rectangle that define area where map drawen
	 */
	public Rectangle getTargetCanvasDrawingArea()
	{
		return targetCanvasDrawingArea;
	}

	/**
	 * Set new view positoin
	 *
	 * @param viewPositionToSet new view position. Center point of map rendering
	 * area
	 * @throws IllegalArgumentException viewPositionToSet is null
	 */
	public void setViewPosition(Location viewPositionToSet) throws IllegalArgumentException
	{
		if (viewPositionToSet == null)
		{
			throw new IllegalArgumentException("viewPositionToSet is null");
		}

		viewPosition = viewPositionToSet;
	}

	/**
	 * Get view position
	 *
	 * @return current view position
	 */
	public Location getViewPosition()
	{
		return viewPosition;
	}

	/**
	 * Set new rendering scale level
	 *
	 * @param scaleLevelToSet new scale level
	 * @throws IllegalArgumentException scaleLevelToSet is less than minimum scale
	 * level, or more than maximum
	 */
	public void setScaleLevel(int scaleLevelToSet) throws IllegalArgumentException
	{
		if (scaleLevelToSet < minimumScaleLevel || scaleLevelToSet > maximumScaleLevel)
		{
			throw new IllegalArgumentException("scaleLevelToSet is out of bounds");
		}

		scaleLevel = scaleLevelToSet;
	}

	/**
	 * Get scale level
	 *
	 * @return current scale level
	 */
	public int getScaleLevel()
	{
		return scaleLevel;
	}

	/**
	 * Get area on map around view position that currently displaying. Size of
	 * area determines by target canvas drawing area
	 *
	 * @return view area
	 */
	public MapBounds getViewArea()
	{
		Location positionOfTopLeft = canvasToGeographics(new Point2D.Double(targetCanvasDrawingArea.getMinX(), targetCanvasDrawingArea.getMinY()));
		Location positionOfBottomRight = canvasToGeographics(new Point2D.Double(targetCanvasDrawingArea.getMaxX(), targetCanvasDrawingArea.getMaxY()));

		return new MapBounds(positionOfTopLeft.getLatitude(), positionOfBottomRight.getLatitude(),
						positionOfTopLeft.getLongitude(), positionOfBottomRight.getLongitude());
	}

	/**
	 * Render map
	 *
	 * @param targetCanvas canvas to draw map on
	 * @param mapToRender rendering map
	 * @param drawSettingsFinder
	 * @throws IllegalArgumentException targetCanvas, mapToRender or
	 * drawSettingsFinder is null
	 */
	public void renderMap(RenderableMap mapToRender, Graphics2D targetCanvas, DrawSettingsFinder drawSettingsFinder) throws IllegalArgumentException
	{
		// todo move mapDrawSettings to contructor and set.. method
		if (mapToRender == null)
		{
			throw new IllegalArgumentException("mapToRender is null");
		}
		if (targetCanvas == null)
		{
			throw new IllegalArgumentException("targetCanvas is null");
		}
		if (drawSettingsFinder == null)
		{
			throw new IllegalArgumentException("drawSettingsFinder is null");
		}

		if (targetCanvasDrawingArea.isEmpty())
		{
			// need test
			return;
		}

		setupRenderingHints(targetCanvas);

		targetCanvas.setBackground(drawSettingsFinder.getMapDrawSettings().getMapBackgroundColor());
		targetCanvas.clearRect(targetCanvasDrawingArea.x, targetCanvasDrawingArea.y,
						targetCanvasDrawingArea.width, targetCanvasDrawingArea.height);

		BufferedImage textCanvasImage = new BufferedImage(targetCanvasDrawingArea.width, targetCanvasDrawingArea.height,
						BufferedImage.TYPE_INT_ARGB);

		Graphics2D textCanvasGraphics = textCanvasImage.createGraphics();
		setupRenderingHints(textCanvasGraphics);

		selectingBuffer.clear();

		SeparatingByDrawingIdMapObjectsRenderer objectsRenderer = new SeparatingByDrawingIdMapObjectsRenderer(new Graphics2DDrawingTool(textCanvasGraphics),
						this, scaleLevel, selectingBuffer, drawSettingsFinder);

		mapToRender.renderObjectsByDrawPriority(objectsRenderer, getViewArea(), new DrawSettingsBasedDrawPriorityComparator(drawSettingsFinder));

		targetCanvas.drawImage(textCanvasImage, 0, 0, null);
	}

	/**
	 * Set graphics rendering hints for map drawing
	 *
	 * @param graphics graphics to setup setting on. Must be not null
	 * @throws IllegalArgumentException graphics is null
	 */
	private void setupRenderingHints(Graphics2D graphics) throws IllegalArgumentException
	{
		if (graphics == null)
		{
			throw new IllegalArgumentException("graphics is null");
		}

		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	}

	/**
	 * Convert point in geographics coordinates on a map to point on drawing
	 * canvas (using current scale and view position)
	 *
	 * @param positionOnMap position of point on a map to convert
	 * @return position of point on drawing canvas
	 * @throws IllegalArgumentException positionOnMap is null
	 */
	@Override
	public Point2D goegraphicsToCanvas(Location positionOnMap) throws IllegalArgumentException
	{
		if (positionOnMap == null)
		{
			throw new IllegalArgumentException("positionOnMap is null");
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
	 * @param positionOnCanvas position of point on drawing canvas to convert
	 * @return position of point on map
	 * @throws IllegalArgumentException positionOnCanvas is null
	 */
	@Override
	public Location canvasToGeographics(Point2D positionOnCanvas) throws IllegalArgumentException
	{
		if (positionOnCanvas == null)
		{
			throw new IllegalArgumentException("positionOnCanvas is null");
		}

		Point2D viewInMercator = MercatorSphericProjection.geographicsToMercator(viewPosition,
						ScalesArray.getScaleByScaleLevel(scaleLevel));

		double pointInMercatorX = positionOnCanvas.getX() + viewInMercator.getX() - targetCanvasDrawingArea.getCenterX();
		double pointInMercatorY = viewInMercator.getY() - positionOnCanvas.getY() + targetCanvasDrawingArea.getCenterY();

		return MercatorSphericProjection.mercatorToGeographics(new Point2D.Double(pointInMercatorX, pointInMercatorY),
						ScalesArray.getScaleByScaleLevel(scaleLevel));
	}
}
