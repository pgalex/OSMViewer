package map;

import drawingStyles.DefenitionTags;
import drawingStyles.MapObjectDrawSettings;
import map.rendering.MapObjectsRenderer;

/**
 * Line (non closed way) on a map
 *
 * @author pgalex
 */
public class MapLine extends MapObjectByPoints
{
	/**
	 * Minimum points count that can be using for line
	 */
	private static final int MINIMUM_POINTS_COUNT = 2;

	/**
	 * Create with parameters
	 *
	 * @param lineId global OpenStreetMap id of object
	 * @param lineDefenitionTags Tags, describes the line
	 * @param linePoints points of line
	 * @throws IllegalArgumentException linePoints contains less than 2 elements
	 */
	public MapLine(long lineId, DefenitionTags lineDefenitionTags, MapPosition[] linePoints) throws IllegalArgumentException
	{
		super(lineId, lineDefenitionTags, linePoints);

		if (linePoints.length < MINIMUM_POINTS_COUNT)
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

		objectsRenderer.renderLine(this);
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

		return objectDrawStyle.isCanBeLine();
	}
}
