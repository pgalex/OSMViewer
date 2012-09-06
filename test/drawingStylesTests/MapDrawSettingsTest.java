package drawingStylesTests;

import IOTesting.IOTester;
import drawingStyles.IOColor;
import drawingStyles.MapDrawSettings;
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
		MapDrawSettings testSettings = new MapDrawSettings(null);
		assertNotNull(testSettings.getMapBackgroundColor());
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

			MapDrawSettings readingSettings = new MapDrawSettings(null);
			IOTester.readFromTestFile(readingSettings);

			assertEquals(writingSetting.getMapBackgroundColor().getColor(), readingSettings.getMapBackgroundColor().getColor());
		}
		catch (Exception ex)
		{
			fail();
		}
	}
}
