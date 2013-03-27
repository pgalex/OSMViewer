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
 * Map data source by SQLite database
 *
 * @author Nocomment85
 */
public class SQLiteDataBaseMapDataSource implements MapDataSource
{
	/**
	 * Connection to database
	 */
	private Connection databaseConnection;

	/**
	 * Create map database by path. If database not exists it will be created, if
	 * exists - open
	 *
	 * @param databasePath path to database
	 * @throws IllegalArgumentException databasePath is null or empty
	 * @throws DatabaseErrorExcetion error while connecting to database
	 */
	public SQLiteDataBaseMapDataSource(String databasePath) throws IllegalArgumentException, DatabaseErrorExcetion
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

			Statement createMapObjectsTableStatement = databaseConnection.createStatement();
			createMapObjectsTableStatement.executeUpdate("CREATE TABLE MapObjects ( id INTEGER PRIMARY KEY, "
							+ "tags BLOB, points BLOB,"
							+ "minLatitude REAL, maxLatitude REAL, minLongitude REAL, maxLongitude REAL )");
			createMapObjectsTableStatement.close();
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
	 * @param id adding map object id
	 * @param tags adding map object tags
	 * @param points points, defines map objects position on map
	 * @throws IllegalArgumentException tags is null, points is null or empty
	 * @throws DatabaseErrorExcetion error while adding map object
	 */
	public void addMapObject(long id, DefenitionTags tags, Location[] points) throws IllegalArgumentException, DatabaseErrorExcetion
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
			PreparedStatement insertStatement = databaseConnection.prepareStatement("INSERT INTO MapObjects VALUES (?,?,?,?,?,?,?)");

			insertStatement.setLong(1, id);
			insertStatement.setBytes(2, convertTagsToBLOB(tags));
			insertStatement.setBytes(3, convertPointsToBLOB(points));

			MapBounds pointsBounds = findPointsBounds(points);
			insertStatement.setDouble(4, pointsBounds.getLatitudeMinimum());
			insertStatement.setDouble(5, pointsBounds.getLatitudeMaximum());
			insertStatement.setDouble(6, pointsBounds.getLongitudeMinimum());
			insertStatement.setDouble(7, pointsBounds.getLongitudeMaximum());

			insertStatement.executeUpdate();

			insertStatement.close();
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
	public void close() throws DatabaseErrorExcetion
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
	 */
	@Override
	public void fetchMapObjectsInArea(MapBounds area, MapDataSourceFetchResultsHandler fetchResultsHandler) throws IllegalArgumentException
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
}
