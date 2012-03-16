package drawingStyleTests;

import drawingStyle.IOColor;
import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author abc
 */
public class IOColorTest
{
	private final String TEST_FILE_NAME = "testFile.txt";

	public IOColorTest()
	{
	}

	/**
	 * Тест чтения/записи в файл
	 */
	@Test
	public void fileTest()
	{
		IOColor writingColor = new IOColor(Color.MAGENTA);
		try
		{
			DataOutputStream output = new DataOutputStream(new FileOutputStream(TEST_FILE_NAME));
			writingColor.writeToStream(output);
			output.close();
		}
		catch (Exception ex)
		{
			fail();
		}

		try
		{
			DataInputStream input = new DataInputStream(new FileInputStream(TEST_FILE_NAME));
			IOColor readingColor = new IOColor();
			readingColor.readFromStream(input);
			input.close();
			assertEquals(writingColor.getColor(), readingColor.getColor());
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
