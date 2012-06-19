package onlineMap;

import drawingStyles.*;
import forms.DrawableOnPanel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.logging.Level;
import java.util.logging.Logger;
import drawingStyles.EditableDefenitionTags;
import map.MapBounds;
import map.MapTag;
import map.rendering.MapRenderer;

/**
 * Organize work between other classes and process user's input
 *
 * @author pgalex
 */
public class OnlineMapProcessor implements DrawableOnPanel
{
	/**
	 * Minimum scale level for viewing online map
	 */
	private static final int ONLINE_MAP_MINIMUM_SCALE_LEVEL = 0;
	/**
	 * Maximum scale level for viewing online map
	 */
	private static final int ONLINE_MAP_MAXIMUM_SCALE_LEVEL = 6;
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
		renderer = new MapRenderer(ONLINE_MAP_MINIMUM_SCALE_LEVEL, ONLINE_MAP_MAXIMUM_SCALE_LEVEL);
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

	private void testSetupStyleViewer()
	{
		styleViewer.setMapDrawingSettings(new MapDrawingSettings(new IOColor(Color.LIGHT_GRAY)));
		PointDrawStyle pointStyle = new PointDrawStyle(null);

		ScaledObjectStyle scaledStyle = new ScaledObjectStyle(true, false, false, pointStyle, null, null, new IOColor(Color.BLACK), new IOFont());

		ScaledObjectStyleArray placeVillageScaledStyles = new ScaledObjectStyleArray();
		placeVillageScaledStyles.setStyleOnScale(0, scaledStyle);

		String[] textTagKeys = new String[1];
		textTagKeys[0] = "name";

		EditableDefenitionTags placeVillageTags = new EditableDefenitionTags();
		placeVillageTags.add(new MapTag("place", "village"));

		MapObjectStyle placeVillageStyle = new MapObjectStyle(true, false, false,
						new TextTagsKeys(textTagKeys), 0, "Village", placeVillageScaledStyles, placeVillageTags);

		styleViewer.addMapObjectStyle(placeVillageStyle);
	}

	private void testLoadMap()
	{
		try
		{
			mapLoader.loadToMap(new MapBounds(1, 2, 3, 4), styleViewer, map);
		}
		catch (Exception ex)
		{
			Logger.getLogger(OnlineMapProcessor.class.getName()).log(Level.SEVERE, null, ex);
		}
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
}
