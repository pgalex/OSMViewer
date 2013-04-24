package com.osmviewer.mapTests;

import com.osmviewer.map.MapLine;
import com.osmviewer.map.MapObject;
import com.osmviewer.map.MapPoint;
import com.osmviewer.map.MapPolygon;
import com.osmviewer.mapDefenitionUtilities.DefenitionTags;
import com.osmviewer.mapDefenitionUtilities.MapBounds;
import com.osmviewer.mapDefenitionUtilities.Location;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * MapPoint class tests
 *
 * @author pgalex
 */
public class MapPointTest
{
	/**
	 * Test setting id after creating
	 */
	@Test
	public void creatingWithIdTest()
	{
		MapPoint point1 = new MapPoint(new Location(), 12, new DefenitionTags());
		assertEquals(12, point1.getId());
	}

	/**
	 * Test MapPoint equals to other MapPoint object and their ids are same
	 */
	@Test
	public void equalsToOtherPointSameIdObjectTest()
	{
		MapPoint point1 = new MapPoint(new Location(), 12, new DefenitionTags());
		MapPoint point2 = new MapPoint(new Location(), 12, new DefenitionTags());
		assertTrue(point1.equals(point2));
	}

	/**
	 * Test MapPoint equals to other MapPoint object and their ids are not same
	 */
	@Test
	public void equalsToOtherPointNotSameIdObjectTest()
	{
		MapPoint point1 = new MapPoint(new Location(), 12, new DefenitionTags());
		MapPoint point2 = new MapPoint(new Location(), 8, new DefenitionTags());
		assertFalse(point1.equals(point2));
	}

	/**
	 * Test MapPoint equals to other MapLine object and their ids are same
	 */
	@Test
	public void equalsToMapLineSameIdObjectTest()
	{
		Location[] points = new Location[2];
		points[0] = new Location(0, 0);
		points[1] = new Location(5, 5);
		MapLine testLine = new MapLine(12, new DefenitionTags(), points);

		MapPoint testPoint = new MapPoint(new Location(), 12, new DefenitionTags());

		assertFalse(testPoint.equals((MapObject) testLine));
	}

	/**
	 * Test MapPoint equals to other MapPolygon object and their ids are same
	 */
	@Test
	public void equalsToMapPolygonSameIdObjectTest()
	{
		Location[] points = new Location[5];
		points[0] = new Location(-1, -2);
		points[1] = new Location(1, 0);
		points[2] = new Location(3, 3);
		points[3] = new Location(7, 7);
		points[4] = points[0];
		MapPolygon testPolygon = new MapPolygon(12, new DefenitionTags(), points);

		MapPoint testPoint = new MapPoint(new Location(), 12, new DefenitionTags());

		assertFalse(testPoint.equals((MapObject) testPolygon));
	}

	/**
	 * Test determining draw priotiry of draw settings not set
	 */
	@Test
	public void determineDrawPriorityWithNullDrawSettingsTest()
	{
		try
		{
			MapPoint testPoint = new MapPoint(new Location(), 12, new DefenitionTags());
			testPoint.determineDrawPriotity();
			fail();
		}
		catch (NullPointerException ex)
		{
			// ok
		}
	}

	/**
	 * Test determining draw priotiry with draw settings
	 */
	@Test
	public void determiningDrawPriorityNormalWorkTest()
	{
		MapPoint testPoint = new MapPoint(new Location(), 12, new DefenitionTags());
		TestRenderableMapObjectDrawSettings testDawSettings = new TestRenderableMapObjectDrawSettings();
		testDawSettings.pointDrawPriority = 5;
		testPoint.setDrawSettings(testDawSettings);

		assertEquals(testDawSettings.pointDrawPriority, testPoint.determineDrawPriotity());
	}

	/**
	 * Testing creating with null position
	 */
	@Test
	public void creatingWithNullPositionTest()
	{
		try
		{
			MapPoint testPoint = new MapPoint(null, 12, new DefenitionTags());
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Testing creating with null tags
	 */
	@Test
	public void creatingWithNullTagsTest()
	{
		try
		{
			MapPoint testPoint = new MapPoint(new Location(), 12, null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Testing accept render when render is null
	 */
	@Test
	public void acceptNullRendererViewerTest()
	{
		try
		{
			MapPoint testPoint = new MapPoint(new Location(), 12, new DefenitionTags());
			testPoint.acceptRenderingVisitor(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Testing is visible in null area
	 */
	@Test
	public void isVisibleInNullAreaTest()
	{
		try
		{
			MapPoint testPoint = new MapPoint(new Location(0, 0), 0, new DefenitionTags());
			testPoint.isVisibleInArea(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Testing is visible in null area
	 */
	@Test
	public void isVisibleInZeroAreaTest()
	{
		MapPoint testPoint = new MapPoint(new Location(5, 5), 0, new DefenitionTags());
		MapBounds testArea = new MapBounds(5, 5, 5, 5);
		assertFalse(testPoint.isVisibleInArea(testArea));
	}

	/**
	 * Testing is visible if point in area; bounds more than zero
	 */
	@Test
	public void isVisiblePointInAreaMoreThanZeroTest()
	{
		MapPoint testPoint = new MapPoint(new Location(5, 5), 0, new DefenitionTags());
		MapBounds testArea = new MapBounds(3, 8, 3, 8);
		assertTrue(testPoint.isVisibleInArea(testArea));
	}

	/**
	 * Testing is visible if point in area; bounds less than zero
	 */
	@Test
	public void isVisiblePointInAreaLessThanZeroTest()
	{
		MapPoint testPoint = new MapPoint(new Location(-5, -5), 0, new DefenitionTags());
		MapBounds testArea = new MapBounds(-6, -4, -6, -4);
		assertTrue(testPoint.isVisibleInArea(testArea));
	}

	/**
	 * Testing is visible if point in area; bounds less than zero
	 */
	@Test
	public void isVisiblePointOnMinimumCornerTest()
	{
		MapPoint testPoint = new MapPoint(new Location(3, 4), 0, new DefenitionTags());
		MapBounds testArea = new MapBounds(3, 5, 4, 7);
		assertTrue(testPoint.isVisibleInArea(testArea));
	}

	/**
	 * Testing is visible if point in area; bounds less than zero
	 */
	@Test
	public void isVisiblePointOnMaximumCornerTest()
	{
		MapPoint testPoint = new MapPoint(new Location(5, 7), 0, new DefenitionTags());
		MapBounds testArea = new MapBounds(3, 5, 4, 7);
		assertFalse(testPoint.isVisibleInArea(testArea));
	}

	/**
	 * Testing is visible if point in area; bounds more than zero
	 */
	@Test
	public void isVisiblePointNotInAreaMoreThanZeroTest()
	{
		MapPoint testPoint = new MapPoint(new Location(2, 10), 0, new DefenitionTags());
		MapBounds testArea = new MapBounds(3, 8, 3, 8);
		assertFalse(testPoint.isVisibleInArea(testArea));
	}

	/**
	 * Testing is visible if point in area; bounds less than zero
	 */
	@Test
	public void isVisiblePointNotInAreaLessThanZeroTest()
	{
		MapPoint testPoint = new MapPoint(new Location(-7, -7), 0, new DefenitionTags());
		MapBounds testArea = new MapBounds(-6, -4, -6, -4);
		assertFalse(testPoint.isVisibleInArea(testArea));
	}
}
