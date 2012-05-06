package osmXml;

/**
 * Osm node (one point)
 *
 * @author preobrazhentsev
 */
public class OSMFileNode extends OsmFileMapObject
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
	public OSMFileNode()
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
}
