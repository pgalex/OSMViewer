package com.osmviewer.sqliteMapDataSource;

import com.osmviewer.map.MapDataSource;
import com.osmviewer.map.MapDataSourceFetchResultsHandler;
import com.osmviewer.map.exceptions.FetchingErrorException;
import com.osmviewer.mapDefenitionUtilities.MapBounds;
import com.osmviewer.mapDefenitionUtilities.DefenitionTags;
import com.osmviewer.mapDefenitionUtilities.Location;
import com.osmviewer.mapDefenitionUtilities.Tag;
import com.osmviewer.sqliteMapDataSource.exceptions.DatabaseErrorExcetion;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Map data source by SQLite database
 *
 * @author Nocomment85
 */
public class SQLiteDatabaseMapDataSource implements MapDataSource
{
	/**
	 * Connection to database
	 */
	private Connection databaseConnection;

	/**
	 * Create map database by path. Database at given path must exists and it will
	 * be open
	 *
	 * @param databasePath path to database. Must be not null, and refers to
	 * exists database
	 * @throws IllegalArgumentException databasePath is null, empty or refers to
	 * not exists database file
	 * @throws DatabaseErrorExcetion error while connecting to database
	 */
	public SQLiteDatabaseMapDataSource(String databasePath) throws IllegalArgumentException, DatabaseErrorExcetion
	{
		if (databasePath == null)
		{
			throw new IllegalArgumentException("databasePath is null");
		}
		if (databasePath.isEmpty())
		{
			throw new IllegalArgumentException("databasePath is empty");
		}
		File databaseFile = new File(databasePath);
		if (!databaseFile.exists())
		{
			throw new IllegalArgumentException("databasePath refers to exists file");
		}

		try
		{
			Class.forName("org.sqlite.JDBC");
			databaseConnection = DriverManager.getConnection("jdbc:sqlite:" + databasePath);
			databaseConnection.setAutoCommit(false);
		}
		catch (ClassNotFoundException | SQLException ex)
		{
			throw new DatabaseErrorExcetion(ex);
		}
	}

	/**
	 * Close database.
	 *
	 * @throws DatabaseErrorExcetion error while closing
	 */
	public synchronized void close() throws DatabaseErrorExcetion
	{
		try
		{
			databaseConnection.close();
		}
		catch (SQLException ex)
		{
			throw new DatabaseErrorExcetion(ex);
		}
	}

	/**
	 * Fetch map objects exists in area, and send them to fetchResultsHandler
	 *
	 * @param area area on map, deteriming what map objects need to fetch
	 * @param fetchResultsHandler handler of fetch results
	 * @throws IllegalArgumentException area is null, fetchResultsHandler is null
	 * @throws FetchingErrorException error while fetching
	 */
	@Override
	public synchronized void fetchMapObjectsInArea(MapBounds area, MapDataSourceFetchResultsHandler fetchResultsHandler) throws IllegalArgumentException,
					FetchingErrorException
	{
		try
		{
			if (area == null)
			{
				throw new IllegalArgumentException("area is null");
			}
			if (fetchResultsHandler == null)
			{
				throw new IllegalArgumentException("fetchResultsHandler is null");
			}

			if (area.isZero())
			{
				return;
			}

			try (PreparedStatement selectMapObjectsStatement = databaseConnection.prepareStatement("SELECT "
							+ "ROWID, drawingId "
							+ "FROM MapObjects "
							+ "WHERE minLatitude<=? AND maxLatitude>=? AND minLongitude<=? AND maxLongitude>=?"))
			{
				selectMapObjectsStatement.setDouble(1, area.getLatitudeMaximum());
				selectMapObjectsStatement.setDouble(2, area.getLatitudeMinimum());
				selectMapObjectsStatement.setDouble(3, area.getLongitudeMaximum());
				selectMapObjectsStatement.setDouble(4, area.getLongitudeMinimum());
				try (ResultSet selectedMapObjectsResultSet = selectMapObjectsStatement.executeQuery())
				{
					while (selectedMapObjectsResultSet.next())
					{
						long rowId = selectedMapObjectsResultSet.getLong(1);
						String drawingId = selectedMapObjectsResultSet.getString(2);

						Location[] points = selectPoints(rowId);
						if (points != null)
						{
							fetchResultsHandler.takeMapObjectData(rowId, drawingId, points);
						}
					}
				}
			}
		}
		catch (SQLException | DatabaseErrorExcetion ex)
		{
			throw new FetchingErrorException(ex);
		}
	}

	/**
	 * Select points of map objects by its id (ROWID) in database
	 *
	 * @param mapObjectRowId map object id (ROWID)
	 * @return points of map object
	 * @throws DatabaseErrorExcetion error wile getting points
	 */
	private Location[] selectPoints(long mapObjectRowId) throws DatabaseErrorExcetion
	{
		try
		{
			ArrayList<Location> selectedPoints;
			try (PreparedStatement selectPointsStatement = databaseConnection.prepareStatement("SELECT latitude,longitude FROM Points "
							+ "WHERE objectId = ?"))
			{
				selectPointsStatement.setLong(1, mapObjectRowId);
				selectedPoints = new ArrayList<>();
				try (ResultSet pointsResultSet = selectPointsStatement.executeQuery())
				{
					while (pointsResultSet.next())
					{
						double latitude = pointsResultSet.getDouble(1);
						double longitude = pointsResultSet.getDouble(2);
						selectedPoints.add(new Location(latitude, longitude));
					}
				}
			}

			return (Location[]) selectedPoints.toArray(new Location[0]);
		}
		catch (SQLException ex)
		{
			throw new DatabaseErrorExcetion(ex);
		}
	}

	/**
	 * Select tags of map objects by its id (ROWID) in database
	 *
	 * @param mapObjectRowId map object id (ROWID)
	 * @return tags of map object
	 * @throws DatabaseErrorExcetion error while getting tags
	 */
	private DefenitionTags selectTags(long mapObjectRowId) throws DatabaseErrorExcetion
	{
		try
		{
			DefenitionTags selectedTags;
			try (PreparedStatement selectTagsStatement = databaseConnection.prepareStatement("SELECT key,value FROM Tags " + "WHERE objectId = ?"))
			{
				selectTagsStatement.setLong(1, mapObjectRowId);
				selectedTags = new DefenitionTags();
				try (ResultSet tagsResultSet = selectTagsStatement.executeQuery())
				{
					while (tagsResultSet.next())
					{
						String key = tagsResultSet.getString(1);
						String value = tagsResultSet.getString(2);
						selectedTags.add(new Tag(key, value));
					}
				}
			}

			return selectedTags;
		}
		catch (SQLException ex)
		{
			throw new DatabaseErrorExcetion(ex);
		}
	}
}
