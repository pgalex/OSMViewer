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
		StandartLineDrawSettings lineStyle = new StandartLineDrawSettings();
		lineStyle.setColor(Color.RED);
		lineStyle.setWidth(5);
		lineStyle.setPattern(pattern);

		BasicStroke stroke = lineStyle.getStroke();
		assertEquals(stroke.getLineWidth(), lineStyle.getWidth(), 0.00001f);
		assertArrayEquals(lineStyle.getPattern(), stroke.getDashArray(), 0.0001f);
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
			StandartLineDrawSettings writedStyle = new StandartLineDrawSettings();
			writedStyle.setColor(Color.CYAN);
			writedStyle.setWidth(11);
			writedStyle.setPattern(pattern);
			
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
