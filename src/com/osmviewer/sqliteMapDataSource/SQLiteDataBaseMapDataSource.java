/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.osmviewer.sqliteMapDataSource;

import com.osmviewer.map.MapDataSource;
import com.osmviewer.map.MapDataSourceFetchResultsHandler;
import com.osmviewer.mapDefenitionUtilities.MapBounds;
import com.osmviewer.mapDefenitionUtilities.DefenitionTags;
import com.osmviewer.mapDefenitionUtilities.Location;
import com.osmviewer.sqliteMapDataSource.exceptions.DatabaseErrorExcetion;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
	 * @param path path to database
	 * @throws DatabaseErrorExcetion can not connect to database file
	 */
	public SQLiteDataBaseMapDataSource(String path) throws DatabaseErrorExcetion
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

			String creatingTableQuery = "CREATE TABLE MapObjects ( id INTEGER PRIMARY KEY, "
							+ "tags BLOB, points BLOB," 
							+ "minLatitude REAL, maxLatitude REAL, minLongitude REAL, maxLongitude REAL )";
			Statement createTableStatement = databaseConnection.createStatement();
			createTableStatement.executeUpdate(creatingTableQuery);
			createTableStatement.close();
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
	 * Add points into database
	 * 
	 * @param id mapObject id
	 * @param tags describes map object
	 * @param points points positions on map
	 * @throws SQLException sql error
	 * @throws IOException input/output error
	 */
	public void addMapObject(long id, DefenitionTags tags, Location[] points) throws SQLException, IOException
	{
		double minLatitude = points[0].getLatitude();
		double minLongitude = points[0].getLongitude();
		double maxLatitude = points[0].getLatitude();
		double maxLongitude = points[0].getLatitude();
		
		for (int i = 0; i < points.length; i++)
		{
			Location mapPosition = points[i];
			if (mapPosition.getLatitude() < minLatitude)
			{
				minLatitude = mapPosition.getLatitude();
			}
 			if (mapPosition.getLongitude() < minLongitude)
			{
				minLongitude = mapPosition.getLongitude();
			}
			if (mapPosition.getLatitude() > maxLatitude)
			{
			 maxLatitude = mapPosition.getLatitude();
			}
			if (mapPosition.getLongitude() > maxLongitude)
			{
			 maxLongitude = mapPosition.getLongitude();
			}
		}
		PreparedStatement insertStatement = databaseConnection.prepareStatement("INSERT INTO MapObjects VALUES (?,?,?,?,?,?,?)");
		insertStatement.setLong(1, id);
		ByteArrayOutputStream tagsByteArrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream tagsDataOutputStream = new DataOutputStream(tagsByteArrayOutputStream);
		tags.writeToStream(tagsDataOutputStream);
		insertStatement.setBytes(2, tagsByteArrayOutputStream.toByteArray());
		tagsByteArrayOutputStream.close();
		tagsDataOutputStream.close();
		 
		ByteArrayOutputStream pointsByteArrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream pointsDataOutputStream = new DataOutputStream(pointsByteArrayOutputStream);
		pointsDataOutputStream.writeInt(points.length);
		for (int i = 0; i < points.length; i++)
		{
			points[i].writeToStream(pointsDataOutputStream);
		}
		insertStatement.setBytes(3, pointsByteArrayOutputStream.toByteArray());
		
		pointsByteArrayOutputStream.close();
		pointsDataOutputStream.close();
		
		insertStatement.setDouble(4, minLatitude);
		insertStatement.setDouble(5, maxLatitude);
		insertStatement.setDouble(6, minLongitude);
		insertStatement.setDouble(7, maxLongitude);
		
		insertStatement.executeUpdate();
		
		insertStatement.close();
	}

	/**
	 * Close database.
	 *
	 * @throws DatabaseErrorExcetion
	 */
	public void closeDatabaseFile() throws DatabaseErrorExcetion
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
