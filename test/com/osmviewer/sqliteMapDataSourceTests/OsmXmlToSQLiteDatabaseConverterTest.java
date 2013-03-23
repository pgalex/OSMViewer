package com.osmviewer.sqliteMapDataSourceTests;

import com.osmviewer.IOTesting.IOTester;
import com.osmviewer.osmXml.exceptions.ParsingOsmErrorException;
import com.osmviewer.sqliteMapDataSource.OsmXmlToSQLiteDatabaseConverter;
import com.osmviewer.sqliteMapDataSource.exceptions.DatabaseErrorExcetion;
import com.osmviewer.sqliteMapDataSource.exceptions.DeletingExistsDatabaseFileErrorException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of OsmXmlToSQLiteDatabaseConverter class
 *
 * @author pgalex
 */
public class OsmXmlToSQLiteDatabaseConverterTest
{
	private DataInputStream createTestMapInputStream() throws FileNotFoundException
	{
		return new DataInputStream(new FileInputStream("test/supportFiles/testmap.osm"));
	}

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
		catch (ParsingOsmErrorException ex)
		{
			fail();
		}
		catch (DeletingExistsDatabaseFileErrorException ex)
		{
			fail();
		}
		catch (DatabaseErrorExcetion ex)
		{
			fail();
		}
	}

	/**
	 * Test converting with null database file
	 */
	@Test
	public void convertingWithNullDatabaseFileNameTest()
	{
		try
		{
			OsmXmlToSQLiteDatabaseConverter converter = new OsmXmlToSQLiteDatabaseConverter();
			converter.convert(createTestMapInputStream(), null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
		catch (FileNotFoundException ex)
		{
			fail();
		}
		catch (ParsingOsmErrorException ex)
		{
			fail();
		}
		catch (DeletingExistsDatabaseFileErrorException ex)
		{
			fail();
		}
		catch (DatabaseErrorExcetion ex)
		{
			fail();
		}
	}

	/**
	 * Test converting with empty database file
	 */
	@Test
	public void convertingWithEmptyDatabaseFileNameTest()
	{
		try
		{
			OsmXmlToSQLiteDatabaseConverter converter = new OsmXmlToSQLiteDatabaseConverter();
			converter.convert(createTestMapInputStream(), "");
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
		catch (FileNotFoundException ex)
		{
			fail();
		}
		catch (ParsingOsmErrorException ex)
		{
			fail();
		}
		catch (DeletingExistsDatabaseFileErrorException ex)
		{
			fail();
		}
		catch (DatabaseErrorExcetion ex)
		{
			fail();
		}
	}

	/**
	 * Test deleting exists database file
	 */
	@Test
	public void deletingExistsDatabaseTest()
	{
		try
		{
			DataOutputStream testFileOutput = IOTester.createTestOutputStream();
			testFileOutput.writeBoolean(true);
			testFileOutput.close();

			OsmXmlToSQLiteDatabaseConverter converter = new OsmXmlToSQLiteDatabaseConverter();
			converter.convert(createTestMapInputStream(), IOTester.TEST_FILE_NAME);
		}
		catch (FileNotFoundException ex)
		{
			fail();
		}
		catch (ParsingOsmErrorException ex)
		{
			fail();
		}
		catch (DeletingExistsDatabaseFileErrorException ex)
		{
			fail();
		}
		catch (IOException ex)
		{
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			fail();
		}
		catch (DatabaseErrorExcetion ex)
		{
			fail();
		}

		File testFile = new File(IOTester.TEST_FILE_NAME);
		assertFalse(testFile.exists());
	}

	/**
	 * Test taking null node from parser
	 */
	@Test
	public void takingNullNodeTest()
	{
		try
		{
			OsmXmlToSQLiteDatabaseConverter converter = new OsmXmlToSQLiteDatabaseConverter();
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
			OsmXmlToSQLiteDatabaseConverter converter = new OsmXmlToSQLiteDatabaseConverter();
			converter.takeWay(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}
}