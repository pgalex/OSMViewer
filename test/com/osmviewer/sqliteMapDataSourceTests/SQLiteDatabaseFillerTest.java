package com.osmviewer.sqliteMapDataSourceTests;

import com.osmviewer.IOTesting.IOTester;
import com.osmviewer.mapDefenitionUtilities.DefenitionTags;
import com.osmviewer.mapDefenitionUtilities.Location;
import com.osmviewer.sqliteMapDataSource.SQLiteDatabaseFiller;
import com.osmviewer.sqliteMapDataSource.exceptions.DatabaseErrorExcetion;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * SQLiteDatabaseFiller ckass tests
 *
 * @author pgalex
 */
public class SQLiteDatabaseFillerTest
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
			SQLiteDatabaseFiller databaseFiller = new SQLiteDatabaseFiller(null);
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
			SQLiteDatabaseFiller databaseFiller = new SQLiteDatabaseFiller("");
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test creating with exists database
	 *
	 * @throws DatabaseErrorExcetion
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@Test
	public void creatingWithExistsDatabaseTest() throws DatabaseErrorExcetion, FileNotFoundException, IOException
	{
		try
		{
			try (DataOutputStream output = IOTester.createTestOutputStream())
			{
				output.writeBoolean(true);
			}
			SQLiteDatabaseFiller databaseFiller = new SQLiteDatabaseFiller(IOTester.TEST_FILE_NAME);
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
			IOTester.deleteTestFile();
			SQLiteDatabaseFiller databaseFiller = new SQLiteDatabaseFiller(IOTester.TEST_FILE_NAME);
			Location[] points = new Location[1];
			points[0] = new Location(10.0, 20.0);
			databaseFiller.addMapObject("1", null, points);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	@Test
	public void addingMapObjectWithNullIdTest() throws DatabaseErrorExcetion
	{
		try
		{
			IOTester.deleteTestFile();
			SQLiteDatabaseFiller databaseFiller = new SQLiteDatabaseFiller(IOTester.TEST_FILE_NAME);
			Location[] points = new Location[1];
			points[0] = new Location(10.0, 20.0);
			databaseFiller.addMapObject(null, new DefenitionTags(), points);
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
			IOTester.deleteTestFile();
			SQLiteDatabaseFiller databaseFiller = new SQLiteDatabaseFiller(IOTester.TEST_FILE_NAME);
			databaseFiller.addMapObject("1", new DefenitionTags(), null);
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
			IOTester.deleteTestFile();
			SQLiteDatabaseFiller databaseFiller = new SQLiteDatabaseFiller(IOTester.TEST_FILE_NAME);
			Location[] points = new Location[3];
			points[0] = new Location(10.0, 20.0);
			databaseFiller.addMapObject("1", new DefenitionTags(), points);
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
			IOTester.deleteTestFile();
			SQLiteDatabaseFiller databaseFiller = new SQLiteDatabaseFiller(IOTester.TEST_FILE_NAME);
			databaseFiller.addMapObject("1", new DefenitionTags(), new Location[3]);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}
}