package drawingStyleTests;

import drawingStyle.MapObjectStyle;
import drawingStyle.StyleProcessor;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import map.EditableDefenitionTags;
import map.MapTag;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Tests of StyleProcessor class
 *
 * @author pgalex
 */
public class StyleProcessorTest
{
	/**
	 * findStyleIndex test with incorrect parameters
	 */
	@Test
	public void FindStyleIndexIncorrectParametersTest()
	{
		assertNull(StyleProcessor.findStyleIndex(null, new EditableDefenitionTags()));
		assertNull(StyleProcessor.findStyleIndex(new MapObjectStyle[0], null));
		assertNull(StyleProcessor.findStyleIndex(new MapObjectStyle[0], new EditableDefenitionTags()));
	}

	/**
	 * findStyleIndex normal work test
	 */
	@Test
	public void findStyleIndexNormalWorkTest()
	{
		EditableDefenitionTags tags1 = new EditableDefenitionTags();
		tags1.add(new MapTag("k1", "v1"));
		tags1.add(new MapTag("k2", "v2"));

		EditableDefenitionTags tags2 = new EditableDefenitionTags();
		tags2.add(new MapTag("k1", "v1"));

		EditableDefenitionTags tags3 = new EditableDefenitionTags();
		tags3.add(new MapTag("k1", "v1"));
		tags3.add(new MapTag("k2", "v2"));
		tags3.add(new MapTag("k3", "v3"));
		tags3.add(new MapTag("k4", "v4"));

		MapObjectStyle[] styles = new MapObjectStyle[5];
		styles[0] = new MapObjectStyle(true, true, true, null, 0, "style1", null, tags1);
		styles[1] = new MapObjectStyle(true, true, true, null, 0, "style2", null, tags2);
		styles[2] = new MapObjectStyle(true, true, true, null, 0, "style3", null, tags3);
		styles[3] = new MapObjectStyle(true, true, true, null, 0, "style4", null, null);

		EditableDefenitionTags objectTags1 = new EditableDefenitionTags();
		objectTags1.add(new MapTag("k1", "v1"));
		assertEquals(1, (int) StyleProcessor.findStyleIndex(styles, objectTags1));

		EditableDefenitionTags objectTags2 = new EditableDefenitionTags();
		objectTags2.add(new MapTag("k1", "v1"));
		objectTags2.add(new MapTag("k5", "v5"));
		assertEquals(1, (int) StyleProcessor.findStyleIndex(styles, objectTags2));

		EditableDefenitionTags objectTags3 = new EditableDefenitionTags();
		objectTags3.add(new MapTag("k1", "v1"));
		objectTags3.add(new MapTag("k5", "v5"));
		objectTags3.add(new MapTag("k2", "v2"));
		assertEquals(0, (int) StyleProcessor.findStyleIndex(styles, objectTags3));

		EditableDefenitionTags objectTags4 = new EditableDefenitionTags();
		objectTags4.add(new MapTag("k1", "v1"));
		objectTags4.add(new MapTag("k3", "v3"));
		objectTags4.add(new MapTag("k5", "v5"));
		objectTags4.add(new MapTag("k4", "v4"));
		objectTags4.add(new MapTag("k2", "v2"));
		assertEquals(2, (int) StyleProcessor.findStyleIndex(styles, objectTags4));

		assertEquals(3, (int) StyleProcessor.findStyleIndex(styles, new EditableDefenitionTags()));

		EditableDefenitionTags objectTags5 = new EditableDefenitionTags();
		objectTags5.add(new MapTag("k5", "v5"));
		assertNull(StyleProcessor.findStyleIndex(styles, objectTags5));
	}

	/**
	 * Reading/writing test 0 styles count
	 */
	@Test
	public void nullLengthReadingWritingTest()
	{
		try
		{
			MapObjectStyle[] writingStyles = new MapObjectStyle[0];

			DataOutputStream output = new DataOutputStream(new FileOutputStream(DrawingStyleIOTester.TEST_FILE_NAME));
			StyleProcessor.writeStylesToStream(writingStyles, output);
			output.close();

			DataInputStream input = new DataInputStream(new FileInputStream(DrawingStyleIOTester.TEST_FILE_NAME));
			MapObjectStyle[] readingStyles = StyleProcessor.readStylesFromStream(input);
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
	@Test
	public void incorrectFileTest()
	{
		// null objects in array
		try
		{
			StyleProcessor.writeStylesToStream(new MapObjectStyle[2], new DataOutputStream(new FileOutputStream(DrawingStyleIOTester.TEST_FILE_NAME)));
			fail();
		}
		catch (Exception e)
		{
			// ok
		}

		try
		{
			StyleProcessor.writeStylesToStream(null, null);
			fail();
		}
		catch (Exception e)
		{
			// ok
		}

		try
		{
			StyleProcessor.readStylesFromStream(null);
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
	@Test
	public void normalFileTest()
	{
		try
		{
			MapObjectStyle[] writingStyles = new MapObjectStyle[2];
			writingStyles[0] = new MapObjectStyle(true, true, true, null, 0, "style1", null, null);
			writingStyles[1] = new MapObjectStyle(true, true, true, null, 0, "style2", null, null);

			DataOutputStream output = new DataOutputStream(new FileOutputStream(DrawingStyleIOTester.TEST_FILE_NAME));
			StyleProcessor.writeStylesToStream(writingStyles, output);
			output.close();

			DataInputStream input = new DataInputStream(new FileInputStream(DrawingStyleIOTester.TEST_FILE_NAME));
			MapObjectStyle[] readingStyles = StyleProcessor.readStylesFromStream(input);
			input.close();

			assertEquals(writingStyles.length, readingStyles.length);
			for (int i = 0; i < readingStyles.length; i++)
				assertEquals(writingStyles[i].getDescription(), readingStyles[i].getDescription());
		}
		catch (Exception ex)
		{
			fail();
		}
	}
}