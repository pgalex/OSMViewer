package com.osmviewer.mapTests;

import com.osmviewer.map.MapSector;
import com.osmviewer.map.exceptions.FetchingErrorException;
import com.osmviewer.mapDefenitionUtilities.DefenitionTags;
import com.osmviewer.mapDefenitionUtilities.Location;
import com.osmviewer.mapDefenitionUtilities.MapBounds;
import com.osmviewer.mapDefenitionUtilities.Tag;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of MapSector class
 *
 * @author pgalex
 */
public class MapSectorTest
{
	/**
	 * Test finding sector index and determing sector bounds by location on map
	 */
	@Test
	public void findingSectorIndexAndBoundsTest()
	{
		Location location = new Location(-55.5, 38.3);
		int sectorLatitudeIndex = MapSector.findSectorLatitudeIndex(location.getLatitude());
		int sectorLongitudeIndex = MapSector.findSectorLongitudeIndex(location.getLongitude());

		MapSector sector = new MapSector(sectorLatitudeIndex, sectorLongitudeIndex);
		MapBounds sectorBounds = sector.getBounds();

		assertTrue(sectorBounds.getLatitudeMinimum() < sectorBounds.getLatitudeMaximum());
		assertTrue(sectorBounds.getLatitudeMinimum() <= location.getLatitude());
		assertTrue(sectorBounds.getLatitudeMaximum() >= location.getLatitude());

		assertTrue(sectorBounds.getLongitudeMinimum() < sectorBounds.getLongitudeMaximum());
		assertTrue(sectorBounds.getLongitudeMinimum() <= location.getLongitude());
		assertTrue(sectorBounds.getLongitudeMaximum() >= location.getLongitude());
	}

	/**
	 * Test taking object with null tags
	 */
	@Test
	public void takeObjectWithNullTagsTest()
	{
		try
		{
			MapSector sector = new MapSector(4, 5);
			Location[] points = new Location[1];
			points[0] = new Location(1, 2);
			sector.takeMapObjectData(0, null, points);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test taking object with null points
	 */
	@Test
	public void takeObjectWithNullPointsTest()
	{
		try
		{
			MapSector sector = new MapSector(4, 5);
			sector.takeMapObjectData(0, new DefenitionTags(), null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test taking object with empty points
	 */
	@Test
	public void takeObjectWithEmptyPointsTest()
	{
		try
		{
			MapSector sector = new MapSector(4, 5);
			sector.takeMapObjectData(0, new DefenitionTags(), new Location[0]);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test taking object with null points contains null elements
	 */
	@Test
	public void takeObjectWithPointsContainsNullTest()
	{
		try
		{
			MapSector sector = new MapSector(4, 5);
			Location[] points = new Location[2];
			points[0] = new Location(1, 2);
			sector.takeMapObjectData(0, new DefenitionTags(), points);
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
	public void loadingWithNullDataSourceTest() throws FetchingErrorException
	{
		try
		{
			MapSector sector = new MapSector(1, 2);
			sector.loadObjects(null, new TestRenderableMapObjectsDrawSettingsFinder());
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test loading objects when objects are in area
	 *
	 * @throws FetchingErrorException
	 */
	@Test
	public void loadingWithObjectsInAreaTest() throws FetchingErrorException
	{
		DefenitionTags someNoteEmptyTags = new DefenitionTags();
		someNoteEmptyTags.add(new Tag("k1", "v1"));
		
		TestMapDataSource testMapDataSource = new TestMapDataSource();

		testMapDataSource.storingIds.add(new Long(15));
		testMapDataSource.storingTags.add(someNoteEmptyTags);
		Location[] points1 = new Location[1];
		points1[0] = new Location(MapSector.LATITUDE_SIZE / 2, MapSector.LONGITUDE_SIZE / 2);
		testMapDataSource.storingPoints.add(points1);

		testMapDataSource.storingIds.add(new Long(16));
		testMapDataSource.storingTags.add(someNoteEmptyTags);
		Location[] points2 = new Location[2];
		points2[0] = new Location(MapSector.LATITUDE_SIZE / 2, MapSector.LONGITUDE_SIZE / 2);
		points2[1] = new Location(MapSector.LATITUDE_SIZE / 3, MapSector.LONGITUDE_SIZE / 3);
		testMapDataSource.storingPoints.add(points2);

		testMapDataSource.storingIds.add(new Long(17));
		testMapDataSource.storingTags.add(someNoteEmptyTags);
		Location[] points3 = new Location[3];
		points3[0] = new Location(MapSector.LATITUDE_SIZE / 2, MapSector.LONGITUDE_SIZE / 2);
		points3[1] = new Location(MapSector.LATITUDE_SIZE / 3, MapSector.LONGITUDE_SIZE / 3);
		points3[2] = new Location(MapSector.LATITUDE_SIZE / 4, MapSector.LONGITUDE_SIZE / 4);
		testMapDataSource.storingPoints.add(points3);

		MapSector sector = new MapSector(0, 0);
		sector.loadObjects(testMapDataSource, new TestRenderableMapObjectsDrawSettingsFinder());

		assertEquals(3, sector.getStoringObjectsCount());
	}
}