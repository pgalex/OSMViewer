package map.exceptions;

import map.DefenitionTags;

/**
 * Tag incorrectTagIndex in defenition tags is out of bounds
 *
 * @author pgalex
 */
public class TagIndexOutOfBoundsException extends DefenitionTagsRuntimeException
{
	/**
	 * Value of index that was out of bounds
	 */
	private int incorrectTagIndex;
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
	 * @param pIncorrectTagIndex Value of index that was out of bounds
	 * @param pBoundsMinimum Bounds minimum
	 * @param pBoundsMaximum Bounds maximum
	 */
	public TagIndexOutOfBoundsException(DefenitionTags pEditedTags, int pIncorrectTagIndex, int pBoundsMinimum, int pBoundsMaximum)
	{
		super(pEditedTags);

		incorrectTagIndex = pIncorrectTagIndex;
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
	 * Get value of incorrectTagIndex that was out of bounds
	 *
	 * @return Value of incorrectTagIndex that was out of bounds
	 */
	public int getIncorrectTagIndex()
	{
		return incorrectTagIndex;
	}
}
