package drawingStylesTests;

import IOTesting.IOTester;
import drawingStyles.LineDrawSettings;
import java.awt.BasicStroke;
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
	 * Test setting null pattern
	 */
	@Test
	public void setNullPatternTest()
	{
		try
		{
			LineDrawSettings settings = new LineDrawSettings();
			settings.setPattern(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test setting empty pattern
	 */
	@Test
	public void setEmptyPatternTest()
	{
		try
		{
			LineDrawSettings settings = new LineDrawSettings();
			settings.setPattern(new float[0]);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test setting null color
	 */
	@Test
	public void setNullColorTest()
	{
		try
		{
			LineDrawSettings settings = new LineDrawSettings();
			settings.setColor(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test creating stroke by line parameters
	 */
	@Test
	public void getStrokeTest()
	{
		float[] pattern = new float[4];
		pattern[0] = 2;
		pattern[1] = 5;
		pattern[2] = 3;
		pattern[3] = 2;
		LineDrawSettings lineStyle = new LineDrawSettings(Color.RED, 5, pattern);

		BasicStroke stroke = lineStyle.getStroke();
		assertEquals(stroke.getLineWidth(), lineStyle.getWidth(), 0.00001f);
		assertArrayEquals(lineStyle.getPattern(), stroke.getDashArray(), 0.0001f);
	}

	/**
	 * Test creating with null line pattern
	 */
	@Test
	public void creatingWithNullPatternTest()
	{
		try
		{
			LineDrawSettings testStyle = new LineDrawSettings(Color.RED, 1, null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test creating with empty line pattern
	 */
	@Test
	public void creatingWithEmptyPatternTest()
	{
		try
		{
			LineDrawSettings testStyle = new LineDrawSettings(Color.RED, 1, new float[0]);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test creating with null line color
	 */
	@Test
	public void creatingWithNullColorTest()
	{
		try
		{
			float[] pattern = new float[4];
			pattern[0] = 2;
			pattern[1] = 3;
			pattern[2] = 4;
			pattern[3] = 5;
			LineDrawSettings testStyle = new LineDrawSettings(null, 1, pattern);
			assertNotNull(testStyle.getColor());
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
			LineDrawSettings writedStyle = new LineDrawSettings(Color.CYAN, 11, pattern);
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
