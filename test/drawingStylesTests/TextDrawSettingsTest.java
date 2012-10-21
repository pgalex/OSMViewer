package drawingStylesTests;

import IOTesting.IOTester;
import drawingStyles.TextDrawSettings;
import java.awt.Color;
import java.awt.Font;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author pgalex
 */
public class TextDrawSettingsTest
{
	/**
	 * Test setting null text color
	 */
	@Test
	public void setNullTextColorTest()
	{
		try
		{
			TextDrawSettings settings = new TextDrawSettings();
			settings.setColor(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test setting null text font
	 */
	@Test
	public void setNullTextFontTest()
	{
		try
		{
			TextDrawSettings settings = new TextDrawSettings();
			settings.setFont(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test creating with null font
	 */
	@Test
	public void creatingWithNullFontTest()
	{
		try
		{
			TextDrawSettings settings = new TextDrawSettings(Color.CYAN, null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Creating with null text color text
	 */
	@Test
	public void creatingWithNullTextColorTest()
	{
		try
		{
			TextDrawSettings settings = new TextDrawSettings(null, null);
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
			TextDrawSettings writingSettings = new TextDrawSettings(Color.ORANGE, new Font("Arial", Font.BOLD, 14));
			IOTester.writeToTestFile(writingSettings);
			
			TextDrawSettings readingSettings = new TextDrawSettings();
			IOTester.readFromTestFile(readingSettings);
			
			assertEquals(writingSettings.getColor(), readingSettings.getColor());
			assertEquals(writingSettings.getFont().getFamily(), readingSettings.getFont().getFamily());
			assertEquals(writingSettings.getFont().getSize(), readingSettings.getFont().getSize());
			assertEquals(writingSettings.getFont().getStyle(), readingSettings.getFont().getStyle());
		}
		catch (Exception ex)
		{
			fail();
		}
		
	}
}
