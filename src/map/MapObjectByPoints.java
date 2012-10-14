package map;

import drawingStyles.DefenitionTags;

/**
 * Object on map defines by points array
 *
 * @author pgalex
 */
public abstract class MapObjectByPoints extends MapObject
{
	/**
	 * Points of object
	 */
	private MapPosition[] points;

	/**
	 * Create object by points
	 *
	 * @param objectId global OpenStreetMap id of object
	 * @param objectDefenitionTags Tags, describes the object
	 * @param objectPoints points of object
	 * @throws IllegalArgumentException objectPoints is null, empty or contains
	 * null elements
	 */
	public MapObjectByPoints(long objectId, DefenitionTags objectDefenitionTags, MapPosition[] objectPoints) throws IllegalArgumentException
	{
		super(objectId, objectDefenitionTags);

		if (!isPointsCorrect(objectPoints))
		{
			throw new IllegalArgumentException();
		}

		points = objectPoints;
	}

	/**
	 * Is points array correct
	 *
	 * @param objectPoints points for testing
	 * @return is points array correct
	 */
	private boolean isPointsCorrect(MapPosition[] objectPoints)
	{
		if (objectPoints == null)
		{
			return false;
		}

		if (objectPoints.length == 0)
		{
			return false;
		}

		for (int i = 0; i < objectPoints.length; i++)
		{
			if (objectPoints[i] == null)
			{
				return false;
			}
		}

		return true;
	}

	/**
	 * Get points of object
	 *
	 * @return points defines map object position
	 */
	public MapPosition[] getPoints()
	{
		return points;
	}
}
