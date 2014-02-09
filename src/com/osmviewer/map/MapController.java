package com.osmviewer.map;

import com.osmviewer.drawingStyles.DrawSettingsViewer;
import com.osmviewer.drawingStyles.DrawingStylesFactory;
import com.osmviewer.forms.DrawableOnPanel;
import com.osmviewer.map.exceptions.FetchingErrorException;
import com.osmviewer.mapDefenitionUtilities.Location;
import com.osmviewer.mapDefenitionUtilities.MapBounds;
import com.osmviewer.rendering.MapRenderer;
import com.osmviewer.rendering.RenderableMapObject;
import com.osmviewer.sqliteMapDataSource.SQLiteDatabaseMapDataSource;
import com.osmviewer.sqliteMapDataSource.exceptions.DatabaseErrorExcetion;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Organize work between other components and process user's input
 *
 * @author pgalex
 */
public class MapController implements DrawableOnPanel
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
	 * Map
	 */
	private final SimpleMap map;
	/**
	 * MapByOsmXmlData renderer - drawes object
	 */
	private final MapRenderer renderer;
	/**
	 * Drawing styles currently uses to render map
	 */
	private final DrawSettingsViewer styleViewer;
	/**
	 * Currently using map data source for map loading. Null if data source not
	 * set
	 */
	private MapDataSource mapDataSource;

	/**
	 * Create online map processor
	 *
	 * @param startViewPosition start view position (on map)
	 * @param startScaleLevel start scale level
	 * @param startCanvasWidth start target canvas width
	 * @param startCanvasHeight start target canvas height
	 */
	public MapController(Location startViewPosition, int startScaleLevel,
					int startCanvasWidth, int startCanvasHeight)
	{
		map = new SimpleMap();
		mapDataSource = null;

		renderer = new MapRenderer(MINIMUM_SCALE_LEVEL, MAXIMUM_SCALE_LEVEL, startScaleLevel);
		renderer.setViewPosition(startViewPosition);
		renderer.setTargetCanvasDrawingArea(new Rectangle(0, 0, startCanvasWidth, startCanvasHeight));

		styleViewer = DrawingStylesFactory.createStandartDrawSettingsViewer();

		//testSetupStyleViewer();
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
	 * Set new view position (center point of rendering map area)
	 *
	 * @param positionOnMap new view position
	 */
	public void setViewPosition(Location positionOnMap)
	{
		renderer.setViewPosition(positionOnMap);
	}

	/**
	 * Get current view position (center point of rendering map area)
	 *
	 * @return current view position
	 */
	public Location getViewPosition()
	{
		return renderer.getViewPosition();
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

		Location newViewPositionOnMap = renderer.canvasToGeographics(newViewPositionOnCanvas);
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
			throw new IllegalArgumentException("scaleLevelToSet is out of bounds");
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
			throw new IllegalArgumentException("pointOnTargetCanvas is null");
		}

		/*RenderableMapObject[] objectsUnderPoint = renderer.findObjectsAtPoint(pointOnTargetCanvas);
		if (objectsUnderPoint.length > 0)
		{
			renderer.setObjectToDrawAsHighlighted(objectsUnderPoint[0]);
		}
		else
		{
			renderer.resetHighlightedObject();
		}*/
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
			throw new IllegalArgumentException("selectingObject is null");
		}

		//renderer.setObjectToDrawAsSelected(selectingObject);
	}

	/**
	 * Reset objects selection
	 */
	public void resetSelectedObject()
	{
		//renderer.resetSelectedObject();
	}

	/**
	 * Find map objects at point among drawen on target canvas
	 *
	 * @param pointOnCanvas point on canvas to find map object at it
	 * @return objects at point among drawen on target canvas, Sorted by draw
	 * priority of drawen objects. Empty if no objects found.
	 * @throws IllegalArgumentException pointOnCanvas is null
	 */
	/*public RenderableMapObject[] findObjectsAtPoint(Point2D pointOnCanvas) throws IllegalArgumentException
	{
		if (pointOnCanvas == null)
		{
			throw new IllegalArgumentException("pointOnCanvas is null");
		}

		return renderer.findObjectsAtPoint(pointOnCanvas);
	}*/

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
			styleViewer.readFromFile(new File("standartDrawStyles/defaultMapStyle.dat"));
		}
		catch (IOException ex)
		{
			Logger.getLogger(MapController.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Get minimum scale level for viewing online map
	 *
	 * @return Minimum scale level for viewing online map
	 */
	public int getMinimumScaleLevel()
	{
		return MINIMUM_SCALE_LEVEL;
	}

	/**
	 * Get maximum scale level for viewing online map
	 *
	 * @return Maximum scale level for viewing online map
	 */
	public int getMaximumScaleLevel()
	{
		return MAXIMUM_SCALE_LEVEL;
	}

	/**
	 * Set currently map data source by map database file
	 *
	 * @param databaseFilePath path to map objects database
	 * @throws IllegalArgumentException databaseFilePath is null or empty
	 * @throws DatabaseErrorExcetion error while creating datasource by given file
	 */
	public void setMapDataSourceByDatabase(String databaseFilePath) throws IllegalArgumentException, DatabaseErrorExcetion
	{
		if (databaseFilePath == null)
		{
			throw new IllegalArgumentException("databaseFilePath is null");
		}
		if (databaseFilePath.isEmpty())
		{
			throw new IllegalArgumentException("databaseFilePath is empty");
		}

		mapDataSource = new SQLiteDatabaseMapDataSource(databaseFilePath);
	}

	/**
	 * Is any map data source set
	 *
	 * @return Is any map data source set
	 */
	private boolean isMapDataSourceSet()
	{
		return mapDataSource != null;
	}

	/**
	 * Load map objects, that exists in current view position (including scale
	 * level and view area size), from current data source. If map data source not
	 * set, no object will be loaded
	 *
	 * @throws IllegalArgumentException loadingHandler is null
	 * @throws FetchingErrorException error while loading map objects
	 */
	public void loadMapByCurrentViewPosition() throws FetchingErrorException
	{
		if (isMapDataSourceSet())
		{
			map.loadObjectsInArea(new MapBounds(-180, 180, -90, 90), mapDataSource);
		}
	}
}
