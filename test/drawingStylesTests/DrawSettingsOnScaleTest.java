package drawingStylesTests;

import IOTesting.IOTester;
import drawingStyles.DrawSettingsOnScale;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * DrawSettingsOnScale class tests
 *
 * @author abc
 */
public class DrawSettingsOnScaleTest
{
	/**
	 * Auto initialize in constructor test
	 */
	@Test
	public void autoInitializeTest()
	{
		DrawSettingsOnScale testStyle = new DrawSettingsOnScale(true, true, true, null, null, null, null);
		assertNotNull(testStyle.getPointDrawSettings());
		assertNotNull(testStyle.getLineDrawSettings());
		assertNotNull(testStyle.getPolygonDrawSettings());
		assertNotNull(testStyle.getTextDrawSettings());
	}

	/**
	 * Reading/writing test
	 */
	@Test
	public void readingWritingTest()
	{
		try
		{
			DrawSettingsOnScale writedStyle = new DrawSettingsOnScale(true, false, true, null, null, null, null);

			IOTester.writeToTestFile(writedStyle);

			DrawSettingsOnScale readStyle = new DrawSettingsOnScale();
			IOTester.readFromTestFile(readStyle);

			assertEquals(writedStyle.isDrawLine(), readStyle.isDrawLine());
			assertEquals(writedStyle.isDrawPoint(), readStyle.isDrawPoint());
			assertEquals(writedStyle.isDrawPolygon(), readStyle.isDrawPolygon());
		}
		catch (Exception ex)
		{
			fail();
		}
	}
}
