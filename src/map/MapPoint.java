package map;

import drawingStyles.DefenitionTags;
import drawingStyles.MapObjectDrawSettings;

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
	 * Create with parameters
	 *
	 * @param pointPosition position on a map (spheric coords)
	 * @param pointId global unique id from OpenStreetMap
	 * @param pointDefenitionTags defenition tags
	 * @throws IllegalArgumentException position is null
	 */
	public MapPoint(MapPosition pointPosition, long pointId, DefenitionTags pointDefenitionTags) throws IllegalArgumentException
	{
		super(pointId, pointDefenitionTags);

		if (pointPosition == null)
		{
			throw new IllegalArgumentException();
		}

		position = pointPosition;
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
	 * Is object visible in given area
	 *
	 * @param area area to test visibility in
	 * @return is object visible in area
	 * @throws IllegalArgumentException area is null
	 */
	@Override
	public boolean isVisibleInArea(MapBounds area) throws IllegalArgumentException
	{
		if (area == null)
		{
			throw new IllegalArgumentException();
		}

		return true;
	}

	/**
	 * Render with objects render visitor
	 *
	 * @param objectsRenderer objects renderer
	 */
	@Override
	public void acceptRenderer(MapObjectsRenderer objectsRenderer)
	{
		if (objectsRenderer == null)
		{
			return;
		}

		objectsRenderer.renderPoint(this);
	}

	/**
	 * Can this type of map object be drawen with this style
	 *
	 * @param objectDrawStyle drawing style of object
	 * @return Can this type of map object be drawen with this style
	 */
	@Override
	protected boolean canBeDrawenWithStyle(MapObjectDrawSettings objectDrawStyle)
	{
		if (objectDrawStyle == null)
		{
			return false;
		}

		return objectDrawStyle.isCanBePoint();
	}
}
