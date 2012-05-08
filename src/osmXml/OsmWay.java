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
	 * Default constructor
	 */
	public OsmWay()
	{
		super();
		nodesIds = new ArrayList<Long>();
	}

	/**
	 * Add id of node of way
	 *
	 * @param pNodeId id of way's node
	 */
	public void addNodeId(long pNodeId)
	{
		nodesIds.add(pNodeId);
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
