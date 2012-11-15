package onlineMapTests;

import map.MapLine;
import map.MapObjectsRenderer;
import map.MapPoint;
import map.MapPolygon;

/**
 * Mock class of objects renderer, using for testing
 *
 * @author pgalex
 */
public class MapObjectsRendererMock implements MapObjectsRenderer
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
	public void renderPoint(MapPoint pPoint)
	{
		pointsRendered++;
	}

	@Override
	public void renderLine(MapLine pLine)
	{
		linesRendered++;
	}

	@Override
	public void renderPolygon(MapPolygon pPolygon)
	{
		polygonsRendered++;
	}
}
