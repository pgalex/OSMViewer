package com.osmviewer.mapTests;

import com.osmviewer.IOTesting.IOTester;
import com.osmviewer.map.OsmXmlToSQLiteDatabaseConverter;
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
	/**
	 * Test converting with null osm xml data input
	 */
	@Test
	public void convertingWithNullInputStreamTest()
	{
		try
		{
			OsmXmlToSQLiteDatabaseConverter converter = new OsmXmlToSQLiteDatabaseConverter();
			converter.convert(null, IOTester.TEST_FILE_NAME);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test converting with null database file name
	 */
	@Test
	public void convertingWithNullDatabaseFileNameTest()
	{
		try
		{
			OsmXmlToSQLiteDatabaseConverter converter = new OsmXmlToSQLiteDatabaseConverter();
			converter.convert(IOTester.createTestInputStream(), null);
			fail();
		}
		catch (FileNotFoundException ex)
		{
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test converting with empty database file name
	 */
	@Test
	public void convertingWithEmptyDatabaseFileNameTest()
	{
		try
		{
			OsmXmlToSQLiteDatabaseConverter converter = new OsmXmlToSQLiteDatabaseConverter();
			converter.convert(IOTester.createTestInputStream(), "");
			fail();
		}
		catch (FileNotFoundException ex)
		{
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}
}