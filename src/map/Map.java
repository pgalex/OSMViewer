package map;

import drawingStyles.StyleViewer;

/**
 * Interface of map - container of map objects
 *
 * @author pgalex
 */
public interface Map
{
	/**
	 * Sort all objects by draw priority
	 *
	 * @param pStyleViewer Style viewer to find object draw priority
	 */
	public abstract void sortObjectsByDrawPriority(StyleViewer pStyleViewer);

	/**
	 * Accept objects renderer visitor. Render every object of map
	 *
	 * @param pObjectsRenderer objects renderer
	 */
	public void acceptObjectsRenderer(MapObjectsRenderer pObjectsRenderer);
}
