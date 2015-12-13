package com.osmviewer.sqliteMapDataSource;

import com.osmviewer.osmXml.OsmNode;
import com.osmviewer.sqliteMapDataSource.exceptions.DatabaseErrorExcetion;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Database, using for temporary storing and searching nodes (point-like objects from .osm xml file)
 *
 * @author pgalex
 */
public class TemporaryOsmNodesDatabase
{
	/**
	 * Maximum number of insert commands in adding statement batch
	 */
	private static final int ADD_NODES_MAXIMUM_BATCH_SIZE = 2500;
	/**
	 * Connection to temponary SQLite database
	 */
	private Connection databaseConnection;
	/**
	 * File of database
	 */
	private File databaseFile;
	/**
	 * Statement using for batched adding nodes
	 */
	private PreparedStatement addNodeStatement;
	/**
	 * Currently batch size of adding statement
	 */
	private int addingNodesCurrentBatchSize;

	/**
	 * Create temporary database on file. If database not exists it will be
	 * created, if exists - open
	 *
	 * @throws DatabaseErrorExcetion error while creating database
	 */
	public TemporaryOsmNodesDatabase() throws DatabaseErrorExcetion
	{
		//todo overwrite exists file; take database file path in constuctor, and not use createTempFile
		try
		{
			Class.forName("org.sqlite.JDBC");
			databaseFile = File.createTempFile("osmViewer", "TempDatabase"); //todo give file name in contructor for testing
			databaseConnection = DriverManager.getConnection("jdbc:sqlite:" + databaseFile.getPath());
			databaseConnection.setAutoCommit(false);
			try (Statement createNodesTableStatement = databaseConnection.createStatement())
			{
				createNodesTableStatement.executeUpdate("CREATE TABLE IF NOT EXISTS Nodes ( id INTEGER PRIMARY KEY, "
								+ "latitude REAL, longitude REAL )");
				databaseConnection.commit();
			}

			addNodeStatement = databaseConnection.prepareStatement("INSERT INTO Nodes VALUES (?,?,?)");
			addingNodesCurrentBatchSize = 0;
		}
		catch (ClassNotFoundException | SQLException | IOException ex)
		{
			throw new DatabaseErrorExcetion(ex);
		}
	}

	/**
	 * Get path to database file
	 *
	 * @return path to database file
	 */
	public String getDatabaseFilePath()
	{
		return databaseFile.getPath();
	}

	/**
	 * Close database. File of database will be deleted
	 *
	 * @throws DatabaseErrorExcetion
	 */
	public void closeAndDeleteDatabaseFile() throws DatabaseErrorExcetion
	{
		try
		{
			addNodeStatement.close();
			databaseConnection.close();
			databaseFile.delete();
		}
		catch (SQLException ex)
		{
			throw new DatabaseErrorExcetion(ex);
		}
	}

	/**
	 * Add osm node to database. After adding call commitAddedNodes method
	 *
	 * @param nodeToAdd adding node
	 * @throws IllegalArgumentException nodeToAdd is null
	 * @throws DatabaseErrorExcetion error while adding
	 */
	public void addNode(OsmNode nodeToAdd) throws IllegalArgumentException, DatabaseErrorExcetion
	{
		if (nodeToAdd == null)
		{
			throw new IllegalArgumentException("nodeToAdd is null");
		}

		try
		{
			addNodeStatement.setLong(1, nodeToAdd.getId());
			addNodeStatement.setDouble(2, nodeToAdd.getLatitude());
			addNodeStatement.setDouble(3, nodeToAdd.getLongitude());
			addNodeStatement.addBatch();
			addingNodesCurrentBatchSize++;
			if (addingNodesCurrentBatchSize >= ADD_NODES_MAXIMUM_BATCH_SIZE)
			{
				commitAddedNodes();
				addingNodesCurrentBatchSize = 0;
			}
		}
		catch (SQLException ex)
		{
			throw new DatabaseErrorExcetion(ex);
		}
	}

	/**
	 * Commit added nodes to database
	 *
	 * @throws DatabaseErrorExcetion error while executing commit
	 */
	private void commitAddedNodes() throws DatabaseErrorExcetion
	{
		try
		{
			addNodeStatement.executeBatch();
			databaseConnection.commit();
		}
		catch (SQLException ex)
		{
			throw new DatabaseErrorExcetion(ex);
		}
	}

	/**
	 * Find osm node in database by its unique id
	 *
	 * @param nodeId openstreetmap id of node to find
	 * @return found node. Null if not found
	 * @throws DatabaseErrorExcetion error while getting node from database
	 */
	public TemporaryDatabaseOsmNode findNodeById(long nodeId) throws DatabaseErrorExcetion
	{
		try
		{
			TemporaryDatabaseOsmNode foundNode = null;
			try (PreparedStatement selectNodeStatement = databaseConnection.prepareStatement("SELECT * FROM Nodes WHERE id=?"))
			{
				selectNodeStatement.setLong(1, nodeId);
				try (ResultSet selectedNodeResultSet = selectNodeStatement.executeQuery())
				{
					boolean isResultsExists = selectedNodeResultSet.next();
					if (isResultsExists)
					{
						foundNode = new TemporaryDatabaseOsmNode(selectedNodeResultSet);
					}
				}
			}

			return foundNode;
		}
		catch (SQLException ex)
		{
			throw new DatabaseErrorExcetion(ex);
		}
	}

	/**
	 * Commit last batched but not commited added nodes to database
	 *
	 * @throws DatabaseErrorExcetion error while commiting
	 */
	public void commitLastBatchedNodes() throws DatabaseErrorExcetion
	{
		if (addingNodesCurrentBatchSize > 0)
		{
			commitAddedNodes();
		}
	}

	/**
	 * Create indexes for temporary nodes table
	 *
	 * @throws DatabaseErrorExcetion error while creating indexes
	 */
	public void createIndexes() throws DatabaseErrorExcetion
	{
		try
		{
			try (Statement createIndexStatement = databaseConnection.createStatement())
			{
				createIndexStatement.executeUpdate("CREATE INDEX IF NOT EXISTS NodesIdsIndex ON Nodes (id)");
				databaseConnection.commit();
			}
		}
		catch (SQLException ex)
		{
			throw new DatabaseErrorExcetion(ex);
		}
	}
}
