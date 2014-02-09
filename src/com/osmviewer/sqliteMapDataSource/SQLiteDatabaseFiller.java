package com.osmviewer.sqliteMapDataSource;

import com.osmviewer.mapDefenitionUtilities.DefenitionTags;
import com.osmviewer.mapDefenitionUtilities.Location;
import com.osmviewer.mapDefenitionUtilities.MapBounds;
import com.osmviewer.mapDefenitionUtilities.Tag;
import com.osmviewer.sqliteMapDataSource.exceptions.DatabaseErrorExcetion;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Filler of sqlite database using as map data source
 *
 * @author pgalex
 */
public class SQLiteDatabaseFiller
{
	/**
	 * Maximum number of insert commands in adding map objects statement bactch
	 */
	private static int ADD_MAP_OBJECTS_MAXIMUM_BATCH_SIZE = 1000;
	/**
	 * Connection to database
	 */
	private Connection databaseConnection;
	/**
	 * Statement using for adding map objects
	 */
	private PreparedStatement insertMapObjectStatement;
	/**
	 * Statement using for adding points of map object
	 */
	private PreparedStatement insertPointStatement;
	/**
	 * Statement using for adding tags of map object
	 */
	private PreparedStatement insertTagStatement;
	/**
	 * Currently batch size of adding map objects statement
	 */
	private int addingMapObjectsCurrentBatchSize;
	/**
	 * Counter of adding map objects index (ROWID) in database
	 */
	private int mapObjectIndex;

	/**
	 * Create with path to database. Database file must be not exists. Database
	 * will be created at path
	 *
	 * @param databasePath path to database. Must be not null, not empty and
	 * refers to not exists file
	 * @throws IllegalArgumentException databasePath is null, or file it refers
	 * exists
	 * @throws DatabaseErrorExcetion error while creating and getting connect to
	 * database
	 */
	public SQLiteDatabaseFiller(String databasePath) throws IllegalArgumentException, DatabaseErrorExcetion
	{
		// database must be added fully at one time cuz of adding rules (mapObjectIndex)
		if (databasePath == null)
		{
			throw new IllegalArgumentException("databasePath is null");
		}
		if (databasePath.isEmpty())
		{
			throw new IllegalArgumentException("databasePath is empty");
		}
		File databaseFile = new File(databasePath);
		if (databaseFile.exists())
		{
			throw new IllegalArgumentException("databasePath refers to exists file");
		}

		try
		{
			Class.forName("org.sqlite.JDBC");
			databaseConnection = DriverManager.getConnection("jdbc:sqlite:" + databasePath);
			databaseConnection.setAutoCommit(false);

			Statement createMapObjectsTableStatement = databaseConnection.createStatement();
			createMapObjectsTableStatement.executeUpdate("CREATE TABLE IF NOT EXISTS MapObjects ("
							+ "drawingId TEXT, "
							+ "minLatitude REAL, maxLatitude REAL, minLongitude REAL, maxLongitude REAL )");
			databaseConnection.commit();
			createMapObjectsTableStatement.close();

			Statement createPointsTableStatement = databaseConnection.createStatement();
			createPointsTableStatement.executeUpdate("CREATE TABLE IF NOT EXISTS Points (objectId INTEGER, latitude REAL, longitude REAL)");
			databaseConnection.commit();
			createPointsTableStatement.close();

			Statement createTagsTableStatement = databaseConnection.createStatement();
			createTagsTableStatement.executeUpdate("CREATE TABLE IF NOT EXISTS Tags (objectId INTEGER, key TEXT, value TEXT)");
			databaseConnection.commit();
			createTagsTableStatement.close();

			insertMapObjectStatement = databaseConnection.prepareStatement("INSERT INTO MapObjects VALUES (?,?,?,?,?)");
			insertPointStatement = databaseConnection.prepareStatement("INSERT INTO Points VALUES (?,?,?)");
			insertTagStatement = databaseConnection.prepareStatement("INSERT INTO Tags VALUES (?,?,?)");
			addingMapObjectsCurrentBatchSize = 0;
			mapObjectIndex = 1;
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
	 * Add map object to database
	 *
	 * @param drawingId adding map object id. Must be not null
	 * @param tags adding map object tags
	 * @param points points, defines map objects position on map
	 * @throws IllegalArgumentException tags is null, points is null or empty;
	 * drawingId is null
	 * @throws DatabaseErrorExcetion error while adding map object
	 */
	public synchronized void addMapObject(String drawingId, DefenitionTags tags, Location[] points) throws IllegalArgumentException, DatabaseErrorExcetion
	{
		if (drawingId == null)
		{
			throw new IllegalArgumentException("drawingId is null");
		}
		if (tags == null)
		{
			throw new IllegalArgumentException("tags is null");
		}
		if (!isMapObjectPointsCorrect(points))
		{
			throw new IllegalArgumentException("points incorrect");
		}

		try
		{
			insertPoints(points);
			insertTags(tags);
			insertMapObjectStatement.setString(1, drawingId);
			MapBounds pointsBounds = findPointsBounds(points);
			insertMapObjectStatement.setDouble(2, pointsBounds.getLatitudeMinimum());
			insertMapObjectStatement.setDouble(3, pointsBounds.getLatitudeMaximum());
			insertMapObjectStatement.setDouble(4, pointsBounds.getLongitudeMinimum());
			insertMapObjectStatement.setDouble(5, pointsBounds.getLongitudeMaximum());
			insertMapObjectStatement.addBatch();
			addingMapObjectsCurrentBatchSize++;
			mapObjectIndex++;
			if (addingMapObjectsCurrentBatchSize >= ADD_MAP_OBJECTS_MAXIMUM_BATCH_SIZE)
			{
				commitAddedMapObjects();
				addingMapObjectsCurrentBatchSize = 0;
			}
		}
		catch (SQLException ex)
		{
			throw new DatabaseErrorExcetion(ex);
		}
	}

	/**
	 * Insert points link with currently adding map object by mapObjectIndex
	 *
	 * @param points points of map object to add. Must be not null
	 * @throws IllegalArgumentException points is null
	 * @throws DatabaseErrorExcetion error while adding points
	 */
	private void insertPoints(Location[] points) throws IllegalArgumentException, DatabaseErrorExcetion
	{
		if (points == null)
		{
			throw new IllegalArgumentException("points is null");
		}

		for (int i = 0; i < points.length; i++)
		{
			try
			{
				insertPointStatement.setLong(1, mapObjectIndex);
				insertPointStatement.setDouble(2, points[i].getLatitude());
				insertPointStatement.setDouble(3, points[i].getLongitude());
				insertPointStatement.addBatch();
			}
			catch (SQLException ex)
			{
				throw new DatabaseErrorExcetion(ex);
			}
		}
	}

	/**
	 * Insert tags link with currently adding map object by mapObjectIndex
	 *
	 * @param tags tags of map object to add. Must be not null
	 * @throws IllegalArgumentException tags is null
	 * @throws DatabaseErrorExcetion error while addding
	 */
	private void insertTags(DefenitionTags tags) throws IllegalArgumentException, DatabaseErrorExcetion
	{
		if (tags == null)
		{
			throw new IllegalArgumentException("tags is null");
		}

		for (int i = 0; i < tags.count(); i++)
		{
			try
			{
				Tag tag = tags.get(i);
				insertTagStatement.setLong(1, mapObjectIndex);
				insertTagStatement.setString(2, tag.getKey());
				insertTagStatement.setString(3, tag.getValue());
				insertTagStatement.addBatch();
			}
			catch (SQLException ex)
			{
				throw new DatabaseErrorExcetion(ex);
			}
		}
	}

	/**
	 * Commit added map objects to database
	 *
	 * @throws DatabaseErrorExcetion error while commiting
	 */
	private synchronized void commitAddedMapObjects() throws DatabaseErrorExcetion
	{
		try
		{
			insertMapObjectStatement.executeBatch();
			insertPointStatement.executeBatch();
			insertTagStatement.executeBatch();
			databaseConnection.commit();
		}
		catch (SQLException ex)
		{
			throw new DatabaseErrorExcetion(ex);
		}
	}

	/**
	 * Commit last batched but not commited added map objects to database
	 *
	 * @throws DatabaseErrorExcetion error while commiting
	 */
	public synchronized void commitLastBatchedMapObjects() throws DatabaseErrorExcetion
	{
		if (addingMapObjectsCurrentBatchSize > 0)
		{
			commitAddedMapObjects();
		}
	}

	/**
	 * Find bounds including all given points
	 *
	 * @param points points on map which surrounding bounds need to find
	 * @return bounds including all given points
	 * @throws IllegalArgumentException points is null, empty or contains null
	 */
	private MapBounds findPointsBounds(Location[] points) throws IllegalArgumentException
	{
		// todo move in MapBounds
		if (!isMapObjectPointsCorrect(points))
		{
			throw new IllegalArgumentException("points incorrect");
		}

		double minLatitude = points[0].getLatitude();
		double minLongitude = points[0].getLongitude();
		double maxLatitude = points[0].getLatitude();
		double maxLongitude = points[0].getLongitude();

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

		return new MapBounds(minLatitude, maxLatitude, minLongitude, maxLongitude);
	}

	/**
	 * Is map objects points correct
	 *
	 * @param points map objects points
	 * @return is map object points not null, not empty, not contains null
	 * elements
	 */
	private boolean isMapObjectPointsCorrect(Location[] points)
	{
		if (points == null)
		{
			return false;
		}
		if (points.length == 0)
		{
			return false;
		}
		for (int i = 0; i < points.length; i++)
		{
			if (points[i] == null)
			{
				return false;
			}
		}
		return true;
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
			insertMapObjectStatement.close();
			insertPointStatement.close();
			insertTagStatement.close();
			databaseConnection.close();
		}
		catch (SQLException ex)
		{
			throw new DatabaseErrorExcetion(ex);
		}
	}

	/**
	 * Create indexes
	 *
	 * @throws DatabaseErrorExcetion error while creating indexes
	 */
	public synchronized void createIndexes() throws DatabaseErrorExcetion
	{
		try
		{
			Statement createMapObjectsIndexStatement = databaseConnection.createStatement();
			createMapObjectsIndexStatement.executeUpdate("CREATE INDEX IF NOT EXISTS MapObjectBoundsIndex ON "
							+ "MapObjects (minLatitude, maxLatitude, minLongitude, maxLongitude)");
			databaseConnection.commit();
			createMapObjectsIndexStatement.close();

			Statement createTagsIndexStatement = databaseConnection.createStatement();
			createTagsIndexStatement.executeUpdate("CREATE INDEX IF NOT EXISTS TagsObjectIdIndex ON "
							+ "Tags (objectId)");
			databaseConnection.commit();
			createTagsIndexStatement.close();

			Statement createPointsIndexStatement = databaseConnection.createStatement();
			createPointsIndexStatement.executeUpdate("CREATE INDEX IF NOT EXISTS pointsObjectIdIndex ON "
							+ "Points (objectId)");
			databaseConnection.commit();
			createPointsIndexStatement.close();
		}
		catch (SQLException ex)
		{
			throw new DatabaseErrorExcetion(ex);
		}
	}
}
