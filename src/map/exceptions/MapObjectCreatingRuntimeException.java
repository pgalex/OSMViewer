package map.exceptions;

import drawingStyles.DefenitionTags;

/**
 * Exception throwed while created any object on a map
 *
 * @author pgalex
 */
public class MapObjectCreatingRuntimeException extends MapRuntimeException
{
	/**
	 * Id of created point
	 */
	private long createdObjectId;
	/**
	 * tags of created point
	 */
	private DefenitionTags createdObjectTags;

	/**
	 * Constructor
	 *
	 * @param pCreatedObjectId Id of created object
	 * @param pCreatedObjectTags tags of created object
	 */
	public MapObjectCreatingRuntimeException(long pCreatedObjectId, DefenitionTags pCreatedObjectTags)
	{
		createdObjectId = pCreatedObjectId;
		createdObjectTags = pCreatedObjectTags;
	}

	/**
	 * Get id of created object
	 *
	 * @return Id of created object
	 */
	public long getCreatedObjectId()
	{
		return createdObjectId;
	}

	/**
	 * Get tags of created object
	 *
	 * @return tags of created object
	 */
	public DefenitionTags getCreatedObjectTags()
	{
		return createdObjectTags;
	}
}
