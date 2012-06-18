package mapTests;

import java.awt.Rectangle;
import map.rendering.MapRenderer;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

/**
 * MapRenderer class tests
 *
 * @author pgalex
 */
public class MapRendererTest
{
	/**
	 * Test setting null view position
	 */
	@Test
	public void setNullViewPositionTest()
	{
		MapRenderer testRenderer = new MapRenderer();
		testRenderer.setViewPosition(null);

		assertNotNull(testRenderer.getViewPosition());
	}

	/**
	 * Testing initializing drawing area with default value in default constructor
	 */
	@Test
	public void initializingDrawingAreaTest()
	{
		MapRenderer testRenderer = new MapRenderer();
		assertNotNull(testRenderer.getTargetCanvasDrawingArea());
	}

	/**
	 * Test setting new value of drawing area
	 */
	@Test
	public void setNewDrawingAreaTest()
	{
		MapRenderer testRenderer = new MapRenderer();
		testRenderer.setTargetCanvasDrawingArea(new Rectangle(10, 10, 20, 20));
		assertEquals(new Rectangle(10, 10, 20, 20), testRenderer.getTargetCanvasDrawingArea());
	}

	/**
	 * Test setting null value of drawing area
	 */
	@Test
	public void setNullDrawingAreaTest()
	{
		MapRenderer testRenderer = new MapRenderer();
		testRenderer.setTargetCanvasDrawingArea(new Rectangle(20, 20, 30, 30));
		testRenderer.setTargetCanvasDrawingArea(null);
		assertEquals(new Rectangle(20, 20, 30, 30), testRenderer.getTargetCanvasDrawingArea());
	}
}
