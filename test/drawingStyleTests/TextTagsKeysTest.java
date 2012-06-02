package drawingStyleTests;

import drawingStyle.TextTagsKeys;
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
	 * Test auto initialize in contructor
	 */
	@Test
	public void autoInitializeTest()
	{
		TextTagsKeys testKeys1 = new TextTagsKeys(null);
		assertNotNull(testKeys1.getTagsKeys());

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

			DrawingStyleIOTester.writeToTestFile(writingTagsKeys);

			TextTagsKeys readingTagsKeys = new TextTagsKeys();
			DrawingStyleIOTester.readFromTestFile(readingTagsKeys);

			assertArrayEquals(writingTagsKeys.getTagsKeys(), readingTagsKeys.getTagsKeys());
		}
		catch (Exception ex)
		{
			fail();
		}
	}
}
