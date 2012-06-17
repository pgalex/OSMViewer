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
	 * Constructor
	 *
	 * @param pLatitudeMinimum Latitude minimum
	 * @param pLatitudeMaximum Latitude maximum
	 * @param pLongitudeMinimum Longitude minimum
	 * @param pLongitudeMaximum Longitude maximum
	 */
	public MapBounds(double pLatitudeMinimum, double pLatitudeMaximum, double pLongitudeMinimum,
					double pLongitudeMaximum)
	{
		latitudeMinimum = pLatitudeMinimum;
		latitudeMaximum = pLatitudeMaximum;
		longitudeMinimum = pLongitudeMinimum;
		longitudeMaximum = pLongitudeMaximum;
		invertBoundsIfNeed();
	}

	/**
	 * Swap minimum and maximum values if bounds are inverted
	 */
	private void invertBoundsIfNeed()
	{
		if (latitudeMinimum > latitudeMaximum)
			swapLatitude();

		if (longitudeMinimum > longitudeMaximum)
			swapLongitude();
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
	 * Is rectangle defines zero area. Some min/max pair of coordinates is equals
	 *
	 * @return Is rectangle defines zero area
	 */
	public boolean isZero()
	{
		boolean isLatitudeEquals = Double.compare(latitudeMinimum, latitudeMaximum) == 0;
		boolean isLongitudeEquals = Double.compare(longitudeMinimum, longitudeMaximum) == 0;

		if (isLatitudeEquals || isLongitudeEquals)
			return true;
		else
			return false;
	}
}
