package com.osmviewer.drawingStylesTests;

import com.osmviewer.IOTesting.IOTester;
import com.osmviewer.drawingStyles.ImageWithIO;
import java.io.IOException;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Testing ImageWithIO class
 *
 * @author abc
 */
public class ImageWithIOTest
{
	/**
	 * Name of icon file using for testing loading image file in ImageWithIO class.
	 * Should exists in main folder
	 */
	private final String ICON_FILE_NAME = "test/supportFiles/testIcon.png";

	/**
	 * Testing default constructor for not creating image ( null and not loaded
	 * are not the same )
	 */
	@Test
	public void defaultContructorTest()
	{
		ImageWithIO testImage = new ImageWithIO();
		assertNull(testImage.getImage());
	}

	/**
	 * Test loading image in constructor
	 */
	@Test
	public void loadingImageTest()
	{
		try
		{
			ImageWithIO testImage = new ImageWithIO(ICON_FILE_NAME);
			assertNotNull(testImage.getImage());
		}
		catch (IOException ex)
		{
			fail();
		}
	}

	/**
	 * Test reading/writing loaded image (not null)
	 */
	@Test
	public void readingWritingLoadedImageTest()
	{
		try
		{
			ImageWithIO writedImage = new ImageWithIO(ICON_FILE_NAME);
			writedImage.writeToStream(IOTester.createTestOutputStream());

			ImageWithIO readImage = new ImageWithIO();
			readImage.readFromStream(IOTester.createTestInputStream());

			assertNotNull(readImage.getImage());
		}
		catch (Exception ex)
		{
			fail();
		}
	}

	/**
	 * Test reading/writing null image
	 */
	@Test
	public void readingWritingNullImageTest()
	{
		try
		{
			ImageWithIO writedImage = new ImageWithIO();
			writedImage.writeToStream(IOTester.createTestOutputStream());

			ImageWithIO readImage = new ImageWithIO();
			readImage.readFromStream(IOTester.createTestInputStream());

			assertNull(readImage.getImage());
		}
		catch (Exception ex)
		{
			fail();
		}
	}
}
