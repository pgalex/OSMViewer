package com.osmviewer.mapDefenitionUtilities;

/**
 * Position on a map
 *
 * @author pgalex
 */
public class Location
{
	/**
	 * Latitude in degrees
	 */
	private double latitude;
	/**
	 * Longitude in degrees
	 */
	private double longitude;

	/**
	 * Create zero position
	 */
	public Location()
	{
		latitude = 0;
		longitude = 0;
	}

	/**
	 * Create with values
	 *
	 * @param positionLatitude latitude in degrees
	 * @param positionLongitude longitude in degrees
	 */
	public Location(double positionLatitude, double positionLongitude)
	{
		latitude = positionLatitude;
		longitude = positionLongitude;
	}

	/**
	 * Get latitude
	 *
	 * @return latitude in degrees
	 */
	public double getLatitude()
	{
		return latitude;
	}

	/**
	 * Set latitude
	 *
	 * @param latitudeToSet latitudeToSet in degrees
	 */
	public void setLatitude(double latitudeToSet)
	{
		latitude = latitudeToSet;
	}

	/**
	 * Get longitude
	 *
	 * @return longitude in degrees
	 */
	public double getLongitude()
	{
		return longitude;
	}

	/**
	 * Set longitude
	 *
	 * @param longitudeToSet longitude in degrees
	 */
	public void setLongitude(double longitudeToSet)
	{
		longitude = longitudeToSet;
	}

	/**
	 * Compate with other position
	 *
	 * @param locationToCompare position for comparing
	 * @return is position same
	 * @throws IllegalArgumentException pointToCompare is null
	 */
	public boolean compareTo(Location locationToCompare) throws IllegalArgumentException
	{
		if (locationToCompare == null)
		{
			throw new IllegalArgumentException("locationToCompare is null");
		}

		boolean latitudeSame = Double.compare(latitude, locationToCompare.getLatitude()) == 0;
		boolean longitudeSame = Double.compare(longitude, locationToCompare.getLongitude()) == 0;

		return latitudeSame && longitudeSame;
	}
}
