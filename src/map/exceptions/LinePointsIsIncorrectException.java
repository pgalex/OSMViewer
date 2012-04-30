package map.exceptions;

import map.DefenitionTags;

/**
 * Points of line are null, empty or contains null elements
 *
 * @author pgalex
 */
public class LinePointsIsIncorrectException extends MapObjectCreatingException
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
