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
	 * Accept visitor for all map objects visible in area. Object should be given
	 * to objectsVisitor by its draw priority
	 *
	 * @param objectsRenderingVisitor objects renderer
	 * @param renderingArea area to determine which object need give to
	 * objectsVisitor
	 * @param objectsDrawPriorityComparator comparator for sorting rendering
	 * objects by its draw priority
	 * @throws IllegalArgumentException objectsVisitor, area or
	 * objectsDrawPriorityComparator is null
	 */
	public void renderObjectsByDrawPriority(RenderableMapObjectsVisitor objectsRenderingVisitor,
					MapBounds renderingArea, RenderableMapObjectsDrawPriorityComparator objectsDrawPriorityComparator) throws IllegalArgumentException;
}
