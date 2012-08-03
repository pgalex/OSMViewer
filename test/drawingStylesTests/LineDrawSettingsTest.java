package drawingStylesTests;

import IOTesting.IOTester;
import drawingStyles.LineDrawSettings;
import drawingStyles.LinePattern;
import java.awt.Color;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * LineDrawSettings class tests
 *
 * @author abc
 */
public class LineDrawSettingsTest
{
	/**
	 * Test auto initialize in constructor
	 */
	@Test
	public void autoInitializeTest()
	{
		LineDrawSettings testStyle = new LineDrawSettings(null, 1, null);
		assertNotNull(testStyle.getColor());
		assertNotNull(testStyle.getPattern());
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
			LinePattern linePattern = new LinePattern();
			linePattern.setPattern(pattern);
			LineDrawSettings writedStyle = new LineDrawSettings(Color.CYAN, 11, linePattern);
			IOTester.writeToTestFile(writedStyle);

			LineDrawSettings readStyle = new LineDrawSettings();
			IOTester.readFromTestFile(readStyle);

			assertEquals(writedStyle.getColor(), readStyle.getColor());
			assertEquals(writedStyle.getWidth(), readStyle.getWidth(), 0.000001f);
			assertArrayEquals(writedStyle.getPattern(), readStyle.getPattern(), 0.01f);
		}
		catch (Exception ex)
		{
			fail();
		}
	}
}
