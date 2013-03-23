package com.osmviewer.sqliteMapDataSource;

import com.osmviewer.osmXml.OsmNode;
import com.osmviewer.osmXml.OsmWay;
import com.osmviewer.osmXml.OsmXmlParser;
import com.osmviewer.osmXml.OsmXmlParsingResultsHandler;
import com.osmviewer.osmXml.exceptions.ParsingOsmErrorException;
import com.osmviewer.osmXmlSAXParsing.SAXOsmXmlParser;
import com.osmviewer.sqliteMapDataSource.exceptions.DatabaseErrorExcetion;
import com.osmviewer.sqliteMapDataSource.exceptions.DeletingExistsDatabaseFileErrorException;
import java.io.File;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Fills SQLite database with map objects converted from .osm xml data
 *
 * @author pgalex
 */
public class OsmXmlToSQLiteDatabaseConverter implements OsmXmlParsingResultsHandler
{
	/**
	 * Parser of osm xml data
	 */
	private OsmXmlParser osmXmlParser;
	/**
	 * Database for temporary storing parsed osm nodes
	 */
	private TemporaryOsmNodesDatabase nodesTemporaryDatabase;
	/**
	 * Database for temporary storing parsed osm nodes
	 */
	private SQLiteDataBaseMapDataSource database;
	/**
	 * Create converter
	 */
	public OsmXmlToSQLiteDatabaseConverter()
	{
		osmXmlParser = new SAXOsmXmlParser();
		nodesTemporaryDatabase = null;
	}

	/**
	 * Create and fill database with converted from osm xml input data
	 *
	 * @param sourceOsmXmlInputStream source osm xml input
	 * @param databaseFileName path to result database. If database not exists it
	 * will be created, if exists - overwrited
	 * @throws IllegalArgumentException sourceOsmXmlInputStream is null,
	 * databaseFileName is null or empty
	 * @throws ParsingOsmErrorException error while parsing osm xml data
	 * @throws DeletingExistsDatabaseFileErrorException can not delete exists
	 * database file
	 * @throws DatabaseErrorExcetion error while working with database
	 */
	public void convert(InputStream sourceOsmXmlInputStream, String databaseFileName) throws IllegalArgumentException,
					ParsingOsmErrorException, DeletingExistsDatabaseFileErrorException, DatabaseErrorExcetion
	{
		if (sourceOsmXmlInputStream == null)
		{
			throw new IllegalArgumentException("sourceOsmXmlInputStream is null");
		}
		if (databaseFileName == null)
		{
			throw new IllegalArgumentException("databaseFileName is null");
		}
		if (databaseFileName.isEmpty())
		{
			throw new IllegalArgumentException("databaseFileName is empty");
		}

		File databaseFile = new File(databaseFileName);
		if (databaseFile.exists())
		{
			boolean existsDatabaseDeleted = databaseFile.delete();
			if (!existsDatabaseDeleted)
			{
				throw new DeletingExistsDatabaseFileErrorException("Can not delete exists database file");
			}
		}

		nodesTemporaryDatabase = new TemporaryOsmNodesDatabase();

	//	database = new SQLiteDataBaseMapDataSource(databaseFileName);
		// инициализировать результирующую БД

		osmXmlParser.parse(sourceOsmXmlInputStream, this);

		nodesTemporaryDatabase.closeAndDeleteDatabaseFile();
	//	database.closeAndDeleteDatabaseFile();
	}

	/**
	 * Take and process parsed osm node
	 *
	 * @param parsedNode parsed osm node
	 * @throws IllegalArgumentException parsedNode is null
	 */
	@Override
	public void takeNode(OsmNode parsedNode) throws IllegalArgumentException
	{
		if (parsedNode == null)
		{
			throw new IllegalArgumentException("parsedNode is null");
		}
		try
		{
			nodesTemporaryDatabase.addNode(parsedNode);
			// если точка с тегами, добавить объект в результирующую БД
		}
		catch (DatabaseErrorExcetion ex)
		{
			Logger.getLogger(OsmXmlToSQLiteDatabaseConverter.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Take and process parsed osm way
	 *
	 * @param parsedWay parsed osm way
	 * @throws IllegalArgumentException parsedWay is null
	 */
	@Override
	public void takeWay(OsmWay parsedWay) throws IllegalArgumentException
	{
		if (parsedWay == null)
		{
			throw new IllegalArgumentException("parsedWay is null");
		}

		// найти точки из временной БД
		// добавить в результирующую БД объект
	}
}
