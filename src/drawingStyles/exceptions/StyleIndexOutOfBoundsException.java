package drawingStyles.exceptions;

import drawingStyles.StyleEditor;

/**
 * Style index of map object is out of bounds
 *
 * @author pgalex
 */
public class StyleIndexOutOfBoundsException extends DrawingStylesRuntimeException
{
	/**
	 * Incorrect style index
	 */
	private Integer incorrectStyleIndex;
	/**
	 * Minimun value of style index
	 */
	private Integer boundsMinimum;
	/**
	 * Maximum value of style index
	 */
	private Integer boundsMaximum;

	/**
	 * Create exception
	 *
	 * @param incorrectStyleIndexValue Style index that was incorrect
	 * @param styleIndexBoundsMinimum Minimun value of style index
	 * @param styleIndexBoundsMaximum Maximum value of style index
	 */
	public StyleIndexOutOfBoundsException(Integer incorrectStyleIndexValue, Integer styleIndexBoundsMinimum, Integer styleIndexBoundsMaximum)
	{
		incorrectStyleIndex = incorrectStyleIndexValue;
		boundsMinimum = styleIndexBoundsMinimum;
		boundsMaximum = styleIndexBoundsMaximum;
	}

	/**
	 * Get style index that was incorrect
	 *
	 * @return Incorrect style index
	 */
	public Integer getIncorrectStyleIndex()
	{
		return incorrectStyleIndex;
	}

	/**
	 * Get minimun value of style index
	 *
	 * @return Minimun value of style index
	 */
	public Integer getBoundsMinimum()
	{
		return boundsMinimum;
	}

	/**
	 * Get maximum value of style index
	 *
	 * @return Maximum value of style index
	 */
	public Integer getBoundsMaximum()
	{
		return boundsMaximum;
	}
}
