package map;

import map.exceptions.LinePointsIsIncorrectException;

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
	 * Points of line
	 */
	private MapPosition[] points;

	/**
	 * Constructor
	 *
	 * @param pId global OpenStreetMap id of object
	 * @param pDefenitionTags Tags, describes the line
	 * @param pPoints points of line
	 * @throws LinePointsIsIncorrectException line points array is null, empty or
	 * contains null elements
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
		if (pPoints.length == 0)
			return true;
		for (int i = 0; i < pPoints.length; i++)
		{
			if (pPoints[i] == null)
				return true;
		}

		return false;
	}
}
