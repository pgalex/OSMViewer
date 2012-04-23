package map.exceptions;

import map.DefenitionTags;

/**
 * Tag index in defenition tags is out of bounds
 *
 * @author pgalex
 */
public class TagIndexOutOfBoundsException extends DefenitionTagsRuntimeException
{
	/**
	 * Value of index that was out of bounds
	 */
	private int index;
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
	 * @param pEditedTags Tags that was edited when exception is throws
	 * @param pIndex Value of index that was out of bounds
	 * @param pBoundsMin Bounds minimum
	 * @param pBoundsMax Bounds maximum
	 */
	public TagIndexOutOfBoundsException(DefenitionTags pEditedTags, int pIndex, int pBoundsMin, int pBoundsMax)
	{
		super(pEditedTags);
		
		index = pIndex;
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

	/**
	 * Get Value of index that was out of bounds
	 *
	 * @return Value of index that was out of bounds
	 */
	public int getIndex()
	{
		return index;
	}
}
