package com.osmviewer.renderingTests;

import com.osmviewer.mapDefenitionUtilities.DefenitionTags;
import com.osmviewer.mapDefenitionUtilities.Location;
import com.osmviewer.rendering.RenderableMapObjectDrawSettings;
import com.osmviewer.rendering.RenderableMapObjectsVisitor;
import com.osmviewer.rendering.RenderableMapPolygon;
import java.util.Random;

/**
 * Test implementation of RenderableMapPolygon
 *
 * @author pgalex
 */
public class TestRenderableMapPolygon implements RenderableMapPolygon
{
	public int drawPriority;
	public RenderableMapObjectDrawSettings drawSettings;

	@Override
	public int getPointsCount()
	{
		return 3;
	}
	
	@Override
	public Location getPoint(int index) throws IllegalArgumentException
	{
		Random random = new Random();
		return new Location(random.nextDouble(), random.nextDouble());
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
		renderingVisitor.visitPolygon(this);
	}
}
