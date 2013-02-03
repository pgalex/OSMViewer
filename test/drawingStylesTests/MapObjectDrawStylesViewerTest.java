package drawingStylesTests;

import IOTesting.IOTester;
import drawingStyles.DrawingStylesFactory;
import mapDefenitionUtilities.DefenitionTags;
import drawingStyles.MapDrawSettings;
import drawingStyles.MapObjectDrawSettings;
import mapDefenitionUtilities.Tag;
import drawingStyles.StyleEditor;
import drawingStyles.StyleViewer;
import java.awt.Color;
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
			MapObjectDrawSettings style1 = new MapObjectDrawSettings();
			style1.setDescription("style1");
			MapObjectDrawSettings style2 = new MapObjectDrawSettings();
			style2.setDescription("style2");
			MapObjectDrawSettings style3 = new MapObjectDrawSettings();
			style3.setDescription("style3");

			StyleEditor writedEditor = DrawingStylesFactory.createStyleEditor();
			writedEditor.addMapObjectDrawSettings(style1);
			writedEditor.addMapObjectDrawSettings(style2);
			writedEditor.addMapObjectDrawSettings(style3);
			writedEditor.setMapDrawSettings(new MapDrawSettings(Color.red));

			IOTester.writeToTestFile(writedEditor);

			StyleViewer readViewer = DrawingStylesFactory.createStyleViewer();
			IOTester.readFromTestFile(readViewer);

			assertEquals(writedEditor.getMapObjectDrawSettings(0).getDescription(), readViewer.getMapObjectDrawSettings(0).getDescription());
			assertEquals(writedEditor.getMapObjectDrawSettings(1).getDescription(), readViewer.getMapObjectDrawSettings(1).getDescription());
			assertEquals(writedEditor.getMapObjectDrawSettings(2).getDescription(), readViewer.getMapObjectDrawSettings(2).getDescription());
			assertEquals(writedEditor.getMapDrawSettings().getMapBackgroundColor(),
							readViewer.getMapDrawSettings().getMapBackgroundColor());
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
			DefenitionTags tags1 = new DefenitionTags();
			tags1.add(new Tag("k1", "v1"));
			tags1.add(new Tag("k2", "v2"));
			tags1.add(new Tag("k3", "v3"));
			tags1.add(new Tag("k4", "v4"));

			DefenitionTags tags2 = new DefenitionTags();
			tags2.add(new Tag("k1", "v1"));
			tags2.add(new Tag("k2", "v2"));
			tags2.add(new Tag("k5", "v5"));

			DefenitionTags tags3 = new DefenitionTags();
			tags3.add(new Tag("k8", "v8"));

			MapObjectDrawSettings style1 = new MapObjectDrawSettings();
			style1.setDescription("style8");
			style1.setDefenitionTags(tags1);
			MapObjectDrawSettings style2 = new MapObjectDrawSettings();
			style2.setDescription("style9");
			style2.setDefenitionTags(tags2);
			MapObjectDrawSettings style3 = new MapObjectDrawSettings();
			style3.setDescription("style10");
			style3.setDefenitionTags(tags3);

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

			DefenitionTags testTags = new DefenitionTags();
			testTags.add(new Tag("k9", "v9"));
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
			DefenitionTags tags1 = new DefenitionTags();
			tags1.add(new Tag("k1", "v1"));
			MapObjectDrawSettings style1 = new MapObjectDrawSettings();
			style1.setDescription("style1");
			style1.setDefenitionTags(tags1);
			
			DefenitionTags tags2 = new DefenitionTags();
			tags2.add(new Tag("k2", "v2"));
			MapObjectDrawSettings style2 = new MapObjectDrawSettings();
			style2.setDescription("style2");
			style2.setDefenitionTags(tags2);
			
			StyleEditor editor = DrawingStylesFactory.createStyleEditor();
			editor.addMapObjectDrawSettings(style1);
			editor.addMapObjectDrawSettings(style2);
			IOTester.writeToTestFile(editor);

			StyleViewer viewer = DrawingStylesFactory.createStyleViewer();
			IOTester.readFromTestFile(viewer);

			assertEquals(style1.getDescription(), viewer.getMapObjectDrawSettings(0).getDescription());
			assertEquals(style2.getDescription(), viewer.getMapObjectDrawSettings(1).getDescription());
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

			assertNull(viewer.getMapObjectDrawSettings(-1));
			assertNull(viewer.getMapObjectDrawSettings(0));
			assertNull(viewer.getMapObjectDrawSettings(1));
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

			assertNull(viewer.getMapObjectDrawSettings(null));
		}
		catch (Exception ex)
		{
			fail();
		}
	}
}
