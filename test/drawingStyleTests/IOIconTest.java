package drawingStyleTests;

import drawingStyle.IOIcon;
import java.io.*;
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
		IOIcon testImage = null;
		try
		{
			testImage = new IOIcon(ICON_FILE_NAME);
		}
		catch (IOException ex)
		{
			fail();
		}

		assertNotNull(testImage.getImage());
	}

	/**
	 * Test reading/writing loaded image (not null)
	 */
	@Test
	public void normalImageTest()
	{
		try
		{
			IOIcon writingImage = new IOIcon(ICON_FILE_NAME);
			DataOutputStream output = new DataOutputStream(new FileOutputStream(DrawingStyleTestsParameters.TEST_FILE_NAME));
			writingImage.writeToStream(output);
		}
		catch (IOException ex)
		{
			fail();
		}

		IOIcon readingImage = new IOIcon();
		try
		{
			DataInputStream input = new DataInputStream(new FileInputStream(DrawingStyleTestsParameters.TEST_FILE_NAME));
			readingImage.readFromStream(input);
			assertNotNull(readingImage.getImage());
		}
		catch (IOException ex)
		{
			fail();
		}
	}
}
