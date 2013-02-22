package com.osmviewer.mapTests;

import com.osmviewer.mapDefenitionUtilities.DefenitionTags;
import com.osmviewer.rendering.RenderableMapLineDrawSettings;
import com.osmviewer.rendering.RenderableMapObjectDrawSettings;
import com.osmviewer.rendering.RenderableMapPointDrawSettings;
import com.osmviewer.rendering.RenderableMapPolygonDrawSettings;
import com.osmviewer.rendering.TextDrawSettings;

/**
 * Mock implementation of RenderableMapObjectDrawSettings
 *
 * @author pgalex
 */
public class TestRenderableMapObjectDrawSettings implements RenderableMapObjectDrawSettings
{
	public int pointDrawPriority;
	public int lineDrawPriority;
	public int polygonDrawPriority;

	@Override
	public boolean isCanBePoint()
	{
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public boolean isCanBeLine()
	{
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public boolean isCanBePolygon()
	{
		throw new UnsupportedOperationException("Not supported yet.");
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
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public RenderableMapLineDrawSettings findLineDrawSettings(int scaleLevel)
	{
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public RenderableMapPolygonDrawSettings findPolygonDrawSettings(int scaleLevel)
	{
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public TextDrawSettings findTextDrawSettings(int scaleLevel)
	{
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public String findTextInTags(DefenitionTags tagsWhereFindText)
	{
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public String getName()
	{
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public String getDescription()
	{
		throw new UnsupportedOperationException("Not supported yet.");
	}
}
