package com.osmviewer.sqliteMapDataSource;

import com.osmviewer.osmXml.OsmNode;
import com.osmviewer.sqliteMapDataSource.exceptions.DatabaseErrorExcetion;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
	 * Connection to temponary SQLite database
	 */
	private Connection databaseConnection;
	/**
	 * File of database
	 */
	private File databaseFile;

	/**
	 * Create temporary database on file. If database not exists it will be
	 * created, if exists - open
	 *
	 * @throws DatabaseErrorExcetion can not connect to database file
	 */
	public TemporaryOsmNodesDatabase() throws DatabaseErrorExcetion
	{
		try
		{
			Class.forName("org.sqlite.JDBC");
			databaseFile = File.createTempFile("osmViewer", "TempDatabase");
			databaseConnection = DriverManager.getConnection("jdbc:sqlite:" + databaseFile.getPath());

			String creatingNodesTableQuery = "CREATE TABLE IF NOT EXISTS Nodes ( id INTEGER PRIMARY KEY, "
							+ "latitude REAL, longitude REAL )";
			Statement createNodesTableStatement = databaseConnection.createStatement();
			createNodesTableStatement.executeUpdate(creatingNodesTableQuery);
			createNodesTableStatement.close();
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
			databaseConnection.close();
			databaseFile.delete();
		}
		catch (Exception ex)
		{
			throw new DatabaseErrorExcetion(ex);
		}
	}

	/**
	 * Add osm node to database
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
			PreparedStatement addNodeStatement = databaseConnection.prepareStatement("INSERT INTO Nodes VALUES (?,?,?)");
			addNodeStatement.setLong(1, nodeToAdd.getId());
			addNodeStatement.setDouble(2, nodeToAdd.getLatitude());
			addNodeStatement.setDouble(3, nodeToAdd.getLongitude());
			addNodeStatement.executeUpdate();
			addNodeStatement.close();
		}
		catch (SQLException ex)
		{
			throw new DatabaseErrorExcetion(ex);
		}
	}
}
