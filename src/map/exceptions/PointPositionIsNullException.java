package map.exceptions;

import map.DefenitionTags;

/**
 * position gives to MapPoint constructor is null
 *
 * @author pgalex
 */
public class PointPositionIsNullException extends RuntimeException
{
	/**
	 * Id of created point
	 */
	private long objectId;
	/**
	 * tags of created point
	 */
	private DefenitionTags objectTags;

	/**
	 * Constructor
	 *
	 * @param pObjectId Id of created point
	 * @param pObjecTags tags of created point
	 */
	public PointPositionIsNullException(long pObjectId, DefenitionTags pObjecTags)
	{
		objectId = pObjectId;
		objectTags = pObjecTags;
	}

	/**
	 * Get Id of created point
	 *
	 * @return Id of created point
	 */
	public long getObjectId()
	{
		return objectId;
	}

	/**
	 * Get tags of created point
	 *
	 * @return tags of created point
	 */
	public DefenitionTags getObjecTags()
	{
		return objectTags;
	}
}
