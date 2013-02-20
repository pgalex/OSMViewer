package com.osmviewer.osmXml;

/**
 * Sector bounds in .osm file. Границы сектора .osm файла
 *
 * @author preobrazhentsev
 */
public class OsmBounds
{
	/**
	 * Latitude minimum
	 */
	private double latitudeMinimum;
	/**
	 * Latitude maximum
	 */
	private double latitudeMaximum;
	/**
	 * Longitude minimum
	 */
	private double longitudeMinimum;
	/**
	 * Longitude maximum
	 */
	private double longitudeMaximum;

	/**
	 * Create zero bounds
	 */
	public OsmBounds()
	{
		latitudeMinimum = 0;
		latitudeMaximum = 0;
		longitudeMinimum = 0;
		longitudeMaximum = 0;
	}

	/**
	 * Set latitude minimum
	 *
	 * @param latitudeMinimumToSet Latitude minimum
	 */
	public void setLatitudeMinimum(double latitudeMinimumToSet)
	{
		latitudeMinimum = latitudeMinimumToSet;
	}

	/**
	 * Get latitude minimum
	 *
	 * @return Latitude minimum
	 */
	public double getLatitudeMinimum()
	{
		return latitudeMinimum;
	}

	/**
	 * Set latitude maximum
	 *
	 * @param latitudeMaximumToSet Latitude maximum
	 */
	public void setLatitudeMaximum(double latitudeMaximumToSet)
	{
		latitudeMaximum = latitudeMaximumToSet;
	}

	/**
	 * Get latitude maximum
	 *
	 * @return Latitude maximum
	 */
	public double getLatitudeMaximum()
	{
		return latitudeMaximum;
	}

	/**
	 * Set longitude minimum
	 *
	 * @param longitudeMinimumToSet Longitude minimum
	 */
	public void setLongitudeMinimum(double longitudeMinimumToSet)
	{
		longitudeMinimum = longitudeMinimumToSet;
	}

	/**
	 * Get longitude minimum
	 *
	 * @return Longitude minimum
	 */
	public double getLongitudeMinimum()
	{
		return longitudeMinimum;
	}

	/**
	 * Set longitude maximum
	 *
	 * @param longitudeMaximumToSet Longitude maximum
	 */
	public void setLongitudeMaximum(double longitudeMaximumToSet)
	{
		longitudeMaximum = longitudeMaximumToSet;
	}

	/**
	 * Get longitude maximum
	 *
	 * @return Longitude maximum
	 */
	public double getLongitudeMaximum()
	{
		return longitudeMaximum;
	}
}
