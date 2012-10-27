package drawingStylesTests;

import IOTesting.IOTester;
import drawingStyles.ImageWithIO;
import drawingStyles.LineDrawSettings;
import drawingStyles.PolygonDrawSettings;
import drawingStyles.PolygonFiller;
import drawingStyles.PolygonFillersFactory;
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
			PolygonDrawSettings settings = new PolygonDrawSettings(PolygonFillersFactory.createColorFiller(Color.BLACK),
							null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Creating with null fill color test
	 */
	@Test
	public void creatingWithNullFillerTest()
	{
		try
		{
			PolygonDrawSettings testStyle = new PolygonDrawSettings(null, new LineDrawSettings());
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test creating texture filler
	 */
	@Test
	public void creatingTexturePaintTest()
	{
		try
		{
			ImageWithIO fillIcon = new ImageWithIO("test/supportFiles/testIcon.png");
			PolygonFiller filler = PolygonFillersFactory.createTextureFiller(fillIcon.getImage());
			PolygonDrawSettings polygonStyle = new PolygonDrawSettings(filler, new LineDrawSettings());
			Paint paint = polygonStyle.getPaint();

			assertTrue(paint instanceof TexturePaint);
		}
		catch (IOException ex)
		{
			fail();
		}
	}

	/**
	 * Test creating color paint
	 */
	@Test
	public void creatingSolidColorPaintTest()
	{
		PolygonFiller filler = PolygonFillersFactory.createColorFiller(Color.GREEN);
		PolygonDrawSettings polygonStyle = new PolygonDrawSettings(filler, new LineDrawSettings());
		Paint paint = polygonStyle.getPaint();

		assertTrue(paint instanceof Color);
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
			PolygonFiller filler = PolygonFillersFactory.createTextureFiller(fillIcon.getImage());
			PolygonDrawSettings writedStyle = new PolygonDrawSettings(filler, borderStyle);

			IOTester.writeToTestFile(writedStyle);

			PolygonDrawSettings readStyle = new PolygonDrawSettings();
			IOTester.readFromTestFile(readStyle);

			assertEquals(writedStyle.getFiller().getType(), readStyle.getFiller().getType());
			assertEquals(writedStyle.getBorderDrawSettings().getColor(), readStyle.getBorderDrawSettings().getColor());
			assertEquals(writedStyle.getBorderDrawSettings().getWidth(), readStyle.getBorderDrawSettings().getWidth(), 0.00001f);
		}
		catch (Exception ex)
		{
			fail();
		}
	}
}
