package mapTests;

import java.awt.Color;
import java.awt.Rectangle;
import map.MapRenderer;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

/**
 *
 * @author pgalex
 */
public class MapRendererTest
{
	/**
	 * Testing setBackground work
	 */
	@Test
	public void setBackgroundTest()
	{
		MapRenderer testRenderer = new MapRenderer();
		assertNotNull(testRenderer.getBackgroundColor());

		//not null
		testRenderer.setBackgroundColor(Color.RED);
		assertEquals(Color.RED, testRenderer.getBackgroundColor());

		//null
		testRenderer.setBackgroundColor(Color.GREEN);
		testRenderer.setBackgroundColor(null);
		assertEquals(Color.GREEN, testRenderer.getBackgroundColor());
	}

	/**
	 * Testing setDrawingArea work
	 */
	@Test
	public void setDrawingAreaTest()
	{
		MapRenderer testRenderer = new MapRenderer();
		assertNotNull(testRenderer.getDrawingArea());

		//not null
		testRenderer.setDrawingArea(new Rectangle(10, 10, 20, 20));
		assertEquals(new Rectangle(10, 10, 20, 20), testRenderer.getDrawingArea());

		//null
		testRenderer.setDrawingArea(new Rectangle(20, 20, 30, 30));
		testRenderer.setDrawingArea(null);
		assertEquals(new Rectangle(20, 20, 30, 30), testRenderer.getDrawingArea());
	}
}
