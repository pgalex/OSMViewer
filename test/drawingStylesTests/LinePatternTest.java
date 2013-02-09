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
	 * Test setting default line pattern in default constructor
	 */
	@Test
	public void creatingWithDefaultPatternTest()
	{
		LinePattern pattern = new LinePattern();
		
		assertNotNull(pattern.getPattern());
		assertTrue(pattern.getPattern().length > 0);
	}

	/**
	 * Test creating with incorrect pattern
	 */
	@Test
	public void creatingWithNullPatternTest()
	{
		try
		{
			LinePattern pattern = new LinePattern(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test creating with empty pattern
	 */
	@Test
	public void creatingWithEmptyPatternTest()
	{
		try
		{
			LinePattern pattern = new LinePattern(new float[0]);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test setting null line pattern
	 */
	@Test
	public void setNullPatternTest()
	{
		LinePattern pattern = new LinePattern();
		
		try
		{
			pattern.setPattern(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test setting zero length line pattern
	 */
	@Test
	public void setZeroLengthPatternTest()
	{
		LinePattern pattern = new LinePattern();
		
		try
		{
			pattern.setPattern(new float[0]);
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
			LinePattern writingPattern = new LinePattern();
			writingPattern.setPattern(pattern);
			writingPattern.writeToStream(IOTester.createTestOutputStream());
			
			LinePattern readingPattern = new LinePattern();
			readingPattern.readFromStream(IOTester.createTestInputStream());
			
			assertArrayEquals(writingPattern.getPattern(), readingPattern.getPattern(), 0.0001f);
		}
		catch (Exception ex)
		{
			fail();
		}
	}
}
