package drawingStylesTests;

import drawingStyles.MapObjectDrawSettings;
import drawingStyles.MapDrawSettings;
import drawingStyles.IOColor;
import drawingStyles.StyleViewer;
import drawingStyles.DrawingStylesFactory;
import drawingStyles.StyleEditor;
import IOTesting.IOTester;
import java.awt.Color;
import drawingStyles.EditableDefenitionTags;
import drawingStyles.MapTag;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * MapObjectStyleViewer (like StyleViewer ) tests
 *
 * @author pgalex
 */
public class MapObjectDrawStylesViewerTest
{
	/**
	 * Initializing all fields test
	 */
	@Test
	public void initializingTest()
	{
		StyleViewer testViewer = DrawingStylesFactory.createStyleViewer();
		assertNotNull(testViewer.getMapDrawSettings());
	}

	/**
	 * Loading form null stream
	 */
	@Test
	public void readingFromNullStreamTest()
	{
		StyleViewer viewer = DrawingStylesFactory.createStyleViewer();
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
			MapObjectDrawSettings style1 = new MapObjectDrawSettings(true, true, false, null, 0, "style1", null, null);
			MapObjectDrawSettings style2 = new MapObjectDrawSettings(true, false, true, null, 0, "style2", null, null);
			MapObjectDrawSettings style3 = new MapObjectDrawSettings(false, true, true, null, 0, "style3", null, null);

			StyleEditor writedEditor = DrawingStylesFactory.createStyleEditor();
			writedEditor.addMapObjectDrawSettings(style1);
			writedEditor.addMapObjectDrawSettings(style2);
			writedEditor.addMapObjectDrawSettings(style3);
			writedEditor.setMapDrawSettings(new MapDrawSettings(new IOColor(Color.red)));

			IOTester.writeToTestFile(writedEditor);

			StyleViewer readViewer = DrawingStylesFactory.createStyleViewer();
			IOTester.readFromTestFile(readViewer);

			assertEquals(writedEditor.getMapObjectDrawSettings(0).getDescription(), readViewer.findMapObjectDrawStyle(0).getDescription());
			assertEquals(writedEditor.getMapObjectDrawSettings(1).getDescription(), readViewer.findMapObjectDrawStyle(1).getDescription());
			assertEquals(writedEditor.getMapObjectDrawSettings(2).getDescription(), readViewer.findMapObjectDrawStyle(2).getDescription());
			assertEquals(writedEditor.getMapDrawSettings().getMapBackgroundColor().getColor(), 
							readViewer.getMapDrawSettings().getMapBackgroundColor().getColor());
		}
		catch (Exception ex)
		{
			fail();
		}
	}

	/**
	 * Test findStyleIndex - founding index by tags
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

			MapObjectDrawSettings style1 = new MapObjectDrawSettings(true, true, true, null, 0, "style1", null, tags1);
			MapObjectDrawSettings style2 = new MapObjectDrawSettings(true, true, true, null, 0, "style2", null, tags2);
			MapObjectDrawSettings style3 = new MapObjectDrawSettings(true, true, true, null, 0, "style3", null, tags3);

			StyleEditor editor = DrawingStylesFactory.createStyleEditor();
			editor.addMapObjectDrawSettings(style1);
			editor.addMapObjectDrawSettings(style2);
			editor.addMapObjectDrawSettings(style3);

			IOTester.writeToTestFile(editor);

			StyleViewer viewer = DrawingStylesFactory.createStyleViewer();
			IOTester.readFromTestFile(viewer);

			assertEquals(0, (int) viewer.findStyleIndex(tags1));
			assertEquals(1, (int) viewer.findStyleIndex(tags2));
			assertEquals(2, (int) viewer.findStyleIndex(tags3));
		}
		catch (Exception ex)
		{
			fail();
		}
	}

	/**
	 * Testing findStyleIndex method if style with given tags not exists
	 */
	@Test
	public void getStyleIndexNotFoundTest()
	{
		try
		{
			StyleViewer viewer = DrawingStylesFactory.createStyleViewer();

			EditableDefenitionTags testTags = new EditableDefenitionTags();
			testTags.add(new MapTag("k9", "v9"));
			assertNull(viewer.findStyleIndex(testTags));
		}
		catch (Exception ex)
		{
			fail();
		}
	}

	/**
	 * Testing findStyleIndex method by null tags
	 */
	@Test
	public void getStyleIndexByNullTagsTest()
	{
		try
		{
			StyleViewer viewer = DrawingStylesFactory.createStyleViewer();

			assertNull(viewer.findStyleIndex(null));
		}
		catch (Exception ex)
		{
			fail();
		}
	}

	/**
	 * Testing getMapObjectDrawSettings in normal work
	 */
	@Test
	public void getMapObjectStyleNormalWorkTest()
	{
		try
		{
			// for test normal work need to save some styles
			EditableDefenitionTags tags1 = new EditableDefenitionTags();
			tags1.add(new MapTag("k1", "v1"));
			MapObjectDrawSettings style1 = new MapObjectDrawSettings(true, true, true, null, 0, "style1", null, tags1);

			EditableDefenitionTags tags2 = new EditableDefenitionTags();
			tags2.add(new MapTag("k2", "v2"));
			MapObjectDrawSettings style2 = new MapObjectDrawSettings(true, true, true, null, 0, "style2", null, tags2);

			StyleEditor editor = DrawingStylesFactory.createStyleEditor();
			editor.addMapObjectDrawSettings(style1);
			editor.addMapObjectDrawSettings(style2);
			IOTester.writeToTestFile(editor);

			StyleViewer viewer = DrawingStylesFactory.createStyleViewer();
			IOTester.readFromTestFile(viewer);

			assertEquals(style1.getDescription(), viewer.findMapObjectDrawStyle(0).getDescription());
			assertEquals(style2.getDescription(), viewer.findMapObjectDrawStyle(1).getDescription());
		}
		catch (Exception ex)
		{
			fail();
		}
	}

	/**
	 * Testing getMapObjectDrawSettings by index not assisiated with style
	 */
	@Test
	public void getMapObjectStyleNotExistsTest()
	{
		try
		{
			StyleViewer viewer = DrawingStylesFactory.createStyleViewer();

			assertNull(viewer.findMapObjectDrawStyle(-1));
			assertNull(viewer.findMapObjectDrawStyle(0));
			assertNull(viewer.findMapObjectDrawStyle(1));
		}
		catch (Exception ex)
		{
			fail();
		}
	}

	/**
	 * Testing getMapObjectDrawSettings by null index
	 */
	@Test
	public void getMapObjectStyleByNullIndexTest()
	{
		try
		{
			StyleViewer viewer = DrawingStylesFactory.createStyleViewer();

			assertNull(viewer.findMapObjectDrawStyle(null));
		}
		catch (Exception ex)
		{
			fail();
		}
	}
}
