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
	 * Taked from parser osm nodes
	 */
	private ArrayList<OsmNode> takedNodes;
	/**
	 * ParTaked from parser osm ways
	 */
	private ArrayList<OsmWay> takedWays;

	/**
	 * Create empty
	 */
	public OsmParsingResultsCollector()
	{
		takedNodes = new ArrayList<OsmNode>();
		takedWays = new ArrayList<OsmWay>();
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

		takedNodes.add(parsedNode);
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

		takedWays.add(parsedWay);
	}

	/**
	 * Get osm nodes taked from parser
	 *
	 * @return osm nodes taked from parser
	 */
	public ArrayList<OsmNode> getTakedNodes()
	{
		return takedNodes;
	}

	/**
	 * Get osm ways taked from parser
	 *
	 * @return osm ways taked from parserF
	 */
	public ArrayList<OsmWay> getTakedWays()
	{
		return takedWays;
	}
}
