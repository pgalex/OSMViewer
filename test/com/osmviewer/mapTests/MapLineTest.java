package com.osmviewer.mapTests;

import com.osmviewer.map.MapLine;
import com.osmviewer.map.MapPoint;
import com.osmviewer.mapDefenitionUtilities.DefenitionTags;
import com.osmviewer.mapDefenitionUtilities.MapBounds;
import com.osmviewer.mapDefenitionUtilities.Location;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * MapLine class tests
 *
 * @author pgalex
 */
public class MapLineTest
{
	/**
	 * Test determining draw priotiry of draw settings not set
	 */
	@Test
	public void determineDrawPriorityWithNullDrawSettingsTest()
	{
		try
		{
			Location[] points = new Location[2];
			points[0] = new Location(1, 2);
			points[1] = new Location(2, 3);
			MapLine testLine = new MapLine(0, new DefenitionTags(), points);
			testLine.determineDrawPriotity();
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

		Location[] points = new Location[2];
		points[0] = new Location(1, 2);
		points[1] = new Location(2, 3);
		MapLine testLine = new MapLine(0, new DefenitionTags(), points);

		TestRenderableMapObjectDrawSettings testDawSettings = new TestRenderableMapObjectDrawSettings();
		testDawSettings.lineDrawPriority = 3;
		testLine.setDrawSettings(testDawSettings);

		assertEquals(testDawSettings.lineDrawPriority, testLine.determineDrawPriotity());
	}

	/**
	 * Test getting point with index less than bounds
	 */
	@Test
	public void getPointIndexLessThanBoundTest()
	{
		try
		{
			Location[] points = new Location[3];
			points[0] = new Location(1, 2);
			points[1] = new Location(2, 3);
			MapLine testLine = new MapLine(0, new DefenitionTags(), points);
			testLine.getPoint(-1);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test getting point with index more than bounds
	 */
	@Test
	public void getPointIndexMoreThanBoundTest()
	{
		try
		{
			Location[] points = new Location[3];
			points[0] = new Location(1, 2);
			points[1] = new Location(2, 3);
			MapLine testLine = new MapLine(0, new DefenitionTags(), points);
			testLine.getPoint(points.length);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Creating MapLine with null tags
	 */
	@Test
	public void creatingWithNullTagsTest()
	{
		try
		{
			Location[] points = new Location[2];
			points[0] = new Location(1, 2);
			points[1] = new Location(2, 3);
			MapLine testLine = new MapLine(0, null, points);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Creating MapLine with null points
	 */
	@Test
	public void creatingWithNullPointsTest()
	{
		try
		{
			MapLine testLine = new MapLine(11, new DefenitionTags(), null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Creating MapLine by points contaning null elemetets
	 */
	@Test
	public void creatingWithOnePointTest()
	{
		try
		{
			Location[] points = new Location[1];
			points[0] = new Location();
			MapLine testLine = new MapLine(10, new DefenitionTags(), points);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Creating MapLine by points contaning null elemetets
	 */
	@Test
	public void creatingWithPointsContainsNullTest()
	{
		try
		{
			Location[] points = new Location[3];
			points[0] = new Location();
			points[1] = null;
			points[2] = new Location();
			MapLine testLine = new MapLine(10, new DefenitionTags(), points);
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
			Location[] points = new Location[2];
			points[0] = new Location(1, 2);
			points[1] = new Location(2, 3);
			MapLine testLine = new MapLine(0, new DefenitionTags(), points);
			testLine.acceptRenderingVisitor(null);
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
			Location[] points = new Location[2];
			points[0] = new Location(1, 2);
			points[1] = new Location(2, 3);
			MapLine testLine = new MapLine(1, new DefenitionTags(), points);
			testLine.isVisibleInArea(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test is visible - all line point inside area
	 */
	@Test
	public void isVisibleAllPointInAreaTest()
	{
		Location[] points = new Location[3];
		points[0] = new Location(-2, 2);
		points[1] = new Location(5, -5);
		points[2] = new Location(3, 5);
		MapLine testLine = new MapLine(1, new DefenitionTags(), points);
		MapBounds testArea = new MapBounds(-5, 7, -8, 10);
		assertTrue(testLine.isVisibleInArea(testArea));
	}

	/**
	 * Test is visible - all line point surround area, and line not intersect area
	 */
	@Test
	public void isVisibleAllPointSurroundAreaNotIntersectTest()
	{
		Location[] points = new Location[4];
		points[0] = new Location(-7, -9);
		points[1] = new Location(-7, 9);
		points[2] = new Location(7, 9);
		points[3] = new Location(7, -9);
		MapLine testLine = new MapLine(1, new DefenitionTags(), points);
		MapBounds testArea = new MapBounds(-5, 5, -7, 7);
		assertFalse(testLine.isVisibleInArea(testArea));
	}

	/**
	 * Test is visible - all line point outside area, and line intersect area
	 */
	@Test
	public void isVisibleAllPointOutAreaIntersectTest()
	{
		Location[] points = new Location[2];
		points[0] = new Location(0, 0);
		points[1] = new Location(5, 5);
		MapLine testLine = new MapLine(1, new DefenitionTags(), points);
		MapBounds testArea = new MapBounds(1, 4, 1, 4);
		assertTrue(testLine.isVisibleInArea(testArea));
	}

	/**
	 * Test is visible - some line point outside area, and line intersect area
	 */
	@Test
	public void isVisibleSomePointOutAreaIntersectTest()
	{
		Location[] points = new Location[4];
		points[0] = new Location(-1, -1);
		points[1] = new Location(2, 2);
		points[2] = new Location(2, 10);
		points[3] = new Location(3, 3);
		MapLine testLine = new MapLine(1, new DefenitionTags(), points);
		MapBounds testArea = new MapBounds(0, 5, 0, 5);
		assertTrue(testLine.isVisibleInArea(testArea));
	}
}
