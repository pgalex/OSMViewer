package com.osmviewer.mapDefenitionUtilitiesTests;

import com.osmviewer.mapDefenitionUtilities.Tag;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Tag class tests
 *
 * @author abc
 */
public class TagTest
{
	/**
	 * Test creating tag with null key
	 */
	@Test
	public void creatingWithNullKeyTest()
	{
		try
		{
			Tag tag = new Tag(null, "23");
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test creating tag with null value
	 */
	@Test
	public void creatingWithNullValueTest()
	{
		try
		{
			Tag tag = new Tag("123", null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Testing compare two empty tags
	 */
	@Test
	public void comparingTwoEmptyTagsTest()
	{
		Tag tag1 = new Tag();
		Tag tag2 = new Tag();
		assertTrue(tag1.equals(tag2));
	}

	/**
	 * Testing compare with one empty tag
	 */
	@Test
	public void compareWithOneEmptyTagTest()
	{
		Tag tag1 = new Tag("k1", "v1");
		Tag tag2 = new Tag();
		assertFalse(tag1.equals(tag2));
	}

	/**
	 * Testing compare equal tags
	 */
	@Test
	public void compareEqualTagsTest()
	{
		Tag tag1 = new Tag("k1", "v1");
		Tag tag2 = new Tag("k1", "v1");
		assertTrue(tag1.equals(tag2));
	}

	/**
	 * Testing compare not equal tags
	 */
	@Test
	public void compareNotEqualTagsTest()
	{
		Tag tag1 = new Tag("k1", "v1");
		Tag tag2 = new Tag("k2", "v2");
		assertFalse(tag1.equals(tag2));
	}

	/**
	 * Testing compare equal tags with different case
	 */
	@Test
	public void compareEqualWithDifferentCaseTagsTest()
	{
		Tag tag1 = new Tag("k1", "v1");
		Tag tag2 = new Tag("K1", "V1");
		assertTrue(tag1.equals(tag2));
	}
}
