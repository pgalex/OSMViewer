package com.osmviewer.mapDefenitionUtilitiesTests;

import com.osmviewer.mapDefenitionUtilities.DefenitionTags;
import com.osmviewer.mapDefenitionUtilities.Tag;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Tests of DefenitionTags
 *
 * @author pgalex
 */
public class DefenitionTagsTest
{
	@Test
	public void containsNullTag()
	{
		DefenitionTags defenitionTags = new DefenitionTags();
		try
		{
			defenitionTags.contains(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	@Test
	public void containsOneTag()
	{
		DefenitionTags defenitionTags = new DefenitionTags();
		defenitionTags.add(new Tag("a", "b"));
		assertTrue(defenitionTags.contains(new Tag("a", "b")));
	}

	@Test
	public void containsSeveralTags()
	{
		DefenitionTags defenitionTags = new DefenitionTags();
		defenitionTags.add(new Tag("a", "b"));
		defenitionTags.add(new Tag("c", "d"));
		defenitionTags.add(new Tag("e", "f"));
		assertTrue(defenitionTags.contains(new Tag("e", "f")));
	}

	@Test
	public void notContains()
	{
		DefenitionTags defenitionTags = new DefenitionTags();
		defenitionTags.add(new Tag("a", "b"));
		defenitionTags.add(new Tag("c", "d"));
		defenitionTags.add(new Tag("e", "f"));
		assertFalse(defenitionTags.contains(new Tag("o", "o")));
	}

	@Test
	public void containsEmpty()
	{
		DefenitionTags defenitionTags = new DefenitionTags();
		assertFalse(defenitionTags.contains(new Tag()));
	}

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
		assertTrue(tags.get(0).equals(new Tag("k1", "v1")));
		assertTrue(tags.get(1).equals(new Tag("k2", "v2")));
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
		assertTrue(tags.get(0).equals(new Tag("k1", "v1")));

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
