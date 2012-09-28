package drawingStylesTests;

import IOTesting.IOTester;
import drawingStyles.DefenitionTags;
import drawingStyles.EditableDefenitionTags;
import drawingStyles.MapTag;
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
	 * Creating with null keys
	 */
	@Test
	public void creatingWithNullKeysTest()
	{
		try
		{
			TextTagsKeys testKeys1 = new TextTagsKeys(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

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
		testKeys.addKey(addingKey);

		assertEquals(addingKey, testKeys.getKey(testKeys.getKeysCount() - 1));
	}

	/**
	 * Testing getKeysCount method
	 */
	@Test
	public void getKeyCountTest()
	{
		String[] keys =
		{
			"k1", "k2"
		};
		TextTagsKeys testKeys = new TextTagsKeys(keys);
		assertEquals(keys.length, testKeys.getKeysCount());
	}

	/**
	 * Testing getKey method by index more than bounds
	 */
	@Test
	public void getKeyByIndexMoreThanBoundsTest()
	{
		try
		{
			String[] keys =
			{
				"k1", "k2"
			};
			TextTagsKeys testKeys = new TextTagsKeys(keys);

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
			String[] keys =
			{
				"k1", "k2"
			};
			TextTagsKeys testKeys = new TextTagsKeys(keys);

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
		String[] keys = new String[0];
		TextTagsKeys textTagsKeys = new TextTagsKeys(keys);

		EditableDefenitionTags tags = new EditableDefenitionTags();
		tags.add(new MapTag("name", "testName"));
		tags.add(new MapTag("highway", "residential"));

		assertEquals("", textTagsKeys.findTextInTags(tags));
	}

	/**
	 * Test keys priority in text finding. Priority determines by key index
	 */
	@Test
	public void foundTextKeysPriorityTest()
	{
		String[] keys = new String[2];
		keys[0] = "description";
		keys[1] = "name";
		TextTagsKeys textTagsKeys = new TextTagsKeys(keys);

		EditableDefenitionTags tags = new EditableDefenitionTags();
		tags.add(new MapTag("name", "testName"));
		tags.add(new MapTag("description", "testDescription"));

		assertEquals("testDescription", textTagsKeys.findTextInTags(tags));
	}

	/**
	 * Test finding if no keys found in tags
	 */
	@Test
	public void foundTextNotFoundTest()
	{
		String[] keys = new String[2];
		keys[0] = "name";
		keys[1] = "description";
		TextTagsKeys textTagsKeys = new TextTagsKeys(keys);

		EditableDefenitionTags tags = new EditableDefenitionTags();
		tags.add(new MapTag("highway", "residential"));
		tags.add(new MapTag("source", "gps"));
		tags.add(new MapTag("name", "testName"));

		assertEquals("testName", textTagsKeys.findTextInTags(tags));
	}

	/**
	 * Test finding often work
	 */
	@Test
	public void foundTextFoundingTest()
	{
		String[] keys = new String[2];
		keys[0] = "description";
		keys[1] = "name";
		TextTagsKeys textTagsKeys = new TextTagsKeys(keys);

		EditableDefenitionTags tags = new EditableDefenitionTags();
		tags.add(new MapTag("highway", "residential"));
		tags.add(new MapTag("source", "gps"));

		assertEquals("", textTagsKeys.findTextInTags(tags));
	}

	/**
	 * Reading/writing test
	 */
	@Test
	public void readingWritingTest()
	{
		try
		{
			String[] keys =
			{
				"k1", "k2"
			};
			TextTagsKeys writingTagsKeys = new TextTagsKeys(keys);
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
