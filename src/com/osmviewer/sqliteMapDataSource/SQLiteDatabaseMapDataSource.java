package com.osmviewer.sqliteMapDataSource;

import com.osmviewer.map.MapDataSource;
import com.osmviewer.map.MapDataSourceFetchResultsHandler;
import com.osmviewer.map.exceptions.FetchingErrorException;
import com.osmviewer.mapDefenitionUtilities.MapBounds;
import com.osmviewer.mapDefenitionUtilities.DefenitionTags;
import com.osmviewer.mapDefenitionUtilities.Location;
import com.osmviewer.sqliteMapDataSource.exceptions.DatabaseErrorExcetion;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
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

			PreparedStatement selectMapObjectsStatement = databaseConnection.prepareStatement("SELECT * FROM MapObjects "
							+ "WHERE minLatitude<=? AND maxLatitude>=? "
							+ "AND minLongitude<=? AND maxLongitude>=?");
			selectMapObjectsStatement.setDouble(1, area.getLatitudeMaximum());
			selectMapObjectsStatement.setDouble(2, area.getLatitudeMinimum());
			selectMapObjectsStatement.setDouble(3, area.getLongitudeMaximum());
			selectMapObjectsStatement.setDouble(4, area.getLongitudeMinimum());
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
