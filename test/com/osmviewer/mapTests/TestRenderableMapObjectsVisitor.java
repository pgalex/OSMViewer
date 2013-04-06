package com.osmviewer.mapTests;

import com.osmviewer.rendering.RenderableMapLine;
import com.osmviewer.rendering.RenderableMapObjectsVisitor;
import com.osmviewer.rendering.RenderableMapPoint;
import com.osmviewer.rendering.RenderableMapPolygon;

/**
 * Test implementation of RenderableMapObjectsVisitor
 *
 * @author pgalex
 */
public class TestRenderableMapObjectsVisitor implements RenderableMapObjectsVisitor
{
	@Override
	public void visitPoint(RenderableMapPoint renderablePoint) throws IllegalArgumentException
	{
	}

	@Override
	public void visitLine(RenderableMapLine renderableLine) throws IllegalArgumentException
	{
	}

	@Override
	public void visitPolygon(RenderableMapPolygon renderablePolygon) throws IllegalArgumentException
	{
	}
}
