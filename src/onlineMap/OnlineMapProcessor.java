package onlineMap;

import drawingStyles.*;
import forms.DrawableOnPanel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import map.MapPosition;
import drawingStyles.MapTag;
import map.rendering.MapRenderer;

/**
 * Organize work between other classes and process user's input
 *
 * @author pgalex
 */
public class OnlineMapProcessor implements DrawableOnPanel
{
	/**
	 * Minimum scale level for viewing online map. First render that can be
	 * exported in openstreetmap.org
	 */
	private static final int ONLINE_MAP_MINIMUM_SCALE_LEVEL = 10;
	/**
	 * Maximum scale level for viewing online map. Maximum scale of
	 * openstreetmap.org
	 */
	private static final int ONLINE_MAP_MAXIMUM_SCALE_LEVEL = 18;
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
		renderer = new MapRenderer(ONLINE_MAP_MINIMUM_SCALE_LEVEL, ONLINE_MAP_MAXIMUM_SCALE_LEVEL, 12);
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
			DrawStyleOnScale scaledStyle = new DrawStyleOnScale(true, false, false, pointStyle, null, null, new IOColor(Color.RED), new IOFont());

			DrawStyleOnScaleArray scaleStylesArray = new DrawStyleOnScaleArray();
			for (int i = ONLINE_MAP_MINIMUM_SCALE_LEVEL; i <= ONLINE_MAP_MAXIMUM_SCALE_LEVEL; i++)
				scaleStylesArray.setStyleOnScale(i, scaledStyle);

			String[] textTagKeys = new String[1];
			textTagKeys[0] = "name";

			EditableDefenitionTags objectDefenitionTags = new EditableDefenitionTags();
			objectDefenitionTags.add(new MapTag("shop", "convenience"));

			MapObjectDrawSettings placeVillageStyle = new MapObjectDrawSettings(true, false, false,
							new TextTagsKeys(textTagKeys), 0, "convenience shop", scaleStylesArray, objectDefenitionTags);

			styleViewer.addMapObjectStyle(placeVillageStyle);
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
}
