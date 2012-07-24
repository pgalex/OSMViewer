package drawingStyles.exceptions;

import drawingStyles.DrawSettingsOnScaleArray;

/**
 * Scale level is out of bounds
 *
 * @author pgalex
 */
public class ScaleLevelOutOfBoundsException extends ScaledObjectStyleArrayRuntimeException
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
	 * Constructor
	 *
	 * @param pArrayThrowedException Scaled object style array throwed this
	 * exception
	 * @param pIncorrectScaleLevel Value of scale level that was incorrect
	 * @param pBoundsMinimum Minimum value of scale level
	 * @param pBoundsMaximum Maximum value of scale level
	 */
	public ScaleLevelOutOfBoundsException(DrawSettingsOnScaleArray pArrayThrowedException, int pIncorrectScaleLevel, int pBoundsMinimum, int pBoundsMaximum)
	{
		super(pArrayThrowedException);
		incorrectScaleLevel = pIncorrectScaleLevel;
		boundsMinimum = pBoundsMinimum;
		boundsMaximum = pBoundsMaximum;
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
