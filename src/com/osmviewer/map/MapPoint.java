package com.osmviewer.map;

import java.awt.geom.Rectangle2D;
import com.osmviewer.mapDefenitionUtilities.DefenitionTags;
import com.osmviewer.mapDefenitionUtilities.MapBounds;
import com.osmviewer.mapDefenitionUtilities.MapPosition;
import com.osmviewer.rendering.RenderableMapObjectDrawSettings;
import com.osmviewer.rendering.RenderableMapObjectsVisitor;
import com.osmviewer.rendering.RenderableMapPoint;

/**
 * Object on a map, consisting of one node
 *
 * @author pgalex
 */
public class MapPoint extends MapObject implements RenderableMapPoint
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
	 * Determine draw prority using draw settings
	 *
	 * @return draw prority
	 * @throws NullPointerException draw settings not set
	 */
	@Override
	public int determineDrawPriotity() throws NullPointerException
	{
		RenderableMapObjectDrawSettings drawSettings = getDrawSettings();
		if (drawSettings == null)
		{
			throw new NullPointerException();
		}

		return drawSettings.getPointDrawPriority();
	}
	
	/**
	 * Get position on a map
	 *
	 * @return position on a map
	 */
	@Override
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

		if (area.isZero())
		{
			return false;
		}

		Rectangle2D areaAsRectangle = new Rectangle2D.Double(area.getLatitudeMinimum(), area.getLongitudeMinimum(),
						area.getLatitudeSize(), area.getLongitudeSize());

		return areaAsRectangle.contains(position.getLatitude(), position.getLongitude());
	}

	/**
	 * Render with objects render visitor
	 *
	 * @param objectsRenderer objects renderer
	 * @throws IllegalArgumentException objectsRenderer is null
	 */
	@Override
	public void acceptRenderingVisitor(RenderableMapObjectsVisitor objectsRenderer) throws IllegalArgumentException
	{
		if (objectsRenderer == null)
		{
			throw new IllegalArgumentException();
		}

		objectsRenderer.visitPoint(this);
	}

	/**
	 * Can this type of map object be drawen with this style
	 *
	 * @param objectDrawStyle drawing style of object
	 * @return Can this type of map object be drawen with this style
	 */
	@Override
	public boolean canBeDrawenWithStyle(RenderableMapObjectDrawSettings objectDrawStyle)
	{
		if (objectDrawStyle == null)
		{
			return false;
		}

		return objectDrawStyle.isCanBePoint();
	}
}
