package drawingStyleTests;

import IOTesting.IOTester;
import drawingStyle.IOColor;
import drawingStyle.MapDrawingSettings;
import java.awt.Color;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * MapDrawingSettings class tests
 *
 * @author pgalex
 */
public class MapDrawingSettingsTest
{
	/**
	 * Testing auto initialize in constructor
	 */
	@Test
	public void autoInitializeTest()
	{
		MapDrawingSettings testSettings = new MapDrawingSettings(null);
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
			MapDrawingSettings writingSetting = new MapDrawingSettings(new IOColor(Color.ORANGE));
			IOTester.writeToTestFile(writingSetting);

			MapDrawingSettings readingSettings = new MapDrawingSettings(null);
			IOTester.readFromTestFile(readingSettings);

			assertEquals(writingSetting.getMapBackgroundColor().getColor(), readingSettings.getMapBackgroundColor().getColor());
		}
		catch (Exception ex)
		{
			fail();
		}
	}
}
