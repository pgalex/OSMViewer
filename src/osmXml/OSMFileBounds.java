package osmXml;

/**
 * Sector bounds in .osm file. Границы сектора .osm файла
 *
 * @author preobrazhentsev
 */
public class OSMFileBounds
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
	 * Default constructor
	 */
	public OSMFileBounds()
	{
		latitudeMinimum = 0;
		latitudeMaximum = 0;
		longitudeMinimum = 0;
		longitudeMaximum = 0;
	}

	/**
	 * Set latitude minimum
	 *
	 * @param pLatitudeMinimum Latitude minimum
	 */
	public void setLatitudeMinimum(double pLatitudeMinimum)
	{
		latitudeMinimum = pLatitudeMinimum;
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
	 * @param pLatitudeMaximum Latitude maximum
	 */
	public void setLatitudeMaximum(double pLatitudeMaximum)
	{
		latitudeMaximum = pLatitudeMaximum;
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
	 * @param pLongitudeMinimum Longitude minimum
	 */
	public void setLongitudeMinimum(double pLongitudeMinimum)
	{
		longitudeMinimum = pLongitudeMinimum;
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
	 * @param pLongitudeMaximum Longitude maximum
	 */
	public void setLongitudeMaximum(double pLongitudeMaximum)
	{
		longitudeMaximum = pLongitudeMaximum;
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
