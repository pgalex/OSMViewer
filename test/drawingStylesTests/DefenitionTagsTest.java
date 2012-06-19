package drawingStylesTests;

import IOTesting.IOTester;
import drawingStyles.DefenitionTags;
import drawingStyles.EditableDefenitionTags;
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
	 * Test getting tag from empty EditableDefenitionTags
	 */
	@Test
	public void gettingTagFromEmptyListTest()
	{
		EditableDefenitionTags tags = new EditableDefenitionTags();

		assertNull(tags.get(0));
	}

	/**
	 * Test getting tag by index less then bounds
	 */
	@Test
	public void gettingTagLessThenBoundsTest()
	{
		EditableDefenitionTags tags = new EditableDefenitionTags();
		tags.add(new MapTag("k2", "v2"));

		assertNull(tags.get(-1));
	}

	/**
	 * Test getting tag by index more then bounds
	 */
	@Test
	public void gettingTagMoreThenBoundsTest()
	{
		EditableDefenitionTags tags = new EditableDefenitionTags();
		tags.add(new MapTag("k2", "v2"));

		assertNull(tags.get(tags.size()));
	}

	/**
	 * Comparing defenition when both defenition tags are empty
	 */
	@Test
	public void comparingAllTagsEmptyTest()
	{
		EditableDefenitionTags styleTags = new EditableDefenitionTags();
		EditableDefenitionTags objectTags = new EditableDefenitionTags();

		assertTrue(styleTags.compareTo(objectTags));
	}

	/**
	 * Comparing defenition not empty with empty tags
	 */
	@Test
	public void comparingNotEmptyWithEmptyTest()
	{
		EditableDefenitionTags styleTags = new EditableDefenitionTags();
		EditableDefenitionTags objectTags = new EditableDefenitionTags();

		objectTags.add(new MapTag("k1", "v1"));

		assertFalse(styleTags.compareTo(objectTags));
	}

	/**
	 * Comparing defenition empty with not empty tags
	 */
	@Test
	public void comparingEmptyWithNotEmptyTest()
	{
		EditableDefenitionTags styleTags = new EditableDefenitionTags();
		EditableDefenitionTags objectTags = new EditableDefenitionTags();

		styleTags.add(new MapTag("k1", "v1"));

		assertFalse(styleTags.compareTo(objectTags));
	}

	/**
	 * Comparing defenition equal tags (comparing should not be depends on order
	 * of tags)
	 */
	@Test
	public void comparingFullEqualTagsTest()
	{
		EditableDefenitionTags styleTags = new EditableDefenitionTags();
		EditableDefenitionTags objectTags = new EditableDefenitionTags();

		objectTags.add(new MapTag("k2", "v2"));
		objectTags.add(new MapTag("k1", "v1"));
		objectTags.add(new MapTag("k3", "v3"));
		styleTags.add(new MapTag("k1", "v1"));
		styleTags.add(new MapTag("k3", "v3"));
		styleTags.add(new MapTag("k2", "v2"));

		assertTrue(styleTags.compareTo(objectTags));
	}

	/**
	 * Comparing defenition not equal tags
	 */
	@Test
	public void comparingNotEqualTagsTest()
	{
		EditableDefenitionTags styleTags = new EditableDefenitionTags();
		EditableDefenitionTags objectTags = new EditableDefenitionTags();

		objectTags.add(new MapTag("k4", "v4"));
		objectTags.add(new MapTag("k1", "v1"));
		objectTags.add(new MapTag("k3", "v3"));
		styleTags.add(new MapTag("k1", "v1"));
		styleTags.add(new MapTag("k3", "v3"));
		styleTags.add(new MapTag("k2", "v2"));

		assertFalse(styleTags.compareTo(objectTags));
	}

	/**
	 * Comparing defenition tags not equal and count of tags are different
	 */
	@Test
	public void comparingNotEqualWithDifferentCountTest()
	{
		EditableDefenitionTags styleTags = new EditableDefenitionTags();
		EditableDefenitionTags objectTags = new EditableDefenitionTags();

		objectTags.add(new MapTag("k4", "v4"));
		objectTags.add(new MapTag("k1", "v1"));
		styleTags.add(new MapTag("k1", "v1"));
		styleTags.add(new MapTag("k3", "v3"));
		styleTags.add(new MapTag("k2", "v2"));

		assertFalse(styleTags.compareTo(objectTags));
	}

	/**
	 * Comparing defenition tags, some tags are not exists
	 */
	@Test
	public void comparingNotFullEqualsTest()
	{
		EditableDefenitionTags styleTags = new EditableDefenitionTags();
		EditableDefenitionTags objectTags = new EditableDefenitionTags();

		objectTags.add(new MapTag("k3", "v3"));
		objectTags.add(new MapTag("k2", "v2"));
		objectTags.add(new MapTag("k1", "v1"));
		styleTags.add(new MapTag("k1", "v1"));
		styleTags.add(new MapTag("k3", "v3"));

		assertTrue(styleTags.compareTo(objectTags));
	}

	/**
	 * Reading/writing test
	 */
	@Test
	public void readingWritingTest()
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
