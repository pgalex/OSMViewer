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
		MapRenderer testRenderer = new MapRenderer(0, 10);
		testRenderer.setViewPosition(null);

		assertNotNull(testRenderer.getViewPosition());
	}

	/**
	 * Test scale level less than bounds
	 */
	@Test
	public void setScaleLevelLessThanBoundsTest()
	{
		MapRenderer testRenderer = new MapRenderer(2, 10);
		testRenderer.setScaleLevel(5);

		testRenderer.setScaleLevel(1);
		assertEquals(5, testRenderer.getScaleLevel());
	}

	/**
	 * Test scale level more than bounds
	 */
	@Test
	public void setScaleLevelMoreThanBoundsTest()
	{
		MapRenderer testRenderer = new MapRenderer(2, 10);
		testRenderer.setScaleLevel(4);

		testRenderer.setScaleLevel(11);
		assertEquals(4, testRenderer.getScaleLevel());
	}
	
	/**
	 * Testing initializing drawing area with default value in default constructor
	 */
	@Test
	public void initializingDrawingAreaTest()
	{
		MapRenderer testRenderer = new MapRenderer(0, 10);
		assertNotNull(testRenderer.getTargetCanvasDrawingArea());
	}

	/**
	 * Test setting new value of drawing area
	 */
	@Test
	public void setNewDrawingAreaTest()
	{
		MapRenderer testRenderer = new MapRenderer(0, 10);
		testRenderer.setTargetCanvasDrawingArea(new Rectangle(10, 10, 20, 20));
		assertEquals(new Rectangle(10, 10, 20, 20), testRenderer.getTargetCanvasDrawingArea());
	}

	/**
	 * Test setting null value of drawing area
	 */
	@Test
	public void setNullDrawingAreaTest()
	{
		MapRenderer testRenderer = new MapRenderer(0, 10);
		testRenderer.setTargetCanvasDrawingArea(new Rectangle(20, 20, 30, 30));
		testRenderer.setTargetCanvasDrawingArea(null);
		assertEquals(new Rectangle(20, 20, 30, 30), testRenderer.getTargetCanvasDrawingArea());
	}
}
