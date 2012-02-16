/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import drawingStyle.IOColor;
import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

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
	public void FileTest()
	{
		IOColor writingColor = new IOColor(Color.MAGENTA);
		try
		{
			DataOutputStream output = new DataOutputStream(new FileOutputStream(TEST_FILE_NAME));
			writingColor.WriteToStream(output);
			output.close();
		}
		catch (Exception ex)
		{
			fail();
		}

		IOColor readingColor = new IOColor();
		try
		{
			DataInputStream input = new DataInputStream(new FileInputStream(TEST_FILE_NAME));
			readingColor = IOColor.ReadFromStream(input);
			input.close();
			assertEquals(writingColor, readingColor);
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
