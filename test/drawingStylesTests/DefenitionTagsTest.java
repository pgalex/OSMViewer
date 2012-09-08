package drawingStylesTests;

import IOTesting.IOTester;
import drawingStyles.DefenitionTags;
import drawingStyles.EditableDefenitionTags;
import drawingStyles.MapTag;
import drawingStyles.exceptions.IncorrectParameterException;
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

		try
		{
			tags.get(0);
		}
		catch (IncorrectParameterException ex)
		{
			// ok
		}
	}

	/**
	 * Test getting tag by index less then bounds
	 */
	@Test
	public void gettingTagLessThenBoundsTest()
	{
		EditableDefenitionTags tags = new EditableDefenitionTags();
		tags.add(new MapTag("k2", "v2"));

		try
		{
			tags.get(-1);
		}
		catch (IncorrectParameterException ex)
		{
			// ok
		}
	}

	/**
	 * Test getting tag by index more then bounds
	 */
	@Test
	public void gettingTagMoreThenBoundsTest()
	{
		EditableDefenitionTags tags = new EditableDefenitionTags();
		tags.add(new MapTag("k2", "v2"));

		try
		{
			tags.get(tags.size());
		}
		catch (IncorrectParameterException ex)
		{
			// ok
		}
	}
	
	/**
	 * Comparing defenition when other tags are null
	 */
	@Test
	public void includingInNullTagsTest()
	{
		try
		{
			DefenitionTags tags = new DefenitionTags();
			tags.includingIn(null);
			fail();
		}
		catch(IncorrectParameterException ex)
		{
			// ok
		}
	}

	/**
	 * Comparing defenition when both defenition tags are empty
	 */
	@Test
	public void includingInAllTagsEmptyTest()
	{
		EditableDefenitionTags styleTags = new EditableDefenitionTags();
		EditableDefenitionTags objectTags = new EditableDefenitionTags();

		assertTrue(styleTags.includingIn(objectTags));
	}

	/**
	 * Comparing defenition not empty with empty tags
	 */
	@Test
	public void includingInNotEmptyWithEmptyTest()
	{
		EditableDefenitionTags styleTags = new EditableDefenitionTags();
		EditableDefenitionTags objectTags = new EditableDefenitionTags();

		objectTags.add(new MapTag("k1", "v1"));

		assertFalse(styleTags.includingIn(objectTags));
	}

	/**
	 * Comparing defenition empty with not empty tags
	 */
	@Test
	public void includingInEmptyWithNotEmptyTest()
	{
		EditableDefenitionTags styleTags = new EditableDefenitionTags();
		EditableDefenitionTags objectTags = new EditableDefenitionTags();

		styleTags.add(new MapTag("k1", "v1"));

		assertFalse(styleTags.includingIn(objectTags));
	}

	/**
	 * Comparing defenition equal tags (comparing should not be depends on order
	 * of tags)
	 */
	@Test
	public void includingInFullEqualTagsTest()
	{
		EditableDefenitionTags styleTags = new EditableDefenitionTags();
		EditableDefenitionTags objectTags = new EditableDefenitionTags();

		objectTags.add(new MapTag("k2", "v2"));
		objectTags.add(new MapTag("k1", "v1"));
		objectTags.add(new MapTag("k3", "v3"));
		styleTags.add(new MapTag("k1", "v1"));
		styleTags.add(new MapTag("k3", "v3"));
		styleTags.add(new MapTag("k2", "v2"));

		assertTrue(styleTags.includingIn(objectTags));
	}

	/**
	 * Comparing defenition not equal tags
	 */
	@Test
	public void includingInNotEqualTagsTest()
	{
		EditableDefenitionTags styleTags = new EditableDefenitionTags();
		EditableDefenitionTags objectTags = new EditableDefenitionTags();

		objectTags.add(new MapTag("k4", "v4"));
		objectTags.add(new MapTag("k1", "v1"));
		objectTags.add(new MapTag("k3", "v3"));
		styleTags.add(new MapTag("k1", "v1"));
		styleTags.add(new MapTag("k3", "v3"));
		styleTags.add(new MapTag("k2", "v2"));

		assertFalse(styleTags.includingIn(objectTags));
	}

	/**
	 * Comparing defenition tags not equal and count of tags are different
	 */
	@Test
	public void includingInNotEqualWithDifferentCountTest()
	{
		EditableDefenitionTags styleTags = new EditableDefenitionTags();
		EditableDefenitionTags objectTags = new EditableDefenitionTags();

		objectTags.add(new MapTag("k4", "v4"));
		objectTags.add(new MapTag("k1", "v1"));
		styleTags.add(new MapTag("k1", "v1"));
		styleTags.add(new MapTag("k3", "v3"));
		styleTags.add(new MapTag("k2", "v2"));

		assertFalse(styleTags.includingIn(objectTags));
	}

	/**
	 * Comparing defenition tags, some tags are not exists
	 */
	@Test
	public void includingInNotFullEqualsTest()
	{
		EditableDefenitionTags styleTags = new EditableDefenitionTags();
		EditableDefenitionTags objectTags = new EditableDefenitionTags();

		objectTags.add(new MapTag("k3", "v3"));
		objectTags.add(new MapTag("k2", "v2"));
		objectTags.add(new MapTag("k1", "v1"));
		styleTags.add(new MapTag("k1", "v1"));
		styleTags.add(new MapTag("k3", "v3"));

		assertTrue(styleTags.includingIn(objectTags));
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
			{
				assertTrue(writingTags.get(i).compareTo(readingTags.get(i)));
			}
		}
		catch (Exception ex)
		{
			fail();
		}
	}
}
