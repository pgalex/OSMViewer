package rendering;

/**
 * Array of scales by scale level. Used to incapluste access to array of scale
 * by scale level. For drawing
 *
 * @author pgalex
 */
public class ScalesArray
{
	/**
	 * Minimum scale level (osm like)
	 */
	public static final int MINIMUM_SCALE_LEVEL = 2;
	/**
	 * Maximum scale level (osm like)
	 */
	public static final int MAXIMUM_SCALE_LEVEL = 18;
	/**
	 * Scale on each scale level. Array size is from MINIMUM_SCALE_LEVEL to
	 * MAXIMUM_SCALE_LEVEL
	 */
	private static final double scaleByScaleLevel[] =
	{
		1.0 / 70656.0,
		1.0 / 35328.0,
		1.0 / 17664.0,
		1.0 / 8832.0,
		1.0 / 4416.0,
		1.0 / 2204.0,
		1.0 / 1014.0,
		1.0 / 552.0,
		1.0 / 276.0,
		1.0 / 74.0,
		1.0 / 37.0,
		1.0 / 18.5,
		1.0 / 9.0, 
		1.0 / 7.5,
		1.0 / 4.0,
		1.0 / 2.0,
		1.0,
	};

	/**
	 * Get scale by scale level.
	 *
	 * @param scaleLevel scale level. if its out of bounds will be used nearest
	 * correct scale level (maximum or minimum)
	 * @return scale by scale level. If scale level more than maximum of less than
	 * minimum will be return value on bound
	 */
	public static double getScaleByScaleLevel(int scaleLevel)
	{
		int normalizedScaleLevel = normalizeScaleLevel(scaleLevel);
		int scaleLevelInStaticArrayBounds = normalizedScaleLevel - MINIMUM_SCALE_LEVEL;

		return scaleByScaleLevel[scaleLevelInStaticArrayBounds];
	}

	/**
	 * Normalize scale level to MINIMUM/MAXIMUM bounds
	 *
	 * @param scaleLevel scale level to normalize
	 * @return nearest correct value of scale level
	 */
	private static int normalizeScaleLevel(int scaleLevel)
	{
		int normalizedScaleLevel = scaleLevel;

		if (normalizedScaleLevel < MINIMUM_SCALE_LEVEL)
		{
			normalizedScaleLevel = MINIMUM_SCALE_LEVEL;
		}

		if (normalizedScaleLevel > MAXIMUM_SCALE_LEVEL)
		{
			normalizedScaleLevel = MAXIMUM_SCALE_LEVEL;
		}

		return normalizedScaleLevel;
	}
}
