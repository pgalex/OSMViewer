package map.rendering;

/**
 * Array of scales(in meters per pixel) by scale level. Used to incapluste
 * access to array of scale by scale level. For drawing
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
	 * openstreetmap like scale on each scale level in meters per pixel in
	 * equator. Array size is from MINIMUM_SCALE_LEVEL to MAXIMUM_SCALE_LEVEL
	 */
	private static final double scaleByScaleLevel[] =
	{
		1,
		1,
		1,
		1,
		1,
		1,
		1,
		1,
		1,
		1,
		0.014,
		1,
		1,
		1,
		1,
		1,
		1,
	};

	/**
	 * Get scale by scale level.
	 *
	 * @param pScaleLevel scale level. if its out of bounds will be used nearest
	 * correct scale level (maximum or minimum)
	 * @param pLatitude latitude where need find scale
	 * @return scale by scale level in meters per pixel. If scale level more than
	 * maximum of less than minimum will be return value on each bound
	 */
	public static double getScaleByScaleLevel(int pScaleLevel, double pLatitude)
	{
		int normalizedScaleLevel = normalizeScaleLevel(pScaleLevel);
		int scaleLevelInStaticArrayBounds = normalizedScaleLevel - MINIMUM_SCALE_LEVEL;

		// провекру деления на ноль
		return scaleByScaleLevel[scaleLevelInStaticArrayBounds] / Math.cos(pLatitude * Math.PI / 180.0);
	}

	/**
	 * Normalize scale level to MINIMUM/MAXIMUM bounds
	 *
	 * @param pScaleLevel scale level to normalize
	 * @return nearest correct value of scale level
	 */
	private static int normalizeScaleLevel(int pScaleLevel)
	{
		int normalizedScaleLevel = pScaleLevel;

		if (normalizedScaleLevel < MINIMUM_SCALE_LEVEL)
			normalizedScaleLevel = MINIMUM_SCALE_LEVEL;

		if (normalizedScaleLevel > MAXIMUM_SCALE_LEVEL)
			normalizedScaleLevel = MAXIMUM_SCALE_LEVEL;

		return normalizedScaleLevel;
	}
}
