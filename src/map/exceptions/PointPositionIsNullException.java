package map.exceptions;

import map.DefenitionTags;

/**
 * position gives to MapPoint constructor is null
 *
 * @author pgalex
 */
public class PointPositionIsNullException extends MapObjectCreatingException
{
	/**
	 * Constructor
	 * @param pCreatedPointId id of created point
	 * @param pCreatedPointTags tags of created point
	 */
	public PointPositionIsNullException(long pCreatedPointId, DefenitionTags pCreatedPointTags)
	{
		super(pCreatedPointId, pCreatedPointTags);
	}
}
