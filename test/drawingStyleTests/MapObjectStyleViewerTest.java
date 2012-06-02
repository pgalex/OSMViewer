package drawingStyleTests;

import drawingStyle.DrawingStyleFactory;
import drawingStyle.MapObjectStyle;
import drawingStyle.StyleEditor;
import drawingStyle.StyleViewer;
import map.EditableDefenitionTags;
import map.MapTag;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * MapObjectStyleViewer (like StyleViewer ) tests
 * @author pgalex
 */
public class MapObjectStyleViewerTest
{
	/**
	 * Loading file with incorrect name
	 */
	@Test
	public void incorrectFileTest()
	{
		StyleViewer viewer = DrawingStyleFactory.createStyleViewer();
		try
		{
			viewer.readFromStream(null);
			fail();
		}
		catch (Exception ex)
		{
			// ok
		}
	}

	/**
	 * Reading/writing test
	 */
	@Test
	public void readingWritingTest()
	{
		try
		{
			MapObjectStyle style1 = new MapObjectStyle(true, true, false, null, 0, "style1", null, null);
			MapObjectStyle style2 = new MapObjectStyle(true, false, true, null, 0, "style2", null, null);
			MapObjectStyle style3 = new MapObjectStyle(false, true, true, null, 0, "style3", null, null);

			StyleEditor writedEditor = DrawingStyleFactory.createStyleEditor();
			writedEditor.add(style1);
			writedEditor.add(style2);
			writedEditor.add(style3);

			DrawingStyleIOTester.writeToTestFile(writedEditor);

			StyleViewer readViewer = DrawingStyleFactory.createStyleViewer();
			DrawingStyleIOTester.readFromTestFile(readViewer);

			assertEquals(writedEditor.getMapObjectStyle(0).getDescription(), readViewer.getMapObjectStyle(0).getDescription());
			assertEquals(writedEditor.getMapObjectStyle(1).getDescription(), readViewer.getMapObjectStyle(1).getDescription());
			assertEquals(writedEditor.getMapObjectStyle(2).getDescription(), readViewer.getMapObjectStyle(2).getDescription());

		}
		catch (Exception ex)
		{
			fail();
		}
	}

	/**
	 * Test getStyleIndex and getMapObjectStyle methods
	 */
	@Test
	public void getTest()
	{
		try
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

			MapObjectStyle style1 = new MapObjectStyle(true, true, true, null, 0, "style1", null, tags1);
			MapObjectStyle style2 = new MapObjectStyle(true, true, true, null, 0, "style2", null, tags2);
			MapObjectStyle style3 = new MapObjectStyle(true, true, true, null, 0, "style3", null, tags3);

			StyleEditor editor = DrawingStyleFactory.createStyleEditor();
			editor.add(style1);
			editor.add(style2);
			editor.add(style3);

			DrawingStyleIOTester.writeToTestFile(editor);

			StyleViewer viewer = DrawingStyleFactory.createStyleViewer();
			DrawingStyleIOTester.readFromTestFile(viewer);

			// normal work
			assertEquals(0, (int) viewer.getStyleIndex(tags1));
			assertEquals(1, (int) viewer.getStyleIndex(tags2));
			assertEquals(2, (int) viewer.getStyleIndex(tags3));

			// not exists
			EditableDefenitionTags testTags = new EditableDefenitionTags();
			testTags.add(new MapTag("k9", "v9"));
			assertNull(viewer.getStyleIndex(testTags));
			// null
			assertNull(viewer.getStyleIndex(null));

			// getMapObjectStyle
			assertEquals(style1.getDescription(), viewer.getMapObjectStyle(0).getDescription());
			assertEquals(style2.getDescription(), viewer.getMapObjectStyle(1).getDescription());
			assertEquals(style3.getDescription(), viewer.getMapObjectStyle(2).getDescription());
			// not exists
			assertNull(viewer.getMapObjectStyle(-1));
			assertNull(viewer.getMapObjectStyle(null));
		}
		catch (Exception ex)
		{
			fail();
		}
	}
}
