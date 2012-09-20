package mapTests;

import drawingStyles.DrawingStylesFactory;
import map.rendering.MapObjectsRendererSeparatingText;
import map.rendering.MapRenderer;
import org.junit.Test;

/**
 *
 * @author pgalex
 */
public class MapObjectsRendererSeparatingTextTest
{
	/**
	 * Creating with incorrect canvas test
	 */
	@Test
	public void creatingWithNullCanvas()
	{
		MapRenderer mapRenderer = new MapRenderer(5, 10, 5);
		try
		{
			MapObjectsRendererSeparatingText objectsRenderer = new MapObjectsRendererSeparatingText(null, DrawingStylesFactory.createStyleViewer(), mapRenderer, 5);
			//fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}
	// can not test other arguments cuz can not get valid targetCanvas
}
