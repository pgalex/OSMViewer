package com.osmviewer.sqliteMapDataSource;

import com.osmviewer.map.MapDataSource;
import com.osmviewer.map.MapDataSourceFetchResultsHandler;
import com.osmviewer.map.exceptions.FetchingErrorException;
import com.osmviewer.mapDefenitionUtilities.MapBounds;
import com.osmviewer.mapDefenitionUtilities.DefenitionTags;
import com.osmviewer.mapDefenitionUtilities.Location;
import com.osmviewer.sqliteMapDataSource.exceptions.DatabaseErrorExcetion;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Map data source by SQLite database
 *
 * @author Nocomment85
 */
public class SQLiteDatabaseMapDataSource implements MapDataSource
{
	/**
	 * Sector size by latitude
	 */
	private static double SECTOR_LATITUDE_SIZE = 2.00;
	/**
	 * Sector size by longitude
	 */
	private static double SECTOR_LONGITUDE_SIZE = 2.00;
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
	 * Create map database by path. If database not exists it will be created, if
	 * exists - open
	 *
	 * @param databasePath path to database
	 * @throws IllegalArgumentException databasePath is null or empty
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

		try
		{
			Class.forName("org.sqlite.JDBC");
			databaseConnection = DriverManager.getConnection("jdbc:sqlite:" + databasePath);
			databaseConnection.setAutoCommit(false);

			Statement createMapObjectsTableStatement = databaseConnection.createStatement();
			createMapObjectsTableStatement.executeUpdate("CREATE TABLE IF NOT EXISTS MapObjects ("
							+ "osmId INTEGER, tags BLOB, points BLOB,"
							+ "minLatitudeSector INTEGER, maxLatitudeSector INTEGER,"
							+ "minLongitudeSector INTEGER, maxLongitudeSector INTEGER,"
							+ "minLatitude REAL, maxLatitude REAL, minLongitude REAL, maxLongitude REAL )");
			databaseConnection.commit();
			createMapObjectsTableStatement.close();

			insertMapObjectStatement = databaseConnection.prepareStatement("INSERT INTO MapObjects VALUES (?,?,?,?,?,?,?,?,?,?,?)");
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
			insertMapObjectStatement.setInt(4, findSectorLatitudeIndex(pointsBounds.getLatitudeMinimum()));
			insertMapObjectStatement.setInt(5, findSectorLatitudeIndex(pointsBounds.getLatitudeMaximum()));
			insertMapObjectStatement.setInt(6, findSectorLongitudeIndex(pointsBounds.getLongitudeMinimum()));
			insertMapObjectStatement.setInt(7, findSectorLongitudeIndex(pointsBounds.getLongitudeMaximum()));
			insertMapObjectStatement.setDouble(8, pointsBounds.getLatitudeMinimum());
			insertMapObjectStatement.setDouble(9, pointsBounds.getLatitudeMaximum());
			insertMapObjectStatement.setDouble(10, pointsBounds.getLongitudeMinimum());
			insertMapObjectStatement.setDouble(11, pointsBounds.getLongitudeMaximum());
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
	 * Find latitude index of sector for latitude value
	 *
	 * @param latitude latitude to find index by
	 * @return found sector latitude index
	 */
	private int findSectorLatitudeIndex(double latitude)
	{
		return (int) Math.floor(latitude / SECTOR_LATITUDE_SIZE);
	}

	/**
	 * Find longitude index of sector for longitude value
	 *
	 * @param longitude longitude to find index by
	 * @return found sector longitude index
	 */
	private int findSectorLongitudeIndex(double longitude)
	{
		return (int) Math.floor(longitude / SECTOR_LONGITUDE_SIZE);
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

			PreparedStatement selectMapObjectsStatement = databaseConnection.prepareStatement("SELECT * FROM MapObjects "
							+ "WHERE minLatitudeSector<=? AND maxLatitudeSector>=? AND "
							+ "minLongitudeSector<=? AND maxLongitudeSector>=? AND "
							+ "minLatitude<=? AND maxLatitude>=? AND "
							+ "minLongitude<=? AND maxLongitude>=?");
			selectMapObjectsStatement.setDouble(1, findSectorLatitudeIndex(area.getLatitudeMaximum()));
			selectMapObjectsStatement.setDouble(2, findSectorLatitudeIndex(area.getLatitudeMinimum()));
			selectMapObjectsStatement.setDouble(3, findSectorLongitudeIndex(area.getLongitudeMaximum()));
			selectMapObjectsStatement.setDouble(4, findSectorLongitudeIndex(area.getLongitudeMinimum()));
			selectMapObjectsStatement.setDouble(5, area.getLatitudeMaximum());
			selectMapObjectsStatement.setDouble(6, area.getLatitudeMinimum());
			selectMapObjectsStatement.setDouble(7, area.getLongitudeMaximum());
			selectMapObjectsStatement.setDouble(8, area.getLongitudeMinimum());
			ResultSet selectedMapObjectsResultSet = selectMapObjectsStatement.executeQuery();

			while (selectedMapObjectsResultSet.next())
			{
				long id = selectedMapObjectsResultSet.getLong(1);
				DefenitionTags tags = readTagFromBLOB(selectedMapObjectsResultSet.getBytes(2));
				Location[] points = readPointsFromBLOB(selectedMapObjectsResultSet.getBytes(3));
				if (tags != null && points != null)
				{
					fetchResultsHandler.takeMapObjectData(id, tags, points);
				}
			}

			selectedMapObjectsResultSet.close();
			selectMapObjectsStatement.close();
		}
		catch (SQLException ex)
		{
			throw new FetchingErrorException(ex);
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
							+ "MapObjects (minLatitudeSector, maxLatitudeSector, minLongitudeSector, maxLongitudeSector, "
							+ "minLatitude, maxLatitude, minLongitude, maxLongitude)");
			databaseConnection.commit();
			createIndexStatement.close();
		}
		catch (SQLException ex)
		{
			throw new DatabaseErrorExcetion(ex);
		}
	}

	/**
	 * Read map object tags from BLOB
	 *
	 * @param tagsBLOB map object tags BLOB
	 * @return tags read from BLOB. null if can not read
	 */
	private DefenitionTags readTagFromBLOB(byte[] tagsBLOB)
	{
		try
		{
			DefenitionTags tagFromBLOB = new DefenitionTags();

			ByteArrayInputStream tagsByteArrayInputStream = new ByteArrayInputStream(tagsBLOB);
			DataInputStream tagsDataInputStream = new DataInputStream(tagsByteArrayInputStream);
			tagFromBLOB.readFromStream(tagsDataInputStream);
			tagsByteArrayInputStream.close();

			return tagFromBLOB;
		}
		catch (Exception ex)
		{
			return null;
		}
	}

	/**
	 * Read map object points from BLOB
	 *
	 * @param pointsBLOB map object points BLOB
	 * @return points read from BLOB. null if can not read
	 */
	private Location[] readPointsFromBLOB(byte[] pointsBLOB)
	{
		try
		{
			ByteArrayInputStream pointsByteArrayInputStream = new ByteArrayInputStream(pointsBLOB);
			DataInputStream pointDataInputStream = new DataInputStream(pointsByteArrayInputStream);
			int pointsCount = pointDataInputStream.readInt();
			Location[] pointsFromBLOB = new Location[pointsCount];
			for (int i = 0; i < pointsCount; i++)
			{
				pointsFromBLOB[i] = new Location();
				pointsFromBLOB[i].readFromStream(pointDataInputStream);
			}
			pointsByteArrayInputStream.close();
			pointDataInputStream.close();

			return pointsFromBLOB;
		}
		catch (Exception ex)
		{
			return null;
		}
	}
}
