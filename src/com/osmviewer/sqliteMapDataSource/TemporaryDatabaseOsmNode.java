package com.osmviewer.sqliteMapDataSource;

import com.osmviewer.sqliteMapDataSource.exceptions.DatabaseErrorExcetion;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Osm node from temporary nodes database
 *
 * @author pgalex
 */
public class TemporaryDatabaseOsmNode
{
	/**
	 * openstreetmap unique id of node
	 */
	private long id;
	/**
	 * latitude of node position on map
	 */
	private double latitude;
	/**
	 * longitude of node position on map
	 */
	private double longitude;

	/**
	 * Create by results set from temporary nodes database
	 *
	 * @param temporaryDatabaseResultSet results set from temporary nodes
	 * database, using to create node
	 * @throws IllegalArgumentException temporaryDatabaseResultSet is null
	 * @throws DatabaseErrorExcetion error while getting
	 */
	public TemporaryDatabaseOsmNode(ResultSet temporaryDatabaseResultSet) throws IllegalArgumentException, DatabaseErrorExcetion
	{
		if (temporaryDatabaseResultSet == null)
		{
			throw new IllegalArgumentException("temporaryDatabaseResultSet is null");
		}

		try
		{
			id = temporaryDatabaseResultSet.getLong(1);
			latitude = temporaryDatabaseResultSet.getDouble(2);
			longitude = temporaryDatabaseResultSet.getDouble(3);
		}
		catch (SQLException ex)
		{
			throw new DatabaseErrorExcetion(ex);
		}
	}

	/**
	 * Get openstreetmap unique id of node
	 *
	 * @return id of node
	 */
	public long getId()
	{
		return id;
	}

	/**
	 * Get latitude
	 *
	 * @return latitude of node position on map
	 */
	public double getLatitude()
	{
		return latitude;
	}

	/**
	 * Get longitude
	 *
	 * @return longitude of node position on map
	 */
	public double getLongitude()
	{
		return longitude;
	}
}
