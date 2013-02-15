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
			
			writedStyle.writeToStream(IOTester.createTestOutputStream());
			
			DrawSettingsOnScale readStyle = new DrawSettingsOnScale();
			readStyle.readFromStream(IOTester.createTestInputStream());
			
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
