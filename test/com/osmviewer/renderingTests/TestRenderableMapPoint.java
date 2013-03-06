package com.osmviewer.renderingTests;

import com.osmviewer.mapDefenitionUtilities.DefenitionTags;
import com.osmviewer.mapDefenitionUtilities.Location;
import com.osmviewer.rendering.RenderableMapObjectDrawSettings;
import com.osmviewer.rendering.RenderableMapObjectsVisitor;
import com.osmviewer.rendering.RenderableMapPoint;

/**
 * Test implementation of RenderableMapPoint
 *
 * @author pgalex
 */
public class TestRenderableMapPoint implements RenderableMapPoint
{
	public int drawPriority;
	public RenderableMapObjectDrawSettings drawSettings;

	@Override
	public Location getPosition()
	{
		return new Location();
	}
	
	@Override
	public int determineDrawPriotity() throws NullPointerException
	{
		return drawPriority;
	}
	
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
		renderingVisitor.visitPoint(this);
	}
}
