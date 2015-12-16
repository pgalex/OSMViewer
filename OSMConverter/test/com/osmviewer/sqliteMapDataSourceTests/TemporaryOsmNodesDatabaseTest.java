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
	 * Test creating temporary database file
	 *
	 * @throws DatabaseErrorExcetion
	 */
	@Test
	public void creatingDatabaseFileInContructorTest() throws DatabaseErrorExcetion
	{
		TemporaryOsmNodesDatabase database = new TemporaryOsmNodesDatabase();
		File databaseFile = new File(database.getDatabaseFilePath());

		assertTrue(databaseFile.exists());
	}

	/**
	 * Test throwing exception when adding null node
	 *
	 * @throws DatabaseErrorExcetion
	 */
	@Test
	public void exceptionAddingNullNodeTest() throws DatabaseErrorExcetion
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

		database.closeAndDeleteDatabaseFile();
	}

	/**
	 * Test finding node in database
	 *
	 * @throws DatabaseErrorExcetion
	 */
	@Test
	public void findingNodeTest() throws DatabaseErrorExcetion
	{
		TemporaryOsmNodesDatabase database = new TemporaryOsmNodesDatabase();
		OsmNodeFake addingNode = new OsmNodeFake();
		addingNode.id = 123456789;
		addingNode.latitude = 12.15;
		addingNode.longitude = -5.5;
		database.addNode(addingNode);
		database.commitLastBatchedNodes();

		TemporaryDatabaseOsmNode foundNode = database.findNodeById(addingNode.id);
		database.closeAndDeleteDatabaseFile();

		assertNotNull(foundNode);
		assertEquals(addingNode.id, foundNode.getId());
		assertEquals(addingNode.latitude, foundNode.getLatitude(), 0.00001);
		assertEquals(addingNode.longitude, foundNode.getLongitude(), 0.00001);
	}

	/**
	 * Test finding node which not exists in database
	 *
	 * @throws DatabaseErrorExcetion
	 */
	@Test
	public void findingUnexistsNodeTest() throws DatabaseErrorExcetion
	{
		TemporaryOsmNodesDatabase database = new TemporaryOsmNodesDatabase();
		OsmNodeFake addingNode = new OsmNodeFake();
		addingNode.id = 123456789;
		addingNode.latitude = 12.15;
		addingNode.longitude = -5.5;
		database.addNode(addingNode);
		database.commitLastBatchedNodes();

		TemporaryDatabaseOsmNode foundNode = database.findNodeById(123);
		database.closeAndDeleteDatabaseFile();

		assertNull(foundNode);
	}

	/**
	 * Test commiting adding nodes in batch
	 *
	 * @throws DatabaseErrorExcetion
	 */
	@Test
	public void commitingInBatchTest() throws DatabaseErrorExcetion
	{
		/*TemporaryOsmNodesDatabase database = new TemporaryOsmNodesDatabase();

		 TestOsmNode addingNode = new TestOsmNode();
		 addingNode.id = 10;
		 database.addNode(addingNode);

		 TemporaryDatabaseOsmNode gettedNodeBeforeCommiting = database.findNodeById(addingNode.id);
		 assertNull(gettedNodeBeforeCommiting);

		 database.commitLastBatchedNodes();

		 TemporaryDatabaseOsmNode gettedNodeAfterCommiting = database.findNodeById(addingNode.id);
		 assertNotNull(gettedNodeAfterCommiting);

		 database.closeAndDeleteDatabaseFile();*/
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
		/*TemporaryOsmNodesDatabase database = new TemporaryOsmNodesDatabase();
		 File databaseFile = new File(database.getDatabaseFilePath());
		 assertTrue(databaseFile.exists());

		 database.closeAndDeleteDatabaseFile();
		 assertFalse(databaseFile.exists());*/
	}
}
