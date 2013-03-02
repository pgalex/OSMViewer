package com.osmviewer.osmXml;

import com.osmviewer.osmXml.exceptions.ParsingOsmErrorException;
import java.io.InputStream;

/**
 * .osm xml data parser
 *
 * @author pgalex
 */
public interface OsmXmlParser
{
	/**
	 * Parse openstreetmap xml map data from stream
	 *
	 * @param input input osm xml data stream
	 * @throws IllegalArgumentException input is null
	 * @throws ParsingOsmErrorException error while parsing data from input
	 */
	void parse(InputStream input) throws IllegalArgumentException, ParsingOsmErrorException;
}
