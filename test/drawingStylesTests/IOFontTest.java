package drawingStylesTests;

import IOTesting.IOTester;
import drawingStyles.IOFont;
import java.awt.Font;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Testing IOFont class
 *
 * @author Евгений
 */
public class IOFontTest
{
	/**
	 * Test auto initialize in contructor
	 */
	@Test
	public void autoInitializeTest()
	{
		IOFont testFont = new IOFont(null);
		assertNotNull(testFont.getFont());
	}

	/**
	 * Reading/writing test
	 */
	@Test
	public void readingWritingTest()
	{
		try
		{
			IOFont writedFont = new IOFont(new Font("Arial", 0, 15));
			IOTester.writeToTestFile(writedFont);

			IOFont readFont = new IOFont();
			IOTester.readFromTestFile(readFont);

			assertEquals(writedFont.getFont().getFamily(), readFont.getFont().getFamily());
			assertEquals(writedFont.getFont().getStyle(), readFont.getFont().getStyle());
			assertEquals(writedFont.getFont().getSize(), readFont.getFont().getSize());
		}
		catch (Exception ex)
		{
			fail();
		}
	}
}
