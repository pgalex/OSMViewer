package mapDefenitionUtilities;

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
	 * Create zero position
	 */
	public MapPosition()
	{
		latitude = 0;
		longitude = 0;
	}

	/**
	 * Create with parameters
	 *
	 * @param positionLatitude latitude in degrees
	 * @param positionLongitude longitude in degrees
	 */
	public MapPosition(double positionLatitude, double positionLongitude)
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
	 * @param pointToCompare position for comparing
	 * @return is position same
	 * @throws IllegalArgumentException pointToCompare is null
	 */
	public boolean compareTo(MapPosition pointToCompare) throws IllegalArgumentException
	{
		if (pointToCompare == null)
		{
			throw new IllegalArgumentException();
		}
		
		boolean latitudeSame = Double.compare(latitude, pointToCompare.getLatitude()) == 0;
		boolean longitudeSame = Double.compare(longitude, pointToCompare.getLongitude()) == 0;

		return latitudeSame && longitudeSame;
	}
}
