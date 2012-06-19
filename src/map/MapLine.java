package map;

import drawingStyles.DefenitionTags;
import drawingStyles.MapObjectStyle;
import map.exceptions.LinePointsIsIncorrectException;
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
	 * Constructor
	 *
	 * @param pId global OpenStreetMap id of object
	 * @param pDefenitionTags Tags, describes the line
	 * @param pPoints points of line
	 * @throws LinePointsIsIncorrectException line points array is null, not
	 * contains needed points count or contains null elements
	 */
	public MapLine(long pId, DefenitionTags pDefenitionTags, MapPosition[] pPoints) throws LinePointsIsIncorrectException
	{
		super(pId, pDefenitionTags);

		if (isPointsIncorrect(pPoints))
			throw new LinePointsIsIncorrectException(pId, pDefenitionTags);

		points = pPoints;
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
	 * @param pObjectsRenderer objects renderer
	 */
	@Override
	public void acceptRenderer(MapObjectsRenderer pObjectsRenderer)
	{
		if (pObjectsRenderer == null)
			return;

		pObjectsRenderer.renderLine(this);
	}

	/**
	 * Is array of points incorrect
	 *
	 * @param pPoints points of line
	 * @return is array of points incorrect
	 */
	private boolean isPointsIncorrect(MapPosition[] pPoints)
	{
		if (pPoints == null)
			return true;
		if (pPoints.length < MINIMUM_POINTS_COUNT)
			return true;
		for (int i = 0; i < pPoints.length; i++)
		{
			if (pPoints[i] == null)
				return true;
		}

		return false;
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

		return pStyle.isCanBeLine();
	}
}
