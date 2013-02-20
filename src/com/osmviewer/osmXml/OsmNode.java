package com.osmviewer.osmXml;

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
	 * Create with default values
	 */
	public OsmNode()
	{
		super();
		
		latitude = 0;
		longitude = 0;
	}

	/**
	 * Set node position latitude
	 *
	 * @param latitudeToSet position latitude
	 */
	public void setLatitude(double latitudeToSet)
	{
		latitude = latitudeToSet;
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
	 * @param longitudeToSet position longitude
	 */
	public void setLongitude(double longitudeToSet)
	{
		longitude = longitudeToSet;
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
