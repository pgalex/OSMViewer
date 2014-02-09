package com.osmviewer.mapTests;

import com.osmviewer.map.MapObject;
import com.osmviewer.mapDefenitionUtilities.Location;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of map object class
 *
 * @author preobrazhentsev
 */
public class MapObjectTest
{
	private final Location[] someCorrectPoints;

	public MapObjectTest()
	{
		this.someCorrectPoints = new Location[4];
		this.someCorrectPoints[0] = new Location(5, 10);
		this.someCorrectPoints[1] = new Location(2, 4);
		this.someCorrectPoints[2] = new Location(15, 16);
		this.someCorrectPoints[3] = new Location(1, 2);
	}

	@Test
	public void creatingWithNullDrawingId()
	{
		try
		{
			MapObject mapObject = new MapObject(null, someCorrectPoints);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	@Test
	public void creatingWithNullPoint()
	{
		try
		{
			MapObject mapObject = new MapObject("abc", null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	@Test
	public void creatingWithEmptyPoints()
	{
		try
		{
			MapObject mapObject = new MapObject("abc", new Location[0]);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	@Test
	public void creatingWithPointsContainsNull()
	{
		Location[] pointsContainsNull = new Location[3];
		pointsContainsNull[0] = new Location(5, 10);
		pointsContainsNull[1] = null;
		pointsContainsNull[2] = new Location(15, 16);
		try
		{
			MapObject mapObject = new MapObject("abc", pointsContainsNull);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	@Test
	public void creatingWithCorrectParameters()
	{
		MapObject mapObject = new MapObject("abc", someCorrectPoints);

		assertEquals("abc", mapObject.getDrawingId());
		assertEquals(someCorrectPoints.length, mapObject.getPointsCount());
		//testing that array index-value pair are equals
		assertEquals(someCorrectPoints[0], mapObject.getPoint(0));
		assertEquals(someCorrectPoints[someCorrectPoints.length - 1], mapObject.getPoint(mapObject.getPointsCount() - 1));
	}
}
