package map;

import drawingStyles.DefenitionTags;
import drawingStyles.MapObjectDrawSettings;

/**
 * Polygon (closed way) on a map
 *
 * @author pgalex
 */
public class MapPolygon extends MapObjectByPoints
{
	/**
	 * Minimum points count that can be using for polygon
	 */
	private static final int MINIMUM_POINTS_COUNT = 3;

	/**
	 * Create with parameters
	 *
	 * @param polygonId global OpenStreetMap id of object
	 * @param polygonDefenitionTags Tags, describes the polygon
	 * @param polygonPoints polygonPoints of polygon
	 * @throws IllegalArgumentException polygonPointsless contains less than 3
	 * elements
	 */
	public MapPolygon(long polygonId, DefenitionTags polygonDefenitionTags, MapPosition[] polygonPoints) throws IllegalArgumentException
	{
		super(polygonId, polygonDefenitionTags, polygonPoints);

		if (polygonPoints.length < MINIMUM_POINTS_COUNT)
		{
			throw new IllegalArgumentException();
		}
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

		objectsRenderer.renderPolygon(this);
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

		return objectDrawStyle.isCanBePolygon();
	}
}
