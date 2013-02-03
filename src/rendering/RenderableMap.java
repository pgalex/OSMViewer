package rendering;

import map.MapBounds;

/**
 * Interface of map that can be render
 *
 * @author pgalex
 */
public interface RenderableMap
{
	/**
	 * Accept visitor for all map objects visible in area. Object should be given
	 * to objectsVisitor by its draw priority
	 *
	 * @param objectsVisitor objects renderer
	 * @param area area to determine which object need give to objectsVisitor
	 * @param objectsDrawPriorityComparator comparator for sorting rendering
	 * objects by its draw priority
	 * @throws IllegalArgumentException objectsVisitor, area or
	 * objectsDrawPriorityComparator is null
	 */
	public void renderObjectsByDrawPriority(RenderableMapObjectsVisitor objectsVisitor, MapBounds area,
					RenderableMapObjectsDrawPriorityComparator objectsDrawPriorityComparator) throws IllegalArgumentException;
}
