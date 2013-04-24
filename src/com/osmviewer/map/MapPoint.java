package com.osmviewer.map;

import com.osmviewer.mapDefenitionUtilities.DefenitionTags;
import com.osmviewer.mapDefenitionUtilities.MapBounds;
import com.osmviewer.mapDefenitionUtilities.Location;
import com.osmviewer.rendering.RenderableMapObjectDrawSettings;
import com.osmviewer.rendering.RenderableMapObjectsVisitor;
import com.osmviewer.rendering.RenderableMapPoint;
import java.awt.geom.Rectangle2D;

/**
 * Object on a map, consisting of one node
 *
 * @author pgalex
 */
public class MapPoint extends MapObject implements RenderableMapPoint
{
	/**
	 * ID, comes from OpenStreetMap
	 */
	private long id;
	/**
	 * Position of object on a map (spheric coords)
	 */
	private Location position;

	/**
	 * Create with parameters
	 *
	 * @param pointPosition position on a map (spheric coords)
	 * @param pointId point(node) id from OpenStreetMap
	 * @param pointDefenitionTags defenition tags
	 * @throws IllegalArgumentException position is null
	 */
	public MapPoint(Location pointPosition, long pointId, DefenitionTags pointDefenitionTags) throws IllegalArgumentException
	{
		super(pointDefenitionTags);

		if (pointPosition == null)
		{
			throw new IllegalArgumentException("pointPosition is null");
		}

		id = pointId;
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
			throw new NullPointerException("draw settings not set (null)");
		}

		return drawSettings.getPointDrawPriority();
	}

	/**
	 * Get position on a map
	 *
	 * @return position on a map
	 */
	@Override
	public Location getPosition()
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
			throw new IllegalArgumentException("area is null");
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
			throw new IllegalArgumentException("objectsRenderer is null");
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

	/**
	 * Get ID
	 *
	 * @return ID of object, comes from OpenStreetMap
	 */
	public long getId()
	{
		return id;
	}

	/**
	 * Is equals to objects.
	 *
	 * @param objectToCompare object for comparing
	 * @return is equals to given objects. node-like objects is equal to other
	 * node-like if thier ids equals, and to equals to other types of map objects
	 */
	@Override
	public boolean equals(Object objectToCompare)
	{
		if (objectToCompare instanceof MapPoint)
		{
			MapPoint comparingPoint = (MapPoint) objectToCompare;
			return comparingPoint.id == id;
		}
		else
		{
			return false;
		}
	}

	/**
	 * Get hash code
	 *
	 * @return hash code
	 */
	@Override
	public int hashCode()
	{
		int hash = 7;
		hash = 79 * hash + (int) (this.id ^ (this.id >>> 32));
		return hash;
	}
}
