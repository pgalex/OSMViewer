package onlineMapTests;

import drawingStyles.DefenitionTags;
import drawingStyles.DrawingStylesFactory;
import drawingStyles.MapObjectDrawSettings;
import drawingStyles.StyleEditor;
import drawingStyles.Tag;
import map.*;
import map.onlineMap.OnlineMap;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * OnlineMap class tests
 *
 * @author pgalex
 */
public class OnlineMapTest
{
	/**
	 * Test adding object without style index
	 */
	@Test
	public void addingMapObjectWithoutStyleIndexTest()
	{
		MapPoint pointWithoutStyleIndex = new MapPoint(new MapPosition(), 1, new DefenitionTags());

		TestOnlineMap testMap = new TestOnlineMap();
		testMap.addObject(pointWithoutStyleIndex);

		assertTrue(testMap.getObjects().isEmpty());
	}

	/**
	 * Test rendering visitor work, normal work
	 */
	@Test
	public void renderingNormalWorkTest()
	{
		DefenitionTags someTags = new DefenitionTags();
		someTags.add(new Tag("k1", "v1"));

		StyleEditor testEditor = DrawingStylesFactory.createStyleEditor();

		MapObjectDrawSettings style1 = new MapObjectDrawSettings();
		style1.setCanBePoint();
		style1.setCanBeLine();
		style1.setCanBePolygon();
		style1.setDefenitionTags(someTags);
		testEditor.addMapObjectDrawSettings(style1);

		MapPosition[] somePoints = new MapPosition[4];
		somePoints[0] = new MapPosition(1, 2);
		somePoints[1] = new MapPosition(3, 4);
		somePoints[2] = new MapPosition(5, 6);
		somePoints[3] = somePoints[0];

		MapLine line1 = new MapLine(0, someTags, somePoints);
		line1.assignStyleIndex(testEditor);

		MapPoint point1 = new MapPoint(new MapPosition(), 1, someTags);
		point1.assignStyleIndex(testEditor);

		MapPolygon polygon1 = new MapPolygon(0, someTags, somePoints);
		polygon1.assignStyleIndex(testEditor);

		MapPoint point2 = new MapPoint(new MapPosition(), 1, someTags);
		point2.assignStyleIndex(testEditor);

		MapPoint point3 = new MapPoint(new MapPosition(), 1, someTags);
		point3.assignStyleIndex(testEditor);

		OnlineMap testMap = new OnlineMap();
		testMap.addObject(line1);
		testMap.addObject(point1);
		testMap.addObject(polygon1);
		testMap.addObject(point2);
		testMap.addObject(point3);

		MapObjectsRendererMock objectsRendererMock = new MapObjectsRendererMock();
		testMap.rendersObjectInArea(objectsRendererMock, new MapBounds(-10, 10, -10, 10));

		assertEquals(3, objectsRendererMock.pointsRendered);
		assertEquals(1, objectsRendererMock.linesRendered);
		assertEquals(1, objectsRendererMock.polygonsRendered);
	}

	/**
	 * Rendering with null renderer test
	 */
	@Test
	public void renderingWithNullRendererTest()
	{
		OnlineMap testMap = new OnlineMap();
		try
		{
			testMap.rendersObjectInArea(null, new MapBounds(1, 2, 3, 4));
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Renderering with null area test
	 */
	@Test
	public void renderingWithNullRenderingAreaTest()
	{
		OnlineMap testMap = new OnlineMap();
		try
		{
			testMap.rendersObjectInArea(new MapObjectsRendererMock(), null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}
}
