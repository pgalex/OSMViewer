package drawingStylesTests;

import IOTesting.IOTester;
import drawingStyles.ColorWithIO;
import java.awt.Color;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * ColorWithIO class tests
 *
 * @author abc
 */
public class ColorWithIOTest
{
	/**
	 * Creating with null color test
	 */
	@Test
	public void autoInitializeTest()
	{
		try
		{
			ColorWithIO testColor = new ColorWithIO(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Set null storing color test
	 */
	@Test
	public void setNullColorTest()
	{
		try
		{
			ColorWithIO testColor = new ColorWithIO();
			testColor.setStoringColor(null);
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
			ColorWithIO writedColor = new ColorWithIO(Color.MAGENTA);
			IOTester.writeToTestFile(writedColor);
			
			ColorWithIO readColor = new ColorWithIO();
			IOTester.readFromTestFile(readColor);
			
			assertEquals(writedColor.getStoringColor(), readColor.getStoringColor());
		}
		catch (Exception ex)
		{
			fail();
		}
	}
}
