package rendering.selectingTests;

import mapDefenitionUtilities.DefenitionTags;
import rendering.RenderableMapObject;
import rendering.RenderableMapObjectDrawSettings;
import rendering.RenderableMapObjectsVisitor;

/**
 * Testing implementation of renderable map object - unknown renderable object.
 * Using to test rendering.selecting classes
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
	public RenderableMapObjectDrawSettings getDrawSettings()
	{
		return null;
	}

	@Override
	public void acceptRenderingVisitor(RenderableMapObjectsVisitor renderingVisitor) throws IllegalArgumentException
	{
	}
}
