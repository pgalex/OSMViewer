package map;

/**
 * Position on a map
 *
 * @author pgalex
 */
public class MapPosition
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
	 * Default constructor
	 */
	public MapPosition()
	{
		latitude = 0;
		longitude = 0;
	}

	/**
	 * Constructor
	 *
	 * @param pLatitude latitude in degrees
	 * @param pLongitude longitude in degrees
	 */
	public MapPosition(double pLatitude, double pLongitude)
	{
		latitude = pLatitude;
		longitude = pLongitude;
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
	 * @param pLatitude pLatitude in degrees
	 */
	public void setLatitude(double pLatitude)
	{
		latitude = pLatitude;
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
	 * @param pLongitude longitude in degrees
	 */
	public void setLongitude(double pLongitude)
	{
		longitude = pLongitude;
	}
}
