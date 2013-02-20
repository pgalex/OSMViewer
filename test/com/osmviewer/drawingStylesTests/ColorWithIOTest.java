package com.osmviewer.drawingStylesTests;

import com.osmviewer.IOTesting.IOTester;
import com.osmviewer.drawingStyles.ColorWithIO;
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
			writedColor.writeToStream(IOTester.createTestOutputStream());
			
			ColorWithIO readColor = new ColorWithIO();
			readColor.readFromStream(IOTester.createTestInputStream());
			
			assertEquals(writedColor.getStoringColor(), readColor.getStoringColor());
		}
		catch (Exception ex)
		{
			fail();
		}
	}
}
