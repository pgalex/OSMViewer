/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import drawingStyle.PolygonDrawStyle;
import java.awt.Color;
import java.io.FileInputStream;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.DataOutputStream;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author abc
 */
public class PolygonDrawStyleTest
{
	private final String TEST_FILE_NAME = "testFile.txt";

	public PolygonDrawStyleTest()
	{
	}

	@Test
	public void FileTest()
	{
		PolygonDrawStyle writingStyle = new PolygonDrawStyle();
		writingStyle.fillColor = Color.MAGENTA;
		writingStyle.borderDrawStyle.color = Color.ORANGE;
		writingStyle.borderDrawStyle.width = 22;
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
		PolygonDrawStyle readingStyle = new PolygonDrawStyle();
		try
		{
			DataInputStream input = new DataInputStream(new FileInputStream(TEST_FILE_NAME));
			readingStyle.ReadFromStream(input);
			input.close();
			assertEquals(writingStyle.fillColor, readingStyle.fillColor);
			assertEquals(writingStyle.borderDrawStyle.color, readingStyle.borderDrawStyle.color);
			assertEquals(writingStyle.borderDrawStyle.width, readingStyle.borderDrawStyle.width);
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
