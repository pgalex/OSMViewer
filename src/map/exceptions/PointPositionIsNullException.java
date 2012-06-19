package map.exceptions;

import drawingStyles.DefenitionTags;

/**
 * position gives to MapPoint constructor is null
 *
 * @author pgalex
 */
public class PointPositionIsNullException extends MapObjectCreatingRuntimeException
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
