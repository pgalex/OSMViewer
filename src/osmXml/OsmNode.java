package osmXml;

import map.*;

/**
 * Osm node (one point)
 *
 * @author preobrazhentsev
 */
public class OsmNode extends OsmMapObject
{
	/**
	 * Position - latitude
	 */
	private double latitude;
	/**
	 * Position - longitude
	 */
	private double longitude;

	/**
	 * Default constructor
	 */
	public OsmNode()
	{
		super();
	}

	/**
	 * Set node position latitude
	 *
	 * @param pLatitude position latitude
	 */
	public void setLatitude(double pLatitude)
	{
		latitude = pLatitude;
	}

	/**
	 * Get node position latitude
	 *
	 * @return position latitude
	 */
	public double getLatitude()
	{
		return latitude;
	}

	/**
	 * Set node position longitude
	 *
	 * @param pLongitude position longitude
	 */
	public void setLongitude(double pLongitude)
	{
		longitude = pLongitude;
	}

	/**
	 * Get node position longitude
	 *
	 * @return position longitude
	 */
	public double getLongitude()
	{
		return longitude;
	}
	
	/**
	 * Create point on a map by this node data
	 * 
	 * @return point on a map by this node 
	 */
	public MapPoint createMapPoint()
	{
		EditableDefenitionTags pointTags = new EditableDefenitionTags();
		for (MapTag nodeTags : getTags())
		{
			if(nodeTags!=null)
				pointTags.add(nodeTags);
		}
		MapPoint resultPoint = new MapPoint(new MapPosition(latitude, longitude), getId(), pointTags);
		return resultPoint;
	}
}
