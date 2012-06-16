package drawingStylesTests;

import IOTesting.IOTester;
import drawingStyles.LinePattern;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * LinePattern class tests
 *
 * @author pgalex
 */
public class LinePatternTest
{
	/**
	 * Testing auto initialize in constructor
	 */
	@Test
	public void autoInitializeTest()
	{
		LinePattern pattern = new LinePattern(null);

		assertNotNull(pattern.getPattern());
		assertTrue(pattern.getPattern().length > 0);
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
			LinePattern writedPattern = new LinePattern(pattern);
			IOTester.writeToTestFile(writedPattern);

			LinePattern readPattern = new LinePattern();
			IOTester.readFromTestFile(readPattern);

			assertArrayEquals(writedPattern.getPattern(), readPattern.getPattern(), 0.0001f);
		}
		catch (Exception ex)
		{
			fail();
		}
	}
}
