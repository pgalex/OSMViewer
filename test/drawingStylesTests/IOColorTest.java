package drawingStylesTests;

import IOTesting.IOTester;
import drawingStyles.IOColor;
import drawingStyles.exceptions.ColorIsNullException;
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
	 * Creating with null color test
	 */
	@Test
	public void autoInitializeTest()
	{
		try
		{
			IOColor testColor = new IOColor(null);
			fail();
		}
		catch (ColorIsNullException ex)
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
			IOColor testColor = new IOColor();
			testColor.setColor(null);
			fail();
		}
		catch (ColorIsNullException ex)
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
