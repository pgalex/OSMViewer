package mapTests;

import java.awt.Color;
import java.awt.Rectangle;
import map.MapRenderer;
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
	 * Testing initializing background color with default value in default
	 * constructor
	 */
	@Test
	public void initializingBackgroundColorTest()
	{
		MapRenderer testRenderer = new MapRenderer();
		assertNotNull(testRenderer.getBackgroundColor());
	}

	/**
	 * Test setting new background color
	 */
	@Test
	public void setNewBackgroundColorTest()
	{
		MapRenderer testRenderer = new MapRenderer();
		testRenderer.setBackgroundColor(Color.RED);
		assertEquals(Color.RED, testRenderer.getBackgroundColor());
	}

	/**
	 * Test setting null background color
	 */
	@Test
	public void setNullBackgroundColorTest()
	{
		MapRenderer testRenderer = new MapRenderer();
		testRenderer.setBackgroundColor(Color.GREEN);
		testRenderer.setBackgroundColor(null);
		assertEquals(Color.GREEN, testRenderer.getBackgroundColor());
	}

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
