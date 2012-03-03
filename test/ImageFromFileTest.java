/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import drawingStyle.ImageFromFile;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author abc
 */
public class ImageFromFileTest
{
	private final String TEST_FILE_NAME = "testFile.txt";
	
	public ImageFromFileTest()
	{
	}

	/**
	 * Тест загрузки значка
	 */
	@Test
	public void getImageTest()
	{
		ImageFromFile testImage = new ImageFromFile();
		testImage.imageFileName = "";
		assertNull(testImage.getImage());
		
		testImage.imageFileName = "testIcon.png";
		assertNotNull(testImage.getImage());
	}
	
	/**
	 * Загрузка/чтение
	 */
	@Test
	public void FileTest()
	{
		ImageFromFile writingImage = new ImageFromFile();
		writingImage.imageFileName = "icon1.png";
		try
		{
			DataOutputStream output = new DataOutputStream(new FileOutputStream(TEST_FILE_NAME));
			writingImage.WriteToStream(output);
			output.close();
		}
		catch (Exception ex)
		{
			fail();
		}

		ImageFromFile readingImage = new ImageFromFile();
		try
		{
			DataInputStream input = new DataInputStream(new FileInputStream(TEST_FILE_NAME));
			readingImage.ReadFromStream(input);
			input.close();
			assertEquals(writingImage.imageFileName, readingImage.imageFileName);
		}
		catch (Exception ex)
		{
			fail();
		}
	}

	@BeforeClass
	public static void setUpClass() throws Exception
	{
	}

	@AfterClass
	public static void tearDownClass() throws Exception
	{
	}
}
