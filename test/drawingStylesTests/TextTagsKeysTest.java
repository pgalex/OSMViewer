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
	 * Test auto initialize in contructor with null array
	 */
	@Test
	public void autoInitializeByNullTest()
	{
		TextTagsKeys testKeys1 = new TextTagsKeys(null);
		assertNotNull(testKeys1.getTagsKeys());
	}

	/**
	 * Test auto initialize in contructor with empty array
	 */
	@Test
	public void autoInitializeByEmptyTest()
	{
		TextTagsKeys testKeys2 = new TextTagsKeys(new String[0]);
		assertEquals(0, testKeys2.getTagsKeys().length);
	}

	/**
	 * Default constructor test
	 */
	@Test
	public void defaultConstructorInitializeTest()
	{
		TextTagsKeys testKeys = new TextTagsKeys();
		assertNotNull(testKeys.getTagsKeys());
	}

	/**
	 * Test finding text in null tags
	 */
	@Test
	public void foundTextInNullTagsTest()
	{
		TextTagsKeys textTagsKeys = new TextTagsKeys();
		assertEquals("", textTagsKeys.findTextInTags(null));
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

			assertArrayEquals(writingTagsKeys.getTagsKeys(), readingTagsKeys.getTagsKeys());
		}
		catch (Exception ex)
		{
			fail();
		}
	}
}
