package drawingStyleTests;

import drawingStyle.DrawingStyleFactory;
import drawingStyle.MapObjectStyle;
import drawingStyle.StyleEditor;
import drawingStyle.StyleViewer;
import java.io.IOException;
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
public class MapObjectStyleViewerTest
{
	private final String TEST_FILE_NAME = "testFile.txt";

	public MapObjectStyleViewerTest()
	{
	}

	/**
	 * Loading file with incorrect name
	 */
	@Test
	public void incorrectFileTest()
	{
		StyleViewer viewer = new DrawingStyleFactory().createStyleViewer();
		try
		{
			viewer.loadFromFile(null);
			fail();
		}
		catch (Exception ex)
		{
			// ok
		}
	}

	/**
	 * Normal reading file
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
			writingEditor.saveToFile(TEST_FILE_NAME);
		}
		catch (IOException ex)
		{
			fail();
		}

		StyleViewer readingViewer = DrawingStyleFactory.createStyleViewer();
		try
		{
			readingViewer.loadFromFile(TEST_FILE_NAME);
		}
		catch (Exception ex)
		{
			fail();
		}

		assertEquals(writingEditor.getMapObjectStyle(0).getDescription(), readingViewer.getMapObjectStyle(0).getDescription());
		assertEquals(writingEditor.getMapObjectStyle(1).getDescription(), readingViewer.getMapObjectStyle(1).getDescription());
		assertEquals(writingEditor.getMapObjectStyle(2).getDescription(), readingViewer.getMapObjectStyle(2).getDescription());
	}
	

	/**
	 * Test getStyleIndex and getMapObjectStyle methods
	 */
	@Test
	public void getTest()
	{
		// for test normal work need to save some styles
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
		editor.add(style3);
		try
		{
			editor.saveToFile(TEST_FILE_NAME);
		}
		catch (IOException ex)
		{
			fail();
		}

		StyleViewer viewer = DrawingStyleFactory.createStyleViewer();
		try
		{
			viewer.loadFromFile(TEST_FILE_NAME);
		}
		catch (Exception ex)
		{
			fail();
		}

		// normal work
		assertEquals(0, viewer.getStyleIndex(tags1));
		assertEquals(1, viewer.getStyleIndex(tags2));
		assertEquals(2, viewer.getStyleIndex(tags3));

		// not exists
		try
		{
			EditableDefenitionTags testTags = new EditableDefenitionTags();
			testTags.add(new MapTag("k9", "v9"));
			viewer.getStyleIndex(testTags);
			fail();
		}
		catch (Exception ex)
		{
			// ok
		}
		// null
		try
		{
			viewer.getStyleIndex(null);
			fail();
		}
		catch (Exception ex)
		{
			// ok
		}

		// getMapObjectStyle
		assertEquals(style1.getDescription(), viewer.getMapObjectStyle(0).getDescription());
		assertEquals(style2.getDescription(), viewer.getMapObjectStyle(1).getDescription());
		assertEquals(style3.getDescription(), viewer.getMapObjectStyle(2).getDescription());
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
