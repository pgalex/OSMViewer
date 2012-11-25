package renderingTests;

import drawingStyles.TextDrawSettings;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import static org.junit.Assert.*;
import org.junit.Test;
import rendering.TextCanvas;

/**
 * Tests of TextCanvas class
 *
 * @author pgalex
 */
public class TextCanvasTest
{
	/**
	 * Test drawing text on with points array contains null elements
	 */
	@Test
	public void drawTextOnMultilineContainsNullPointsTest()
	{
		try
		{
			BufferedImage someImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
			TextCanvas textCanvas = new TextCanvas(someImage.createGraphics());

			Point2D[] points = new Point2D[2];
			points[0] = new Point2D.Double(1, 2);
			points[1] = null;
			textCanvas.drawTextOnMultiline("123", new TextDrawSettings(), points);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test drawing text on with points array length less than 2
	 */
	@Test
	public void drawTextOnMultilineWithTooSmallLengthTest()
	{
		try
		{
			BufferedImage someImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
			TextCanvas textCanvas = new TextCanvas(someImage.createGraphics());

			Point2D[] points = new Point2D[1];
			points[0] = new Point2D.Double(1, 2);
			textCanvas.drawTextOnMultiline("123", new TextDrawSettings(), points);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test drawing text on with null points of multiline array
	 */
	@Test
	public void drawTextOnMultilineWithNullPointsArrayTest()
	{
		try
		{
			BufferedImage someImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
			TextCanvas textCanvas = new TextCanvas(someImage.createGraphics());

			textCanvas.drawTextOnMultiline("123", new TextDrawSettings(), null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test drawing text on with null draw settings multiline
	 */
	@Test
	public void drawTextWithNullSettingsOnMultilineTest()
	{
		try
		{
			BufferedImage someImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
			TextCanvas textCanvas = new TextCanvas(someImage.createGraphics());

			Point2D[] points = new Point2D[2];
			points[0] = new Point2D.Double(1, 2);
			points[1] = new Point2D.Double(3, 4);
			textCanvas.drawTextOnMultiline("123", null, points);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test drawing null text on multiline
	 */
	@Test
	public void drawNullTextOnMultilineTest()
	{
		try
		{
			BufferedImage someImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
			TextCanvas textCanvas = new TextCanvas(someImage.createGraphics());

			Point2D[] points = new Point2D[2];
			points[0] = new Point2D.Double(1, 2);
			points[1] = new Point2D.Double(3, 4);
			textCanvas.drawTextOnMultiline(null, new TextDrawSettings(), points);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

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
