package com.osmviewer.onlineMapTests;

import com.osmviewer.map.MapLine;
import com.osmviewer.rendering.RenderableMapObjectsVisitor;
import com.osmviewer.map.MapPoint;
import com.osmviewer.map.MapPolygon;
import com.osmviewer.rendering.RenderableMapLine;
import com.osmviewer.rendering.RenderableMapPoint;
import com.osmviewer.rendering.RenderableMapPolygon;

/**
 * Mock class of objects renderer, using for testing
 *
 * @author pgalex
 */
public class MapObjectsRendererMock implements RenderableMapObjectsVisitor
{
	public int pointsRendered;
	public int linesRendered;
	public int polygonsRendered;

	public MapObjectsRendererMock()
	{
		pointsRendered = 0;
		linesRendered = 0;
		polygonsRendered = 0;
	}

	@Override
	public void visitPoint(RenderableMapPoint pPoint)
	{
		pointsRendered++;
	}

	@Override
	public void visitLine(RenderableMapLine pLine)
	{
		linesRendered++;
	}

	@Override
	public void visitPolygon(RenderableMapPolygon pPolygon)
	{
		polygonsRendered++;
	}
}
