package map;

/**
 * Describes area in spheric coordinates by latitude/longitude bounds
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
	 * Create with parameters
	 *
	 * @param boundsLatitudeMinimum Latitude minimum
	 * @param boundsLatitudeMaximum Latitude maximum
	 * @param boundsLongitudeMinimum Longitude minimum
	 * @param boundsLongitudeMaximum Longitude maximum
	 */
	public MapBounds(double boundsLatitudeMinimum, double boundsLatitudeMaximum, double boundsLongitudeMinimum,
					double boundsLongitudeMaximum)
	{
		latitudeMinimum = boundsLatitudeMinimum;
		latitudeMaximum = boundsLatitudeMaximum;
		longitudeMinimum = boundsLongitudeMinimum;
		longitudeMaximum = boundsLongitudeMaximum;
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

		if (isLatitudeEquals || isLongitudeEquals)
		{
			return true;
		}
		else
		{
			return false;
		}
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
}
