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
	public int drawPriority;

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
		return drawPriority;
	}

	@Override
	public int getLineDrawPriority()
	{
		return drawPriority;
	}

	@Override
	public int getPolygonDrawPriority()
	{
		return drawPriority;
	}

	@Override
	public RenderableMapPointDrawSettings findPointDrawSettings(int scaleLevel)
	{
		return null;
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
		return null;
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
