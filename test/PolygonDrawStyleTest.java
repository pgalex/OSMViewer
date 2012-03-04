/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import drawingStyle.IOColor;
import drawingStyle.ImageFromFile;
import drawingStyle.LineDrawStyle;
import drawingStyle.PolygonDrawStyle;
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
public class PolygonDrawStyleTest
{
	private final String TEST_FILE_NAME = "testFile.txt";

	public PolygonDrawStyleTest()
	{
	}

	/**
	 * тест создания с нулевыми аргументами
	 */
	@Test
	public void constructorTest()
	{
		PolygonDrawStyle testStyle = new PolygonDrawStyle(null, null, null);
		assertNotNull(testStyle.getBorderDrawStyle());
		assertNotNull(testStyle.getFillColor());
		assertNotNull(testStyle.getFillImage());
	}

	/**
	 * Чтение запись
	 */
	@Test
	public void fileTest()
	{
		LineDrawStyle borderStyle = new LineDrawStyle(new IOColor(Color.CYAN), 10, LineDrawStyle.SOLID_LINE_PATTERN);
		PolygonDrawStyle writingStyle = new PolygonDrawStyle(new IOColor(Color.MAGENTA), borderStyle,
						new ImageFromFile("icon1.png"));
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
			assertEquals(writingStyle.getFillColor(), readingStyle.getFillColor());
			assertEquals(writingStyle.getBorderDrawStyle().getColor(), readingStyle.getBorderDrawStyle().getColor());
			assertEquals(writingStyle.getBorderDrawStyle().getWidth(), readingStyle.getBorderDrawStyle().getWidth());
			assertEquals(writingStyle.getFillImage().getImageFileName(), readingStyle.getFillImage().getImageFileName());
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
