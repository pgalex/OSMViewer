package com.osmviewer.map;

import com.osmviewer.mapDefenitionUtilities.DefenitionTags;
import com.osmviewer.mapDefenitionUtilities.MapBounds;
import com.osmviewer.rendering.RenderableMapObject;
import com.osmviewer.rendering.RenderableMapObjectDrawSettings;

/**
 * Some object on a map
 *
 * @author pgalex
 */
public abstract class MapObject implements RenderableMapObject
{
	/**
	 * ID of object, comes from OpenStreetMap
	 */
	private long id;
	/**
	 * Tags, describes the object.Can be empty ( cuz in openstreetmap can be
	 * object without tags )
	 */
	private DefenitionTags defenitionTags;
	/**
	 * Draw settings of map object. If null - not defined
	 */
	private RenderableMapObjectDrawSettings drawSettings;

	/**
	 * Create with parameters
	 *
	 * @param objectId global OpenStreetMap id of object
	 * @param objectDefenitionTags Tags, describes the object
	 * @throws IllegalArgumentException objectDefenitionTags is null
	 */
	public MapObject(long objectId, DefenitionTags objectDefenitionTags) throws IllegalArgumentException
	{
		if (objectDefenitionTags == null)
		{
			throw new IllegalArgumentException("objectDefenitionTags is null");
		}

		id = objectId;
		defenitionTags = objectDefenitionTags;
		drawSettings = null;
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
	 * Get defenition tags
	 *
	 * @return defenition tags
	 */
	@Override
	public DefenitionTags getDefenitionTags()
	{
		return defenitionTags;
	}

	/**
	 * Get draw settings
	 *
	 * @return draw settings of map object. Null if not defined
	 */
	@Override
	public RenderableMapObjectDrawSettings getDrawSettings()
	{
		return drawSettings;
	}

	/**
	 * Set draw settings
	 *
	 * @param drawSettingsToSet new draw settings
	 * @throws IllegalArgumentException drawSettingsToSet is null
	 */
	public void setDrawSettings(RenderableMapObjectDrawSettings drawSettingsToSet) throws IllegalArgumentException
	{
		if (drawSettingsToSet == null)
		{
			throw new IllegalArgumentException("drawSettingsToSet is null");
		}

		drawSettings = drawSettingsToSet;
	}

	/**
	 * Is object visible in given area
	 *
	 * @param area area to test visibility in
	 * @return is object visible in area
	 * @throws IllegalArgumentException area is null
	 */
	public abstract boolean isVisibleInArea(MapBounds area) throws IllegalArgumentException;

	/**
	 * Can this type of map object be drawen with this style
	 *
	 * @param drawSettings drawing style of object
	 * @return Can this type of map object be drawen with this style
	 */
	public abstract boolean canBeDrawenWithStyle(RenderableMapObjectDrawSettings drawSettings);
}
