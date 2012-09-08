package drawingStylesTests;

import IOTesting.IOTester;
import drawingStyles.IOColor;
import drawingStyles.exceptions.IncorrectParameterException;
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
		catch (IncorrectParameterException ex)
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
			testColor.setStoringColor(null);
			fail();
		}
		catch (IncorrectParameterException ex)
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
			
			assertEquals(writedColor.getStoringColor(), readColor.getStoringColor());
		}
		catch (Exception ex)
		{
			fail();
		}
	}
}
