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
	private long pointId;
	/**
	 * tags of created point
	 */
	private DefenitionTags pointTags;

	/**
	 * Constructor
	 *
	 * @param pPointId Id of created point
	 * @param pPointTags tags of created point
	 */
	public PointPositionIsNullException(long pPointId, DefenitionTags pPointTags)
	{
		pointId = pPointId;
		pointTags = pPointTags;
	}

	/**
	 * Get id of created point
	 *
	 * @return Id of created point
	 */
	public long getPointId()
	{
		return pointId;
	}

	/**
	 * Get tags of created point
	 *
	 * @return tags of created point
	 */
	public DefenitionTags getPointTags()
	{
		return pointTags;
	}
}
