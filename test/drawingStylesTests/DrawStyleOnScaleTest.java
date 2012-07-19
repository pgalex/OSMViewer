package drawingStylesTests;

import IOTesting.IOTester;
import drawingStyles.IOColor;
import drawingStyles.IOFont;
import drawingStyles.DrawStyleOnScale;
import java.awt.Color;
import java.awt.Font;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * DrawStyleOnScale class tests
 *
 * @author abc
 */
public class DrawStyleOnScaleTest
{
	/**
	 * Auto initialize in constructor test
	 */
	@Test
	public void autoInitializeTest()
	{
		DrawStyleOnScale testStyle = new DrawStyleOnScale(true, true, true, null, null, null, null, null);
		assertNotNull(testStyle.getPointStyle());
		assertNotNull(testStyle.getLineStyle());
		assertNotNull(testStyle.getPolygonStyle());
		assertNotNull(testStyle.getTextColor());
		assertNotNull(testStyle.getTextFont());
	}

	/**
	 * Reading/writing test
	 */
	@Test
	public void readingWritingTest()
	{
		try
		{
			DrawStyleOnScale writedStyle = new DrawStyleOnScale(true, false, true, null, null,
							null, new IOColor(Color.RED), new IOFont(new Font("Arial", 1, 3)));

			IOTester.writeToTestFile(writedStyle);

			DrawStyleOnScale readStyle = new DrawStyleOnScale();
			IOTester.readFromTestFile(readStyle);

			assertEquals(writedStyle.isDrawLine(), readStyle.isDrawLine());
			assertEquals(writedStyle.isDrawPoint(), readStyle.isDrawPoint());
			assertEquals(writedStyle.isDrawPolygon(), readStyle.isDrawPolygon());
			assertEquals(writedStyle.getTextColor().getColor(), readStyle.getTextColor().getColor());
			assertEquals(writedStyle.getTextFont().getFont(), readStyle.getTextFont().getFont());
			assertEquals(writedStyle.getTextFont().getFont().getFamily(), readStyle.getTextFont().getFont().getFamily());
			assertEquals(writedStyle.getTextFont().getFont().getStyle(), readStyle.getTextFont().getFont().getStyle());
			assertEquals(writedStyle.getTextFont().getFont().getSize(), readStyle.getTextFont().getFont().getSize());
		}
		catch (Exception ex)
		{
			fail();
		}
	}
}
