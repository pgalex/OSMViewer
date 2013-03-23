/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.osmviewer.sqliteMapDataSource;

import com.osmviewer.map.MapDataSource;
import com.osmviewer.map.MapDataSourceFetchResultsHandler;
import com.osmviewer.mapDefenitionUtilities.MapBounds;
import com.osmviewer.sqliteMapDataSource.exceptions.DatabaseErrorExcetion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Nocomment85
 */
public class SQLiteDataBaseMapDataSource implements MapDataSource
{
	/**
	 * Connection to temponary SQLite database
	 */
	private Connection databaseConnection;
	/**
	 * Create temporary database on file. If database not exists it will be
	 * created, if exists - open
	 *
	 * @param  path path to database
	 * @throws DatabaseErrorExcetion can not connect to database file
	 */
	public SQLiteDataBaseMapDataSource( String path ) throws DatabaseErrorExcetion
	{
		if (path == null)
		{
			throw new IllegalArgumentException("path is null");
		}
		if (path.isEmpty())
		{
			throw new IllegalArgumentException("path is empty");
		}
		try
		{
			Class.forName("org.sqlite.JDBC");
			databaseConnection = DriverManager.getConnection("jdbc:sqlite:" + path);
			System.out.println(path);

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
	}
	
		/**
	 * Close database. 
	 *
	 * @throws DatabaseErrorExcetion
	 */
	public void closeAndDeleteDatabaseFile() throws DatabaseErrorExcetion
	{
		try
		{
			databaseConnection.close();
		}
		catch (Exception ex)
		{
			throw new DatabaseErrorExcetion(ex);
		}
	}

	@Override
	public void fetchMapObjectsInArea(MapBounds area, MapDataSourceFetchResultsHandler fetchResultsHandler) throws IllegalArgumentException
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
}
