package drawingStylesTests;

import IOTesting.IOTester;
import drawingStyles.ImageWithIO;
import drawingStyles.LineDrawSettings;
import drawingStyles.PolygonDrawSettings;
import java.awt.Color;
import java.awt.Paint;
import java.awt.TexturePaint;
import java.io.IOException;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * PolygonDrawSettings class tests
 *
 * @author abc
 */
public class PolygonDrawSettingsTest
{
	/**
	 * Test creating with null border draw settings
	 */
	@Test
	public void creatingWithNullBorderDrawSettingsTest()
	{
		try
		{
			PolygonDrawSettings settings = new PolygonDrawSettings(Color.BLACK, null, null);
			fail();
		}
		catch(IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test creating paint if fill image is not null
	 */
	@Test
	public void creatingTexturePaintTest()
	{
		try
		{
			ImageWithIO fillIcon = new ImageWithIO("test/supportFiles/testIcon.png");
			PolygonDrawSettings polygonStyle = new PolygonDrawSettings(Color.GREEN, new LineDrawSettings(), fillIcon.getImage());
			Paint paint = polygonStyle.getPaint();

			assertTrue(paint instanceof TexturePaint);
		}
		catch (IOException ex)
		{
			fail();
		}
	}

	/**
	 * Test creating paint if fill image is null
	 */
	@Test
	public void creatingSolidPaintTest()
	{
		PolygonDrawSettings polygonStyle = new PolygonDrawSettings(Color.GREEN, new LineDrawSettings(), null);
		Paint paint = polygonStyle.getPaint();

		assertTrue(paint instanceof Color);
		assertEquals(((Color) paint), polygonStyle.getFillColor());
	}

	/**
	 * Creating with null fill color test
	 */
	@Test
	public void creatingWithNullFillColorTest()
	{
		try
		{
			PolygonDrawSettings testStyle = new PolygonDrawSettings(null, new LineDrawSettings(), null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Reading/writing test
	 */
	@Test
	public void readingWritingTest()
	{
		try
		{
			float[] pattern = new float[4];
			pattern[0] = 2;
			pattern[1] = 3;
			pattern[2] = 4;
			pattern[3] = 5;
			LineDrawSettings borderStyle = new LineDrawSettings(Color.CYAN, 10, pattern);
			ImageWithIO fillIcon = new ImageWithIO("test/supportFiles/testIcon.png");
			PolygonDrawSettings writedStyle = new PolygonDrawSettings(Color.MAGENTA, borderStyle,
							fillIcon.getImage());

			IOTester.writeToTestFile(writedStyle);

			PolygonDrawSettings readStyle = new PolygonDrawSettings();
			IOTester.readFromTestFile(readStyle);

			assertEquals(writedStyle.getFillColor(), readStyle.getFillColor());
			assertNotNull(writedStyle.getFillImage());
			assertEquals(writedStyle.getBorderDrawSettings().getColor(), readStyle.getBorderDrawSettings().getColor());
			assertEquals(writedStyle.getBorderDrawSettings().getWidth(), readStyle.getBorderDrawSettings().getWidth(), 0.00001f);
		}
		catch (Exception ex)
		{
			fail();
		}
	}
}
