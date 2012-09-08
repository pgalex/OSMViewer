package drawingStyles.exceptions;

/**
 * Scale level is out of bounds
 *
 * @author pgalex
 */
public class ScaleLevelOutOfBoundsException extends DrawingStylesRuntimeException
{
	/**
	 * Value of scale level that was incorrect
	 */
	private int incorrectScaleLevel;
	/**
	 * Minimum value of scale level
	 */
	private int boundsMinimum;
	/**
	 * Maximum value of scale level
	 */
	private int boundsMaximum;

	/**
	 * Create exception
	 *
	 * @param incorrectScaleLevelValue Value of scale level that was incorrect
	 * @param scaleLevelBoundsMinimum Minimum value of scale level
	 * @param scaleLevelBoundsMaximum Maximum value of scale level
	 */
	public ScaleLevelOutOfBoundsException(int incorrectScaleLevelValue, int scaleLevelBoundsMinimum, int scaleLevelBoundsMaximum)
	{
		incorrectScaleLevel = incorrectScaleLevelValue;
		boundsMinimum = scaleLevelBoundsMinimum;
		boundsMaximum = scaleLevelBoundsMaximum;
	}

	/**
	 * Get value of scale level that was incorrect
	 *
	 * @return Value of scale level that was incorrect
	 */
	public int getIncorrectScaleLevel()
	{
		return incorrectScaleLevel;
	}

	/**
	 * Get minimum value of scale level
	 *
	 * @return Minimum value of scale level
	 */
	public int getBoundsMinimum()
	{
		return boundsMinimum;
	}

	/**
	 * Get maximum value of scale level
	 *
	 * @return Maximum value of scale level
	 */
	public int getBoundsMaximum()
	{
		return boundsMaximum;
	}
}
