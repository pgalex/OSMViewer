package mapDefenitionUtilitiesTests;

import IOTesting.IOTester;
import mapDefenitionUtilities.Tag;
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
	 * Reading/writing test
	 */
	@Test
	public void readingWritingTest()
	{
		try
		{
			Tag writingTag = new Tag("key1", "value1");
			IOTester.writeToTestFile(writingTag);

			Tag readingTag = new Tag();
			IOTester.readFromTestFile(readingTag);

			assertEquals(writingTag.getKey(), readingTag.getKey());
			assertEquals(writingTag.getValue(), readingTag.getValue());
		}
		catch (Exception ex)
		{
			fail();
		}
	}
	
	/**
	 * Testing compare with null tag
	 */
	@Test
	public void comparingWithNullTagTest()
	{
		try
		{
			Tag tag = new Tag();
			tag.compareTo(null);
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
		assertTrue(tag1.compareTo(tag2));
	}

	/**
	 * Testing compare with one empty tag
	 */
	@Test
	public void compareWithOneEmptyTagTest()
	{
		Tag tag1 = new Tag("k1", "v1");
		Tag tag2 = new Tag();
		assertFalse(tag1.compareTo(tag2));
	}

	/**
	 * Testing compare equal tags
	 */
	@Test
	public void compareEqualTagsTest()
	{
		Tag tag1 = new Tag("k1", "v1");
		Tag tag2 = new Tag("k1", "v1");
		assertTrue(tag1.compareTo(tag2));
	}

	/**
	 * Testing compare not equal tags
	 */
	@Test
	public void compareNotEqualTagsTest()
	{
		Tag tag1 = new Tag("k1", "v1");
		Tag tag2 = new Tag("k2", "v2");
		assertFalse(tag1.compareTo(tag2));
	}

	/**
	 * Testing compare equal tags with different case
	 */
	@Test
	public void compareEqualWithDifferentCaseTagsTest()
	{
		Tag tag1 = new Tag("k1", "v1");
		Tag tag2 = new Tag("K1", "V1");
		assertTrue(tag1.compareTo(tag2));
	}
}
