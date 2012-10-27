package drawingStylesTests;

import drawingStyles.ColorPolygonFiller;
import drawingStyles.PolygonFillerType;
import java.awt.Color;
import java.awt.Paint;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author pgalex
 */
public class ColorPolygonFillerTest
{
	/**
	 * Test creating with null color
	 */
	@Test
	public void creatingWithNullColorTest()
	{
		try
		{
			ColorPolygonFiller filler = new ColorPolygonFiller(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test getType of filler
	 */
	@Test
	public void typeTest()
	{
		ColorPolygonFiller filler = new ColorPolygonFiller(Color.BLACK);
		assertEquals(PolygonFillerType.BY_COLOR, filler.getType());
	}

	/**
	 * Test creating paint
	 */
	@Test
	public void getPaintTest()
	{
		final Color someColor = Color.RED;
		ColorPolygonFiller filler = new ColorPolygonFiller(someColor);
		Paint paint = filler.getPaint();

		assertTrue(paint instanceof Color);
		assertEquals(someColor, ((Color) paint));
	}
}
