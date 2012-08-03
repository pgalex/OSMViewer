package drawingStylesTests;

import IOTesting.IOTester;
import drawingStyles.LineDrawSettings;
import drawingStyles.LinePattern;
import drawingStyles.PolygonDrawSettings;
import java.awt.Color;
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
