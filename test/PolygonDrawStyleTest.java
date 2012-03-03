/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import drawingStyle.IOColor;
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
	public void fileTest()
	{
		PolygonDrawStyle writingStyle = new PolygonDrawStyle();
		writingStyle.fillColor = new IOColor(Color.MAGENTA);
		writingStyle.borderDrawStyle.color = new IOColor(Color.ORANGE);
		writingStyle.borderDrawStyle.width = 22;
		writingStyle.fillImage.imageFileName = "icon1.png";
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
		PolygonDrawStyle readingStyle = new PolygonDrawStyle();
		try
		{
			DataInputStream input = new DataInputStream(new FileInputStream(TEST_FILE_NAME));
			readingStyle.readFromStream(input);
			input.close();
			assertEquals(writingStyle.fillColor, readingStyle.fillColor);
			assertEquals(writingStyle.borderDrawStyle.color, readingStyle.borderDrawStyle.color);
			assertEquals(writingStyle.borderDrawStyle.width, readingStyle.borderDrawStyle.width);
			assertEquals(writingStyle.fillImage.imageFileName, readingStyle.fillImage.imageFileName);
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
