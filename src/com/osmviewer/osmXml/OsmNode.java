package com.osmviewer.osmXml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

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
	 * Creating noe by xml attributes
	 *
	 * @param attributes node xml tag attributes
	 * @throws IllegalArgumentException attributes is null
	 * @throws SAXException error while parsing attributes
	 */
	public OsmNode(Attributes attributes) throws IllegalArgumentException, SAXException
	{
		super();

		if (attributes == null)
		{
			throw new IllegalArgumentException();
		}

		try
		{
			setId(Long.valueOf(attributes.getValue("id")));
			latitude = Double.valueOf(attributes.getValue("lat"));
			longitude = Double.valueOf(attributes.getValue("lon"));
		}
		catch (Exception ex)
		{
			throw new SAXException(ex);
		}

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
