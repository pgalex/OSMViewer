package onlineMapTests;

import drawingStyles.DefenitionTags;
import drawingStyles.DrawingStylesFactory;
import drawingStyles.EditableDefenitionTags;
import drawingStyles.MapObjectDrawSettings;
import drawingStyles.MapTag;
import drawingStyles.StyleEditor;
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
		EditableDefenitionTags someTags = new EditableDefenitionTags();
		someTags.add(new MapTag("k1", "v1"));

		StyleEditor testEditor = DrawingStylesFactory.createStyleEditor();

		MapObjectDrawSettings style1 = new MapObjectDrawSettings();
		style1.setCanBePoint();
		style1.setCanBeLine();
		style1.setCanBePolygon();
		style1.setDefenitionTags(someTags);
		testEditor.addMapObjectDrawSettings(style1);

		MapPosition[] somePoints = new MapPosition[3];
		somePoints[0] = new MapPosition();
		somePoints[1] = new MapPosition();
		somePoints[2] = new MapPosition();

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
