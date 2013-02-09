package mapDefenitionUtilitiesTests;

import IOTesting.IOTester;
import mapDefenitionUtilities.DefenitionTags;
import mapDefenitionUtilities.Tag;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Testing methods of DefenitionTags and DefenitionTags
 *
 * @author pgalex
 */
public class DefenitionTagsTest
{
	/**
	 * Test getting tag from empty DefenitionTags
	 */
	@Test
	public void gettingTagFromEmptyListTest()
	{
		DefenitionTags tags = new DefenitionTags();
		
		try
		{
			tags.get(0);
		}
		catch (IllegalArgumentException ex)
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
		DefenitionTags tags = new DefenitionTags();
		tags.add(new Tag("k2", "v2"));
		
		try
		{
			tags.get(-1);
		}
		catch (IllegalArgumentException ex)
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
		DefenitionTags tags = new DefenitionTags();
		tags.add(new Tag("k2", "v2"));
		
		try
		{
			tags.get(tags.count());
		}
		catch (IllegalArgumentException ex)
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
		catch (IllegalArgumentException ex)
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
		DefenitionTags styleTags = new DefenitionTags();
		DefenitionTags objectTags = new DefenitionTags();
		
		assertTrue(styleTags.includingIn(objectTags));
	}

	/**
	 * Comparing defenition not empty with empty tags
	 */
	@Test
	public void includingInNotEmptyWithEmptyTest()
	{
		DefenitionTags styleTags = new DefenitionTags();
		DefenitionTags objectTags = new DefenitionTags();
		
		objectTags.add(new Tag("k1", "v1"));
		
		assertFalse(styleTags.includingIn(objectTags));
	}

	/**
	 * Comparing defenition empty with not empty tags
	 */
	@Test
	public void includingInEmptyWithNotEmptyTest()
	{
		DefenitionTags styleTags = new DefenitionTags();
		DefenitionTags objectTags = new DefenitionTags();
		
		styleTags.add(new Tag("k1", "v1"));
		
		assertFalse(styleTags.includingIn(objectTags));
	}

	/**
	 * Comparing defenition equal tags (comparing should not be depends on order
	 * of tags)
	 */
	@Test
	public void includingInFullEqualTagsTest()
	{
		DefenitionTags styleTags = new DefenitionTags();
		DefenitionTags objectTags = new DefenitionTags();
		
		objectTags.add(new Tag("k2", "v2"));
		objectTags.add(new Tag("k1", "v1"));
		objectTags.add(new Tag("k3", "v3"));
		styleTags.add(new Tag("k1", "v1"));
		styleTags.add(new Tag("k3", "v3"));
		styleTags.add(new Tag("k2", "v2"));
		
		assertTrue(styleTags.includingIn(objectTags));
	}

	/**
	 * Comparing defenition not equal tags
	 */
	@Test
	public void includingInNotEqualTagsTest()
	{
		DefenitionTags styleTags = new DefenitionTags();
		DefenitionTags objectTags = new DefenitionTags();
		
		objectTags.add(new Tag("k4", "v4"));
		objectTags.add(new Tag("k1", "v1"));
		objectTags.add(new Tag("k3", "v3"));
		styleTags.add(new Tag("k1", "v1"));
		styleTags.add(new Tag("k3", "v3"));
		styleTags.add(new Tag("k2", "v2"));
		
		assertFalse(styleTags.includingIn(objectTags));
	}

	/**
	 * Comparing defenition tags not equal and count of tags are different
	 */
	@Test
	public void includingInNotEqualWithDifferentCountTest()
	{
		DefenitionTags styleTags = new DefenitionTags();
		DefenitionTags objectTags = new DefenitionTags();
		
		objectTags.add(new Tag("k4", "v4"));
		objectTags.add(new Tag("k1", "v1"));
		styleTags.add(new Tag("k1", "v1"));
		styleTags.add(new Tag("k3", "v3"));
		styleTags.add(new Tag("k2", "v2"));
		
		assertFalse(styleTags.includingIn(objectTags));
	}

	/**
	 * Comparing defenition tags, some tags are not exists
	 */
	@Test
	public void includingInNotFullEqualsTest()
	{
		DefenitionTags styleTags = new DefenitionTags();
		DefenitionTags objectTags = new DefenitionTags();
		
		objectTags.add(new Tag("k3", "v3"));
		objectTags.add(new Tag("k2", "v2"));
		objectTags.add(new Tag("k1", "v1"));
		styleTags.add(new Tag("k1", "v1"));
		styleTags.add(new Tag("k3", "v3"));
		
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
			DefenitionTags writingTags = new DefenitionTags();
			writingTags.add(new Tag("k3", "v3"));
			writingTags.add(new Tag("k1", "v1"));
			writingTags.add(new Tag("k2", "v2"));
			
			writingTags.writeToStream(IOTester.createTestOutputStream());
			
			DefenitionTags readingTags = new DefenitionTags();
			readingTags.readFromStream(IOTester.createTestInputStream());
			
			assertEquals(writingTags.count(), readingTags.count());
			for (int i = 0; i < writingTags.count(); i++)
			{
				assertTrue(writingTags.get(i).compareTo(readingTags.get(i)));
			}
		}
		catch (Exception ex)
		{
			fail();
		}
	}

	/**
	 * Testing adding new tags - normal work
	 */
	@Test
	public void addingTagsTest()
	{
		DefenitionTags tags = new DefenitionTags();
		tags.add(new Tag("k1", "v1"));
		tags.add(new Tag("k2", "v2"));
		
		assertFalse(tags.isEmpty());
		assertEquals(2, tags.count());
		assertTrue(tags.get(0).compareTo(new Tag("k1", "v1")));
		assertTrue(tags.get(1).compareTo(new Tag("k2", "v2")));
	}

	/**
	 * Testing adding null tag
	 */
	@Test
	public void addingNullTagTest()
	{
		DefenitionTags tags = new DefenitionTags();
		tags.add(new Tag("k1", "v1"));
		try
		{
			tags.add(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Testing clear method
	 */
	@Test
	public void clearTest()
	{
		DefenitionTags tags = new DefenitionTags();
		tags.add(new Tag("k1", "v1"));
		tags.add(new Tag("k2", "v2"));
		
		tags.clear();
		assertTrue(tags.isEmpty());
		assertEquals(0, tags.count());
	}

	/**
	 * Test removing tags
	 */
	@Test
	public void removeTest()
	{
		DefenitionTags tags = new DefenitionTags();
		tags.add(new Tag("k1", "v1"));
		tags.add(new Tag("k2", "v2"));
		
		tags.remove(1);
		assertEquals(1, tags.count());
		assertTrue(tags.get(0).compareTo(new Tag("k1", "v1")));
		
		tags.remove(0);
		assertTrue(tags.isEmpty());
		assertEquals(0, tags.count());
	}

	/**
	 * Test removing tag with index less then bounds
	 */
	@Test
	public void removeWithIndexLessThanBoundsTest()
	{
		DefenitionTags tags = new DefenitionTags();
		tags.add(new Tag("k1", "v1"));
		try
		{
			tags.remove(-1);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test removing tag with index more then bounds
	 */
	@Test
	public void removeWithIndexMoreThanBoundsTest()
	{
		DefenitionTags tags = new DefenitionTags();
		tags.add(new Tag("k1", "v1"));
		try
		{
			tags.remove(tags.count());
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}
}
