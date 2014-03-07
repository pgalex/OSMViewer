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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
	private final OsmXmlParser osmXmlParser;
	/**
	 * Database for temporary storing parsed osm nodes
	 */
	private TemporaryOsmNodesDatabase nodesTemporaryDatabase;
	/**
	 * Destenation map objects database
	 */
	private SQLiteDatabaseFiller mapObjectsDatabase;
	/**
	 * Using to find map objects ids
	 */
	private MapObjectsDrawingIdFinder identifierFinder;
	/**
	 * Is first osm way found while handling converting results
	 */
	private boolean firstWayFoundWhileConverting;

	/**
	 * Create converter
	 *
	 * @param identifierFinder finder of map objects ids. Must be not null
	 * @throws IllegalArgumentException identifierFinder is null
	 */
	public OsmXmlToSQLiteDatabaseConverter(MapObjectsDrawingIdFinder identifierFinder) throws IllegalArgumentException
	{
		if (identifierFinder == null)
		{
			throw new IllegalArgumentException("identifierFinder is null");
		}

		this.identifierFinder = identifierFinder;
		osmXmlParser = new SAXOsmXmlParser();
		nodesTemporaryDatabase = null;
		mapObjectsDatabase = null;
		firstWayFoundWhileConverting = false;
	}

	/**
	 * Create and fill database with converted from osm xml input data
	 *
	 * @param sourceFilePath path to source osm xml map data file
	 * @param databaseFilePath path to result database. If database not exists it
	 * will be created, if exists - overwrited
	 * @throws IllegalArgumentException sourceFilePath is null or empty,
	 * databaseFileName is null or empty; sourceFilePath equals databaseFilePath;
	 * @throws ParsingOsmErrorException error while parsing osm xml data
	 * @throws DeletingExistsDatabaseFileErrorException can not delete exists
	 * database file
	 * @throws DatabaseErrorExcetion error while working with database
	 * @throws FileNotFoundException source file not found
	 */
	public void convert(String sourceFilePath, String databaseFilePath) throws IllegalArgumentException, ParsingOsmErrorException,
					DeletingExistsDatabaseFileErrorException, DatabaseErrorExcetion, FileNotFoundException
	{
		if (sourceFilePath == null)
		{
			throw new IllegalArgumentException("sourceFilePath is null");
		}
		if (sourceFilePath.isEmpty())
		{
			throw new IllegalArgumentException("sourceFilePath is empty");
		}
		if (databaseFilePath == null)
		{
			throw new IllegalArgumentException("databaseFileName is null");
		}
		if (databaseFilePath.isEmpty())
		{
			throw new IllegalArgumentException("databaseFileName is empty");
		}
		if (sourceFilePath.equalsIgnoreCase(databaseFilePath))
		{
			throw new IllegalArgumentException("sourceFilePath and databaseFilePath are same");
		}

		File databaseFile = new File(databaseFilePath);
		if (databaseFile.exists())
		{
			boolean existsDatabaseDeleted = databaseFile.delete();
			if (!existsDatabaseDeleted)
			{
				throw new DeletingExistsDatabaseFileErrorException("Can not delete exists database file");
			}
		}

		nodesTemporaryDatabase = new TemporaryOsmNodesDatabase();
		mapObjectsDatabase = new SQLiteDatabaseFiller(databaseFilePath);

		firstWayFoundWhileConverting = false;
		osmXmlParser.parse(new FileInputStream(sourceFilePath), this);
		mapObjectsDatabase.commitLastBatchedMapObjects();
		mapObjectsDatabase.createIndexes();

		nodesTemporaryDatabase.closeAndDeleteDatabaseFile();
		mapObjectsDatabase.close();
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
				// nodes with tags are another map objects
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

		String drawingId = identifierFinder.findNodeDrawingId(nodeTags);
		if (drawingId != null)
		{
			Location[] nodePoints = new Location[1];
			nodePoints[0] = new Location(nodeToAdd.getLatitude(), nodeToAdd.getLongitude());
			mapObjectsDatabase.addMapObject(drawingId, nodeTags, nodePoints);
		}
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
	 * Take and process parsed osm way
	 *
	 * @param parsedWay parsed osm way. Must be not null
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
			tryTakeWay(parsedWay);
		}
		catch (DatabaseErrorExcetion ex)
		{
			// вести учет не добавленных way
			Logger.getLogger(OsmXmlToSQLiteDatabaseConverter.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Take and process parsed osm way without exceptions processing
	 *
	 * @param parsedWay parsed osm way. Must be not null
	 * @throws DatabaseErrorExcetion error while adding map object created by way
	 * to database
	 * @throws IllegalArgumentException parsedWay is null
	 */
	private void tryTakeWay(OsmWay parsedWay) throws DatabaseErrorExcetion, IllegalArgumentException
	{
		if (parsedWay == null)
		{
			throw new IllegalArgumentException("parsedWay is null");
		}

		if (!firstWayFoundWhileConverting)
		{
			// when first way found means that all nodes parsed
			nodesTemporaryDatabase.commitLastBatchedNodes();
			nodesTemporaryDatabase.createIndexes();
			firstWayFoundWhileConverting = true;
		}

		if (parsedWay.getNodesIdsCount() > 0)
		{
			DefenitionTags wayTags = new DefenitionTags();
			for (int i = 0; i < parsedWay.getTagsCount(); i++)
			{
				wayTags.add(createMapTagByOsmTag(parsedWay.getTag(i)));
			}

			String drawingId;
			if (parsedWay.isClosed())
			{
				drawingId = identifierFinder.findClosedWayDrawingId(wayTags);
			}
			else
			{
				drawingId = identifierFinder.findNonClosedWayDrawingId(wayTags);
			}

			if (drawingId != null)
			{
				Location[] wayPoints = findWayPointsInNodesTemporaryDatabase(parsedWay);
				if (wayPoints.length > 0)
				{
					mapObjectsDatabase.addMapObject(drawingId, wayTags, wayPoints);
				}
			}
		}
	}

	/**
	 * Find nodes of way locations in temporary nodes database
	 *
	 * @param way way, which nodes positions need to find
	 * @return way points, empty if can not find one or more points
	 * @throws IllegalArgumentException way is null
	 * @throws DatabaseErrorExcetion error while finding in temporary database
	 */
	private Location[] findWayPointsInNodesTemporaryDatabase(OsmWay way) throws IllegalArgumentException, DatabaseErrorExcetion
	{
		if (way == null)
		{
			throw new IllegalArgumentException("way is null");
		}

		boolean allPointsFound = true;
		Location[] wayPoints = new Location[way.getNodesIdsCount()];
		for (int i = 0; i < way.getNodesIdsCount(); i++)
		{
			TemporaryDatabaseOsmNode foundNode = nodesTemporaryDatabase.findNodeById(way.getNodeId(i));
			if (foundNode != null)
			{
				wayPoints[i] = new Location(foundNode.getLatitude(), foundNode.getLongitude());
			}
			else
			{
				allPointsFound = false;
			}
		}

		if (allPointsFound)
		{
			return wayPoints;
		}
		else
		{
			return new Location[0];
		}
	}
}
