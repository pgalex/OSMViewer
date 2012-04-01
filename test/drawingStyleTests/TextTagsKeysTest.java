package drawingStyleTests;

import drawingStyle.LinePattern;
import drawingStyle.TextTagsKeys;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author pgalex
 */
public class TextTagsKeysTest
{
	private final String TEST_FILE_NAME = "testFile.txt";

	public TextTagsKeysTest()
	{
	}

	/**
	 * Test auto initialize in contructor
	 */
	@Test
	public void autoInitializeTest()
	{
		TextTagsKeys testKeys1 = new TextTagsKeys(null);
		assertNotNull(testKeys1);

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
			DataOutputStream output = new DataOutputStream(new FileOutputStream(TEST_FILE_NAME));
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
			DataInputStream input = new DataInputStream(new FileInputStream(TEST_FILE_NAME));
			readingTagsKeys.readFromStream(input);
			input.close();
			assertArrayEquals(writingTagsKeys.getTagsKeys(), readingTagsKeys.getTagsKeys());
		}
		catch (Exception ex)
		{
			fail();
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
