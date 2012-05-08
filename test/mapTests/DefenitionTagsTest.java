package mapTests;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import map.DefenitionTags;
import map.EditableDefenitionTags;
import map.MapTag;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Testing methods of Defenition by using EditableDefenitionTags
 * @author pgalex
 */
public class DefenitionTagsTest
{
	private final String TEST_FILE_NAME = "testFile.txt";

	public DefenitionTagsTest()
	{
	}

	
	/**
	 * get method test
	 */
	@Test
	public void getTest()
	{
		EditableDefenitionTags tags = new EditableDefenitionTags();
		// null list
		assertNull(tags.get(0));

		// normal work
		try
		{
			tags.add(new MapTag("k3", "v3"));
			tags.add(new MapTag("k1", "v1"));
			tags.add(new MapTag("k2", "v2"));
			
			for (int i = 0; i < tags.size(); i++)
				assertTrue(tags.get(i).compareTo(tags.get(i)));
		}
		catch (Exception e)
		{
			fail();
		}

		// out of bounds
		tags = new EditableDefenitionTags();
		tags.add(new MapTag("k3", "v3"));
		tags.add(new MapTag("k1", "v1"));
		tags.add(new MapTag("k2", "v2"));
		assertNull(tags.get(-1));
		
		tags = new EditableDefenitionTags();
		tags.add(new MapTag("k3", "v3"));
		tags.add(new MapTag("k1", "v1"));
		tags.add(new MapTag("k2", "v2"));
		assertNull(tags.get(tags.size()));
	}

	/**
	 * Comparing defenition tags test
	 */
	@Test
	public void compareToTest()
	{
		EditableDefenitionTags compareTags = new EditableDefenitionTags();
		EditableDefenitionTags objectTags = new EditableDefenitionTags();

		//пустые списки
		objectTags.clear();
		compareTags.clear();
		assertEquals(true, objectTags.compareTo(compareTags));

		// в стиле пустой
		objectTags.clear();
		compareTags.clear();
		compareTags.add(new MapTag("k1", "v1"));
		assertEquals(false, objectTags.compareTo(compareTags));

		// в тегах пустой
		objectTags.clear();
		compareTags.clear();
		objectTags.add(new MapTag("k1", "v1"));
		assertEquals(false, objectTags.compareTo(compareTags));

		// разный порядок
		objectTags.clear();
		compareTags.clear();
		objectTags.add(new MapTag("k2", "v2"));
		objectTags.add(new MapTag("k1", "v1"));
		objectTags.add(new MapTag("k3", "v3"));
		compareTags.add(new MapTag("k1", "v1"));
		compareTags.add(new MapTag("k3", "v3"));
		compareTags.add(new MapTag("k2", "v2"));
		assertEquals(true, objectTags.compareTo(compareTags));

		// несовпадение
		objectTags.add(new MapTag("k4", "v4"));
		objectTags.add(new MapTag("k1", "v1"));
		objectTags.add(new MapTag("k3", "v3"));
		compareTags.add(new MapTag("k1", "v1"));
		compareTags.add(new MapTag("k3", "v3"));
		compareTags.add(new MapTag("k2", "v2"));
		assertEquals(false, objectTags.compareTo(compareTags));

		// неравное кол-во, несовпадают
		objectTags.add(new MapTag("k4", "v4"));
		objectTags.add(new MapTag("k1", "v1"));
		compareTags.add(new MapTag("k1", "v1"));
		compareTags.add(new MapTag("k3", "v3"));
		compareTags.add(new MapTag("k2", "v2"));
		assertEquals(false, objectTags.compareTo(compareTags));

		// неравное кол-во, совпадают
		objectTags.add(new MapTag("k3", "v3"));
		objectTags.add(new MapTag("k1", "v1"));
		compareTags.add(new MapTag("k1", "v1"));
		compareTags.add(new MapTag("k3", "v3"));
		compareTags.add(new MapTag("k2", "v2"));
		assertEquals(false, objectTags.compareTo(compareTags));
	}

	/**
	 * Reading/writing
	 */
	@Test
	public void fileTest()
	{
		EditableDefenitionTags writingTags = new EditableDefenitionTags();
		writingTags.add(new MapTag("k3", "v3"));
		writingTags.add(new MapTag("k1", "v1"));
		writingTags.add(new MapTag("k2", "v2"));
		//запись
		try
		{
			DataOutputStream output = new DataOutputStream(new FileOutputStream(TEST_FILE_NAME));
			writingTags.writeToStream(output);
			output.close();
		}
		catch (Exception ex)
		{
			fail();
		}

		//чтение
		DefenitionTags readingTags = new DefenitionTags();
		try
		{
			DataInputStream input = new DataInputStream(new FileInputStream(TEST_FILE_NAME));
			readingTags.readFromStream(input);
			input.close();
			assertEquals(writingTags.size(), readingTags.size());
			for (int i = 0; i < writingTags.size(); i++)
				assertTrue(writingTags.get(i).compareTo(readingTags.get(i)));
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
