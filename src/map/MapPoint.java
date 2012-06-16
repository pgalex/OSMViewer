package map;

import drawingStyles.MapObjectStyle;
import map.rendering.MapObjectsRenderer;
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
	 * @throws PointPositionIsNullException position is null
	 */
	public MapPoint(MapPosition pPosition, long pId, DefenitionTags pDefenitionTags) throws PointPositionIsNullException
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

	/**
	 * Render with objects render visitor
	 *
	 * @param pObjectsRenderer objects renderer
	 */
	@Override
	public void acceptRenderer(MapObjectsRenderer pObjectsRenderer)
	{
		if (pObjectsRenderer == null)
			return;

		pObjectsRenderer.renderPoint(this);
	}

	/**
	 * Can this type of map object be drawen with this style
	 *
	 * @param pStyle drawing style of object
	 * @return Can this type of map object be drawen with this style
	 */
	@Override
	protected boolean canBeDrawenWithStyle(MapObjectStyle pStyle)
	{
		if (pStyle == null)
			return false;

		return pStyle.isCanBePoint();
	}
}
