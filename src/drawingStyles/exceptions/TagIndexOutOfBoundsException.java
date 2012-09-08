package drawingStyles.exceptions;

import map.exceptions.MapRuntimeException;

/**
 * Tag incorrectTagIndex in defenition tags is out of bounds
 *
 * @author pgalex
 */
public class TagIndexOutOfBoundsException extends MapRuntimeException
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
	 * Create exception
	 *
	 * @param incorrectTagIndexValue Value of index that was out of bounds
	 * @param indexBoundsMinimum Bounds minimum
	 * @param indexBoundsMaximum Bounds maximum
	 */
	public TagIndexOutOfBoundsException(int incorrectTagIndexValue, int indexBoundsMinimum, int indexBoundsMaximum)
	{
		incorrectTagIndex = incorrectTagIndexValue;
		boundsMinimum = indexBoundsMinimum;
		boundsMaximum = indexBoundsMaximum;
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
