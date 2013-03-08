package com.osmviewer.osmXmlParsingTests;

import com.osmviewer.osmXml.OsmNode;
import com.osmviewer.osmXml.OsmWay;
import com.osmviewer.osmXml.OsmXmlParsingHandler;
import java.util.ArrayList;

/**
 * Test implementation of OsmXmlParsingHandler
 *
 * @author pgalex
 */
public class TestOsmXmlParsingHandler implements OsmXmlParsingHandler
{
	public ArrayList<OsmNode> nodes;
	public ArrayList<OsmWay> ways;
	
	public TestOsmXmlParsingHandler()
	{
		nodes = new ArrayList<OsmNode>();
		ways = new ArrayList<OsmWay>();
	}
	
	@Override
	public void takeNode(OsmNode parsedNode) throws IllegalArgumentException
	{
		nodes.add(parsedNode);
	}
	
	@Override
	public void takeWay(OsmWay parsedWay) throws IllegalArgumentException
	{
		ways.add(parsedWay);
	}
}
