package map.exceptions;

import map.DefenitionTags;

/**
 * Tag incorrectIndex in defenition tags is out of bounds
 *
 * @author pgalex
 */
public class TagIndexOutOfBoundsException extends DefenitionTagsRuntimeException
{
	/**
	 * Value of index that was out of bounds
	 */
	private int incorrectIndex;
	/**
	 * Bounds minimum
	 */
	private int boundsMinimum;
	/**
	 * Bounds maximum
	 */
	private int boundsMaximum;

	/**
	 * Constructor
	 *
	 * @param pEditedTags Tags that was edited when exception is throws
	 * @param pIncorrectIndex Value of index that was out of bounds
	 * @param pBoundsMinimum Bounds minimum
	 * @param pBoundsMaximum Bounds maximum
	 */
	public TagIndexOutOfBoundsException(DefenitionTags pEditedTags, int pIncorrectIndex, int pBoundsMinimum, int pBoundsMaximum)
	{
		super(pEditedTags);

		incorrectIndex = pIncorrectIndex;
		boundsMinimum = pBoundsMinimum;
		boundsMaximum = pBoundsMaximum;
	}

	/**
	 * Get bounds minimum
	 *
	 * @return Bounds minimum
	 */
	public int getBoundsMinimum()
	{
		return boundsMinimum;
	}

	/**
	 * Get bounds maximum
	 *
	 * @return Bounds maximum
	 */
	public int getBoundsMaximum()
	{
		return boundsMaximum;
	}

	/**
	 * Get value of incorrectIndex that was out of bounds
	 *
	 * @return Value of incorrectIndex that was out of bounds
	 */
	public int getIncorrectIndex()
	{
		return incorrectIndex;
	}
}
