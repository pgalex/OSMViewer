package drawingStyleTests;

import IOTesting.IOTester;
import drawingStyle.IOIcon;
import java.io.IOException;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Testing IOIcon class
 *
 * @author abc
 */
public class IOIconTest
{
	/**
	 * Name of icon file using for testing loading image file in IOIcon class.
	 * Should exists in main folder
	 */
	private final String ICON_FILE_NAME = "testIcon.png";

	/**
	 * Testing default constructor for not creating image ( null and not loaded
	 * are not the same )
	 */
	@Test
	public void defaultContructorTest()
	{
		IOIcon testImage = new IOIcon();
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
			IOIcon testImage = new IOIcon(ICON_FILE_NAME);
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
			IOIcon writedImage = new IOIcon(ICON_FILE_NAME);
			IOTester.writeToTestFile(writedImage);

			IOIcon readImage = new IOIcon();
			IOTester.readFromTestFile(readImage);

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
			IOIcon writedImage = new IOIcon();
			IOTester.writeToTestFile(writedImage);

			IOIcon readImage = new IOIcon();
			IOTester.readFromTestFile(readImage);

			assertNull(readImage.getImage());
		}
		catch (Exception ex)
		{
			fail();
		}
	}
}
