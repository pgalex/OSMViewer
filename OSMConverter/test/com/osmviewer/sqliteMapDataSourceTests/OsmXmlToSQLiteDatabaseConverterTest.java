package com.osmviewer.sqliteMapDataSourceTests;

import com.osmviewer.sqliteMapDataSource.OsmXmlToSQLiteDatabaseConverter;
import java.io.FileNotFoundException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of OsmXmlToSQLiteDatabaseConverter class
 *
 * @author pgalex
 */
public class OsmXmlToSQLiteDatabaseConverterTest
{
	private static final String TEST_MAP_FILE = "test/supportFiles/testmap.osm";

	/**
	 * Test converting with source osm xml data file name
	 *
	 * @throws FileNotFoundException
	 */
	@Test
	public void convertingWithNullSourceFileTest() throws FileNotFoundException
	{
		/*try
		 {
		 OsmXmlToSQLiteDatabaseConverter converter = new OsmXmlToSQLiteDatabaseConverter();
		 converter.convert(null, IOTester.TEST_FILE_NAME, new TestMapObjectsIdentifiersFinder());
		 fail();
		 }
		 catch (IllegalArgumentException ex)
		 {
		 // ok
		 }*/
	}

	/**
	 * Test converting with empty source osm xml data file name
	 *
	 * @throws FileNotFoundException
	 */
	@Test
	public void convertingWithEmptySourceFileTest() throws FileNotFoundException
	{
		/*try
		 {
		 OsmXmlToSQLiteDatabaseConverter converter = new OsmXmlToSQLiteDatabaseConverter();
		 converter.convert("", IOTester.TEST_FILE_NAME, new TestMapObjectsIdentifiersFinder());
		 fail();
		 }
		 catch (IllegalArgumentException ex)
		 {
		 // ok
		 }*/
	}

	/**
	 * Test converting with null database file
	 */
	@Test
	public void convertingWithNullDatabaseFileNameTest()
	{
		/*try
		 {
		 OsmXmlToSQLiteDatabaseConverter converter = new OsmXmlToSQLiteDatabaseConverter();
		 converter.convert(IOTester.TEST_FILE_NAME, null, new TestMapObjectsIdentifiersFinder());
		 fail();
		 }
		 catch (IllegalArgumentException ex)
		 {
		 // ok
		 }*/
	}

	/**
	 * Test converting with empty database file
	 */
	@Test
	public void convertingWithEmptyDatabaseFileNameTest()
	{
		/*try
		 {
		 OsmXmlToSQLiteDatabaseConverter converter = new OsmXmlToSQLiteDatabaseConverter();
		 converter.convert(TEST_MAP_FILE, "", new TestMapObjectsIdentifiersFinder());
		 fail();
		 }
		 catch (IllegalArgumentException ex)
		 {
		 // ok
		 }
		 catch (FileNotFoundException | ParsingOsmErrorException | DeletingExistsDatabaseFileErrorException | DatabaseErrorExcetion ex)
		 {
		 fail();
		 }*/
	}

	/**
	 * Test converting with equals source and destenation database files
	 *
	 * @throws FileNotFoundException
	 */
	@Test
	public void convertingWithEqualsSourceAndDatabaseFileTest() throws FileNotFoundException
	{
		/*try
		 {
		 OsmXmlToSQLiteDatabaseConverter converter = new OsmXmlToSQLiteDatabaseConverter();
		 converter.convert(IOTester.TEST_FILE_NAME, IOTester.TEST_FILE_NAME, new TestMapObjectsIdentifiersFinder());
		 fail();
		 }
		 catch (IllegalArgumentException ex)
		 {
		 // ok
		 }*/
	}

	/**
	 * Test taking null node from parser
	 */
	@Test
	public void takingNullNodeTest()
	{
		try
		{
			OsmXmlToSQLiteDatabaseConverter converter = new OsmXmlToSQLiteDatabaseConverter(new MapObjectsIdFinderFake());
			converter.takeNode(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test taking null way from parser
	 */
	@Test
	public void takingNullWayTest()
	{
		try
		{
			OsmXmlToSQLiteDatabaseConverter converter = new OsmXmlToSQLiteDatabaseConverter(new MapObjectsIdFinderFake());
			converter.takeWay(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}
}
