package com.osmviewer.rendering;

import com.osmviewer.rendering.drawingIdBasedRenderers.PolygonDrawSettings;

/**
 * Finds draw settings - parameters for map object drawing
 *
 * @author preobrazhentsev
 */
public interface DrawSettingsFinder
{
	/**
	 * Find renderer type that must be used to render map object with given
	 * drawing id
	 *
	 * @param drawingId drawing id to find renderer by. Must be not null
	 * @return renderer typed. Null if not found
	 * @throws IllegalArgumentException drawingId is null
	 */
	public String findRendererType(String drawingId) throws IllegalArgumentException;

	/**
	 * Find draw priority for map object with given drawing id
	 *
	 * @param drawingId drawing id to find draw priority by. Must be not null
	 * @return draw priority value. Null if not found
	 * @throws IllegalArgumentException drawingId is null
	 */
	public Integer findDrawPriority(String drawingId) throws IllegalArgumentException;

	/**
	 * Find polygon draw settings for map object with given drawing id
	 *
	 * @param drawingId drawing id to find draw settinga by. Must be not null
	 * @return polygon draw settings. Null if can not find suitable draw settings
	 * @throws IllegalArgumentException drawingId is null
	 */
	public PolygonDrawSettings findPolygonDrawSettings(String drawingId) throws IllegalArgumentException;

	/**
	 * Get common map draw settings
	 *
	 * @return map draw settings
	 */
	public RenderableMapDrawSettings getMapDrawSettings();
}
