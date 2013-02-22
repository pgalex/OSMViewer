package com.osmviewer.renderingTests;

import com.osmviewer.rendering.RenderableMapDrawSettings;
import java.awt.Color;

/**
 * Test implementation of MapDrawSettings
 *
 * @author pgalex
 */
public class TestRenderableMapDrawSettings implements RenderableMapDrawSettings
{
	@Override
	public Color getMapBackgroundColor()
	{
		return Color.WHITE;
	}
}
