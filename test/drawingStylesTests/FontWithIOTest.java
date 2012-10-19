package drawingStylesTests;

import IOTesting.IOTester;
import drawingStyles.FontWithIO;
import java.awt.Font;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Testing FontWithIO class
 *
 * @author Евгений
 */
public class FontWithIOTest
{
	/**
	 * Test creating with null storing font
	 */
	@Test
	public void creatingWithNullFontTest()
	{
		try
		{
			FontWithIO testFont = new FontWithIO(null);
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
			FontWithIO writedFont = new FontWithIO(new Font("Arial", 0, 15));
			IOTester.writeToTestFile(writedFont);

			FontWithIO readFont = new FontWithIO();
			IOTester.readFromTestFile(readFont);

			assertEquals(writedFont.getStoringFont().getFamily(), readFont.getStoringFont().getFamily());
			assertEquals(writedFont.getStoringFont().getStyle(), readFont.getStoringFont().getStyle());
			assertEquals(writedFont.getStoringFont().getSize(), readFont.getStoringFont().getSize());
		}
		catch (Exception ex)
		{
			fail();
		}
	}
}
