package map;

import drawingStyle.StyleViewer;

/**
 * Uses to render Map in right order
 *
 * @author pgalex
 */
public class MapRenderer
{
	/**
	 * Default constructor
	 */
	public MapRenderer()
	{
	}

	/**
	 * Render map
	 *
	 * @param pMap map for rendering
	 * @param pStyleViewer drawing styles, uses for rendering
	 */
	public void renderMap(Map pMap, StyleViewer pStyleViewer)
	{
		if (pMap == null || pStyleViewer == null)
			return;
		
		// clear canvas
		
		pMap.sortObjectsByDrawPriority(pStyleViewer);

		//MapObjectsRendererSeparatingText objectsRenderer = new MapObjectsRendererSeparatingText();
		//pMap.acceptObjectsRenderer(objectsRenderer);
		
		// draw objects canvas
		// draw text canvas
	}
}
