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
 *
 * @author pgalex
 */
public class DefenitionTagsTest
{
	private final String TEST_FILE_NAME = "testFile.txt";

	public DefenitionTagsTest()
	{
	}

	/**
	 * Auto initialize null fields in constructor
	 */
	@Test
	public void constructorTest()
	{
		DefenitionTags tags = new DefenitionTags(null);
		try
		{
			tags.isEmpty(); // trying to use inner fields
		}
		catch (Exception e)
		{
			fail();
		}
		assertTrue(tags.isEmpty());
		assertEquals(0, tags.size());
	}

	/**
	 * get method test
	 */
	@Test
	public void getTest()
	{
		DefenitionTags tags = new DefenitionTags();
		// null list
		assertNull(tags.get(0));

		// normal work
		try
		{
			ArrayList<MapTag> tagsArray = new ArrayList<MapTag>();
			tagsArray.add(new MapTag("k3", "v3"));
			tagsArray.add(new MapTag("k1", "v1"));
			tagsArray.add(new MapTag("k2", "v2"));
			tags = new DefenitionTags(tagsArray);
			for (int i = 0; i < tagsArray.size(); i++)
				assertTrue(tagsArray.get(i).compareTo(tags.get(i)));
		}
		catch (Exception e)
		{
			fail();
		}

		// out of bounds
		ArrayList<MapTag> tagsArray = new ArrayList<MapTag>();
		tagsArray.add(new MapTag("k3", "v3"));
		tagsArray.add(new MapTag("k1", "v1"));
		tagsArray.add(new MapTag("k2", "v2"));
		tags = new DefenitionTags(tagsArray);
		assertNull(tags.get(-1));
		
		tagsArray.add(new MapTag("k3", "v3"));
		tagsArray.add(new MapTag("k1", "v1"));
		tagsArray.add(new MapTag("k2", "v2"));
		tags = new DefenitionTags(tagsArray);
		assertNull(tags.get(tagsArray.size()));
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
		ArrayList<MapTag> tagsArray = new ArrayList<MapTag>();
		tagsArray.add(new MapTag("k3", "v3"));
		tagsArray.add(new MapTag("k1", "v1"));
		tagsArray.add(new MapTag("k2", "v2"));
		DefenitionTags writingTags = new DefenitionTags(tagsArray);
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
