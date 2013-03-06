package com.osmviewer.mapTests;

import com.osmviewer.map.MapPoint;
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
