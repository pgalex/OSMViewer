package map;

import drawingStyles.DefenitionTags;
import drawingStyles.MapObjectDrawStyle;
import map.rendering.MapObjectsRenderer;

/**
 * Closed line(way) on a map
 *
 * @author pgalex
 */
public class MapPolygon extends MapLine
{
	/**
	 * Create with parameters
	 *
	 * @param polygonId global OpenStreetMap id of object
	 * @param polygonDefenitionTags Tags, describes the polygon
	 * @param polygonPoints polygonPoints of polygon
	 * @throws IllegalArgumentException polygon polygonPoints array is null,
	 * empty or contains null elements
	 */
	public MapPolygon(long polygonId, DefenitionTags polygonDefenitionTags, MapPosition[] polygonPoints) throws IllegalArgumentException
	{
		super(polygonId, polygonDefenitionTags, polygonPoints);
	}

	/**
	 * Get center point of polygon
	 *
	 * @return center point
	 */
	public MapPosition getCenterPoint()
	{
		double pointLatitudeSum = 0;
		double pointLongitudeSum = 0;

		MapPosition[] polygonPoints = getPoints();
		for (int i = 0; i < polygonPoints.length; i++)
		{
			pointLatitudeSum += polygonPoints[i].getLatitude();
			pointLongitudeSum += polygonPoints[i].getLongitude();
		}

		return new MapPosition(pointLatitudeSum / polygonPoints.length, pointLongitudeSum / polygonPoints.length);
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
	protected boolean canBeDrawenWithStyle(MapObjectDrawStyle objectDrawStyle)
	{
		if (objectDrawStyle == null)
		{
			return false;
		}

		return objectDrawStyle.canBePolygon();
	}
}
