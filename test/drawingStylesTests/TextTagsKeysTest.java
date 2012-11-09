package drawingStylesTests;

import IOTesting.IOTester;
import drawingStyles.DefenitionTags;
import drawingStyles.EditableDefenitionTags;
import drawingStyles.Tag;
import drawingStyles.TextTagsKeys;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * TextTagsKeys class tests
 *
 * @author pgalex
 */
public class TextTagsKeysTest
{
	/**
	 * Default constructor test
	 */
	@Test
	public void defaultConstructorInitializeTest()
	{
		TextTagsKeys testKeys = new TextTagsKeys();
		assertTrue(testKeys.getKeysCount() > 0);
	}

	/**
	 * Testing adding null tag key
	 */
	@Test
	public void addingNullKeyTest()
	{
		try
		{
			TextTagsKeys testKeys = new TextTagsKeys();
			testKeys.addKey(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			//ok
		}
	}

	/**
	 * Testing adding empty tag key
	 */
	@Test
	public void addingEmptyKeyTest()
	{
		try
		{
			TextTagsKeys testKeys = new TextTagsKeys();
			testKeys.addKey("");
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			//ok
		}
	}

	/**
	 * Testing addKey method normal work
	 */
	@Test
	public void addingKeyNormalWorkTest()
	{
		String addingKey = "someKey";

		TextTagsKeys testKeys = new TextTagsKeys();
		testKeys.removeAllKeys();
		testKeys.addKey(addingKey);

		assertEquals(addingKey, testKeys.getKey(testKeys.getKeysCount() - 1));
	}

	/**
	 * Testing getKeysCount method
	 */
	@Test
	public void getKeyCountTest()
	{
		TextTagsKeys testKeys = new TextTagsKeys();
		testKeys.removeAllKeys();
		testKeys.addKey("k1");
		testKeys.addKey("k2");
		
		assertEquals(2, testKeys.getKeysCount());
	}

	/**
	 * Testing getKey method by index more than bounds
	 */
	@Test
	public void getKeyByIndexMoreThanBoundsTest()
	{
		try
		{
			TextTagsKeys testKeys = new TextTagsKeys();
			testKeys.removeAllKeys();
			testKeys.addKey("k1");
			testKeys.addKey("k2");

			testKeys.getKey(testKeys.getKeysCount());
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Testing getKey method by index less than bounds
	 */
	@Test
	public void getKeyByIndexLessThanBoundsTest()
	{
		try
		{
			TextTagsKeys testKeys = new TextTagsKeys();
			testKeys.removeAllKeys();
			testKeys.addKey("k1");
			testKeys.addKey("k2");

			testKeys.getKey(-1);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test finding text in null tags
	 */
	@Test
	public void foundTextInNullTagsTest()
	{
		try
		{
			TextTagsKeys textTagsKeys = new TextTagsKeys();
			assertEquals("", textTagsKeys.findTextInTags(null));
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test finding text in empty tags
	 */
	@Test
	public void foundTextInEmptyTagsTest()
	{
		TextTagsKeys textTagsKeys = new TextTagsKeys();
		assertEquals("", textTagsKeys.findTextInTags(new DefenitionTags()));
	}

	/**
	 * Test finding text in empty tags
	 */
	@Test
	public void foundTextWithEmptyKeysTest()
	{
		TextTagsKeys textTagsKeys = new TextTagsKeys();
		textTagsKeys.removeAllKeys();

		EditableDefenitionTags tags = new EditableDefenitionTags();
		tags.add(new Tag("name", "testName"));
		tags.add(new Tag("highway", "residential"));

		assertEquals("", textTagsKeys.findTextInTags(tags));
	}

	/**
	 * Test keys priority in text finding. Priority determines by key index
	 */
	@Test
	public void foundTextKeysPriorityTest()
	{
		TextTagsKeys textTagsKeys = new TextTagsKeys();
		textTagsKeys.removeAllKeys();
		textTagsKeys.addKey("description");
		textTagsKeys.addKey("name");

		EditableDefenitionTags tags = new EditableDefenitionTags();
		tags.add(new Tag("name", "testName"));
		tags.add(new Tag("description", "testDescription"));

		assertEquals("testDescription", textTagsKeys.findTextInTags(tags));
	}

	/**
	 * Test finding if no keys found in tags
	 */
	@Test
	public void foundTextNotFoundTest()
	{
		TextTagsKeys textTagsKeys = new TextTagsKeys();
		textTagsKeys.removeAllKeys();
		textTagsKeys.addKey("name");
		textTagsKeys.addKey("description");
		

		EditableDefenitionTags tags = new EditableDefenitionTags();
		tags.add(new Tag("highway", "residential"));
		tags.add(new Tag("source", "gps"));
		tags.add(new Tag("name", "testName"));

		assertEquals("testName", textTagsKeys.findTextInTags(tags));
	}

	/**
	 * Test finding often work
	 */
	@Test
	public void foundTextFoundingTest()
	{
		TextTagsKeys textTagsKeys = new TextTagsKeys();
		textTagsKeys.removeAllKeys();
		textTagsKeys.addKey("description");
		textTagsKeys.addKey("name");

		EditableDefenitionTags tags = new EditableDefenitionTags();
		tags.add(new Tag("highway", "residential"));
		tags.add(new Tag("source", "gps"));

		assertEquals("", textTagsKeys.findTextInTags(tags));
	}
	
	/**
	 * Remove all keys method test
	 */
	@Test
	public void removeAllKeysTest()
	{
		TextTagsKeys textTagsKeys = new TextTagsKeys();
		textTagsKeys.addKey("k1");
		textTagsKeys.addKey("k2");
		textTagsKeys.removeAllKeys();
		assertEquals(0, textTagsKeys.getKeysCount());
	}

	/**
	 * Reading/writing test
	 */
	@Test
	public void readingWritingTest()
	{
		try
		{
			TextTagsKeys writingTagsKeys = new TextTagsKeys();
			writingTagsKeys.removeAllKeys();
			writingTagsKeys.addKey("k1");
			writingTagsKeys.addKey("k2");
			
			IOTester.writeToTestFile(writingTagsKeys);

			TextTagsKeys readingTagsKeys = new TextTagsKeys();
			IOTester.readFromTestFile(readingTagsKeys);

			assertEquals(writingTagsKeys.getKeysCount(), readingTagsKeys.getKeysCount());
			for (int i = 0; i < writingTagsKeys.getKeysCount(); i++)
			{
				assertEquals(writingTagsKeys.getKey(i), readingTagsKeys.getKey(i));
			}
		}
		catch (Exception ex)
		{
			fail();
		}
	}
}
