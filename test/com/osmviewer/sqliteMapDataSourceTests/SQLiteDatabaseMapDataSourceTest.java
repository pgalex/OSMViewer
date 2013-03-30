package com.osmviewer.sqliteMapDataSourceTests;

import com.osmviewer.IOTesting.IOTester;
import com.osmviewer.mapDefenitionUtilities.DefenitionTags;
import com.osmviewer.mapDefenitionUtilities.Location;
import com.osmviewer.sqliteMapDataSource.SQLiteDataBaseMapDataSource;
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
			SQLiteDataBaseMapDataSource database = new SQLiteDataBaseMapDataSource(null);
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
			SQLiteDataBaseMapDataSource database = new SQLiteDataBaseMapDataSource("");
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
			SQLiteDataBaseMapDataSource database = new SQLiteDataBaseMapDataSource(IOTester.TEST_FILE_NAME);
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
			SQLiteDataBaseMapDataSource database = new SQLiteDataBaseMapDataSource(IOTester.TEST_FILE_NAME);
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
			SQLiteDataBaseMapDataSource database = new SQLiteDataBaseMapDataSource(IOTester.TEST_FILE_NAME);
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
			SQLiteDataBaseMapDataSource database = new SQLiteDataBaseMapDataSource(IOTester.TEST_FILE_NAME);
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
}