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

			PreparedStatement selectMapObjectsStatement = databaseConnection.prepareStatement("SELECT "
							+ "ROWID, osmId "
							+ "FROM MapObjects "
							+ "WHERE minLatitude<=? AND maxLatitude>=? AND minLongitude<=? AND maxLongitude>=?");
			selectMapObjectsStatement.setDouble(1, area.getLatitudeMaximum());
			selectMapObjectsStatement.setDouble(2, area.getLatitudeMinimum());
			selectMapObjectsStatement.setDouble(3, area.getLongitudeMaximum());
			selectMapObjectsStatement.setDouble(4, area.getLongitudeMinimum());
			ResultSet selectedMapObjectsResultSet = selectMapObjectsStatement.executeQuery();

			while (selectedMapObjectsResultSet.next())
			{
				long rowId = selectedMapObjectsResultSet.getLong(1);
				long osmId = selectedMapObjectsResultSet.getLong(2);

				DefenitionTags tags = selectTags(rowId);
				Location[] points = selectPoints(rowId);
				if (tags != null && points != null)
				{
					fetchResultsHandler.takeMapObjectData(osmId, tags, points);
				}
			}

			selectedMapObjectsResultSet.close();
			selectMapObjectsStatement.close();
		}
		catch (SQLException ex)
		{
			throw new FetchingErrorException(ex);
		}
		catch (DatabaseErrorExcetion ex)
		{
			throw new FetchingErrorException(ex);
		}
	}

	/**
	 * Select points of map objects by its id (ROWID) in database
	 *
	 * @param mapObjectId map object id (ROWID)
	 * @return points of map object
	 * @throws DatabaseErrorExcetion error wile getting points
	 */
	private Location[] selectPoints(long mapObjectId) throws DatabaseErrorExcetion
	{
		try
		{
			PreparedStatement selectPointsStatement = databaseConnection.prepareStatement("SELECT latitude,longitude FROM Points "
							+ "WHERE objectId = ?");
			selectPointsStatement.setLong(1, mapObjectId);

			ArrayList<Location> selectedPoints = new ArrayList<Location>();

			ResultSet pointsResultSet = selectPointsStatement.executeQuery();
			while (pointsResultSet.next())
			{
				double latitude = pointsResultSet.getDouble(1);
				double longitude = pointsResultSet.getDouble(2);
				selectedPoints.add(new Location(latitude, longitude));
			}
			pointsResultSet.close();
			selectPointsStatement.close();

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
	 * @param mapObjectId map object id (ROWID)
	 * @return tags of map object
	 * @throws DatabaseErrorExcetion error while getting tags
	 */
	private DefenitionTags selectTags(long mapObjectId) throws DatabaseErrorExcetion
	{
		try
		{
			PreparedStatement selectTagsStatement = databaseConnection.prepareStatement("SELECT key,value FROM Tags "
							+ "WHERE objectId = ?");
			selectTagsStatement.setLong(1, mapObjectId);

			DefenitionTags selectedTags = new DefenitionTags();
			ResultSet tagsResultSet = selectTagsStatement.executeQuery();
			while (tagsResultSet.next())
			{
				String key = tagsResultSet.getString(1);
				String value = tagsResultSet.getString(2);
				selectedTags.add(new Tag(key, value));
			}
			tagsResultSet.close();
			selectTagsStatement.close();

			return selectedTags;
		}
		catch (SQLException ex)
		{
			throw new DatabaseErrorExcetion(ex);
		}
	}
}
