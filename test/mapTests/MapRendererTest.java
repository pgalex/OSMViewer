package mapTests;

import java.awt.Color;
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
	 * Testing initializing drawing area with default value in default constructor
	 */
	@Test
	public void initializingDrawingAreaTest()
	{
		MapRenderer testRenderer = new MapRenderer();
		assertNotNull(testRenderer.getDrawingArea());
	}

	/**
	 * Test setting new value of drawing area
	 */
	@Test
	public void setNewDrawingAreaTest()
	{
		MapRenderer testRenderer = new MapRenderer();
		testRenderer.setDrawingArea(new Rectangle(10, 10, 20, 20));
		assertEquals(new Rectangle(10, 10, 20, 20), testRenderer.getDrawingArea());
	}

	/**
	 * Test setting null value of drawing area
	 */
	@Test
	public void setNullDrawingAreaTest()
	{
		MapRenderer testRenderer = new MapRenderer();
		testRenderer.setDrawingArea(new Rectangle(20, 20, 30, 30));
		testRenderer.setDrawingArea(null);
		assertEquals(new Rectangle(20, 20, 30, 30), testRenderer.getDrawingArea());
	}
}
