package renderingTests;

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
