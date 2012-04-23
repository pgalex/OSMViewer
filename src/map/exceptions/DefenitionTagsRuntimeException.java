package map.exceptions;

import map.DefenitionTags;

/**
 * Runtime exception throws in one defenition tags class
 *
 * @author pgalex
 */
public class DefenitionTagsRuntimeException extends RuntimeException
{
	/**
	 * Tags that was edited when exception is throws
	 */
	private DefenitionTags editedTags;

	/**
	 * Constructor
	 *
	 * @param pEditedTags Tags that was edited when exception is throws
	 */
	public DefenitionTagsRuntimeException(DefenitionTags pEditedTags)
	{
		editedTags = pEditedTags;
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
