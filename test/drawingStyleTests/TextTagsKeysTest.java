package drawingStyleTests;

import drawingStyle.TextTagsKeys;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import static org.junit.Assert.*;
import org.junit.Test;

/**
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
	public void defaultConstructorTest()
	{
		TextTagsKeys testKeys = new TextTagsKeys();
		assertNotNull(testKeys.getTagsKeys());
	}

	/**
	 * Reading/writing test
	 */
	@Test
	public void fileTest()
	{
		String[] keys =
		{
			"k1", "k2"
		};
		TextTagsKeys writingTagsKeys = new TextTagsKeys(keys);
		try
		{
			DataOutputStream output = new DataOutputStream(new FileOutputStream(DrawingStyleTestsParameters.TEST_FILE_NAME));
			writingTagsKeys.writeToStream(output);
			output.close();
		}
		catch (Exception ex)
		{
			fail();
		}

		TextTagsKeys readingTagsKeys = new TextTagsKeys();
		try
		{
			DataInputStream input = new DataInputStream(new FileInputStream(DrawingStyleTestsParameters.TEST_FILE_NAME));
			readingTagsKeys.readFromStream(input);
			input.close();
			assertArrayEquals(writingTagsKeys.getTagsKeys(), readingTagsKeys.getTagsKeys());
		}
		catch (Exception ex)
		{
			fail();
		}
	}
}
