import drawingStyle.IOIcon;
import java.io.*;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author abc
 */
public class IOIconTest
{
	private final String TEST_FILE_NAME = "testFile.txt";
	private final String ICON_FILE_NAME = "testIcon.png";

	public IOIconTest()
	{
	}

	/**
	 * Тест конструктор
	 */
	@Test
	public void defaulContructorTest()
	{
		// при нулевом имени файла изображение пустое
		IOIcon testImage = new IOIcon();
		assertNull(testImage.getImage());
	}

	/**
	 * Тест загрузки значка
	 */
	@Test
	public void getImageTest()
	{

		// нормальная работа - изображение загрузилось
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
	 * Тест чтения записи пустого изображения
	 */
	@Test
	public void fileNullImageTest()
	{
		IOIcon testImage = new IOIcon();
		try
		{
			DataOutputStream output = new DataOutputStream(new FileOutputStream(TEST_FILE_NAME));
			testImage.writeToStream(output);
		}
		catch (IOException ex)
		{
			fail();
		}
	}

	/**
	 * Тест чтения записи нормального (существующего) изображения. При изменение
	 * необходима проверка в окне
	 */
	@Test
	public void normalImageTest()
	{
		try
		{
			IOIcon writingImage = new IOIcon(ICON_FILE_NAME);
			DataOutputStream output = new DataOutputStream(new FileOutputStream(TEST_FILE_NAME));
			writingImage.writeToStream(output);
		}
		catch (IOException ex)
		{
			fail();
		}

		IOIcon readingImage = new IOIcon();
		try
		{
			DataInputStream input = new DataInputStream(new FileInputStream(TEST_FILE_NAME));
			readingImage.readFromStream(input);
			assertNotNull(readingImage.getImage());
		}
		catch (IOException ex)
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
