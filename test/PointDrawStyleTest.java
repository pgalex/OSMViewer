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
	private final String ICON_FILE_NAME = "icon1.png";
	private final String TEST_FILE_NAME = "testFile.txt";
	
	public PointDrawStyleTest()
	{
	}
	
	@Test
	public void FileTest()
	{
		PointDrawStyle pointStyle = new PointDrawStyle();
		assertEquals(true, pointStyle.iconFileName.isEmpty());

		//запись
		pointStyle.iconFileName = ICON_FILE_NAME;
		try
		{
			DataOutputStream output = new DataOutputStream(new FileOutputStream(TEST_FILE_NAME));
			pointStyle.WriteToStream(output);
			output.close();
		}
		catch (Exception ex)
		{
			fail();
		}
		
		
		//чтение
		pointStyle.iconFileName = "";
		try
		{
			DataInputStream input = new DataInputStream(new FileInputStream(TEST_FILE_NAME));
			pointStyle.ReadFromStream(input);
			input.close();
			assertEquals(ICON_FILE_NAME, pointStyle.iconFileName);
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
