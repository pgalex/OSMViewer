package onlineMap;

import drawingStyles.*;
import forms.DrawableOnPanel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import map.MapPosition;
import map.rendering.MapRenderer;

/**
 * Organize work between other classes and process user's input
 *
 * @author pgalex
 */
public class OnlineMapProcessor implements DrawableOnPanel
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
	 * Constructor
	 *
	 * @param pStartViewPosition start view position (on map)
	 * @param pStartScaleLevel start scale level
	 * @param pStartCanvasWidth start target canvas width
	 * @param pStartCanvasHeight start target canvas height
	 */
	public OnlineMapProcessor(MapPosition pStartViewPosition, int pStartScaleLevel,
					int pStartCanvasWidth, int pStartCanvasHeight)
	{
		map = new OnlineMap();
		
		mapLoader = new OnlineMapLoader();
		
		renderer = new MapRenderer(MINIMUM_SCALE_LEVEL, MAXIMUM_SCALE_LEVEL, pStartScaleLevel);
		renderer.setViewPosition(pStartViewPosition);
		renderer.setTargetCanvasDrawingArea(new Rectangle(0, 0, pStartCanvasWidth, pStartCanvasHeight));
		
		styleViewer = DrawingStylesFactory.createStyleEditor();
		
		testSetupStyleViewer();
		testLoadMap();
	}

	/**
	 * Set new size of target canvas
	 *
	 * @param pWidth new width
	 * @param pHeight new height
	 */
	public void setCanvasSize(int pWidth, int pHeight)
	{
		renderer.setTargetCanvasDrawingArea(new Rectangle(0, 0, pWidth, pHeight));
	}

	/**
	 * Set new view position (center point of rendering map area) using position
	 * on map
	 *
	 * @param pPositionOnMap new view position
	 */
	public void setViewPosition(MapPosition pPositionOnMap)
	{
		renderer.setViewPosition(pPositionOnMap);
	}

	/**
	 * Move current view position by pixels distance by x and y
	 *
	 * @param pDeltaXInPixels moving distance by x in pixels
	 * @param pDeltaYInPixels moving distance by y in pixels
	 */
	public void moveViewPositionByPixels(int pDeltaXInPixels, int pDeltaYInPixels)
	{
		Rectangle targetCanvasArea = renderer.getTargetCanvasDrawingArea();
		Point2D newViewPositionOnCanvas = new Point2D.Double(targetCanvasArea.getWidth() / 2 + pDeltaXInPixels,
						targetCanvasArea.getHeight() / 2 + pDeltaYInPixels);

		MapPosition newViewPositionOnMap = renderer.canvasToGeographics(newViewPositionOnCanvas);
		renderer.setViewPosition(newViewPositionOnMap);
	}

	/**
	 * Set new scale level
	 *
	 * @param pScaleLevel new scale level
	 */
	public void setScaleLevel(int pScaleLevel)
	{
		renderer.setScaleLevel(pScaleLevel);
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
	 * @param pPanelGraphics drawing panel graphics
	 */
	@Override
	public void drawOnPanel(Graphics2D pPanelGraphics)
	{
		renderer.renderMap(pPanelGraphics, map, styleViewer);
	}

	private void testSetupStyleViewer()
	{
		try
		{
			styleViewer.setMapDrawSettings(new MapDrawSettings(null));
			
			IOIcon shopIcon = new IOIcon("icons/shop_convenience.p.16.png");
			PointDrawSettings shopPointStyle = new PointDrawSettings(shopIcon.getImage());
			TextDrawSettings shopTextStyle = new TextDrawSettings(Color.MAGENTA, null);
			DrawSettingsOnScale shopScaledStyle = new DrawSettingsOnScale(true, false, false, shopPointStyle, null, null, shopTextStyle);

			DrawSettingsOnScaleArray shopScaleStylesArray = new DrawSettingsOnScaleArray();
			for (int i = MINIMUM_SCALE_LEVEL; i <= MAXIMUM_SCALE_LEVEL; i++)
			{
				shopScaleStylesArray.setDrawSettingsOnScale(i, shopScaledStyle);
			}

			String[] shopTextTagKeys = new String[1];
			shopTextTagKeys[0] = "name";

			EditableDefenitionTags shopTags = new EditableDefenitionTags();
			shopTags.add(new MapTag("shop", "convenience"));

			MapObjectDrawSettings shopStyle = new MapObjectDrawSettings(true, false, false,
							new TextTagsKeys(shopTextTagKeys), 0, "convenience shop", shopScaleStylesArray, shopTags);
			
			float[] forestPattern = new float[2];
			forestPattern[0] = 5;
			forestPattern[1] = 5;
			LineDrawSettings forestBorderStyle = new LineDrawSettings(new Color(134, 169, 120), 2, forestPattern);
			
			IOIcon forestTexture = new IOIcon("icons/forest.png");
			PolygonDrawSettings forestPolygonStyle = new PolygonDrawSettings(new Color(174, 209, 160), 
							forestBorderStyle, forestTexture.getImage());
			TextDrawSettings forestTextStyle = new TextDrawSettings(new Color(54, 89, 40), null);
			DrawSettingsOnScale forestScaledStyle = new DrawSettingsOnScale(false, false, true, 
							null, null, forestPolygonStyle, forestTextStyle);

			DrawSettingsOnScaleArray forestScaleStylesArray = new DrawSettingsOnScaleArray();
			for (int i = MINIMUM_SCALE_LEVEL; i <= MAXIMUM_SCALE_LEVEL; i++)
			{
				forestScaleStylesArray.setDrawSettingsOnScale(i, forestScaledStyle);
			}

			String[] forestTextTagKeys = new String[1];
			forestTextTagKeys[0] = "name";

			EditableDefenitionTags forestTags = new EditableDefenitionTags();
			forestTags.add(new MapTag("natural", "wood"));

			MapObjectDrawSettings forestStyle = new MapObjectDrawSettings(false, false, true,
							new TextTagsKeys(forestTextTagKeys), 0, "forest", forestScaleStylesArray, forestTags);
			

			styleViewer.addMapObjectDrawSettings(shopStyle);
			styleViewer.addMapObjectDrawSettings(forestStyle);
		}
		catch (IOException ex)
		{
			Logger.getLogger(OnlineMapProcessor.class.getName()).log(Level.SEVERE, null, ex);
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
			Logger.getLogger(OnlineMapProcessor.class.getName()).log(Level.SEVERE, null, ex);
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
