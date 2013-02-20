package com.osmviewer.map;

import com.osmviewer.mapDefenitionUtilities.DefenitionTags;
import com.osmviewer.rendering.RenderableMapObjectDrawSettings;

/**
 * Finder of map object draw settings
 *
 * @author pgalex
 */
public interface RenderableMapObjectsDrawSettingsFinder
{
	/**
	 * Find draw settings of map object, by its defention tags
	 *
	 * @param mapObjectDefenitionTags tags of map object, which draw settings need
	 * to find
	 * @return draw settings of map object. null if not found
	 */
	public RenderableMapObjectDrawSettings findMapObjectDrawSettings(DefenitionTags mapObjectDefenitionTags);
}
