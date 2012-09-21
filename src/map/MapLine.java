package map;

import drawingStyles.DefenitionTags;
import drawingStyles.MapObjectDrawStyle;
import map.rendering.MapObjectsRenderer;

/**
 * Line (way) on a map. If point of line describes any object on map it will be
 * another object (not linked with line) cuz they can have another draw
 * priority, selecting etc
 *
 * @author pgalex
 */
public class MapLine extends MapObject
{
	/**
	 * Minimum point count to define that line is correct
	 */
	private static final int MINIMUM_POINTS_COUNT = 2;
	/**
	 * Points of line
	 */
	private MapPosition[] points;

	/**
	 * Create with parameters
	 *
	 * @param lineId global OpenStreetMap id of object
	 * @param lineDefenitionTags Tags, describes the line
	 * @param linePoints points of line
	 * @throws IllegalArgumentException line points array is null, not
	 * contains needed points count or contains null elements
	 */
	public MapLine(long lineId, DefenitionTags lineDefenitionTags, MapPosition[] linePoints) throws IllegalArgumentException
	{
		super(lineId, lineDefenitionTags);

		if (isPointsIncorrect(linePoints))
		{
			throw new IllegalArgumentException();
		}

		points = linePoints;
	}

	/**
	 * Is array of points incorrect
	 *
	 * @param points points of line
	 * @return is array of points incorrect
	 */
	private boolean isPointsIncorrect(MapPosition[] points)
	{
		if (points == null)
		{
			return true;
		}
		if (points.length < MINIMUM_POINTS_COUNT)
		{
			return true;
		}

		for (int i = 0; i < points.length; i++)
		{
			if (points[i] == null)
			{
				return true;
			}
		}

		return false;
	}

	/**
	 * Get points of line
	 *
	 * @return points of line
	 */
	public MapPosition[] getPoints()
	{
		return points;
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
	protected boolean canBeDrawenWithStyle(MapObjectDrawStyle objectDrawStyle)
	{
		if (objectDrawStyle == null)
		{
			return false;
		}

		return objectDrawStyle.canBeLine();
	}
}
