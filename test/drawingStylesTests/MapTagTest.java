package drawingStylesTests;

import IOTesting.IOTester;
import drawingStyles.MapTag;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * MapTag class tests
 *
 * @author abc
 */
public class MapTagTest
{
	/**
	 * Reading/writing test
	 */
	@Test
	public void readingWritingTest()
	{
		try
		{
			MapTag writingTag = new MapTag("key1", "value1");
			IOTester.writeToTestFile(writingTag);

			MapTag readingTag = new MapTag();
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
	 * Testing compare two empty tags
	 */
	@Test
	public void comparingTwoEmptyTagsTest()
	{
		MapTag tag1 = new MapTag();
		MapTag tag2 = new MapTag();
		assertTrue(tag1.compareTo(tag2));
	}

	/**
	 * Testing compare with one empty tag
	 */
	@Test
	public void compareWithOneEmptyTagTest()
	{
		MapTag tag1 = new MapTag("k1", "v1");
		MapTag tag2 = new MapTag();
		assertFalse(tag1.compareTo(tag2));
	}

	/**
	 * Testing compare equal tags
	 */
	@Test
	public void compareEqualTagsTest()
	{
		MapTag tag1 = new MapTag("k1", "v1");
		MapTag tag2 = new MapTag("k1", "v1");
		assertTrue(tag1.compareTo(tag2));
	}

	/**
	 * Testing compare not equal tags
	 */
	@Test
	public void compareNotEqualTagsTest()
	{
		MapTag tag1 = new MapTag("k1", "v1");
		MapTag tag2 = new MapTag("k2", "v2");
		assertFalse(tag1.compareTo(tag2));
	}

	/**
	 * Testing compare equal tags with different case
	 */
	@Test
	public void compareEqualWithDifferentCaseTagsTest()
	{
		MapTag tag1 = new MapTag("k1", "v1");
		MapTag tag2 = new MapTag("K1", "V1");
		assertTrue(tag1.compareTo(tag2));
	}
}
