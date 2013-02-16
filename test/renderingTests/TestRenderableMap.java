package renderingTests;

import mapDefenitionUtilities.MapBounds;
import rendering.RenderableMap;
import rendering.RenderableMapObjectsDrawPriorityComparator;
import rendering.RenderableMapObjectsVisitor;

/**
 * Fake implementation of renderable map
 *
 * @author pgalex
 */
public class TestRenderableMap implements RenderableMap
{
	@Override
	public void renderObjectsByDrawPriority(RenderableMapObjectsVisitor objectsVisitor,
					MapBounds area, RenderableMapObjectsDrawPriorityComparator objectsDrawPriorityComparator) throws IllegalArgumentException
	{
	}
}
