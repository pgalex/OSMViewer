package drawingStylesTests;

import IOTesting.IOTester;
import drawingStyles.MapDrawSettings;
import drawingStyles.exceptions.ColorIsNullException;
import java.awt.Color;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * MapDrawSettings class tests
 *
 * @author pgalex
 */
public class MapDrawSettingsTest
{
	/**
	 * Testing creating with null background color in constructor
	 */
	@Test
	public void creatingWithNullBackgroundColorTest()
	{
		try
		{
			MapDrawSettings testSettings = new MapDrawSettings(null);
			fail();
		}
		catch (ColorIsNullException ex)
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
			MapDrawSettings writingSetting = new MapDrawSettings(Color.ORANGE);
			IOTester.writeToTestFile(writingSetting);

			MapDrawSettings readingSettings = new MapDrawSettings();
			IOTester.readFromTestFile(readingSettings);

			assertEquals(writingSetting.getMapBackgroundColor().getColor(), readingSettings.getMapBackgroundColor().getColor());
		}
		catch (Exception ex)
		{
			fail();
		}
	}
}
