package map.exceptions;

import map.DefenitionTags;

/**
 * MapTag is null
 *
 * @author pgalex
 */
public class TagIsNullException extends DefenitionTagsRuntimeException
{
	/**
	 * Constructor
	 *
	 * @param pTagsThrowedException Tags that was edited when exception is throws
	 */
	public TagIsNullException(DefenitionTags pTagsThrowedException)
	{
		super(pTagsThrowedException);
	}
}
