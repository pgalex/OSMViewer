package com.osmviewer.renderingTests;

import com.osmviewer.mapDefenitionUtilities.MapBounds;
import com.osmviewer.rendering.RenderableMap;
import com.osmviewer.rendering.RenderableMapObjectsDrawPriorityComparator;
import com.osmviewer.rendering.RenderableMapObjectsVisitor;

/**
 * Fake implementation of renderable map
 *
 * @author pgalex
 */
public class TestRenderableMap implements RenderableMap
{
	@Override
	public void renderObjectsByDrawPriority(RenderableMapObjectsVisitor objectsVisitor,
					MapBounds area, RenderableMapObjectsDrawPriorityComparator objectsDrawPriorityComparator) throws IllegalArgumentException
	{
	}
}
