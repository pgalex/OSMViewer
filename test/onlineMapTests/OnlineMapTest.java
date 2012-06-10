package onlineMapTests;

import map.MapLine;
import map.MapPoint;
import map.MapPolygon;
import map.MapPosition;
import onlineMap.OnlineMap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 * OnlineMap class tests
 *
 * @author pgalex
 */
public class OnlineMapTest
{
	/**
	 * Test rendering visitor work, normal work
	 */
	@Test
	public void renderingNormalWorkTest()
	{
		MapPosition[] somePoints = new MapPosition[2];
		somePoints[0] = new MapPosition();
		somePoints[1] = new MapPosition();

		OnlineMap testMap = new OnlineMap();
		testMap.addObject(new MapLine(0, null, somePoints));
		testMap.addObject(new MapPoint(new MapPosition(), 1, null));
		testMap.addObject(new MapPolygon(0, null, somePoints));
		testMap.addObject(new MapPoint(new MapPosition(), 1, null));
		testMap.addObject(new MapPoint(new MapPosition(), 1, null));

		MapObjectsRendererMock objectsRendererMock = new MapObjectsRendererMock();
		testMap.acceptObjectsRenderer(objectsRendererMock);

		assertEquals(3, objectsRendererMock.pointsRendered);
		assertEquals(1, objectsRendererMock.linesRendered);
		assertEquals(1, objectsRendererMock.polygonsRendered);
	}

	/**
	 * Renderer is null. Should not be any exception
	 */
	@Test
	public void renderingWithNullRendererTest()
	{
		OnlineMap testMap = new OnlineMap();
		try
		{
			testMap.acceptObjectsRenderer(null);
			// ok
		}
		catch (Exception ex)
		{
			fail();
		}
	}
}
