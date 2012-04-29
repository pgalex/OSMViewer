/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mapTests;

import map.EditableDefenitionTags;
import map.MapTag;
import map.exceptions.TagIndexOutOfBoundsException;
import map.exceptions.TagIsNullException;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author pgalex
 */
public class EditableDefenitionTagsTest
{
	public EditableDefenitionTagsTest()
	{
	}

	/**
	 * Testing add method - normal work
	 */
	@Test
	public void addingNormalTest()
	{
		EditableDefenitionTags tags = new EditableDefenitionTags();
		tags.add(new MapTag("k1", "v1"));
		tags.add(new MapTag("k2", "v2"));

		assertFalse(tags.isEmpty());
		assertEquals(2, tags.size());
		assertTrue(tags.get(0).compareTo(new MapTag("k1", "v1")));
		assertTrue(tags.get(1).compareTo(new MapTag("k2", "v2")));
	}

	/**
	 * Testing add method - map tag is null
	 */
	@Test
	public void addingNullParameterTest()
	{
		EditableDefenitionTags tags = new EditableDefenitionTags();
		tags.add(new MapTag("k1", "v1"));
		try
		{
			tags.add(null);
			fail();
		}
		catch (TagIsNullException ex)
		{
			assertEquals(tags, ex.getTagsThrowedException());
		}
	}

	/**
	 * Testing clear method
	 */
	@Test
	public void clearTest()
	{
		EditableDefenitionTags tags = new EditableDefenitionTags();
		tags.add(new MapTag("k1", "v1"));
		tags.add(new MapTag("k2", "v2"));

		assertFalse(tags.isEmpty());
		assertEquals(2, tags.size());
		tags.clear();
		assertTrue(tags.isEmpty());
		assertEquals(0, tags.size());
	}

	/**
	 * Testing remove method
	 */
	@Test
	public void removeTest()
	{
		EditableDefenitionTags tags = new EditableDefenitionTags();
		tags.add(new MapTag("k1", "v1"));
		tags.add(new MapTag("k2", "v2"));

		assertFalse(tags.isEmpty());
		assertEquals(2, tags.size());

		tags.remove(1);
		assertEquals(1, tags.size());
		assertTrue(tags.get(0).compareTo(new MapTag("k1", "v1")));

		tags.remove(0);
		assertTrue(tags.isEmpty());
		assertEquals(0, tags.size());

		tags.add(new MapTag("k1", "v1"));
		try
		{
			tags.remove(-1);
			fail();
		}
		catch (TagIndexOutOfBoundsException ex)
		{
			assertEquals(tags, ex.getTagsThrowedException());
			assertEquals(-1, ex.getIncorrectTagIndex());
			assertEquals(0, ex.getBoundsMinimum());
			assertEquals(tags.size(), ex.getBoundsMaximum());
		}
		try
		{
			tags.remove(tags.size());
			fail();
		}
		catch (TagIndexOutOfBoundsException ex)
		{
			assertEquals(tags, ex.getTagsThrowedException());
			assertEquals(1, ex.getIncorrectTagIndex());
			assertEquals(0, ex.getBoundsMinimum());
			assertEquals(tags.size(), ex.getBoundsMaximum());
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
