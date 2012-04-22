package map.exceptions;

/**
 * Tag index in defenition tags is out of bounds
 *
 * @author pgalex
 */
public class TagIndexOutOfBoundsException extends RuntimeException
{
	/**
	 * Bounds minimum
	 */
	private int boundsMin;
	/**
	 * Bounds maximum
	 */
	private int boundsMax;

	/**
	 * Constructor
	 *
	 * @param pBoundsMin Bounds minimum
	 * @param pBoundsMax Bounds maximum
	 */
	public TagIndexOutOfBoundsException(int pBoundsMin, int pBoundsMax)
	{
		boundsMin = pBoundsMin;
		boundsMax = pBoundsMax;
	}

	/**
	 * Get Bounds minimum
	 *
	 * @return Bounds minimum
	 */
	public int getBoundsMin()
	{
		return boundsMin;
	}

	/**
	 * Get Bounds maximum
	 *
	 * @return Bounds maximum
	 */
	public int getBoundsMax()
	{
		return boundsMax;
	}
}
