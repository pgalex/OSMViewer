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
	 * Render all map objects, visible in area, with renderer
	 *
	 * @param objectsRenderer objects renderer
	 * @param renderingArea area, using to determine map objects that need to draw
	 * @throws IllegalArgumentException objectsRenderer or renderingArea is null
	 */
	public void rendersObjectInArea(MapObjectsRenderer objectsRenderer, MapBounds renderingArea) throws IllegalArgumentException;
}
