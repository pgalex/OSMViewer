package com.osmviewer.onlineMapTests;

import com.osmviewer.map.MapPoint;
import com.osmviewer.map.MapLine;
import com.osmviewer.map.MapPolygon;
import com.osmviewer.drawingStyles.DrawingStylesFactory;
import com.osmviewer.drawingStyles.StandartMapObjectDrawSettings;
import com.osmviewer.drawingStyles.DrawSettingsViewer;
import com.osmviewer.map.onlineMap.OnlineMap;
import com.osmviewer.mapDefenitionUtilities.DefenitionTags;
import com.osmviewer.mapDefenitionUtilities.MapBounds;
import com.osmviewer.mapDefenitionUtilities.MapPosition;
import com.osmviewer.mapDefenitionUtilities.Tag;
import static org.junit.Assert.*;
import org.junit.Test;
import com.osmviewer.rendering.RenderableMapObjectsDrawPriorityComparator;

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
		DefenitionTags someTags = new DefenitionTags();
		someTags.add(new Tag("k1", "v1"));

		StandartMapObjectDrawSettings style1 = new StandartMapObjectDrawSettings();
		style1.setCanBePoint();
		style1.setCanBeLine();
		style1.setCanBePolygon();
		style1.setDefenitionTags(someTags);

		MapPosition[] somePoints = new MapPosition[4];
		somePoints[0] = new MapPosition(1, 2);
		somePoints[1] = new MapPosition(3, 4);
		somePoints[2] = new MapPosition(5, 6);
		somePoints[3] = somePoints[0];

		MapLine line1 = new MapLine(0, someTags, somePoints);
		line1.setDrawSettings(style1);

		MapPoint point1 = new MapPoint(new MapPosition(), 1, someTags);
		point1.setDrawSettings(style1);

		MapPolygon polygon1 = new MapPolygon(0, someTags, somePoints);
		polygon1.setDrawSettings(style1);

		MapPoint point2 = new MapPoint(new MapPosition(), 1, someTags);
		point2.setDrawSettings(style1);

		MapPoint point3 = new MapPoint(new MapPosition(), 1, someTags);
		point3.setDrawSettings(style1);

		OnlineMap testMap = new OnlineMap();
		testMap.addObject(line1);
		testMap.addObject(point1);
		testMap.addObject(polygon1);
		testMap.addObject(point2);
		testMap.addObject(point3);

		MapObjectsRendererMock objectsRendererMock = new MapObjectsRendererMock();
		testMap.renderObjectsByDrawPriority(objectsRendererMock, new MapBounds(-10, 10, -10, 10),
						new RenderableMapObjectsDrawPriorityComparator());

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
			DrawSettingsViewer viewer = DrawingStylesFactory.createStandartDrawSettingsViewer();
			testMap.renderObjectsByDrawPriority(null, new MapBounds(1, 2, 3, 4),
							new RenderableMapObjectsDrawPriorityComparator());
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
			DrawSettingsViewer viewer = DrawingStylesFactory.createStandartDrawSettingsViewer();
			testMap.renderObjectsByDrawPriority(new MapObjectsRendererMock(), null,
							new RenderableMapObjectsDrawPriorityComparator());
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}
}
