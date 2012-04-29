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
	private DefenitionTags tagsThrowedException;

	/**
	 * Constructor
	 *
	 * @param pTagsThrowedException Tags that was edited when exception is throws
	 */
	public DefenitionTagsRuntimeException(DefenitionTags pTagsThrowedException)
	{
		tagsThrowedException = pTagsThrowedException;
	}

	/**
	 * Get tags that was edited when exception is throws
	 *
	 * @return Tags that was edited when exception is throws
	 */
	public DefenitionTags getTagsThrowedException()
	{
		return tagsThrowedException;
	}
}
