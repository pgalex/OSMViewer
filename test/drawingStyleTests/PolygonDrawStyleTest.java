package drawingStyleTests;

import IOTesting.IOTester;
import drawingStyle.*;
import java.awt.Color;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * PolygonDrawStyle class tests
 *
 * @author abc
 */
public class PolygonDrawStyleTest
{
	/**
	 * Test auto initialize in constructor
	 */
	@Test
	public void autoInitializeTest()
	{
		PolygonDrawStyle testStyle = new PolygonDrawStyle(null, null, null);

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
			LineDrawStyle borderStyle = new LineDrawStyle(new IOColor(Color.CYAN), 10, new LinePattern());
			PolygonDrawStyle writedStyle = new PolygonDrawStyle(new IOColor(Color.MAGENTA), borderStyle,
							new IOIcon());

			IOTester.writeToTestFile(writedStyle);

			PolygonDrawStyle readStyle = new PolygonDrawStyle();
			IOTester.readFromTestFile(readStyle);

			assertEquals(writedStyle.getFillColor().getColor(), readStyle.getFillColor().getColor());
			assertEquals(writedStyle.getBorderDrawStyle().getColor().getColor(), readStyle.getBorderDrawStyle().getColor().getColor());
			assertEquals(writedStyle.getBorderDrawStyle().getWidth(), readStyle.getBorderDrawStyle().getWidth());
		}
		catch (Exception ex)
		{
			fail();
		}
	}
}
