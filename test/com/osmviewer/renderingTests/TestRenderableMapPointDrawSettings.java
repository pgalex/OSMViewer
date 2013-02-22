package com.osmviewer.renderingTests;

import com.osmviewer.rendering.RenderableMapPointDrawSettings;
import java.awt.image.BufferedImage;

/**
 * Test implementation of RenderableMapPointDrawSettings
 *
 * @author pgalex
 */
public class TestRenderableMapPointDrawSettings implements RenderableMapPointDrawSettings
{
	public BufferedImage icon;
	@Override
	public BufferedImage getIcon()
	{
		return icon;
	}
}
