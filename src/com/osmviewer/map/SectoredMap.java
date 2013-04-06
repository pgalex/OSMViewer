package com.osmviewer.map;

import com.osmviewer.mapDefenitionUtilities.MapBounds;
import com.osmviewer.rendering.RenderableMap;
import com.osmviewer.rendering.RenderableMapObjectsDrawPriorityComparator;
import com.osmviewer.rendering.RenderableMapObjectsVisitor;

/**
 * Map, storing and processings map objects by sectors
 *
 * @author pgalex
 */
public class SectoredMap implements RenderableMap
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
	@Override
	public void renderObjectsByDrawPriority(RenderableMapObjectsVisitor objectsRenderingVisitor, MapBounds renderingArea, RenderableMapObjectsDrawPriorityComparator objectsDrawPriorityComparator) throws IllegalArgumentException
	{
		if (objectsRenderingVisitor == null)
		{
			throw new IllegalArgumentException("objectsRenderingVisitor is null");
		}
		if (renderingArea == null)
		{
			throw new IllegalArgumentException("renderingArea is null");
		}
		if (objectsDrawPriorityComparator == null)
		{
			throw new IllegalArgumentException("objectsDrawPriorityComparator is null");
		}

		if (renderingArea.isZero())
		{
			return;
		}
	}
}
