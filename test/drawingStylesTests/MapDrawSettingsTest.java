package drawingStylesTests;

import IOTesting.IOTester;
import drawingStyles.StandartMapDrawSettings;
import java.awt.Color;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * StandartMapDrawSettings class tests
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
			StandartMapDrawSettings testSettings = new StandartMapDrawSettings(null);
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
			StandartMapDrawSettings testSettings = new StandartMapDrawSettings();
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
			StandartMapDrawSettings writingSetting = new StandartMapDrawSettings(Color.ORANGE);
			writingSetting.writeToStream(IOTester.createTestOutputStream());
			
			StandartMapDrawSettings readingSettings = new StandartMapDrawSettings();
			readingSettings.readFromStream(IOTester.createTestInputStream());

			assertEquals(writingSetting.getMapBackgroundColor(), readingSettings.getMapBackgroundColor());
		}
		catch (Exception ex)
		{
			fail();
		}
	}
}
