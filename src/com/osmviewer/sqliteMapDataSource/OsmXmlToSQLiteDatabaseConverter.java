package com.osmviewer.sqliteMapDataSource;

import com.osmviewer.osmXml.OsmXmlParser;
import com.osmviewer.osmXmlSAXParsing.SAXOsmXmlParser;
import java.io.InputStream;

/**
 * Fills SQLite database with map objects converted from .osm xml data
 *
 * @author pgalex
 */
public class OsmXmlToSQLiteDatabaseConverter
{
	private OsmXmlParser osmXmlParser;

	/**
	 * Create converter
	 */
	public OsmXmlToSQLiteDatabaseConverter()
	{
		osmXmlParser = new SAXOsmXmlParser();
	}

	/**
	 * Create and fill database with converted from osm xml input data
	 *
	 * @param sourceOsmXmlInputStream source osm xml input
	 * @param databaseFileName path to result database. If database not exists it
	 * will be created, if exists - overwrited
	 * @throws IllegalArgumentException sourceOsmXmlInputStream is null,
	 * databaseFileName is null or empty
	 */
	public void convert(InputStream sourceOsmXmlInputStream, String databaseFileName) throws IllegalArgumentException
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

		// run parsing with SAXOsmXmlParser

		// fill temponaty database with nodes, create and add to destenation database MapPoint s, OsmNodes with tags (OsmMapObjectsConverter)

		// convert, create and add to destenation database objects by OsmWay, using temponary database to find nodes
	}
}
