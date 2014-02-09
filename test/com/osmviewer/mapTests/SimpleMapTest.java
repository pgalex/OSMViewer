package com.osmviewer.mapTests;

import com.osmviewer.map.SimpleMap;
import com.osmviewer.map.exceptions.FetchingErrorException;
import com.osmviewer.mapDefenitionUtilities.Location;
import com.osmviewer.mapDefenitionUtilities.MapBounds;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of SimpleMap class
 *
 * @author preobrazhentsev
 */
public class SimpleMapTest
{
	private final Location[] somePoints;

	public SimpleMapTest()
	{
		somePoints = new Location[4];
		somePoints[0] = new Location(1, 2);
		somePoints[1] = new Location(3, 4);
		somePoints[2] = new Location(5, 6);
		somePoints[3] = new Location(7, 8);
	}

	@Test
	public void loadingWithNullArea() throws FetchingErrorException
	{
		SimpleMap map = new SimpleMap();
		try
		{
			map.loadObjectsInArea(null, new TestMapDataSource());
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	@Test
	public void loadingWithNullDataSource() throws FetchingErrorException
	{
		SimpleMap map = new SimpleMap();
		try
		{
			map.loadObjectsInArea(new MapBounds(), null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	@Test
	public void loadingNormalWork() throws FetchingErrorException
	{

		TestMapDataSource testMapDataSource = new TestMapDataSource();
		testMapDataSource.drawingIds.add("1");
		testMapDataSource.drawingIds.add("2");
		testMapDataSource.drawingIds.add("3");

		testMapDataSource.uniquesIds.add(1);
		testMapDataSource.uniquesIds.add(2);
		testMapDataSource.uniquesIds.add(3);

		testMapDataSource.points.add(somePoints);
		testMapDataSource.points.add(somePoints);
		testMapDataSource.points.add(somePoints);

		SimpleMap map = new SimpleMap();
		map.loadObjectsInArea(new MapBounds(1, 2, 10, 15), testMapDataSource);
		assertEquals(3, map.getStoringObjectsCount());
	}

	@Test
	public void takeMapObjectDataNullDrawingId() throws FetchingErrorException
	{
		SimpleMap map = new SimpleMap();
		try
		{
			map.takeMapObjectData(0, null, somePoints);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	@Test
	public void takeMapObjectDataNullPoints() throws FetchingErrorException
	{
		SimpleMap map = new SimpleMap();
		try
		{
			map.takeMapObjectData(0, "1", null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	@Test
	public void takeMapObjectDataEmptyPoints() throws FetchingErrorException
	{
		SimpleMap map = new SimpleMap();
		try
		{
			map.takeMapObjectData(0, "1", new Location[0]);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	@Test
	public void takeMapObjectDataPointsContainsNull() throws FetchingErrorException
	{
		Location[] pointsContainsNull = new Location[3];
		pointsContainsNull[0] = new Location(5, 10);
		pointsContainsNull[1] = null;
		pointsContainsNull[2] = new Location(15, 16);

		SimpleMap map = new SimpleMap();
		try
		{
			map.takeMapObjectData(0, "1", pointsContainsNull);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	@Test
	public void renderingNullRenderer() throws FetchingErrorException
	{
		SimpleMap map = new SimpleMap();
		try
		{
			map.renderObjectsByDrawPriority(null, new MapBounds(), new TestRenderableMapObjectsDrawPriorityComparator());
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	@Test
	public void renderingNullArea() throws FetchingErrorException
	{
		SimpleMap map = new SimpleMap();
		try
		{
			map.renderObjectsByDrawPriority(new TestMapObjectsRenderer(), null, new TestRenderableMapObjectsDrawPriorityComparator());
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	@Test
	public void renderingNullComparator() throws FetchingErrorException
	{
		SimpleMap map = new SimpleMap();
		try
		{
			map.renderObjectsByDrawPriority(new TestMapObjectsRenderer(), new MapBounds(), null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	@Test
	public void renderingNormalWork() throws FetchingErrorException
	{
		SimpleMap map = new SimpleMap();
		map.takeMapObjectData(0, "0", somePoints);
		map.takeMapObjectData(1, "1", somePoints);
		map.takeMapObjectData(2, "2", somePoints);

		TestMapObjectsRenderer testMapObjectsRenderer = new TestMapObjectsRenderer();
		map.renderObjectsByDrawPriority(testMapObjectsRenderer, new MapBounds(-180, 180, -90, 90), new TestRenderableMapObjectsDrawPriorityComparator());
		assertEquals(map.getStoringObjectsCount(), testMapObjectsRenderer.renderedObjectsCount);
	}
}
