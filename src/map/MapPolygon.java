package map;

import drawingStyles.DefenitionTags;
import drawingStyles.MapObjectStyle;
import map.exceptions.LinePointsIsIncorrectException;
import map.rendering.MapObjectsRenderer;

/**
 * Closed line(way) on a map
 *
 * @author pgalex
 */
public class MapPolygon extends MapLine
{
	/**
	 * Constructor
	 *
	 * @param pId global OpenStreetMap id of object
	 * @param pDefenitionTags Tags, describes the polygon
	 * @param pPoints points of polygon
	 * @throws LinePointsIsIncorrectException polygon points array is null, empty
	 * or contains null elements
	 */
	public MapPolygon(long pId, DefenitionTags pDefenitionTags, MapPosition[] pPoints) throws LinePointsIsIncorrectException
	{
		super(pId, pDefenitionTags, pPoints);
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

		pObjectsRenderer.renderPolygon(this);
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

		return pStyle.canBePolygon();
	}
}
