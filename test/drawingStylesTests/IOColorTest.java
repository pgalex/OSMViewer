package drawingStylesTests;

import IOTesting.IOTester;
import drawingStyles.IOColor;
import java.awt.Color;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * IOColor class tests
 *
 * @author abc
 */
public class IOColorTest
{
	/**
	 * Auto initialize null color in constructor
	 */
	@Test
	public void autoInitializeTest()
	{
		IOColor testColor = new IOColor(null);
		assertNotNull(testColor.getColor());
	}

	/**
	 * Reading/writing test
	 */
	@Test
	public void readingWritingTest()
	{
		try
		{
			IOColor writedColor = new IOColor(Color.MAGENTA);
			IOTester.writeToTestFile(writedColor);

			IOColor readColor = new IOColor();
			IOTester.readFromTestFile(readColor);

			assertEquals(writedColor.getColor(), readColor.getColor());
		}
		catch (Exception ex)
		{
			fail();
		}
	}
}
