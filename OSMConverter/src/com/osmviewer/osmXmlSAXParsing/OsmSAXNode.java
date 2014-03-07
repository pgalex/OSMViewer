package com.osmviewer.osmXmlSAXParsing;

import com.osmviewer.osmXml.OsmNode;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * Osm xml node (one point)
 *
 * @author preobrazhentsev
 */
public class OsmSAXNode extends OsmSAXMapObject implements OsmNode
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
	 * Creating node by xml attributes
	 *
	 * @param attributes node xml tag attributes
	 * @throws IllegalArgumentException attributes is null
	 * @throws SAXException error while parsing attributes
	 */
	public OsmSAXNode(Attributes attributes) throws IllegalArgumentException, SAXException
	{
		if (attributes == null)
		{
			throw new IllegalArgumentException("attributes is null");
		}

		try
		{
			setId(Long.valueOf(attributes.getValue("id")));
			latitude = Double.valueOf(attributes.getValue("lat"));
			longitude = Double.valueOf(attributes.getValue("lon"));
		}
		catch (NumberFormatException ex)
		{
			throw new SAXException(ex);
		}
	}

	/**
	 * Get position latitude
	 *
	 * @return position latitude
	 */
	@Override
	public double getLatitude()
	{
		return latitude;
	}

	/**
	 * Get position longitude
	 *
	 * @return position longitude
	 */
	@Override
	public double getLongitude()
	{
		return longitude;
	}
}
