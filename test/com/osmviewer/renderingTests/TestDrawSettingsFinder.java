package com.osmviewer.renderingTests;

import com.osmviewer.rendering.DrawSettingsFinder;
import com.osmviewer.rendering.RenderableMapDrawSettings;
import com.osmviewer.rendering.drawingIdBasedRenderers.PolygonDrawSettings;

/**
 * Test implementation of DrawSettingsFinder
 *
 * @author preobrazhentsev
 */
public class TestDrawSettingsFinder implements DrawSettingsFinder
{

	@Override
	public String findRendererType(String drawingId) throws IllegalArgumentException
	{
		return "0";
	}

	@Override
	public Integer findDrawPriority(String drawingId) throws IllegalArgumentException
	{
		return 0;
	}

	@Override
	public PolygonDrawSettings findPolygonDrawSettings(String drawingId) throws IllegalArgumentException
	{
		return null;
	}

	@Override
	public RenderableMapDrawSettings getMapDrawSettings()
	{
		return new TestRenderableMapDrawSettings();
	}

}
