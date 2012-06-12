package drawingStyleTests;

import IOTesting.IOTester;
import drawingStyle.*;
import java.awt.Color;
import map.EditableDefenitionTags;
import map.MapTag;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * MapObjectStyleViewer (like StyleViewer ) tests
 *
 * @author pgalex
 */
public class MapObjectStyleViewerTest
{
	/**
	 * Initializing fields of StyleProcessor test
	 */
	@Test
	public void initializingStyleProcessorTest()
	{
		StyleViewer testViewer = DrawingStyleFactory.createStyleViewer();
		assertNotNull(testViewer.getMapDrawingSettings());
	}

	/**
	 * Loading form null stream
	 */
	@Test
	public void readingFromNullStreamTest()
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
			writedEditor.addMapObjectStyle(style1);
			writedEditor.addMapObjectStyle(style2);
			writedEditor.addMapObjectStyle(style3);
			writedEditor.setMapDrawingSettings(new MapDrawingSettings(new IOColor(Color.red)));

			IOTester.writeToTestFile(writedEditor);

			StyleViewer readViewer = DrawingStyleFactory.createStyleViewer();
			IOTester.readFromTestFile(readViewer);

			assertEquals(writedEditor.getMapObjectStyle(0).getDescription(), readViewer.getMapObjectStyle(0).getDescription());
			assertEquals(writedEditor.getMapObjectStyle(1).getDescription(), readViewer.getMapObjectStyle(1).getDescription());
			assertEquals(writedEditor.getMapObjectStyle(2).getDescription(), readViewer.getMapObjectStyle(2).getDescription());
			assertEquals(writedEditor.getMapDrawingSettings().getMapBackgroundColor().getColor(), 
							readViewer.getMapDrawingSettings().getMapBackgroundColor().getColor());
		}
		catch (Exception ex)
		{
			fail();
		}
	}

	/**
	 * Test getStyleIndex - founding index by tags
	 */
	@Test
	public void getStyleIndexWorkTest()
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
			editor.addMapObjectStyle(style1);
			editor.addMapObjectStyle(style2);
			editor.addMapObjectStyle(style3);

			IOTester.writeToTestFile(editor);

			StyleViewer viewer = DrawingStyleFactory.createStyleViewer();
			IOTester.readFromTestFile(viewer);

			assertEquals(0, (int) viewer.getStyleIndex(tags1));
			assertEquals(1, (int) viewer.getStyleIndex(tags2));
			assertEquals(2, (int) viewer.getStyleIndex(tags3));
		}
		catch (Exception ex)
		{
			fail();
		}
	}

	/**
	 * Testing getStyleIndex method if style with given tags not exists
	 */
	@Test
	public void getStyleIndexNotFoundTest()
	{
		try
		{
			StyleViewer viewer = DrawingStyleFactory.createStyleViewer();

			EditableDefenitionTags testTags = new EditableDefenitionTags();
			testTags.add(new MapTag("k9", "v9"));
			assertNull(viewer.getStyleIndex(testTags));
		}
		catch (Exception ex)
		{
			fail();
		}
	}

	/**
	 * Testing getStyleIndex method by null tags
	 */
	@Test
	public void getStyleIndexByNullTagsTest()
	{
		try
		{
			StyleViewer viewer = DrawingStyleFactory.createStyleViewer();

			assertNull(viewer.getStyleIndex(null));
		}
		catch (Exception ex)
		{
			fail();
		}
	}

	/**
	 * Testing getMapObjectStyle in normal work
	 */
	@Test
	public void getMapObjectStyleNormalWorkTest()
	{
		try
		{
			// for test normal work need to save some styles
			EditableDefenitionTags tags1 = new EditableDefenitionTags();
			tags1.add(new MapTag("k1", "v1"));
			MapObjectStyle style1 = new MapObjectStyle(true, true, true, null, 0, "style1", null, tags1);

			EditableDefenitionTags tags2 = new EditableDefenitionTags();
			tags2.add(new MapTag("k2", "v2"));
			MapObjectStyle style2 = new MapObjectStyle(true, true, true, null, 0, "style2", null, tags2);

			StyleEditor editor = DrawingStyleFactory.createStyleEditor();
			editor.addMapObjectStyle(style1);
			editor.addMapObjectStyle(style2);
			IOTester.writeToTestFile(editor);

			StyleViewer viewer = DrawingStyleFactory.createStyleViewer();
			IOTester.readFromTestFile(viewer);

			assertEquals(style1.getDescription(), viewer.getMapObjectStyle(0).getDescription());
			assertEquals(style2.getDescription(), viewer.getMapObjectStyle(1).getDescription());
		}
		catch (Exception ex)
		{
			fail();
		}
	}

	/**
	 * Testing getMapObjectStyle by index not assisiated with style
	 */
	@Test
	public void getMapObjectStyleNotExistsTest()
	{
		try
		{
			StyleViewer viewer = DrawingStyleFactory.createStyleViewer();

			assertNull(viewer.getMapObjectStyle(-1));
			assertNull(viewer.getMapObjectStyle(0));
			assertNull(viewer.getMapObjectStyle(1));
		}
		catch (Exception ex)
		{
			fail();
		}
	}

	/**
	 * Testing getMapObjectStyle by null index
	 */
	@Test
	public void getMapObjectStyleByNullIndexTest()
	{
		try
		{
			StyleViewer viewer = DrawingStyleFactory.createStyleViewer();

			assertNull(viewer.getMapObjectStyle(null));
		}
		catch (Exception ex)
		{
			fail();
		}
	}
}
