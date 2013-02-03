package drawingStylesTests;

import IOTesting.IOTester;
import mapDefenitionUtilities.DefenitionTags;
import drawingStyles.MapObjectDrawSettings;
import drawingStyles.MapObjectDrawStylesContainer;
import mapDefenitionUtilities.Tag;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import static org.junit.Assert.*;

/**
 * Using for testing protected methods of MapObjectDrawStylesContainer
 *
 * @author pgalex
 */
public class TestMapObjectDrawStylesContainer extends MapObjectDrawStylesContainer
{
	/**
	 * Run tests of protected methods of MapObjectDrawStylesContainer
	 */
	public void runProtectedMethodsTests()
	{
		findStyleIndexIncorrectParametersTest();
		findStyleIndexNormalWorkTest();
		nullLengthReadingWritingTest();
		incorrectFileTest();
		normalFileTest();
	}
	
	/**
	 * findStyleIndex test with incorrect parameters
	 */
	private void findStyleIndexIncorrectParametersTest()
	{
		assertNull(findStyleIndex(null, new DefenitionTags()));
		assertNull(findStyleIndex(new MapObjectDrawSettings[0], null));
		assertNull(findStyleIndex(new MapObjectDrawSettings[0], new DefenitionTags()));
	}

	/**
	 * findStyleIndex normal work test
	 */
	private void findStyleIndexNormalWorkTest()
	{
		DefenitionTags tags1 = new DefenitionTags();
		tags1.add(new Tag("k1", "v1"));
		tags1.add(new Tag("k2", "v2"));

		DefenitionTags tags2 = new DefenitionTags();
		tags2.add(new Tag("k1", "v1"));

		DefenitionTags tags3 = new DefenitionTags();
		tags3.add(new Tag("k1", "v1"));
		tags3.add(new Tag("k2", "v2"));
		tags3.add(new Tag("k3", "v3"));
		tags3.add(new Tag("k4", "v4"));
		
		MapObjectDrawSettings style1 = new MapObjectDrawSettings();
		style1.setDescription("style1");
		style1.setDefenitionTags(tags1);
		
		MapObjectDrawSettings style2 = new MapObjectDrawSettings();
		style2.setDescription("style2");
		style2.setDefenitionTags(tags2);
		
		MapObjectDrawSettings style3 = new MapObjectDrawSettings();
		style3.setDescription("style3");
		style3.setDefenitionTags(tags3);
		
		MapObjectDrawSettings style4 = new MapObjectDrawSettings();
		style4.setDescription("style4");

		MapObjectDrawSettings[] styles = new MapObjectDrawSettings[5];
		styles[0] = style1;
		styles[1] = style2;
		styles[2] = style3;
		styles[3] = style4;

		DefenitionTags objectTags1 = new DefenitionTags();
		objectTags1.add(new Tag("k1", "v1"));
		assertEquals(1, (int) findStyleIndex(styles, objectTags1));

		DefenitionTags objectTags2 = new DefenitionTags();
		objectTags2.add(new Tag("k1", "v1"));
		objectTags2.add(new Tag("k5", "v5"));
		assertEquals(1, (int) findStyleIndex(styles, objectTags2));

		DefenitionTags objectTags3 = new DefenitionTags();
		objectTags3.add(new Tag("k1", "v1"));
		objectTags3.add(new Tag("k5", "v5"));
		objectTags3.add(new Tag("k2", "v2"));
		assertEquals(0, (int) findStyleIndex(styles, objectTags3));

		DefenitionTags objectTags4 = new DefenitionTags();
		objectTags4.add(new Tag("k1", "v1"));
		objectTags4.add(new Tag("k3", "v3"));
		objectTags4.add(new Tag("k5", "v5"));
		objectTags4.add(new Tag("k4", "v4"));
		objectTags4.add(new Tag("k2", "v2"));
		assertEquals(2, (int) findStyleIndex(styles, objectTags4));

		assertEquals(3, (int) findStyleIndex(styles, new DefenitionTags()));

		DefenitionTags objectTags5 = new DefenitionTags();
		objectTags5.add(new Tag("k5", "v5"));
		assertNull(findStyleIndex(styles, objectTags5));
	}

	/**
	 * Reading/writing test 0 styles count
	 */
	private void nullLengthReadingWritingTest()
	{
		try
		{
			MapObjectDrawSettings[] writingStyles = new MapObjectDrawSettings[0];

			DataOutputStream output = new DataOutputStream(new FileOutputStream(IOTester.TEST_FILE_NAME));
			writeStylesToStream(writingStyles, output);
			output.close();

			DataInputStream input = new DataInputStream(new FileInputStream(IOTester.TEST_FILE_NAME));
			MapObjectDrawSettings[] readingStyles = readStylesFromStream(input);
			input.close();

			assertEquals(writingStyles.length, readingStyles.length);
		}
		catch (Exception ex)
		{
			fail();
		}
	}

	/**
	 * Reading/writing test with incorrect data
	 */
	private void incorrectFileTest()
	{
		// null objects in array
		try
		{
			writeStylesToStream(new MapObjectDrawSettings[2], new DataOutputStream(new FileOutputStream(IOTester.TEST_FILE_NAME)));
			fail();
		}
		catch (Exception e)
		{
			// ok
		}

		try
		{
			writeStylesToStream(null, null);
			fail();
		}
		catch (Exception e)
		{
			// ok
		}

		try
		{
			readStylesFromStream(null);
			fail();
		}
		catch (Exception e)
		{
			// ok
		}
	}

	/**
	 * Normal reading/writing test
	 */
	private void normalFileTest()
	{
		try
		{
			MapObjectDrawSettings[] writingStyles = new MapObjectDrawSettings[2];
			
			MapObjectDrawSettings style1 = new MapObjectDrawSettings();
			style1.setDescription("style1");
			
			MapObjectDrawSettings style2 = new MapObjectDrawSettings();
			style2.setDescription("style2");
			
			writingStyles[0] = style1;
			writingStyles[1] = style2;

			DataOutputStream output = new DataOutputStream(new FileOutputStream(IOTester.TEST_FILE_NAME));
			writeStylesToStream(writingStyles, output);
			output.close();

			DataInputStream input = new DataInputStream(new FileInputStream(IOTester.TEST_FILE_NAME));
			MapObjectDrawSettings[] readingStyles = readStylesFromStream(input);
			input.close();

			assertEquals(writingStyles.length, readingStyles.length);
			for (int i = 0; i < readingStyles.length; i++)
			{
				assertEquals(writingStyles[i].getDescription(), readingStyles[i].getDescription());
			}
		}
		catch (Exception ex)
		{
			fail();
		}
	}
}
