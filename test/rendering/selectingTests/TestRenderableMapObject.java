package rendering.selectingTests;

import mapDefenitionUtilities.DefenitionTags;
import rendering.RenderableMapObject;
import rendering.RenderableMapObjectsVisitor;

/**
 * Testing implementation of renderable map object - unknown renderable object
 *
 * @author pgalex
 */
public class TestRenderableMapObject implements RenderableMapObject
{
	@Override
	public DefenitionTags getDefenitionTags()
	{
		return new DefenitionTags();
	}

	@Override
	public Integer getStyleIndex()
	{
		return 0;
	}

	@Override
	public void acceptRenderingVisitor(RenderableMapObjectsVisitor renderingVisitor) throws IllegalArgumentException
	{
	}
}
