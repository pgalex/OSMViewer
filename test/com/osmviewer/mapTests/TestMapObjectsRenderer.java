package com.osmviewer.mapTests;

import com.osmviewer.map.MapObject;
import com.osmviewer.rendering.MapObjectsRenderer;

/**
 * Test implementation of MapObjectsRenderer
 *
 * @author preobrazhentsev
 */
public class TestMapObjectsRenderer implements MapObjectsRenderer
{
	public int renderedObjectsCount;

	public TestMapObjectsRenderer()
	{
		renderedObjectsCount = 0;
	}

	@Override
	public void renderMapObject(MapObject mapObjectToRender) throws IllegalArgumentException
	{
		renderedObjectsCount++;
	}

}
