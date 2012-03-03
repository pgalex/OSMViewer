/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import drawingStyle.IOColor;
import drawingStyle.LineDrawStyle;
import java.awt.Color;
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
public class LineDrawStyleTest
{
	private final String TEST_FILE_NAME = "testFile.txt";

	public LineDrawStyleTest()
	{
	}

	@Test
	public void FileTest()
	{
		LineDrawStyle writingStyle = new LineDrawStyle();
		writingStyle.color = new IOColor(Color.CYAN);
		writingStyle.width = 11;
		writingStyle.pattern = new float[4];
		writingStyle.pattern[0] = 2;
		writingStyle.pattern[1] = 3;
		writingStyle.pattern[2] = 4;
		writingStyle.pattern[3] = 5;

		//запись
		try
		{
			DataOutputStream output = new DataOutputStream(new FileOutputStream(TEST_FILE_NAME));
			writingStyle.writeToStream(output);
			output.close();
		}
		catch (Exception ex)
		{
			fail();
		}

		//чтение
		LineDrawStyle readingStyle = new LineDrawStyle();
		try
		{
			DataInputStream input = new DataInputStream(new FileInputStream(TEST_FILE_NAME));
			readingStyle.readFromStream(input);
			input.close();
			assertEquals(writingStyle.color, readingStyle.color);
			assertArrayEquals(writingStyle.pattern, readingStyle.pattern, 0.01f);
			assertEquals(writingStyle.width, readingStyle.width);
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
