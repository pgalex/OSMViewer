package drawingStylesTests;

import IOTesting.IOTester;
import drawingStyles.IOColor;
import drawingStyles.LineDrawStyle;
import drawingStyles.LinePattern;
import java.awt.Color;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * LineDrawStyle class tests
 *
 * @author abc
 */
public class LineDrawStyleTest
{
	/**
	 * Test auto initialize in constructor
	 */
	@Test
	public void autoInitializeTest()
	{
		LineDrawStyle testStyle = new LineDrawStyle(null, 1, null);
		assertNotNull(testStyle.getColor());
		assertNotNull(testStyle.getLinePattern());
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
			LineDrawStyle writedStyle = new LineDrawStyle(new IOColor(Color.CYAN), 11, new LinePattern(pattern));
			IOTester.writeToTestFile(writedStyle);

			LineDrawStyle readStyle = new LineDrawStyle();
			IOTester.readFromTestFile(readStyle);

			assertEquals(writedStyle.getColor().getColor(), readStyle.getColor().getColor());
			assertEquals(writedStyle.getWidth(), readStyle.getWidth());
			assertArrayEquals(writedStyle.getLinePattern().getPattern(), readStyle.getLinePattern().getPattern(), 0.01f);
		}
		catch (Exception ex)
		{
			fail();
		}
	}
}