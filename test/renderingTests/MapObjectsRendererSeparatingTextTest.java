package renderingTests;

import drawingStyles.DrawingStylesFactory;
import java.awt.image.BufferedImage;
import static org.junit.Assert.*;
import org.junit.Test;
import rendering.MapObjectsRendererSeparatingText;
import rendering.MapRenderer;

/**
 * Tests of MapObjectsRendererSeparatingText class
 *
 * @author pgalex
 */
public class MapObjectsRendererSeparatingTextTest
{
	/**
	 * Creating with incorrect objects canvas test
	 */
	@Test
	public void creatingWithNullObjectsCanvasTest()
	{
		MapRenderer mapRenderer = new MapRenderer(5, 10, 5);
		try
		{
			BufferedImage textImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
			MapObjectsRendererSeparatingText objectsRenderer = new MapObjectsRendererSeparatingText(null,
							textImage.createGraphics(), DrawingStylesFactory.createStyleViewer(), mapRenderer, 5);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Creating with incorrect text canvas test
	 */
	@Test
	public void creatingWithNullTextCanvasTest()
	{
		MapRenderer mapRenderer = new MapRenderer(5, 10, 5);
		try
		{
			BufferedImage objectsImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
			MapObjectsRendererSeparatingText objectsRenderer = new MapObjectsRendererSeparatingText(objectsImage.createGraphics(),
							null, DrawingStylesFactory.createStyleViewer(), mapRenderer, 5);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}
}
