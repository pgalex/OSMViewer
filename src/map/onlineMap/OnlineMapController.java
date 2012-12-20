package map.onlineMap;

import drawingStyles.*;
import forms.DrawableOnPanel;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import map.MapPosition;
import rendering.MapRenderer;

/**
 * Organize work between other classes and process user's input
 *
 * @author pgalex
 */
public class OnlineMapController implements DrawableOnPanel
{
	/**
	 * Minimum scale level for viewing online map. First level that can be
	 * exported in openstreetmap.org
	 */
	private static final int MINIMUM_SCALE_LEVEL = 10;
	/**
	 * Maximum scale level for viewing online map. Maximum scale of
	 * openstreetmap.org
	 */
	private static final int MAXIMUM_SCALE_LEVEL = 18;
	/**
	 * Map - stored map objects
	 */
	private OnlineMap map;
	/**
	 * Map loader - gets data from OSM server
	 */
	private OnlineMapLoader mapLoader;
	/**
	 * Map renderer - drawes object
	 */
	private MapRenderer renderer;
	/**
	 * Drawing styles currently uses to render map
	 */
	private StyleEditor styleViewer;

	/**
	 * Create online map processor
	 *
	 * @param startViewPosition start view position (on map)
	 * @param startScaleLevel start scale level
	 * @param startCanvasWidth start target canvas width
	 * @param startCanvasHeight start target canvas height
	 */
	public OnlineMapController(MapPosition startViewPosition, int startScaleLevel,
					int startCanvasWidth, int startCanvasHeight)
	{
		map = new OnlineMap();

		mapLoader = new OnlineMapLoader();

		renderer = new MapRenderer(MINIMUM_SCALE_LEVEL, MAXIMUM_SCALE_LEVEL, startScaleLevel);
		renderer.setViewPosition(startViewPosition);
		renderer.setTargetCanvasDrawingArea(new Rectangle(0, 0, startCanvasWidth, startCanvasHeight));

		styleViewer = DrawingStylesFactory.createStyleEditor();

		testSetupStyleViewer();
		testLoadMap();
	}

	/**
	 * Set new size of target canvas
	 *
	 * @param width new width
	 * @param height new height
	 */
	public void setCanvasSize(int width, int height)
	{
		renderer.setTargetCanvasDrawingArea(new Rectangle(0, 0, width, height));
	}

	/**
	 * Set new view position (center point of rendering map area) using position
	 * on map
	 *
	 * @param positionOnMap new view position
	 */
	public void setViewPosition(MapPosition positionOnMap)
	{
		renderer.setViewPosition(positionOnMap);
	}

	/**
	 * Move current view position by pixels distance by x and y
	 *
	 * @param deltaXInPixels moving distance by x in pixels
	 * @param deltaYInPixels moving distance by y in pixels
	 */
	public void moveViewPositionByPixels(int deltaXInPixels, int deltaYInPixels)
	{
		Rectangle targetCanvasArea = renderer.getTargetCanvasDrawingArea();
		Point2D newViewPositionOnCanvas = new Point2D.Double(targetCanvasArea.getWidth() / 2 + deltaXInPixels,
						targetCanvasArea.getHeight() / 2 + deltaYInPixels);

		MapPosition newViewPositionOnMap = renderer.canvasToGeographics(newViewPositionOnCanvas);
		renderer.setViewPosition(newViewPositionOnMap);
	}

	/**
	 * Set new scale level
	 *
	 * @param scaleLevel new scale level
	 */
	public void setScaleLevel(int scaleLevel)
	{
		renderer.setScaleLevel(scaleLevel);
	}

	/**
	 * Get current scale level
	 *
	 * @return current scale leve
	 */
	public int getScaleLevel()
	{
		return renderer.getScaleLevel();
	}

	/**
	 * Draw objects on drawing panel
	 *
	 * @param panelGraphics drawing panel graphics
	 */
	@Override
	public void drawOnPanel(Graphics2D panelGraphics)
	{
		renderer.renderMap(map, panelGraphics, styleViewer);
	}

	private void testSetupStyleViewer()
	{
		try
		{
			styleViewer.readFromFile(new File("drawStyles/testdrawstyles.dat"));
		}
		catch (IOException ex)
		{
			Logger.getLogger(OnlineMapController.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void testLoadMap()
	{
		try
		{
			mapLoader.loadToMap(renderer.getViewArea(), styleViewer, map);
		}
		catch (Exception ex)
		{
			Logger.getLogger(OnlineMapController.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Get minimum scale level for viewing online map
	 *
	 * @return Minimum scale level for viewing online map
	 */
	public static int GetMinimumScaleLevel()
	{
		return MINIMUM_SCALE_LEVEL;
	}

	/**
	 * Get maximum scale level for viewing online map
	 *
	 * @return Maximum scale level for viewing online map
	 */
	public static int GetMaximumScaleLevel()
	{
		return MAXIMUM_SCALE_LEVEL;
	}
}
