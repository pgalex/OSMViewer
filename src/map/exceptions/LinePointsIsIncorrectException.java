package map.exceptions;

import map.DefenitionTags;

/**
 * line points array is null, not contains needed points count or contains null
 * elements
 *
 * @author pgalex
 */
public class LinePointsIsIncorrectException extends MapObjectCreatingRuntimeException
{
	/**
	 * Constructor
	 *
	 * @param pCreatedLineId id of created line
	 * @param pCreatedLineTags tags of created line
	 */
	public LinePointsIsIncorrectException(long pCreatedLineId, DefenitionTags pCreatedLineTags)
	{
		super(pCreatedLineId, pCreatedLineTags);
	}
}
