package drawingStylesTests;

import IOTesting.IOTester;
import drawingStyles.StandartLineDrawSettings;
import java.awt.BasicStroke;
import java.awt.Color;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * StandartLineDrawSettings class tests
 *
 * @author abc
 */
public class StandartLineDrawSettingsTest
{
	/**
	 * Test setting null pattern
	 */
	@Test
	public void setNullPatternTest()
	{
		try
		{
			StandartLineDrawSettings settings = new StandartLineDrawSettings();
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
			StandartLineDrawSettings settings = new StandartLineDrawSettings();
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
			StandartLineDrawSettings settings = new StandartLineDrawSettings();
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
		StandartLineDrawSettings lineStyle = new StandartLineDrawSettings(Color.RED, 5, pattern);

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
			StandartLineDrawSettings testStyle = new StandartLineDrawSettings(Color.RED, 1, null);
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
			StandartLineDrawSettings testStyle = new StandartLineDrawSettings(Color.RED, 1, new float[0]);
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
			StandartLineDrawSettings testStyle = new StandartLineDrawSettings(null, 1, pattern);
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
			StandartLineDrawSettings writedStyle = new StandartLineDrawSettings(Color.CYAN, 11, pattern);
			writedStyle.writeToStream(IOTester.createTestOutputStream());

			StandartLineDrawSettings readStyle = new StandartLineDrawSettings();
			readStyle.readFromStream(IOTester.createTestInputStream());

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
