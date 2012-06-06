package mapTests;

import IOTesting.IOTester;
import map.DefenitionTags;
import map.EditableDefenitionTags;
import map.MapTag;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Testing methods of DefenitionTags and EditableDefenitionTags
 *
 * @author pgalex
 */
public class DefenitionTagsTest
{
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
	 * Reading/writing test
	 */
	@Test
	public void fileTest()
	{
		try
		{
			EditableDefenitionTags writingTags = new EditableDefenitionTags();
			writingTags.add(new MapTag("k3", "v3"));
			writingTags.add(new MapTag("k1", "v1"));
			writingTags.add(new MapTag("k2", "v2"));

			IOTester.writeToTestFile(writingTags);

			DefenitionTags readingTags = new DefenitionTags();
			IOTester.readFromTestFile(readingTags);

			assertEquals(writingTags.size(), readingTags.size());
			for (int i = 0; i < writingTags.size(); i++)
				assertTrue(writingTags.get(i).compareTo(readingTags.get(i)));
		}
		catch (Exception ex)
		{
			fail();
		}
	}
}
