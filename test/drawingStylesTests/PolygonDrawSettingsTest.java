package drawingStylesTests;

import IOTesting.IOTester;
import drawingStyles.*;
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
		assertNotNull(testStyle.getFillImage());
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
			PolygonDrawSettings writedStyle = new PolygonDrawSettings(new IOColor(Color.MAGENTA), borderStyle,
							new IOIcon());

			IOTester.writeToTestFile(writedStyle);

			PolygonDrawSettings readStyle = new PolygonDrawSettings();
			IOTester.readFromTestFile(readStyle);

			assertEquals(writedStyle.getFillColor().getColor(), readStyle.getFillColor().getColor());
			assertEquals(writedStyle.getBorderDrawStyle().getColor(), readStyle.getBorderDrawStyle().getColor());
			assertEquals(writedStyle.getBorderDrawStyle().getWidth(), readStyle.getBorderDrawStyle().getWidth());
		}
		catch (Exception ex)
		{
			fail();
		}
	}
}
