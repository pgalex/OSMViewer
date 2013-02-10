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
import mapDefenitionUtilities.MapPosition;
import rendering.DrawSettingsViewer;
import rendering.MapRenderer;
import rendering.RenderableMapObject;

/**
 * Organize work between other components and process user's input
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
	private DrawSettingsViewer styleViewer;

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

		styleViewer = DrawingStylesFactory.createStandartDrawSettingsViewer();

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
	 * @param scaleLevelToSet new scale level
	 * @throws IllegalArgumentException scaleLevelToSet is out of bounds
	 */
	public void setScaleLevel(int scaleLevelToSet) throws IllegalArgumentException
	{
		if (scaleLevelToSet < MINIMUM_SCALE_LEVEL || scaleLevelToSet > MAXIMUM_SCALE_LEVEL)
		{
			throw new IllegalArgumentException();
		}

		renderer.setScaleLevel(scaleLevelToSet);
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
	 * Highlight top of drawen objects at point on target canvas. If there is not
	 * objects at point, no objects will be highlighted
	 *
	 * @param pointOnTargetCanvas point on target canvas using to find object to
	 * highlight
	 * @throws IllegalArgumentException pointOnTargetCanvas is null
	 */
	public void highlightTopObjectUnderPoint(Point2D pointOnTargetCanvas) throws IllegalArgumentException
	{
		if (pointOnTargetCanvas == null)
		{
			throw new IllegalArgumentException();
		}

		RenderableMapObject[] objectsUnderPoint = renderer.findObjectsAtPoint(pointOnTargetCanvas);
		if (objectsUnderPoint.length > 0)
		{
			renderer.setObjectToDrawAsHighlighted(objectsUnderPoint[0]);
		}
		else
		{
			renderer.resetHighlightedObject();
		}
	}

	/**
	 * Set object of rendering map to draw as selected
	 *
	 * @param selectingObject object to draw as selected
	 * @throws IllegalArgumentException selectingObject is null
	 */
	public void setObjectToDrawAsSelected(RenderableMapObject selectingObject) throws IllegalArgumentException
	{
		if (selectingObject == null)
		{
			throw new IllegalArgumentException();
		}

		renderer.setObjectToDrawAsSelected(selectingObject);
	}

	/**
	 * Reset objects selection
	 */
	public void resetSelectedObject()
	{
		renderer.resetSelectedObject();
	}

	/**
	 * Find map objects at point among drawen on target canvas
	 *
	 * @param pointOnCanvas point on canvas to find map object at it
	 * @return objects at point among drawen on target canvas, Sorted by draw
	 * priority of drawen objects. Empty if no objects found.
	 * @throws IllegalArgumentException pointOnCanvas is null
	 */
	public RenderableMapObject[] findObjectsAtPoint(Point2D pointOnCanvas) throws IllegalArgumentException
	{
		if (pointOnCanvas == null)
		{
			throw new IllegalArgumentException();
		}

		return renderer.findObjectsAtPoint(pointOnCanvas);
	}

	/**
	 * Draw objects on drawing panel
	 *
	 * @param panelGraphics drawing panel graphics
	 */
	@Override
	public void drawOnPanel(Graphics2D panelGraphics)
	{
		renderer.renderMap(map, panelGraphics, styleViewer.getMapDrawSettings());
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
