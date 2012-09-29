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
	 * Test auto initialize in contructor
	 */
	@Test
	public void autoInitializeTest()
	{
		FontWithIO testFont = new FontWithIO(null);
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
			FontWithIO writedFont = new FontWithIO(new Font("Arial", 0, 15));
			IOTester.writeToTestFile(writedFont);

			FontWithIO readFont = new FontWithIO();
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
