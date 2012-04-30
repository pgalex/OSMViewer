package map;

import java.util.ArrayList;
import map.exceptions.LinePointsIsIncorrectException;

/**
 * Line (way) on a map
 *
 * @author pgalex
 */
public class MapLine extends MapObject
{
	/**
	 * Points of line (each point is a map object too)
	 */
	private ArrayList<MapPoint> points;

	/**
	 * Constructor
	 *
	 * @param pId global OpenStreetMap id of object
	 * @param pDefenitionTags Tags, describes the line
	 * @param pPoints points of line
	 * @throws LinePointsIsIncorrectException line points array is null, empty or
	 * contains null elements
	 */
	public MapLine(long pId, DefenitionTags pDefenitionTags, ArrayList<MapPoint> pPoints) throws LinePointsIsIncorrectException
	{
		super(pId, pDefenitionTags);

		if (pPoints == null)
			throw new LinePointsIsIncorrectException(pId, pDefenitionTags);
		if (pPoints.isEmpty())
			throw new LinePointsIsIncorrectException(pId, pDefenitionTags);
		if (pPoints.contains(null))
			throw new LinePointsIsIncorrectException(pId, pDefenitionTags);

		points = pPoints;
	}

	/**
	 * Get points of line
	 *
	 * @return points of line
	 */
	public ArrayList<MapPoint> getPoints()
	{
		return points;
	}
}
