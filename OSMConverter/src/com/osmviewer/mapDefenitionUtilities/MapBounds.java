package com.osmviewer.mapDefenitionUtilities;

import java.awt.geom.Rectangle2D;

/**
 * Describes an area on map
 *
 * @author pgalex
 */
public class MapBounds
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
	 * Create zero. All bounds values will be 0
	 */
	public MapBounds()
	{
		this.latitudeMinimum = 0;
		this.latitudeMaximum = 0;
		this.longitudeMinimum = 0;
		this.longitudeMaximum = 0;
	}

	/**
	 * Create by computing bounding area of locations
	 *
	 * @param locations locations array. Must be not null, not empty, not contains
	 * null
	 * @throws IllegalArgumentException locations array incorrect
	 */
	public MapBounds(Location[] locations) throws IllegalArgumentException
	{
		//todo tests of this contructor
		if (!isLocationsArrayCorrect(locations))
		{
			throw new IllegalArgumentException("locations incorect");
		}

		this.latitudeMinimum = locations[0].getLatitude();
		this.longitudeMinimum = locations[0].getLongitude();
		this.latitudeMaximum = locations[0].getLatitude();
		this.longitudeMaximum = locations[0].getLongitude();

		for (Location mapPosition : locations)
		{
			if (mapPosition.getLatitude() < this.latitudeMinimum)
			{
				this.latitudeMinimum = mapPosition.getLatitude();
			}
			if (mapPosition.getLongitude() < this.longitudeMinimum)
			{
				this.longitudeMinimum = mapPosition.getLongitude();
			}
			if (mapPosition.getLatitude() > this.latitudeMaximum)
			{
				this.latitudeMaximum = mapPosition.getLatitude();
			}
			if (mapPosition.getLongitude() > this.longitudeMaximum)
			{
				this.longitudeMaximum = mapPosition.getLongitude();
			}
		}
	}

	/**
	 * Is given locations array correct for computing bounds by it
	 *
	 * @param locations locations array to test
	 * @return Is given array correct
	 */
	private boolean isLocationsArrayCorrect(Location[] locations)
	{
		if (locations == null)
		{
			return false;
		}
		if (locations.length == 0)
		{
			return false;
		}
		for (Location location : locations)
		{
			if (location == null)
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * Create with given bounds values. If minimum > maxinum values will be swaped
	 *
	 * @param latitudeMinimum Latitude minimum
	 * @param latitudeMaximum Latitude maximum
	 * @param longitudeMinimum Longitude minimum
	 * @param longitudeMaximum Longitude maximum
	 */
	public MapBounds(double latitudeMinimum, double latitudeMaximum, double longitudeMinimum, double longitudeMaximum)
	{
		this.latitudeMinimum = latitudeMinimum;
		this.latitudeMaximum = latitudeMaximum;
		this.longitudeMinimum = longitudeMinimum;
		this.longitudeMaximum = longitudeMaximum;
		invertBoundsIfNeed();
	}

	/**
	 * Swap minimum and maximum values if bounds are inverted
	 */
	private void invertBoundsIfNeed()
	{
		if (latitudeMinimum > latitudeMaximum)
		{
			swapLatitude();
		}

		if (longitudeMinimum > longitudeMaximum)
		{
			swapLongitude();
		}
	}

	/**
	 * Swap latitude maximum and minimum
	 */
	private void swapLatitude()
	{
		double temp = latitudeMaximum;
		latitudeMaximum = latitudeMinimum;
		latitudeMinimum = temp;
	}

	/**
	 * Swap longitude maximum and minimum
	 */
	private void swapLongitude()
	{
		double temp = longitudeMaximum;
		longitudeMaximum = longitudeMinimum;
		longitudeMinimum = temp;
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
	 * Get latitude maximum
	 *
	 * @return Latitude maximum
	 */
	public double getLatitudeMaximum()
	{
		return latitudeMaximum;
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
	 * Get longitude maximum
	 *
	 * @return Longitude maximum
	 */
	public double getLongitudeMaximum()
	{
		return longitudeMaximum;
	}

	/**
	 * Is bounds defines zero area. Some min-max pair of coordinates is equals
	 *
	 * @return Is bounds defines zero area
	 */
	public boolean isZero()
	{
		boolean isLatitudeEquals = Double.compare(latitudeMinimum, latitudeMaximum) == 0;
		boolean isLongitudeEquals = Double.compare(longitudeMinimum, longitudeMaximum) == 0;
		return isLatitudeEquals || isLongitudeEquals;
	}

	/**
	 * Get size of area by latitude
	 *
	 * @return size by latitude
	 */
	public double getLatitudeSize()
	{
		return latitudeMaximum - latitudeMinimum;
	}

	/**
	 * Get size of area by longitude
	 *
	 * @return size by longitude
	 */
	public double getLongitudeSize()
	{
		return longitudeMaximum - longitudeMinimum;
	}

	/**
	 * Is intersects with other bounds
	 *
	 * @param bounds bounds to test intersction with
	 * @return is this bounds intersects with given bounds.
	 * @throws IllegalArgumentException
	 */
	public boolean intersects(MapBounds bounds) throws IllegalArgumentException
	{
		// todo bounds=null, other tests
		Rectangle2D thisBoundsRectangle = new Rectangle2D.Double(latitudeMinimum, longitudeMinimum,
						getLatitudeSize(), getLongitudeSize());
		Rectangle2D givenBoundsRectangle = new Rectangle2D.Double(bounds.getLatitudeMinimum(),
						bounds.getLongitudeMinimum(), bounds.getLatitudeSize(), bounds.getLongitudeSize());
		return thisBoundsRectangle.intersects(givenBoundsRectangle);
	}
}
