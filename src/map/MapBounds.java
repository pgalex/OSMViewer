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
}
