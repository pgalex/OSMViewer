package osmXml;

import java.util.ArrayList;

/**
 * Way in .osm file
 *
 * @author preobrazhentsev
 */
public class OSMFileWay extends OsmFileMapObject
{
	/**
	 * Ids of nodes of way
	 */
	private ArrayList nodesIds;

	/**
	 * Default constructor
	 */
	public OSMFileWay()
	{
		super();
		nodesIds = new ArrayList();
	}

	/**
	 * Set ids of nodes of way
	 *
	 * @param pNodesIds ids of nodes
	 */
	public void setNodesIds(ArrayList pNodesIds)
	{
		nodesIds = pNodesIds;
	}

	/**
	 * Get ids of nodes of way
	 *
	 * @return ids of nodes
	 */
	public ArrayList getNodesIds()
	{
		return nodesIds;
	}
}
