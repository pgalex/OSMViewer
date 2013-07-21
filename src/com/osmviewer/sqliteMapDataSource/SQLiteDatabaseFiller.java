package com.osmviewer.sqliteMapDataSource;

import com.osmviewer.mapDefenitionUtilities.DefenitionTags;
import com.osmviewer.mapDefenitionUtilities.Location;
import com.osmviewer.mapDefenitionUtilities.MapBounds;
import com.osmviewer.sqliteMapDataSource.exceptions.DatabaseErrorExcetion;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
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
	private static int ADD_MAP_OBJECTS_MAXIMUM_BATCH_SIZE = 8000;
	/**
	 * Connection to database
	 */
	private Connection databaseConnection;
	/**
	 * Statement using for adding map objects
	 */
	private PreparedStatement insertMapObjectStatement;
	/**
	 * Currently batch size of adding map objects statement
	 */
	private int addingMapObjectsCurrentBatchSize;

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
							+ "osmId INTEGER, tags BLOB, points BLOB,"
							+ "minLatitude REAL, maxLatitude REAL, minLongitude REAL, maxLongitude REAL )");
			databaseConnection.commit();
			createMapObjectsTableStatement.close();

			insertMapObjectStatement = databaseConnection.prepareStatement("INSERT INTO MapObjects VALUES (?,?,?,?,?,?,?)");
			addingMapObjectsCurrentBatchSize = 0;
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
	 * @param id adding map object id
	 * @param tags adding map object tags
	 * @param points points, defines map objects position on map
	 * @throws IllegalArgumentException tags is null, points is null or empty
	 * @throws DatabaseErrorExcetion error while adding map object
	 */
	public synchronized void addMapObject(long id, DefenitionTags tags, Location[] points) throws IllegalArgumentException, DatabaseErrorExcetion
	{
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
			insertMapObjectStatement.setLong(1, id);
			insertMapObjectStatement.setBytes(2, convertTagsToBLOB(tags));
			insertMapObjectStatement.setBytes(3, convertPointsToBLOB(points));
			MapBounds pointsBounds = findPointsBounds(points);
			insertMapObjectStatement.setDouble(4, pointsBounds.getLatitudeMinimum());
			insertMapObjectStatement.setDouble(5, pointsBounds.getLatitudeMaximum());
			insertMapObjectStatement.setDouble(6, pointsBounds.getLongitudeMinimum());
			insertMapObjectStatement.setDouble(7, pointsBounds.getLongitudeMaximum());
			insertMapObjectStatement.addBatch();
			addingMapObjectsCurrentBatchSize++;
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
		catch (IOException ex)
		{
			throw new DatabaseErrorExcetion(ex);
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
	 * Convert map object points to bytes array
	 *
	 * @param points map object points for converting
	 * @return bytes array of points
	 * @throws IllegalArgumentException points is null, empty or contains null
	 * @throws IOException error while converting
	 */
	private byte[] convertPointsToBLOB(Location[] points) throws IllegalArgumentException, IOException
	{
		if (!isMapObjectPointsCorrect(points))
		{
			throw new IllegalArgumentException("point incorrect");
		}

		ByteArrayOutputStream pointsByteArrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream pointsDataOutputStream = new DataOutputStream(pointsByteArrayOutputStream);
		pointsDataOutputStream.writeInt(points.length);
		for (int i = 0; i < points.length; i++)
		{
			points[i].writeToStream(pointsDataOutputStream);
		}
		byte[] pointsBLOB = pointsByteArrayOutputStream.toByteArray();

		pointsByteArrayOutputStream.close();
		pointsDataOutputStream.close();

		return pointsBLOB;
	}

	/**
	 * Convert map object tags to array of bytes
	 *
	 * @param tags tags for converting
	 * @return bytes array of tags. Not null
	 * @throws IllegalArgumentException tags is null
	 * @throws IOException error while converting
	 */
	private byte[] convertTagsToBLOB(DefenitionTags tags) throws IllegalArgumentException, IOException
	{
		if (tags == null)
		{
			throw new IllegalArgumentException("tags is null");
		}

		ByteArrayOutputStream tagsByteArrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream tagsDataOutputStream = new DataOutputStream(tagsByteArrayOutputStream);

		tags.writeToStream(tagsDataOutputStream);
		byte[] tagsBLOB = tagsByteArrayOutputStream.toByteArray();

		tagsByteArrayOutputStream.close();
		tagsDataOutputStream.close();

		return tagsBLOB;
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
			Statement createIndexStatement = databaseConnection.createStatement();
			createIndexStatement.executeUpdate("CREATE INDEX IF NOT EXISTS MapObjectBoundsIndex ON "
							+ "MapObjects (minLatitude, maxLatitude, minLongitude, maxLongitude)");
			databaseConnection.commit();
			createIndexStatement.close();
		}
		catch (SQLException ex)
		{
			throw new DatabaseErrorExcetion(ex);
		}
	}
}
