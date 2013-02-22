package com.osmviewer.renderingTests;

import com.osmviewer.mapDefenitionUtilities.DefenitionTags;
import com.osmviewer.rendering.RenderableMapLineDrawSettings;
import com.osmviewer.rendering.RenderableMapObjectDrawSettings;
import com.osmviewer.rendering.RenderableMapPointDrawSettings;
import com.osmviewer.rendering.RenderableMapPolygonDrawSettings;
import com.osmviewer.rendering.TextDrawSettings;

/**
 * Test implementations of RenderableMapObjectDrawSettings
 *
 * @author pgalex
 */
public class TestRenderableMapObjectDrawSettings implements RenderableMapObjectDrawSettings
{
	public int pointDrawPriority;
	public int lineDrawPriority;
	public int polygonDrawPriority;
	public RenderableMapPointDrawSettings pointDrawSettings;

	@Override
	public boolean isCanBePoint()
	{
		return true;
	}

	@Override
	public boolean isCanBeLine()
	{
		return true;
	}

	@Override
	public boolean isCanBePolygon()
	{
		return true;
	}

	@Override
	public int getPointDrawPriority()
	{
		return pointDrawPriority;
	}

	@Override
	public int getLineDrawPriority()
	{
		return lineDrawPriority;
	}

	@Override
	public int getPolygonDrawPriority()
	{
		return polygonDrawPriority;
	}

	@Override
	public RenderableMapPointDrawSettings findPointDrawSettings(int scaleLevel)
	{
		return pointDrawSettings;
	}

	@Override
	public RenderableMapLineDrawSettings findLineDrawSettings(int scaleLevel)
	{
		return null;
	}

	@Override
	public RenderableMapPolygonDrawSettings findPolygonDrawSettings(int scaleLevel)
	{
		return null;
	}

	@Override
	public TextDrawSettings findTextDrawSettings(int scaleLevel)
	{
		return new TestTextDrawSettings();
	}

	@Override
	public String findTextInTags(DefenitionTags tagsWhereFindText)
	{
		return "";
	}

	@Override
	public String getName()
	{
		return "";
	}

	@Override
	public String getDescription()
	{
		return "";
	}
}
