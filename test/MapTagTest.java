/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import map.MapTag;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author abc
 */
public class MapTagTest
{
	private final String TEST_FILE_NAME = "testFile.txt";

	public MapTagTest()
	{
	}

	/**
	 * Чтение запись
	 */
	@Test
	public void FileTest()
	{
		MapTag writingTag = new MapTag("key1", "v1");
		try
		{
			DataOutputStream output = new DataOutputStream(new FileOutputStream(TEST_FILE_NAME));
			writingTag.WriteToStream(output);
			output.close();
		}
		catch (Exception ex)
		{
			fail();
		}

		//чтение
		MapTag readingTag = new MapTag();
		try
		{
			DataInputStream input = new DataInputStream(new FileInputStream(TEST_FILE_NAME));
			readingTag.ReadFromStream(input);
			input.close();
			assertEquals(writingTag.getKey(), readingTag.getKey());
			assertEquals(writingTag.getValue(), readingTag.getValue());
		}
		catch (Exception ex)
		{
			fail();
		}
	}

	/**
	 * Сравнение тегов
	 */
	@Test
	public void compareToTest()
	{
		MapTag tag1 = new MapTag();
		MapTag tag2 = new MapTag();
		//пустые
		assertEquals(true, tag1.compareTo(tag2));

		//один пустой
		tag1.setKey("k1");
		tag1.setValue("v1");
		assertEquals(false, tag1.compareTo(tag2));

		//одинаковые
		tag2.setKey("k1");
		tag2.setValue("v1");
		assertEquals(true, tag1.compareTo(tag2));

		//разные
		tag2.setKey("k2");
		tag2.setValue("v2");
		assertEquals(false, tag1.compareTo(tag2));
		
		//с разным регистром
		tag2.setKey("K1");
		tag2.setValue("V1");
		assertEquals(true, tag1.compareTo(tag2));
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
