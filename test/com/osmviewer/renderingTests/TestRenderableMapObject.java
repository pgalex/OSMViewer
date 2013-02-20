package com.osmviewer.renderingTests;

import com.osmviewer.mapDefenitionUtilities.DefenitionTags;
import com.osmviewer.rendering.RenderableMapObject;
import com.osmviewer.rendering.RenderableMapObjectDrawSettings;
import com.osmviewer.rendering.RenderableMapObjectsVisitor;

/**
 * Testing implementation of renderable map object - unknown renderable object.
 * Using to test rendering.selecting classes
 *
 * @author pgalex
 */
public class TestRenderableMapObject implements RenderableMapObject
{
	public RenderableMapObjectDrawSettings drawSettings;

	@Override
	public DefenitionTags getDefenitionTags()
	{
		return new DefenitionTags();
	}

	@Override
	public RenderableMapObjectDrawSettings getDrawSettings()
	{
		return drawSettings;
	}

	@Override
	public void acceptRenderingVisitor(RenderableMapObjectsVisitor renderingVisitor) throws IllegalArgumentException
	{
	}

	@Override
	public int determineDrawPriotity() throws NullPointerException
	{
		return drawSettings.getPointDrawPriority();
	}
}
