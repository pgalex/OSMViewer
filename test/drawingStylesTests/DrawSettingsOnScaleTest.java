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
	 * Reading/writing test
	 */
	@Test
	public void readingWritingTest()
	{
		try
		{
			DrawSettingsOnScale writedStyle = new DrawSettingsOnScale();
			writedStyle.setDrawPoint();
			writedStyle.setNotDrawLine();
			writedStyle.setDrawPolygon();

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
