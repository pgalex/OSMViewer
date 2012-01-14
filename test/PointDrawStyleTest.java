/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.FileInputStream;
import java.io.DataInputStream;
import drawingStyle.PointDrawStyle;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author abc
 */
public class PointDrawStyleTest
{
	private final String TEST_FILE_NAME = "testFile.txt";

	public PointDrawStyleTest()
	{
	}

	/**
	 * Чтение/запись в файл
	 */
	@Test
	public void FileTest()
	{
		PointDrawStyle writingPoint = new PointDrawStyle();
		writingPoint.icon.imageFileName = "";

		//запись
		try
		{
			DataOutputStream output = new DataOutputStream(new FileOutputStream(TEST_FILE_NAME));
			writingPoint.WriteToStream(output);
			output.close();
		}
		catch (Exception ex)
		{
			fail();
		}

		//чтение
		PointDrawStyle readingPoint = new PointDrawStyle();
		try
		{
			DataInputStream input = new DataInputStream(new FileInputStream(TEST_FILE_NAME));
			readingPoint.ReadFromStream(input);
			input.close();
			assertEquals(writingPoint.icon.imageFileName, readingPoint.icon.imageFileName);
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
