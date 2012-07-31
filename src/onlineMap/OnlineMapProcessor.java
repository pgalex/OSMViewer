package onlineMap;

import drawingStyles.*;
import forms.DrawableOnPanel;
import java.awt.Graphics2D;
import java.awt.Rectangle;
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
	 * Default constructor
	 *
	 */
	public OnlineMapProcessor()
	{
		map = new OnlineMap();
		mapLoader = new OnlineMapLoader();
		renderer = new MapRenderer(MINIMUM_SCALE_LEVEL, MAXIMUM_SCALE_LEVEL, MINIMUM_SCALE_LEVEL);
		styleViewer = DrawingStylesFactory.createStyleEditor();
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
	 * Set new view position (center point of rendering map area)
	 *
	 * @param pLatitude new latitude of view point
	 * @param pLongitude new longitude of view point
	 */
	public void setViewPosition(double pLatitude, double pLongitude)
	{
		renderer.setViewPosition(new MapPosition(pLatitude, pLongitude));
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

	public void testSetupStyleViewer()
	{
		try
		{
			styleViewer.setMapDrawingSettings(new MapDrawingSettings(null));
			IOIcon icon = new IOIcon("icons/shop_convenience.p.16.png");
			PointDrawSettings pointStyle = new PointDrawSettings(icon.getImage());
			DrawSettingsOnScale scaledStyle = new DrawSettingsOnScale(true, false, false, pointStyle, null, null, null);

			DrawSettingsOnScaleArray scaleStylesArray = new DrawSettingsOnScaleArray();
			for (int i = MINIMUM_SCALE_LEVEL; i <= MAXIMUM_SCALE_LEVEL; i++)
			{
				scaleStylesArray.setDrawSettingsOnScale(i, scaledStyle);
			}

			String[] textTagKeys = new String[1];
			textTagKeys[0] = "name";

			EditableDefenitionTags objectDefenitionTags = new EditableDefenitionTags();
			objectDefenitionTags.add(new MapTag("shop", "convenience"));

			MapObjectDrawSettings placeVillageStyle = new MapObjectDrawSettings(true, false, false,
							new TextTagsKeys(textTagKeys), 0, "convenience shop", scaleStylesArray, objectDefenitionTags);

			styleViewer.addMapObjectDrawSettings(placeVillageStyle);
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
