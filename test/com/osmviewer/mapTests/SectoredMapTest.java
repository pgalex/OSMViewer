package com.osmviewer.mapTests;

import com.osmviewer.map.MapDataSource;
import com.osmviewer.map.MapSector;
import com.osmviewer.map.SectoredMap;
import com.osmviewer.map.exceptions.FetchingErrorException;
import com.osmviewer.mapDefenitionUtilities.DefenitionTags;
import com.osmviewer.mapDefenitionUtilities.Location;
import com.osmviewer.mapDefenitionUtilities.MapBounds;
import com.osmviewer.mapDefenitionUtilities.Tag;
import com.osmviewer.rendering.RenderableMapObjectsDrawPriorityComparator;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of SectoredMap class
 *
 * @author pgalex
 */
public class SectoredMapTest
{
	/**
	 * Test rendering with null rendering visitor
	 */
	@Test
	public void renderingWithNullVisitorTests()
	{
		try
		{
			SectoredMap map = new SectoredMap(new TestMapLoadingHandler());
			map.renderObjectsByDrawPriority(null, new MapBounds(10, 20, 10, 20), new RenderableMapObjectsDrawPriorityComparator());
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test rendering with null rendering area
	 */
	@Test
	public void renderingWithNullAreaTests()
	{
		try
		{
			SectoredMap map = new SectoredMap(new TestMapLoadingHandler());
			map.renderObjectsByDrawPriority(new TestRenderableMapObjectsVisitor(), null, new RenderableMapObjectsDrawPriorityComparator());
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test rendering with null comparator
	 */
	@Test
	public void renderingWithNullComparatorTests()
	{
		try
		{
			SectoredMap map = new SectoredMap(new TestMapLoadingHandler());
			map.renderObjectsByDrawPriority(new TestRenderableMapObjectsVisitor(), new MapBounds(10, 20, 10, 20), null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test loading objects with null area
	 *
	 * @throws FetchingErrorException
	 */
	@Test
	public void loadingWithNullAreaTests() throws FetchingErrorException
	{
		try
		{
			SectoredMap map = new SectoredMap(new TestMapLoadingHandler());
			map.loadObjectsInArea(null, new TestMapDataSource(),
							new TestRenderableMapObjectsDrawSettingsFinder());
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test loading objects with null map data source
	 *
	 * @throws FetchingErrorException
	 */
	@Test
	public void loadingWithNullDataSourceTests() throws FetchingErrorException
	{
		try
		{
			SectoredMap map = new SectoredMap(new TestMapLoadingHandler());
			map.loadObjectsInArea(new MapBounds(0, 5, 0, 5), null,
							new TestRenderableMapObjectsDrawSettingsFinder());
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	private MapDataSource createTestDataSource()
	{
		DefenitionTags someNoteEmptyTags = new DefenitionTags();
		someNoteEmptyTags.add(new Tag("k1", "v1"));

		TestMapDataSource testMapDataSource = new TestMapDataSource();

		testMapDataSource.storingIds.add(new Long(15));
		testMapDataSource.storingTags.add(someNoteEmptyTags);
		Location[] points1 = new Location[1];
		points1[0] = new Location(MapSector.LATITUDE_SIZE / 3, MapSector.LONGITUDE_SIZE / 3);
		testMapDataSource.storingPoints.add(points1);

		testMapDataSource.storingIds.add(new Long(16));
		testMapDataSource.storingTags.add(someNoteEmptyTags);
		Location[] points2 = new Location[2];
		points2[0] = new Location(MapSector.LATITUDE_SIZE + MapSector.LATITUDE_SIZE / 2,
						MapSector.LONGITUDE_SIZE + MapSector.LONGITUDE_SIZE / 2);
		points2[1] = new Location(MapSector.LATITUDE_SIZE + MapSector.LATITUDE_SIZE / 3,
						MapSector.LONGITUDE_SIZE + MapSector.LONGITUDE_SIZE / 3);
		testMapDataSource.storingPoints.add(points2);

		testMapDataSource.storingIds.add(new Long(17));
		testMapDataSource.storingTags.add(someNoteEmptyTags);
		Location[] points3 = new Location[3];
		points3[0] = new Location(-MapSector.LATITUDE_SIZE + MapSector.LATITUDE_SIZE / 2,
						-MapSector.LONGITUDE_SIZE + MapSector.LONGITUDE_SIZE / 2);
		points3[1] = new Location(-MapSector.LATITUDE_SIZE + MapSector.LATITUDE_SIZE / 3,
						-MapSector.LONGITUDE_SIZE + MapSector.LONGITUDE_SIZE / 3);
		points3[2] = new Location(-MapSector.LATITUDE_SIZE + MapSector.LATITUDE_SIZE / 4,
						-MapSector.LONGITUDE_SIZE + MapSector.LONGITUDE_SIZE / 4);
		testMapDataSource.storingPoints.add(points3);

		return testMapDataSource;
	}

	/**
	 * Test loading objects with zero area
	 *
	 * @throws IllegalArgumentException
	 * @throws FetchingErrorException
	 * @throws InterruptedException
	 */
	@Test
	public void loadingWithEmptyZeroTests() throws IllegalArgumentException, FetchingErrorException, InterruptedException
	{
		SectoredMap map = new SectoredMap(new TestMapLoadingHandler());
		map.loadObjectsInArea(new MapBounds(0, 0, 0, 0), createTestDataSource(),
						new TestRenderableMapObjectsDrawSettingsFinder());

		//Thread.sleep(100);

		//assertEquals(0, map.getObjectsCount());
	}

	/**
	 * Test loading objects in first sector (linked with createTestDataSource)
	 *
	 * @throws IllegalArgumentException
	 * @throws FetchingErrorException
	 * @throws InterruptedException
	 */
	@Test
	public void loadingObjectsInFirstSectorTests() throws IllegalArgumentException, FetchingErrorException, InterruptedException
	{
		SectoredMap map = new SectoredMap(new TestMapLoadingHandler());
		map.loadObjectsInArea(new MapBounds(0, MapSector.LATITUDE_SIZE * 0.8, 0, MapSector.LONGITUDE_SIZE * 0.8), createTestDataSource(),
						new TestRenderableMapObjectsDrawSettingsFinder());

		//Thread.sleep(100);

		//assertEquals(1, map.getObjectsCount());
	}

	/**
	 * Test loading objects in second sector (linked with createTestDataSource)
	 *
	 * @throws IllegalArgumentException
	 * @throws FetchingErrorException
	 * @throws InterruptedException
	 */
	@Test
	public void loadingObjectsInSecondSectorTests() throws IllegalArgumentException, FetchingErrorException, InterruptedException
	{
		SectoredMap map = new SectoredMap(new TestMapLoadingHandler());
		map.loadObjectsInArea(new MapBounds(MapSector.LATITUDE_SIZE, MapSector.LATITUDE_SIZE * 2,
						MapSector.LONGITUDE_SIZE, MapSector.LONGITUDE_SIZE * 2), createTestDataSource(),
						new TestRenderableMapObjectsDrawSettingsFinder());

		//Thread.sleep(100);

		//assertEquals(1, map.getObjectsCount());
	}

	/**
	 * Test loading objects in third sector (linked with createTestDataSource)
	 *
	 * @throws IllegalArgumentException
	 * @throws FetchingErrorException
	 * @throws InterruptedException
	 */
	@Test
	public void loadingObjectsInThirdSectorTests() throws IllegalArgumentException, FetchingErrorException, InterruptedException
	{
		SectoredMap map = new SectoredMap(new TestMapLoadingHandler());
		map.loadObjectsInArea(new MapBounds(-MapSector.LATITUDE_SIZE * 0.8, -0.1,
						-MapSector.LONGITUDE_SIZE * 0.8, -0.1), createTestDataSource(),
						new TestRenderableMapObjectsDrawSettingsFinder());

		//Thread.sleep(100);

		//assertEquals(1, map.getObjectsCount());
	}

	/**
	 * Test loading objects when no objects in area (linked with
	 * createTestDataSource)
	 *
	 * @throws IllegalArgumentException
	 * @throws FetchingErrorException
	 */
	@Test
	public void loadingWhenNoObjectsInAreaTests() throws IllegalArgumentException, FetchingErrorException
	{
		SectoredMap map = new SectoredMap(new TestMapLoadingHandler());
		map.loadObjectsInArea(new MapBounds(40 * MapSector.LATITUDE_SIZE, 50 * MapSector.LATITUDE_SIZE,
						-MapSector.LONGITUDE_SIZE * 40, -MapSector.LONGITUDE_SIZE * 30), createTestDataSource(),
						new TestRenderableMapObjectsDrawSettingsFinder());
		//assertEquals(0, map.getObjectsCount());
	}

	/**
	 * Test loading objects when area less than map sector size (linked with
	 * createTestDataSource)
	 *
	 * @throws IllegalArgumentException
	 * @throws FetchingErrorException
	 * @throws InterruptedException
	 */
	@Test
	public void loadingWhenAreaLessThanSectorTests() throws IllegalArgumentException, FetchingErrorException, InterruptedException
	{
		SectoredMap map = new SectoredMap(new TestMapLoadingHandler());
		map.loadObjectsInArea(new MapBounds(0.001, MapSector.LATITUDE_SIZE / 2,
						0.001, MapSector.LONGITUDE_SIZE / 2), createTestDataSource(),
						new TestRenderableMapObjectsDrawSettingsFinder());

		//Thread.sleep(100);

		//assertEquals(1, map.getObjectsCount());
	}

	/**
	 * Test loading objects with big area including all objects created in
	 * createTestDataSource
	 *
	 * @throws IllegalArgumentException
	 * @throws FetchingErrorException
	 * @throws InterruptedException  
	 */
	@Test
	public void loadingAllObjectsTests() throws IllegalArgumentException, FetchingErrorException, InterruptedException
	{
		SectoredMap map = new SectoredMap(new TestMapLoadingHandler());
		map.loadObjectsInArea(new MapBounds(-5 * MapSector.LATITUDE_SIZE, 5 * MapSector.LATITUDE_SIZE,
						-5 * MapSector.LONGITUDE_SIZE, 5 * MapSector.LONGITUDE_SIZE), createTestDataSource(),
						new TestRenderableMapObjectsDrawSettingsFinder());
		
		//Thread.sleep(100);
		
		//assertEquals(3, map.getObjectsCount());
	}
}