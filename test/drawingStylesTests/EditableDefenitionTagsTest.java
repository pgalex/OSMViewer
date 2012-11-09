package drawingStylesTests;

import drawingStyles.EditableDefenitionTags;
import drawingStyles.Tag;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * EditableDefenitionTags tests
 *
 * @author pgalex
 */
public class EditableDefenitionTagsTest
{
	/**
	 * Testing adding new tags - normal work
	 */
	@Test
	public void addingTagsTest()
	{
		EditableDefenitionTags tags = new EditableDefenitionTags();
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
		EditableDefenitionTags tags = new EditableDefenitionTags();
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
		EditableDefenitionTags tags = new EditableDefenitionTags();
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
		EditableDefenitionTags tags = new EditableDefenitionTags();
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
		EditableDefenitionTags tags = new EditableDefenitionTags();
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
		EditableDefenitionTags tags = new EditableDefenitionTags();
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
