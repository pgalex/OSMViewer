package com.osmviewer.map;

import com.osmviewer.osmXml.OsmNode;
import com.osmviewer.osmXml.OsmWay;
import com.osmviewer.osmXml.OsmXmlParsingResultsHandler;
import java.util.ArrayList;

/**
 * Handler of parsing result, collection all parsed elements
 *
 * @author pgalex
 */
public class OsmParsingResultsCollector implements OsmXmlParsingResultsHandler
{
	/**
	 * Parsed osm nodes
	 */
	private ArrayList<OsmNode> parsedNodes;
	/**
	 * Parsed osm ways
	 */
	private ArrayList<OsmWay> parsingWays;

	/**
	 * Create empty collector
	 */
	public OsmParsingResultsCollector()
	{
		parsedNodes = new ArrayList<OsmNode>();
		parsingWays = new ArrayList<OsmWay>();
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
			throw new IllegalArgumentException();
		}

		parsedNodes.add(parsedNode);
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
			throw new IllegalArgumentException();
		}

		parsingWays.add(parsedWay);
	}

	public ArrayList<OsmNode> getParsedNodes()
	{
		return parsedNodes;
	}

	public ArrayList<OsmWay> getParsedWays()
	{
		return parsingWays;
	}
}
