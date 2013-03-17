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
	 * Is equals to object
	 *
	 * @param objectToCompare object for comparing. Must be instance of Location
	 * class
	 * @return is equals to given object
	 */
	@Override
	public boolean equals(Object objectToCompare)
	{
		if (!(objectToCompare instanceof Location))
		{
			throw new IllegalArgumentException("objectToCompate is not instance of location");
		}

		Location locationToCompare = (Location) objectToCompare;
		boolean latitudeSame = Double.compare(latitude, locationToCompare.getLatitude()) == 0;
		boolean longitudeSame = Double.compare(longitude, locationToCompare.getLongitude()) == 0;

		return latitudeSame && longitudeSame;
	}

	/**
	 * Get hash code value
	 *
	 * @return hash code value
	 */
	@Override
	public int hashCode()
	{
		int hash = 5;
		hash = 41 * hash + (int) (Double.doubleToLongBits(this.latitude) ^ (Double.doubleToLongBits(this.latitude) >>> 32));
		hash = 41 * hash + (int) (Double.doubleToLongBits(this.longitude) ^ (Double.doubleToLongBits(this.longitude) >>> 32));
		return hash;
	}
}
