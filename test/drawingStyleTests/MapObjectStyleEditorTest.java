package drawingStyleTests;

import drawingStyle.DrawingStyleFactory;
import drawingStyle.MapObjectStyle;
import drawingStyle.StyleEditor;
import java.io.*;
import map.EditableDefenitionTags;
import map.MapTag;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author pgalex
 */
public class MapObjectStyleEditorTest
{
	private final String TEST_FILE_NAME = "testFile.txt";

	public MapObjectStyleEditorTest()
	{
	}

	/**
	 * Test adding style
	 */
	@Test
	public void addTest()
	{
		MapObjectStyle style1 = new MapObjectStyle(true, true, false, "", 0, "style1", null, null);
		MapObjectStyle style2 = new MapObjectStyle(true, false, true, "", 0, "style2", null, null);
		MapObjectStyle style3 = new MapObjectStyle(false, true, true, "", 0, "style3", null, null);

		StyleEditor editor = DrawingStyleFactory.createStyleEditor();
		assertEquals(0, editor.count());

		editor.add(style1);
		assertEquals(1, editor.count());

		editor.add(style2);
		assertEquals(2, editor.count());

		editor.add(style3);
		assertEquals(3, editor.count());

		assertEquals(style1.getDescription(), editor.getMapObjectStyle(0).getDescription());
		assertEquals(style2.getDescription(), editor.getMapObjectStyle(1).getDescription());
		assertEquals(style3.getDescription(), editor.getMapObjectStyle(2).getDescription());

		try
		{
			editor.add(null);
			fail();
		}
		catch (NullPointerException ex)
		{
			// ok
		}
	}

	/**
	 * Test get method
	 */
	@Test
	public void getTest()
	{
		MapObjectStyle style1 = new MapObjectStyle(true, true, false, "", 0, "style1", null, null);
		MapObjectStyle style2 = new MapObjectStyle(true, false, true, "", 0, "style2", null, null);

		StyleEditor editor = DrawingStyleFactory.createStyleEditor();
		assertEquals(0, editor.count());

		editor.add(style1);
		editor.add(style2);
		assertEquals(2, editor.count());

		// normal work
		try
		{
			assertEquals(style1.getDescription(), editor.getMapObjectStyle(0).getDescription());
			assertEquals(style2.getDescription(), editor.getMapObjectStyle(1).getDescription());
		}
		catch (Exception ex)
		{
			fail();
		}

		// less
		assertNull(editor.getMapObjectStyle(-1));
		// more
		assertNull(editor.getMapObjectStyle(editor.count() + 1));
	}

	/**
	 * Testing set method
	 */
	@Test
	public void setTest()
	{
		MapObjectStyle style1 = new MapObjectStyle(true, true, false, "", 0, "style1", null, null);
		MapObjectStyle style2 = new MapObjectStyle(true, false, true, "", 0, "style2", null, null);

		StyleEditor editor = DrawingStyleFactory.createStyleEditor();
		editor.add(style1);
		assertEquals(1, editor.count());

		// normal work
		editor.set(0, style2);
		assertEquals(style2.getDescription(), editor.getMapObjectStyle(0).getDescription());

		// less
		try
		{
			editor.set(-1, style2);
			fail();
		}
		catch (ArrayIndexOutOfBoundsException ex)
		{
			//ok
		}
		// more
		try
		{
			editor.set(editor.count() + 1, style2);
			fail();
		}
		catch (ArrayIndexOutOfBoundsException ex)
		{
			//ok
		}
		// null
		try
		{
			editor.set(0, null);
			fail();
		}
		catch (NullPointerException ex)
		{
			//ok
		}
	}

	/**
	 * Test remove method
	 */
	@Test
	public void removeTest()
	{
		MapObjectStyle style1 = new MapObjectStyle(true, true, false, "", 0, "style1", null, null);
		MapObjectStyle style2 = new MapObjectStyle(true, false, true, "", 0, "style2", null, null);

		StyleEditor editor = DrawingStyleFactory.createStyleEditor();
		editor.add(style1);
		editor.add(style2);
		assertEquals(2, editor.count());

		//normal
		editor.remove(0);
		assertEquals(1, editor.count());
		assertEquals(style2.getDescription(), editor.getMapObjectStyle(0).getDescription());
		editor.remove(0);
		assertEquals(0, editor.count());
		// less
		try
		{
			editor.remove(-1);
			editor.remove(0);
			editor.remove(1);
			fail();
		}
		catch (ArrayIndexOutOfBoundsException ex)
		{
			// ok
		}
		// not exists
		try
		{
			editor.remove(0);
			fail();
		}
		catch (ArrayIndexOutOfBoundsException ex)
		{
			// ok
		}
		// more
		try
		{
			editor.remove(1);
			fail();
		}
		catch (ArrayIndexOutOfBoundsException ex)
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
		MapObjectStyle style1 = new MapObjectStyle(true, true, false, "", 0, "style1", null, null);
		MapObjectStyle style2 = new MapObjectStyle(true, false, true, "", 0, "style2", null, null);
		MapObjectStyle style3 = new MapObjectStyle(false, true, true, "", 0, "style3", null, null);

		StyleEditor writingEditor = DrawingStyleFactory.createStyleEditor();
		writingEditor.add(style1);
		writingEditor.add(style2);
		writingEditor.add(style3);
		try
		{
			DataOutputStream output = new DataOutputStream(new FileOutputStream(TEST_FILE_NAME));
			writingEditor.writeToStream(output);
			output.close();
		}
		catch (IOException ex)
		{
			fail();
		}

		StyleEditor readingEditor = DrawingStyleFactory.createStyleEditor();
		try
		{
			DataInputStream input = new DataInputStream(new FileInputStream(TEST_FILE_NAME));
			readingEditor.readFromStream(input);
			input.close();
		}
		catch (Exception ex)
		{
			fail();
		}

		assertEquals(writingEditor.count(), readingEditor.count());
		for (int i = 0; i < writingEditor.count(); i++)
			assertEquals(writingEditor.getMapObjectStyle(i).getDescription(), readingEditor.getMapObjectStyle(i).getDescription());
	}

	/**
	 * reading/writing test with incorrect parameters
	 */
	@Test
	public void nullFileNameFileTest()
	{
		StyleEditor writingEditor = DrawingStyleFactory.createStyleEditor();
		try
		{
			writingEditor.writeToStream(null);
			fail();
		}
		catch (IOException ex)
		{
		}

		StyleEditor readingEditor = DrawingStyleFactory.createStyleEditor();
		try
		{
			readingEditor.readFromStream(null);
			fail();
		}
		catch (Exception ex)
		{
		}

		assertEquals(writingEditor.count(), readingEditor.count());
		for (int i = 0; i < writingEditor.count(); i++)
			assertEquals(writingEditor.getMapObjectStyle(i).getDescription(), readingEditor.getMapObjectStyle(i).getDescription());
	}

	/**
	 * getStyleIndex test
	 */
	@Test
	public void getStyleIndexTest()
	{
		EditableDefenitionTags tags1 = new EditableDefenitionTags();
		tags1.add(new MapTag("k1", "v1"));
		tags1.add(new MapTag("k2", "v2"));
		tags1.add(new MapTag("k3", "v3"));
		tags1.add(new MapTag("k4", "v4"));

		EditableDefenitionTags tags2 = new EditableDefenitionTags();
		tags2.add(new MapTag("k1", "v1"));
		tags2.add(new MapTag("k2", "v2"));
		tags2.add(new MapTag("k5", "v5"));

		EditableDefenitionTags tags3 = new EditableDefenitionTags();
		tags3.add(new MapTag("k8", "v8"));

		MapObjectStyle style1 = new MapObjectStyle(true, true, true, "", 0, "style1", null, tags1);
		MapObjectStyle style2 = new MapObjectStyle(true, true, true, "", 0, "style2", null, tags2);
		MapObjectStyle style3 = new MapObjectStyle(true, true, true, "", 0, "style3", null, tags3);

		StyleEditor editor = DrawingStyleFactory.createStyleEditor();
		editor.add(style1);
		editor.add(style2);
		editor.add(style3); // there was auto sort

		// full equal
		assertEquals(0, editor.getStyleIndex(tags1));
		assertEquals(1, editor.getStyleIndex(tags2));
		assertEquals(2, editor.getStyleIndex(tags3));

		// part equal
		EditableDefenitionTags testTags1 = new EditableDefenitionTags();
		testTags1.add(new MapTag("k1", "v1"));
		testTags1.add(new MapTag("k2", "v2"));
		testTags1.add(new MapTag("k3", "v3"));
		testTags1.add(new MapTag("k4", "v4"));
		testTags1.add(new MapTag("k5", "v5"));
		testTags1.add(new MapTag("k6", "v6"));
		assertEquals(0, editor.getStyleIndex(testTags1));

		EditableDefenitionTags testTags2 = new EditableDefenitionTags();
		testTags2.add(new MapTag("k1", "v1"));
		testTags2.add(new MapTag("k2", "v2"));
		testTags2.add(new MapTag("k3", "v3"));
		testTags2.add(new MapTag("k5", "v5"));
		testTags2.add(new MapTag("k6", "v6"));
		assertEquals(1, editor.getStyleIndex(testTags2));

		// not found
		EditableDefenitionTags testTags3 = new EditableDefenitionTags();
		testTags3.add(new MapTag("k9", "v9"));
		try
		{
			editor.getStyleIndex(testTags3);
			fail();
		}
		catch (Exception ex)
		{
			//ok
		}
		// null
		try
		{
			editor.getStyleIndex(null);
			fail();
		}
		catch (Exception ex)
		{
			//ok
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
