package com.osmviewer.renderingTests;

import com.osmviewer.rendering.RenderableMapLineDrawSettings;
import java.awt.BasicStroke;
import java.awt.Color;

/**
 * Test implementation of RenderableMapLineDrawSettings
 * @author pgalex
 */
public class TestRenderableMapLineDrawSettings implements RenderableMapLineDrawSettings
{

	@Override
	public Color getColor()
	{
		return Color.BLACK;
	}

	@Override
	public float getWidth()
	{
		return 1;
	}

	@Override
	public BasicStroke getStroke()
	{
		return new BasicStroke();
	}
	
}
