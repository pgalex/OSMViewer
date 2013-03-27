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
 * Database, using for temporary storing and searching nodes form osm xml
 *
 * @author pgalex
 */
public class TemporaryOsmNodesDatabase
{
	/**
	 * Number of insert commands in adding statement bactch
	 */
	private static int ADD_NODES_MAXIMUM_BATCH_SIZE = 1000;
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
		try
		{
			Class.forName("org.sqlite.JDBC");
			databaseFile = File.createTempFile("osmViewer", "TempDatabase");
			databaseConnection = DriverManager.getConnection("jdbc:sqlite:" + databaseFile.getPath());
			databaseConnection.setAutoCommit(false);

			Statement createNodesTableStatement = databaseConnection.createStatement();
			createNodesTableStatement.executeUpdate("CREATE TABLE IF NOT EXISTS Nodes ( id INTEGER PRIMARY KEY, "
							+ "latitude REAL, longitude REAL )");
			databaseConnection.commit();
			createNodesTableStatement.close();

			addNodeStatement = databaseConnection.prepareStatement("INSERT INTO Nodes VALUES (?,?,?)");
			addingNodesCurrentBatchSize = 0;
		}
		catch (ClassNotFoundException ex)
		{
			throw new DatabaseErrorExcetion(ex);
		}
		catch (SQLException ex)
		{
			throw new DatabaseErrorExcetion(ex);
		}
		catch (IOException ex)
		{
			throw new DatabaseErrorExcetion(ex);
		}
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
		catch (Exception ex)
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
	 * Find osm node in database by its openstreetmap unique id. Call
	 * commitAddedNodes before using finding
	 *
	 * @param nodeId openstreetmap id of node to find
	 * @return found node. Null if not found
	 * @throws DatabaseErrorExcetion error while getting node from database
	 */
	public TemporaryDatabaseOsmNode findNodeById(long nodeId) throws DatabaseErrorExcetion
	{
		try
		{
			PreparedStatement selectNodeStatement = databaseConnection.prepareStatement("SELECT * FROM Nodes WHERE id=?");
			selectNodeStatement.setLong(1, nodeId);

			ResultSet selectedNodeResultSet = selectNodeStatement.executeQuery();
			boolean resultsExists = selectedNodeResultSet.next();

			TemporaryDatabaseOsmNode foundNode = null;
			if (resultsExists)
			{
				foundNode = new TemporaryDatabaseOsmNode(selectedNodeResultSet);
			}
			selectedNodeResultSet.close();
			selectNodeStatement.close();
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
}
