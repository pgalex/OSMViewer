package drawingStylesTests;

import IOTesting.IOTester;
import drawingStyles.StandartTextDrawSettings;
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
			StandartTextDrawSettings settings = new StandartTextDrawSettings();
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
			StandartTextDrawSettings settings = new StandartTextDrawSettings();
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
			StandartTextDrawSettings settings = new StandartTextDrawSettings(Color.CYAN, null);
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
			StandartTextDrawSettings settings = new StandartTextDrawSettings(null, null);
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
			StandartTextDrawSettings writingSettings = new StandartTextDrawSettings(Color.ORANGE, new Font("Arial", Font.BOLD, 14));
			writingSettings.writeToStream(IOTester.createTestOutputStream());
			
			StandartTextDrawSettings readingSettings = new StandartTextDrawSettings();
			readingSettings.readFromStream(IOTester.createTestInputStream());
			
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
