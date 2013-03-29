package com.osmviewer.sqliteMapDataSourceTests;

import com.osmviewer.sqliteMapDataSource.TemporaryDatabaseOsmNode;
import com.osmviewer.sqliteMapDataSource.TemporaryOsmNodesDatabase;
import com.osmviewer.sqliteMapDataSource.exceptions.DatabaseErrorExcetion;
import java.io.File;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of TemporaryOsmNodesDatabase class
 *
 * @author pgalex
 */
public class TemporaryOsmNodesDatabaseTest
{
	/**
	 * Test adding null node
	 *
	 * @throws DatabaseErrorExcetion
	 */
	@Test
	public void addingNullNodeTest() throws DatabaseErrorExcetion
	{
		TemporaryOsmNodesDatabase database = new TemporaryOsmNodesDatabase();
		
		try
		{
			database.addNode(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
		finally
		{
			database.closeAndDeleteDatabaseFile();
		}
	}

	/**
	 * Test adding and finding nodes
	 *
	 * @throws DatabaseErrorExcetion
	 */
	@Test
	public void findingNodesNormalWorkTest() throws DatabaseErrorExcetion
	{
		TemporaryOsmNodesDatabase database = new TemporaryOsmNodesDatabase();
		
		TestOsmNode addingNode = new TestOsmNode();
		addingNode.id = 123456789;
		addingNode.latitude = 12.15;
		addingNode.longitude = -5.5;
		database.addNode(addingNode);
		database.commitLastBatchedNodes();
		
		TemporaryDatabaseOsmNode gettedNode = database.findNodeById(addingNode.id);
		database.closeAndDeleteDatabaseFile();
		
		assertNotNull(gettedNode);
		assertEquals(addingNode.id, gettedNode.getId());
		assertEquals(addingNode.latitude, gettedNode.getLatitude(), 0.00001);
		assertEquals(addingNode.longitude, gettedNode.getLongitude(), 0.00001);
	}

	/**
	 * Test node which id no exists in database
	 *
	 * @throws DatabaseErrorExcetion
	 */
	@Test
	public void findingUnexistsNodeTest() throws DatabaseErrorExcetion
	{
		TemporaryOsmNodesDatabase database = new TemporaryOsmNodesDatabase();
		
		TestOsmNode addingNode = new TestOsmNode();
		addingNode.id = 123456789;
		addingNode.latitude = 12.15;
		addingNode.longitude = -5.5;
		database.addNode(addingNode);
		database.commitLastBatchedNodes();
		
		TemporaryDatabaseOsmNode gettedNode = database.findNodeById(123);
		database.closeAndDeleteDatabaseFile();
		
		assertNull(gettedNode);
	}

	/**
	 * Test node which id no exists in database
	 *
	 *
	 * @throws DatabaseErrorExcetion
	 */
	@Test
	public void deletingDatabaseFileTest() throws DatabaseErrorExcetion
	{
		TemporaryOsmNodesDatabase database = new TemporaryOsmNodesDatabase();
		File databaseFile = new File(database.getDatabaseFilePath());
		assertTrue(databaseFile.exists());
		
		database.closeAndDeleteDatabaseFile();
		assertFalse(databaseFile.exists());
	}
}