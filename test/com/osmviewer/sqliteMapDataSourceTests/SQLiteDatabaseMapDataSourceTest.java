package com.osmviewer.sqliteMapDataSourceTests;

import com.osmviewer.IOTesting.IOTester;
import com.osmviewer.map.exceptions.FetchingErrorException;
import com.osmviewer.mapDefenitionUtilities.DefenitionTags;
import com.osmviewer.mapDefenitionUtilities.Location;
import com.osmviewer.mapDefenitionUtilities.MapBounds;
import com.osmviewer.mapDefenitionUtilities.Tag;
import com.osmviewer.sqliteMapDataSource.SQLiteDatabaseMapDataSource;
import com.osmviewer.sqliteMapDataSource.exceptions.DatabaseErrorExcetion;
import java.io.File;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of SQLiteDatabaseMapDataSource class
 *
 * @author pgalex
 */
public class SQLiteDatabaseMapDataSourceTest
{
	/**
	 * Test creating with null database path
	 *
	 * @throws DatabaseErrorExcetion
	 */
	@Test
	public void creatingWithNullDatabasePathTest() throws DatabaseErrorExcetion
	{
		try
		{
			SQLiteDatabaseMapDataSource database = new SQLiteDatabaseMapDataSource(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test creating with empty database path
	 *
	 * @throws DatabaseErrorExcetion
	 */
	@Test
	public void creatingWithEmptyDatabasePathTest() throws DatabaseErrorExcetion
	{
		try
		{
			SQLiteDatabaseMapDataSource database = new SQLiteDatabaseMapDataSource("");
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test adding map object with null tags
	 *
	 * @throws DatabaseErrorExcetion
	 */
	@Test
	public void addingMapObjectWithNullTagsTest() throws DatabaseErrorExcetion
	{
		try
		{
			deleteTestFile();
			SQLiteDatabaseMapDataSource database = new SQLiteDatabaseMapDataSource(IOTester.TEST_FILE_NAME);
			Location[] points = new Location[1];
			points[0] = new Location(10.0, 20.0);
			database.addMapObject(1, null, points);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test adding map object with null points
	 *
	 * @throws DatabaseErrorExcetion
	 */
	@Test
	public void addingMapObjectWithNullPointsTest() throws DatabaseErrorExcetion
	{
		try
		{
			deleteTestFile();
			SQLiteDatabaseMapDataSource database = new SQLiteDatabaseMapDataSource(IOTester.TEST_FILE_NAME);
			database.addMapObject(1, new DefenitionTags(), null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test adding map object with empty points
	 *
	 * @throws DatabaseErrorExcetion
	 */
	@Test
	public void addingMapObjectWithEmptyPointsTest() throws DatabaseErrorExcetion
	{
		try
		{
			deleteTestFile();
			SQLiteDatabaseMapDataSource database = new SQLiteDatabaseMapDataSource(IOTester.TEST_FILE_NAME);
			Location[] points = new Location[3];
			points[0] = new Location(10.0, 20.0);
			database.addMapObject(1, new DefenitionTags(), points);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test adding map object with points contains null elements
	 *
	 * @throws DatabaseErrorExcetion
	 */
	@Test
	public void addingMapObjectWithPointsContainsNullTest() throws DatabaseErrorExcetion
	{
		try
		{
			deleteTestFile();
			SQLiteDatabaseMapDataSource database = new SQLiteDatabaseMapDataSource(IOTester.TEST_FILE_NAME);
			database.addMapObject(1, new DefenitionTags(), new Location[3]);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	private void deleteTestFile()
	{
		File testFile = new File(IOTester.TEST_FILE_NAME);
		if (testFile.exists())
		{
			testFile.delete();
		}
	}

	/**
	 * Test fetching map objects in area normal work
	 *
	 * @throws DatabaseErrorExcetion
	 * @throws IllegalArgumentException
	 * @throws FetchingErrorException
	 */
	@Test
	public void fetchingMapObjectsInAreaWorkTest() throws DatabaseErrorExcetion, IllegalArgumentException, FetchingErrorException
	{
		deleteTestFile();
		SQLiteDatabaseMapDataSource database = new SQLiteDatabaseMapDataSource(IOTester.TEST_FILE_NAME);

		Location[] objectFullInAreaPoints = new Location[1];
		objectFullInAreaPoints[0] = new Location(15, 20);
		DefenitionTags objectFullInAreaTags = new DefenitionTags();
		objectFullInAreaTags.add(new Tag("k1", "v1"));
		database.addMapObject(15, objectFullInAreaTags, objectFullInAreaPoints);

		Location[] objectNotInAreaPoints = new Location[2];
		objectNotInAreaPoints[0] = new Location(2, 2);
		objectNotInAreaPoints[1] = new Location(-5, -8);
		DefenitionTags objectNotInAreaTags = new DefenitionTags();
		database.addMapObject(16, objectNotInAreaTags, objectNotInAreaPoints);
		
		Location[] objectCrossingAreaPoints = new Location[3];
		objectCrossingAreaPoints[0] = new Location(8, 2);
		objectCrossingAreaPoints[1] = new Location(18, 20);
		objectCrossingAreaPoints[2] = new Location(38, 32);
		DefenitionTags objectCrossingAreaTags = new DefenitionTags();
		database.addMapObject(17, objectCrossingAreaTags, objectCrossingAreaPoints);

		database.commitLastBatchedMapObjects();

		TestMapDataSourceFetchResultsHandler resultsHandler = new TestMapDataSourceFetchResultsHandler();
		database.fetchMapObjectsInArea(new MapBounds(10, 30, 5, 25), resultsHandler);

		assertEquals(2, resultsHandler.fetchedIds.size());
		assertEquals(2, resultsHandler.fetchedTags.size());
		assertEquals(2, resultsHandler.fetchedPoints.size());
		
		assertEquals(new Long(15), resultsHandler.fetchedIds.get(0));
		assertEquals(1, resultsHandler.fetchedTags.get(0).count());
		assertEquals("k1", resultsHandler.fetchedTags.get(0).get(0).getKey());
		assertEquals("v1", resultsHandler.fetchedTags.get(0).get(0).getValue());
		assertEquals(1, resultsHandler.fetchedPoints.get(0).length);
		assertEquals(15, resultsHandler.fetchedPoints.get(0)[0].getLatitude(), 0.0001);
		assertEquals(20, resultsHandler.fetchedPoints.get(0)[0].getLongitude(), 0.0001);
		
		assertEquals(new Long(17), resultsHandler.fetchedIds.get(1));
		assertEquals(0, resultsHandler.fetchedTags.get(1).count());
		assertEquals(3, resultsHandler.fetchedPoints.get(1).length);
	}
}