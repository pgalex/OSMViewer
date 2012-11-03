package rendering;

import drawingStyles.TextDrawSettings;
import java.awt.image.BufferedImage;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Tests of TextCanvas class
 *
 * @author pgalex
 */
public class TextCanvasTest
{
	/**
	 * Test creating with null text canvas graphics
	 */
	@Test
	public void creatingWithNullCanvasTest()
	{
		try
		{
			TextCanvas textCanvas = new TextCanvas(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test drawing null text under point
	 */
	@Test
	public void drawNullTextUnderPointTest()
	{
		try
		{
			BufferedImage someImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
			TextCanvas textCanvas = new TextCanvas(someImage.createGraphics());
			textCanvas.drawTextUnderPoint(null, new TextDrawSettings(), 0, 0);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test drawing text under point with null draw settings
	 */
	@Test
	public void drawTextUnderPointWithNullSettingsTest()
	{
		try
		{
			BufferedImage someImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
			TextCanvas textCanvas = new TextCanvas(someImage.createGraphics());
			textCanvas.drawTextUnderPoint("123", null, 0, 0);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}
	
	/**
	 * Test drawing null text at point
	 */
	@Test
	public void drawNullTextAtPointTest()
	{
		try
		{
			BufferedImage someImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
			TextCanvas textCanvas = new TextCanvas(someImage.createGraphics());
			textCanvas.drawTextAtPoint(null, new TextDrawSettings(), 0, 0);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test drawing text at point with null draw settings
	 */
	@Test
	public void drawTextAtPointWithNullSettingsTest()
	{
		try
		{
			BufferedImage someImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
			TextCanvas textCanvas = new TextCanvas(someImage.createGraphics());
			textCanvas.drawTextAtPoint("123", null, 0, 0);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}
}
