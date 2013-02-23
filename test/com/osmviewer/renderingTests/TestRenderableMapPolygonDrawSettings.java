package com.osmviewer.renderingTests;

import com.osmviewer.rendering.RenderableMapLineDrawSettings;
import com.osmviewer.rendering.RenderableMapPolygonDrawSettings;
import java.awt.Color;
import java.awt.Paint;

/**
 * Test implementation of RenderableMapPolygonDrawSettings
 *
 * @author pgalex
 */
public class TestRenderableMapPolygonDrawSettings implements RenderableMapPolygonDrawSettings
{
	@Override
	public boolean isDrawInnerPart()
	{
		return true;
	}

	@Override
	public boolean isDrawBorder()
	{
		return true;
	}

	@Override
	public Paint getPaint()
	{
		return Color.BLACK;
	}

	@Override
	public RenderableMapLineDrawSettings findBorderDrawSettings()
	{
		return new TestRenderableMapLineDrawSettings();
	}
}
