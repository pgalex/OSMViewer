package com.osmviewer.rendering;

import com.osmviewer.mapDefenitionUtilities.MapBounds;

/**
 * Map that can be render
 *
 * @author pgalex
 */
public interface RenderableMap
{
	/**
	 * Render map objects, that visible in area, with given renderer
	 *
	 * @param mapObjectsRenderer objects renderer. Must be not null
	 * @param renderingArea area to determine which object need to render. Must be
	 * not null
	 * @param objectsDrawPriorityComparator comparator for sorting rendering
	 * objects by its draw priority. Must ne not null
	 * @throws IllegalArgumentException mapObjectsRenderer, renderingArea or
	 * objectsDrawPriorityComparator is null
	 */
	public void renderObjectsByDrawPriority(MapObjectsRenderer mapObjectsRenderer,
					MapBounds renderingArea, RenderableMapObjectsDrawPriorityComparator objectsDrawPriorityComparator) throws IllegalArgumentException;
}
