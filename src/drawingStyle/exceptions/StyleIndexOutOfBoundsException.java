package drawingStyle.exceptions;

import drawingStyle.StyleEditor;

/**
 * Style index of map object is out of bounds
 *
 * @author pgalex
 */
public class StyleIndexOutOfBoundsException extends StyleEditorRuntimeException
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
	 * Constructor
	 *
	 * @param pEditorThrowedException Style editor that throwed exception
	 * @param pIncorrectStyleIndex Style index that was incorrect
	 * @param pBoundsMinimum Minimun value of style index
	 * @param pBoundsMaximum Maximum value of style index
	 */
	public StyleIndexOutOfBoundsException(StyleEditor pEditorThrowedException, Integer pIncorrectStyleIndex, Integer pBoundsMinimum, Integer pBoundsMaximum)
	{
		super(pEditorThrowedException);
		incorrectStyleIndex = pIncorrectStyleIndex;
		boundsMinimum = pBoundsMinimum;
		boundsMaximum = pBoundsMaximum;
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