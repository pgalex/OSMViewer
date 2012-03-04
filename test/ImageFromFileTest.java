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
		// при нулевом имени файла изображение пустое
		ImageFromFile testImage = new ImageFromFile("");
		assertNull(testImage.getImage());
		
		// нормальная работа - изображение загрузилось
		testImage.setImageFileName("testIcon.png");
		assertNotNull(testImage.getImage());
		assertNotNull(testImage.getImage()); // изображение загружено
		// установка пустого имени файла после загрузки изображения
		// изображение обнуляется
		testImage.setImageFileName("");
		assertNull(testImage.getImage());
	}
	
	/**
	 * Загрузка/чтение
	 */
	@Test
	public void fileTest()
	{
		ImageFromFile writingImage = new ImageFromFile();
		writingImage.setImageFileName("icon1.png");
		try
		{
			DataOutputStream output = new DataOutputStream(new FileOutputStream(TEST_FILE_NAME));
			writingImage.writeToStream(output);
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
			readingImage.readFromStream(input);
			input.close();
			assertEquals(writingImage.getImageFileName(), readingImage.getImageFileName());
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
