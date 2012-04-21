package map.exceptions;

import map.DefenitionTags;

/**
 * MapTag is null
 *
 * @author pgalex
 */
public class TagIsNullException extends RuntimeException
{
	/**
	 * Tags that was edited when exception is throws
	 */
	private DefenitionTags editedTags;

	/**
	 * Constructor
	 *
	 * @param pEditingTags Tags that was edited when exception is throws
	 */
	public TagIsNullException(DefenitionTags pEditingTags)
	{
		editedTags = pEditingTags;
	}

	/**
	 * Get Tags that was edited when exception is throws
	 *
	 * @return Tags that was edited when exception is throws
	 */
	public DefenitionTags getEditedTags()
	{
		return editedTags;
	}
}
