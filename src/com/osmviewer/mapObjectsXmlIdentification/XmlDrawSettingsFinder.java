package com.osmviewer.mapObjectsXmlIdentification;

import com.osmviewer.rendering.DrawSettingsFinder;
import com.osmviewer.rendering.drawingIdBasedRenderers.PolygonDrawSettings;

/**
 * Finds draw settings by reading them from special xml file
 *
 * @author preobrazhentsev
 */
public class XmlDrawSettingsFinder implements DrawSettingsFinder
{
	/**
	 * Find renderer type that must be used to render map object with given
	 * drawing id
	 *
	 * @param drawingId drawing id to find renderer by. Must be not null
	 * @return renderer typed. Null if not found
	 * @throws IllegalArgumentException drawingId is null
	 */
	@Override
	public String findRendererType(String drawingId) throws IllegalArgumentException
	{
		return "polygon";
	}

	/**
	 * Find draw priority for map object with given drawing id
	 *
	 * @param drawingId drawing id to find draw priority by. Must be not null
	 * @return draw priority value. Null if not found
	 * @throws IllegalArgumentException drawingId is null
	 */
	@Override
	public Integer findDrawPriority(String drawingId) throws IllegalArgumentException
	{
		return 0;
	}

	/**
	 * Find polygon draw settings for map object with given drawing id
	 *
	 * @param drawingId drawing id to find draw settinga by. Must be not null
	 * @return polygon draw settings. Null if can not find suitable draw settings
	 * @throws IllegalArgumentException drawingId is null
	 */
	@Override
	public PolygonDrawSettings findPolygonDrawSettings(String drawingId) throws IllegalArgumentException
	{
		return new XmlPolygonDrawSettings();
	}

}
