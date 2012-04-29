package drawingStyle.exceptions;

/**
 * Style index of map object is out of bounds
 *
 * @author pgalex
 */
public class StyleIndexOutOfBoundsException extends RuntimeException
{
	/**
	 * Incorrect style index
	 */
	private Integer incorrectIndex;
	/**
	 * Minimun value of style index
	 */
	private Integer boundsMinimum;
	/**
	 * Maximum value of style index
	 */
	private Integer boundsMaximum;

	/**
	 * Constructor
	 *
	 * @param pIncorrectIndex Style index that was incorrect
	 * @param pBoundsMinimum Minimun value of style index
	 * @param pBoundsMaximum Maximum value of style index
	 */
	public StyleIndexOutOfBoundsException(Integer pIncorrectIndex, Integer pBoundsMinimum, Integer pBoundsMaximum)
	{
		incorrectIndex = pIncorrectIndex;
		boundsMinimum = pBoundsMinimum;
		boundsMaximum = pBoundsMaximum;
	}

	/**
	 * Get style index that was incorrect
	 *
	 * @return Incorrect style index
	 */
	public Integer getIncorrectIndex()
	{
		return incorrectIndex;
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
