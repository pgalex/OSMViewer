package onlineMapTests;

import map.MapLine;
import rendering.RenderableMapObjectsVisitor;
import map.MapPoint;
import map.MapPolygon;
import rendering.RenderableMapPoint;

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
	public void visitLine(MapLine pLine)
	{
		linesRendered++;
	}

	@Override
	public void visitPolygon(MapPolygon pPolygon)
	{
		polygonsRendered++;
	}
}
