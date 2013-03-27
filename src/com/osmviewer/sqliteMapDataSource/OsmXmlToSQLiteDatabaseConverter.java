package com.osmviewer.sqliteMapDataSource;

import com.osmviewer.osmXml.OsmNode;
import com.osmviewer.osmXml.OsmWay;
import com.osmviewer.osmXml.OsmXmlParser;
import com.osmviewer.osmXml.OsmXmlParsingResultsHandler;
import com.osmviewer.osmXml.exceptions.ParsingOsmErrorException;
import com.osmviewer.osmXmlSAXParsing.SAXOsmXmlParser;
import com.osmviewer.sqliteMapDataSource.exceptions.DatabaseErrorExcetion;
import com.osmviewer.sqliteMapDataSource.exceptions.DeletingExistsDatabaseFileErrorException;
import com.osmviewer.mapDefenitionUtilities.DefenitionTags;
import com.osmviewer.mapDefenitionUtilities.Location;
import com.osmviewer.mapDefenitionUtilities.Tag;
import com.osmviewer.osmXml.OsmTag;
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
	 * Destenation map objects database
	 */
	private SQLiteDataBaseMapDataSource mapObjectsDatabase;
	/**
	 * Is first osm way found while handling converting results
	 */
	private boolean firstWayFoundWhileConverting;

	/**
	 * Create converter
	 */
	public OsmXmlToSQLiteDatabaseConverter()
	{
		osmXmlParser = new SAXOsmXmlParser();
		nodesTemporaryDatabase = null;
		mapObjectsDatabase = null;
		firstWayFoundWhileConverting = false;
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
		mapObjectsDatabase = new SQLiteDataBaseMapDataSource(databaseFileName);

		firstWayFoundWhileConverting = false;
		osmXmlParser.parse(sourceOsmXmlInputStream, this);

		nodesTemporaryDatabase.closeAndDeleteDatabaseFile();
		mapObjectsDatabase.close();
	}

	/**
	 * Create map tag by osm tag
	 *
	 * @param osmTag osm tag
	 * @return map tag created by osm tag
	 * @throws IllegalArgumentException osmTag is null
	 */
	private static Tag createMapTagByOsmTag(OsmTag osmTag) throws IllegalArgumentException
	{
		if (osmTag == null)
		{
			throw new IllegalArgumentException("osmTag is null");
		}

		return new Tag(osmTag.getKey(), osmTag.getValue());
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

			if (parsedNode.getTagsCount() > 0)
			{
				addNodeToMapObjectsDatabase(parsedNode);
			}
		}
		catch (DatabaseErrorExcetion ex)
		{
			// вести учет не добавленных точек
			Logger.getLogger(OsmXmlToSQLiteDatabaseConverter.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Add osm node to map objects database
	 *
	 * @param nodeToAdd adding node
	 * @throws IllegalArgumentException nodeToAdd is null or have not tags
	 * @throws DatabaseErrorExcetion error while adding
	 */
	private void addNodeToMapObjectsDatabase(OsmNode nodeToAdd) throws IllegalArgumentException, DatabaseErrorExcetion
	{
		if (nodeToAdd == null)
		{
			throw new IllegalArgumentException("nodeToAdd is null");
		}
		if (nodeToAdd.getTagsCount() == 0)
		{
			throw new IllegalArgumentException("nodeToAdd havo no tags");
		}

		DefenitionTags nodeTags = new DefenitionTags();
		for (int i = 0; i < nodeToAdd.getTagsCount(); i++)
		{
			nodeTags.add(createMapTagByOsmTag(nodeToAdd.getTag(i)));
		}

		Location[] nodePoints = new Location[1];
		nodePoints[0] = new Location(nodeToAdd.getLatitude(), nodeToAdd.getLongitude());
		mapObjectsDatabase.addMapObject(nodeToAdd.getId(), nodeTags, nodePoints);
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

		try
		{
			if (!firstWayFoundWhileConverting)
			{
				// when first way found means that all nodes parsed
				nodesTemporaryDatabase.commitLastBatchedNodes();
				firstWayFoundWhileConverting = true;
			}

			// найти точки из временной БД
			// добавить в результирующую БД объект
		}
		catch (DatabaseErrorExcetion ex)
		{
			// вести учет не добавленных way
			Logger.getLogger(OsmXmlToSQLiteDatabaseConverter.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
