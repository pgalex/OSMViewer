package com.osmviewer.mapTests;

import com.osmviewer.rendering.RenderableMapObject;
import com.osmviewer.rendering.RenderableMapObjectsDrawPriorityComparator;

/**
 * Test implementation of RenderableMapObjectsDrawPriorityComparator
 *
 * @author preobrazhentsev
 */
public class TestRenderableMapObjectsDrawPriorityComparator implements RenderableMapObjectsDrawPriorityComparator
{

	@Override
	public int compare(RenderableMapObject o1, RenderableMapObject o2)
	{
		return 0;
	}

}
