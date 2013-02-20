package com.osmviewer.renderingTests;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import static org.junit.Assert.*;
import org.junit.Test;
import com.osmviewer.rendering.TextCanvas;

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
			textCanvas.drawTextOnMultiline("123", new TestTextDrawSettings(), points);
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
			textCanvas.drawTextOnMultiline("123", new TestTextDrawSettings(), points);
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
			
			textCanvas.drawTextOnMultiline("123", new TestTextDrawSettings(), null);
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
			textCanvas.drawTextOnMultiline(null, new TestTextDrawSettings(), points);
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
			textCanvas.drawTextUnderPoint(null, new TestTextDrawSettings(), 0, 0);
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
			textCanvas.drawTextAtPoint(null, new TestTextDrawSettings(), 0, 0);
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

	/**
	 * Test computing null text at point bounds
	 */
	@Test
	public void computeTextAtPointBoundsWithNullTextTest()
	{
		try
		{
			BufferedImage someImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
			TextCanvas textCanvas = new TextCanvas(someImage.createGraphics());
			textCanvas.computeTextAtPointBounds(null, new TestTextDrawSettings(), 50, 50);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test computing text at point bounds with text draw settings
	 */
	@Test
	public void computeTextAtPointBoundsWithNullDrawSettingsTest()
	{
		try
		{
			BufferedImage someImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
			TextCanvas textCanvas = new TextCanvas(someImage.createGraphics());
			textCanvas.computeTextAtPointBounds("", null, 50, 50);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test computing empty text at point bounds
	 */
	@Test
	public void computeEmptyTextAtPointBoundsTest()
	{
		BufferedImage someImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
		TextCanvas textCanvas = new TextCanvas(someImage.createGraphics());
		Rectangle2D textBounds = textCanvas.computeTextAtPointBounds("", new TestTextDrawSettings(), 50, 50);
		assertTrue(textBounds.isEmpty());
	}

	/**
	 * Test computing text at point bounds normal work
	 */
	@Test
	public void computeTextAtPointBoundsTest()
	{
		BufferedImage someImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
		TextCanvas textCanvas = new TextCanvas(someImage.createGraphics());
		Rectangle2D textBounds = textCanvas.computeTextAtPointBounds("some text", new TestTextDrawSettings(), 50, 30);
		assertFalse(textBounds.isEmpty());
		assertEquals(50, textBounds.getCenterX(), 0.0001);
		assertEquals(30, textBounds.getCenterY(), 0.0001);
		assertTrue(textBounds.getWidth() > textBounds.getHeight());
	}
}
