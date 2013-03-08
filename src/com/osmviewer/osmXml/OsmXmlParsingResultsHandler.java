package com.osmviewer.osmXml;

/**
 * Handler of parsing .osm xml data results
 *
 * @author pgalex
 */
public interface OsmXmlParsingResultsHandler
{
	/**
	 * Take and process parsed osm node
	 *
	 * @param parsedNode parsed osm node
	 * @throws IllegalArgumentException parsedNode is null
	 */
	void takeNode(OsmNode parsedNode) throws IllegalArgumentException;

	/**
	 * Take and process parsed osm way
	 *
	 * @param parsedWay parsed osm way
	 * @throws IllegalArgumentException parsedWay is null
	 */
	void takeWay(OsmWay parsedWay) throws IllegalArgumentException;
}
