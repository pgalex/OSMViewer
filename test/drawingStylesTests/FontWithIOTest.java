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
	 * Test setting null storing font
	 */
	@Test
	public void setNullFontTest()
	{
		try
		{
			FontWithIO font = new FontWithIO();
			font.setStoringFont(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}
	
	/**
	 * Test setting storing font normal work
	 */
	@Test
	public void setFontNormalWorkTest()
	{
		Font someFont = new Font("Arial", 0, 22);
		FontWithIO testFont = new FontWithIO();
		testFont.setStoringFont(someFont);
		assertEquals(someFont, testFont.getStoringFont());
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
			writedFont.writeToStream(IOTester.createTestOutputStream());

			FontWithIO readFont = new FontWithIO();
			readFont.readFromStream(IOTester.createTestInputStream());

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
