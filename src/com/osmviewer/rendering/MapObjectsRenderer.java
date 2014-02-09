package com.osmviewer.rendering;

import com.osmviewer.map.MapObject;

/**
 * Renderer of map object
 *
 * @author pgalex
 */
public interface MapObjectsRenderer
{
	/**
	 * Render given map object
	 *
	 * @param mapObjectToRender rendering map object. Must be not null
	 * @throws IllegalArgumentException mapObjectToRender is null
	 */
	public void renderMapObject(MapObject mapObjectToRender) throws IllegalArgumentException;
}
