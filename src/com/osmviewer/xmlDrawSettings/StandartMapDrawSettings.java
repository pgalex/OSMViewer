package com.osmviewer.xmlDrawSettings;

import java.awt.Color;
import com.osmviewer.rendering.RenderableMapDrawSettings;

/**
 * Contains draw settings of map (whole map, not of any object)
 *
 * @author pgalex
 */
public class StandartMapDrawSettings implements RenderableMapDrawSettings
{
	/**
	 * Default map background color
	 */
	private static final Color DEFAULT_MAP_BACKGROUND_COLOR = new Color(242, 239, 233);

	/**
	 * Get map background color
	 *
	 * @return map background color
	 */
	@Override
	public Color getMapBackgroundColor()
	{
		return DEFAULT_MAP_BACKGROUND_COLOR;
	}
}
