package onlineMapTests;

import map.*;
import onlineMap.OnlineMap;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author pgalex
 */
public class OnlineMapTest
{
	/**
	 * Mock class of objects renderer, using for testing
	 */
	private class MapObjectsRendererMock implements MapObjectsRenderer
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

	public OnlineMapTest()
	{
	}

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
	 * Renderer is null
	 */
	@Test
	public void renderingIncorrectRendererTest()
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

	@BeforeClass
	public static void setUpClass() throws Exception
	{
	}

	@AfterClass
	public static void tearDownClass() throws Exception
	{
	}
}
