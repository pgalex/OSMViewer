package onlineMap;

import drawingStyles.DrawingStylesFactory;
import drawingStyles.MapObjectStyle;
import drawingStyles.StyleEditor;
import drawingStyles.IOColor;
import drawingStyles.PointDrawStyle;
import drawingStyles.IOFont;
import drawingStyles.ScaledObjectStyle;
import drawingStyles.TextTagsKeys;
import drawingStyles.MapDrawingSettings;
import drawingStyles.ScaledObjectStyleArray;
import forms.DrawableOnPanel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.logging.Level;
import java.util.logging.Logger;
import map.EditableDefenitionTags;
import map.MapBounds;
import map.rendering.MapRenderer;
import map.MapTag;

/**
 * Organize work between other classes and process user's input
 *
 * @author pgalex
 */
public class OnlineMapProcessor implements DrawableOnPanel
{
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
		
		renderer = new MapRenderer();
						
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
	public void resizeCanvas(int pWidth, int pHeight)
	{
		renderer.setDrawingArea(new Rectangle(0, 0, pWidth, pHeight));
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
