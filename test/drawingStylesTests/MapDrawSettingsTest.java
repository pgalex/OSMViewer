package drawingStylesTests;

import IOTesting.IOTester;
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
		try
		{
			MapDrawSettings testSettings = new MapDrawSettings(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Setting null background color test
	 */
	@Test
	public void setNullBackgroundColorTest()
	{
		try
		{
			MapDrawSettings testSettings = new MapDrawSettings();
			testSettings.setMapBackgroundColor(null);
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
			MapDrawSettings writingSetting = new MapDrawSettings(Color.ORANGE);
			writingSetting.writeToStream(IOTester.createTestOutputStream());
			
			MapDrawSettings readingSettings = new MapDrawSettings();
			readingSettings.readFromStream(IOTester.createTestInputStream());

			assertEquals(writingSetting.getMapBackgroundColor(), readingSettings.getMapBackgroundColor());
		}
		catch (Exception ex)
		{
			fail();
		}
	}
}
