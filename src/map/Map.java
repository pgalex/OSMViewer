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
	 * @param styleViewer Style viewer to find object draw priority
	 */
	public void sortObjectsByDrawPriority(StyleViewer styleViewer);

	/**
	 * Accept objects objectsRenderer visitor. Render every object of map
	 *
	 * @param objectsRenderer objects objectsRenderer
	 */
	public void acceptObjectsRenderer(MapObjectsRenderer objectsRenderer);
}
