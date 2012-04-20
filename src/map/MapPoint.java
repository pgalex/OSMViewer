package map;

import map.exceptions.PointPositionIsNullException;

/**
 * Object on a map, consisting of one node
 *
 * @author pgalex
 */
public class MapPoint extends MapObject
{
	/**
	 * Position of object on a map (spheric coords)
	 */
	private MapPosition position;

	/**
	 * Constructor
	 *
	 * @param pPosition position on a map (spheric coords)
	 * @param pId global unique id from OpenStreetMap
	 * @param pDefenitionTags defenition tags
	 * @throws NullPointerException position is null 
	 */
	public MapPoint(MapPosition pPosition, long pId, DefenitionTags pDefenitionTags) throws NullPointerException
	{
		super(pId, pDefenitionTags);

		if (pPosition == null)
			throw new PointPositionIsNullException(pId, pDefenitionTags);

		position = pPosition;
	}

	/**
	 * Get position on a map
	 *
	 * @return position on a map
	 */
	public MapPosition getPosition()
	{
		return position;
	}
}
