package osmXml;

import java.util.ArrayList;

/**
 * Way in .osm file
 *
 * @author preobrazhentsev
 */
public class OsmWay extends OsmMapObject
{
	/**
	 * Ids of nodes of way
	 */
	private ArrayList<Long> nodesIds;

	/**
	 * Create with default values
	 */
	public OsmWay()
	{
		super();
		
		nodesIds = new ArrayList<Long>();
	}

	/**
	 * Add id of node of way
	 *
	 * @param nodeIdToAdd id of way's node
	 */
	public void addNodeId(long nodeIdToAdd)
	{
		nodesIds.add(nodeIdToAdd);
	}

	/**
	 * Get ids of nodes of way
	 *
	 * @return ids of nodes
	 */
	public ArrayList<Long> getNodesIds()
	{
		return nodesIds;
	}
}
