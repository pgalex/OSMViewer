package drawingStylesTests;

import IOTesting.IOTester;
import drawingStyles.IOIcon;
import drawingStyles.LineDrawSettings;
import drawingStyles.LinePattern;
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
	 * Test creating paint if fill image is not null
	 */
	@Test
	public void creatingTexturePaintTest()
	{
		try
		{
			IOIcon fillIcon = new IOIcon("testIcon.png");
			PolygonDrawSettings polygonStyle = new PolygonDrawSettings(Color.GREEN, null, fillIcon.getImage());
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
		PolygonDrawSettings polygonStyle = new PolygonDrawSettings(Color.GREEN, null, null);
		Paint paint = polygonStyle.getPaint();

		assertTrue(paint instanceof Color);
		assertEquals(((Color) paint), polygonStyle.getFillColor());
	}

	/**
	 * Test auto initialize in constructor
	 */
	@Test
	public void autoInitializeTest()
	{
		PolygonDrawSettings testStyle = new PolygonDrawSettings(null, null, null);

		assertNotNull(testStyle.getBorderDrawStyle());
		assertNotNull(testStyle.getFillColor());
	}

	/**
	 * Reading/writing test
	 */
	@Test
	public void readingWritingTest()
	{
		try
		{
			LineDrawSettings borderStyle = new LineDrawSettings(Color.CYAN, 10, new LinePattern());
			PolygonDrawSettings writedStyle = new PolygonDrawSettings(Color.MAGENTA, borderStyle,
							null);

			IOTester.writeToTestFile(writedStyle);

			PolygonDrawSettings readStyle = new PolygonDrawSettings();
			IOTester.readFromTestFile(readStyle);

			assertEquals(writedStyle.getFillColor(), readStyle.getFillColor());
			assertEquals(writedStyle.getBorderDrawStyle().getColor(), readStyle.getBorderDrawStyle().getColor());
			assertEquals(writedStyle.getBorderDrawStyle().getWidth(), readStyle.getBorderDrawStyle().getWidth(), 0.00001f);
		}
		catch (Exception ex)
		{
			fail();
		}
	}
}
