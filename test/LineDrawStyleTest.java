/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.FileInputStream;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.DataOutputStream;
import drawingStyle.LineDrawStyle;
import java.awt.Color;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

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
		writingStyle.color = Color.CYAN;
		writingStyle.width = 11;
		
		//запись
		try
		{
			DataOutputStream output = new DataOutputStream(new FileOutputStream(TEST_FILE_NAME));
			writingStyle.WriteToStream(output);
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
			readingStyle.ReadFromStream(input);
			input.close();
			assertEquals(writingStyle.color, readingStyle.color);
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
