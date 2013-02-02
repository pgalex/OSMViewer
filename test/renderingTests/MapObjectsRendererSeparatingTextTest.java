package renderingTests;

import drawingStyles.DrawingStylesFactory;
import java.awt.image.BufferedImage;
import static org.junit.Assert.*;
import org.junit.Test;
import rendering.MapObjectsRendererSeparatingText;
import rendering.MapRenderer;
import rendering.selectng.SelectingBuffer;

/**
 * Tests of MapObjectsRendererSeparatingText class
 *
 * @author pgalex
 */
public class MapObjectsRendererSeparatingTextTest
{
	/**
	 * Rendering null polygon test
	 */
	@Test
	public void renderingPolygonLineTest()
	{
		MapRenderer mapRenderer = new MapRenderer(5, 10, 5);
		BufferedImage textImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
		BufferedImage objectsImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
		SelectingBuffer selectingBuffer = new SelectingBuffer();

		MapObjectsRendererSeparatingText objectsRenderer = new MapObjectsRendererSeparatingText(objectsImage.createGraphics(),
						textImage.createGraphics(), DrawingStylesFactory.createStyleViewer(), mapRenderer, 5,
						selectingBuffer);
		try
		{
			objectsRenderer.visitPolygon(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test setting null object to draw as highlighted
	 */
	@Test
	public void setNullObjectToHighlightTest()
	{
		MapRenderer mapRenderer = new MapRenderer(5, 10, 5);
		BufferedImage textImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
		BufferedImage objectsImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
		SelectingBuffer selectingBuffer = new SelectingBuffer();

		MapObjectsRendererSeparatingText objectsRenderer = new MapObjectsRendererSeparatingText(objectsImage.createGraphics(),
						textImage.createGraphics(), DrawingStylesFactory.createStyleViewer(), mapRenderer, 5,
						selectingBuffer);
		try
		{
			objectsRenderer.setObjectToDrawAsHighlighted(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Rendering null line test
	 */
	@Test
	public void renderingNullLineTest()
	{
		MapRenderer mapRenderer = new MapRenderer(5, 10, 5);
		BufferedImage textImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
		BufferedImage objectsImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
		SelectingBuffer selectingBuffer = new SelectingBuffer();

		MapObjectsRendererSeparatingText objectsRenderer = new MapObjectsRendererSeparatingText(objectsImage.createGraphics(),
						textImage.createGraphics(), DrawingStylesFactory.createStyleViewer(), mapRenderer, 5,
						selectingBuffer);
		try
		{
			objectsRenderer.visitLine(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Rendering null point test
	 */
	@Test
	public void renderingNullPointTest()
	{
		MapRenderer mapRenderer = new MapRenderer(5, 10, 5);
		BufferedImage textImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
		BufferedImage objectsImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
		SelectingBuffer selectingBuffer = new SelectingBuffer();

		MapObjectsRendererSeparatingText objectsRenderer = new MapObjectsRendererSeparatingText(objectsImage.createGraphics(),
						textImage.createGraphics(), DrawingStylesFactory.createStyleViewer(), mapRenderer, 5,
						selectingBuffer);
		try
		{
			objectsRenderer.visitPoint(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Creating with incorrect objects canvas test
	 */
	@Test
	public void creatingWithNullObjectsCanvasTest()
	{
		MapRenderer mapRenderer = new MapRenderer(5, 10, 5);
		try
		{
			SelectingBuffer selectingBuffer = new SelectingBuffer();
			BufferedImage textImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
			MapObjectsRendererSeparatingText objectsRenderer = new MapObjectsRendererSeparatingText(null,
							textImage.createGraphics(), DrawingStylesFactory.createStyleViewer(), mapRenderer, 5,
							selectingBuffer);
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
			SelectingBuffer selectingBuffer = new SelectingBuffer();
			BufferedImage objectsImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
			MapObjectsRendererSeparatingText objectsRenderer = new MapObjectsRendererSeparatingText(objectsImage.createGraphics(),
							null, DrawingStylesFactory.createStyleViewer(), mapRenderer, 5,
							selectingBuffer);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Creating with null selecting buffer
	 */
	@Test
	public void creatingWithNullSelectingBufferTest()
	{
		MapRenderer mapRenderer = new MapRenderer(5, 10, 5);
		try
		{
			BufferedImage objectsImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
			MapObjectsRendererSeparatingText objectsRenderer = new MapObjectsRendererSeparatingText(objectsImage.createGraphics(),
							objectsImage.createGraphics(), DrawingStylesFactory.createStyleViewer(), mapRenderer, 5,
							null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Creating with null style viewer test
	 */
	@Test
	public void creatingWithNullStyleViewerTest()
	{
		MapRenderer mapRenderer = new MapRenderer(5, 10, 5);
		try
		{
			SelectingBuffer selectingBuffer = new SelectingBuffer();
			BufferedImage textImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
			MapObjectsRendererSeparatingText objectsRenderer = new MapObjectsRendererSeparatingText(textImage.createGraphics(),
							textImage.createGraphics(), null, mapRenderer, 5,
							selectingBuffer);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Creating with null coordinates converter test
	 */
	@Test
	public void creatingWithNullCoordinatesConverterTest()
	{
		MapRenderer mapRenderer = new MapRenderer(5, 10, 5);
		try
		{
			SelectingBuffer selectingBuffer = new SelectingBuffer();
			BufferedImage textImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
			MapObjectsRendererSeparatingText objectsRenderer = new MapObjectsRendererSeparatingText(textImage.createGraphics(),
							textImage.createGraphics(), DrawingStylesFactory.createStyleViewer(), null, 5,
							selectingBuffer);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}
}
