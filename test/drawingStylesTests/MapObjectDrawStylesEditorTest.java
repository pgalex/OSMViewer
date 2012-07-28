package drawingStylesTests;

import IOTesting.IOTester;
import drawingStyles.*;
import drawingStyles.exceptions.MapDrawingSettingsIsNullException;
import drawingStyles.exceptions.MapObjectStyleIsNullException;
import drawingStyles.exceptions.StyleIndexOutOfBoundsException;
import java.awt.Color;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * MapObjectStyleEditor class tests
 *
 * @author pgalex
 */
public class MapObjectDrawStylesEditorTest
{
	/**
	 * Initializing all fields test
	 */
	@Test
	public void initializingTest()
	{
		StyleEditor testEditor = DrawingStylesFactory.createStyleEditor();
		assertNotNull(testEditor.getMapDrawingSettings());
	}

	/**
	 * Test setMapDrawingSettings methods with null settings
	 */
	@Test
	public void setNullMapDrawingSettingsTest()
	{
		StyleEditor testEditor = DrawingStylesFactory.createStyleEditor();
		try
		{
			testEditor.setMapDrawingSettings(null);
			fail();
		}
		catch (MapDrawingSettingsIsNullException ex)
		{
			assertEquals(testEditor, ex.getEditorThrowedException());
		}
	}

	/**
	 * Test adding valid (not null ) styles. New style adds to the end
	 */
	@Test
	public void addingValidStylesTest()
	{
		MapObjectDrawSettings style1 = new MapObjectDrawSettings(true, true, false, null, 0, "style1", null, null);
		MapObjectDrawSettings style2 = new MapObjectDrawSettings(true, false, true, null, 0, "style2", null, null);
		MapObjectDrawSettings style3 = new MapObjectDrawSettings(false, true, true, null, 0, "style3", null, null);

		StyleEditor editor = DrawingStylesFactory.createStyleEditor();
		assertEquals(0, editor.countOfMapObjectDrawSettings());

		editor.addMapObjectDrawSettings(style1);
		editor.addMapObjectDrawSettings(style2);
		editor.addMapObjectDrawSettings(style3);

		assertEquals(3, editor.countOfMapObjectDrawSettings());

		assertEquals(style1.getDescription(), editor.getMapObjectDrawSettings(0).getDescription());
		assertEquals(style2.getDescription(), editor.getMapObjectDrawSettings(1).getDescription());
		assertEquals(style3.getDescription(), editor.getMapObjectDrawSettings(2).getDescription());
	}

	/**
	 * Test adding null style
	 */
	@Test
	public void addingNullStyleTest()
	{
		StyleEditor editor = DrawingStylesFactory.createStyleEditor();
		try
		{
			editor.addMapObjectDrawSettings(null);
			fail();
		}
		catch (MapObjectStyleIsNullException ex)
		{
			assertEquals(editor, ex.getEditorThrowedException());
		}
	}

	/**
	 * Test get styles by valid index
	 */
	@Test
	public void getWithCorrectIndexTest()
	{
		try
		{
			MapObjectDrawSettings style1 = new MapObjectDrawSettings(true, true, false, null, 0, "style1", null, null);
			MapObjectDrawSettings style2 = new MapObjectDrawSettings(true, false, true, null, 0, "style2", null, null);

			StyleEditor editor = DrawingStylesFactory.createStyleEditor();
			editor.addMapObjectDrawSettings(style1);
			editor.addMapObjectDrawSettings(style2);

			assertEquals(2, editor.countOfMapObjectDrawSettings());
			assertEquals(style1.getDescription(), editor.getMapObjectDrawSettings(0).getDescription());
			assertEquals(style2.getDescription(), editor.getMapObjectDrawSettings(1).getDescription());
		}
		catch (Exception ex)
		{
			fail();
		}
	}

	/**
	 * Test get style be incorrect index
	 */
	@Test
	public void getWithIncorrectIndexTest()
	{
		MapObjectDrawSettings style = new MapObjectDrawSettings(true, true, false, null, 0, "style1", null, null);
		StyleEditor editor = DrawingStylesFactory.createStyleEditor();
		editor.addMapObjectDrawSettings(style);

		assertNull(editor.getMapObjectDrawSettings(-1));
		assertNull(editor.getMapObjectDrawSettings(editor.countOfMapObjectDrawSettings() + 1));
		assertNull(editor.getMapObjectDrawSettings(null));
	}

	/**
	 * Testing setMapObjectDrawSettings method normal work
	 */
	@Test
	public void setWithCorrectParametersTest()
	{
		MapObjectDrawSettings style1 = new MapObjectDrawSettings(true, true, false, null, 0, "style1", null, null);
		MapObjectDrawSettings style2 = new MapObjectDrawSettings(true, false, true, null, 0, "style2", null, null);

		StyleEditor editor = DrawingStylesFactory.createStyleEditor();
		editor.addMapObjectDrawSettings(style1);
		editor.setMapObjectDrawSettings(0, style2);

		assertEquals(style2.getDescription(), editor.getMapObjectDrawSettings(0).getDescription());
	}

	/**
	 * Testing setMapObjectDrawSettings method with out of bounds index (less)
	 */
	@Test
	public void setOnLessThanBoundsIndexTest()
	{
		MapObjectDrawSettings style1 = new MapObjectDrawSettings(true, true, false, null, 0, "style1", null, null);
		MapObjectDrawSettings style2 = new MapObjectDrawSettings(true, false, true, null, 0, "style2", null, null);

		StyleEditor editor = DrawingStylesFactory.createStyleEditor();
		editor.addMapObjectDrawSettings(style1);
		try
		{
			editor.setMapObjectDrawSettings(-1, style2);
			fail();
		}
		catch (StyleIndexOutOfBoundsException ex)
		{
			assertEquals(editor, ex.getEditorThrowedException());
			assertEquals(-1, (int) ex.getIncorrectStyleIndex());
			assertEquals(0, (int) ex.getBoundsMinimum());
			assertEquals(editor.countOfMapObjectDrawSettings(), (int) ex.getBoundsMaximum());
		}
	}

	/**
	 * Testing setMapObjectDrawSettings method with out of bounds index (more)
	 */
	@Test
	public void setOnMoreThanBoundsIndexTest()
	{
		MapObjectDrawSettings style1 = new MapObjectDrawSettings(true, true, false, null, 0, "style1", null, null);
		MapObjectDrawSettings style2 = new MapObjectDrawSettings(true, false, true, null, 0, "style2", null, null);

		StyleEditor editor = DrawingStylesFactory.createStyleEditor();
		editor.addMapObjectDrawSettings(style1);
		try
		{
			editor.setMapObjectDrawSettings(editor.countOfMapObjectDrawSettings() + 1, style2);
			fail();
		}
		catch (StyleIndexOutOfBoundsException ex)
		{
			assertEquals(editor, ex.getEditorThrowedException());
			assertEquals(editor.countOfMapObjectDrawSettings() + 1, (int) ex.getIncorrectStyleIndex());
			assertEquals(0, (int) ex.getBoundsMinimum());
			assertEquals(editor.countOfMapObjectDrawSettings(), (int) ex.getBoundsMaximum());
		}
	}

	/**
	 * Testing setMapObjectDrawSettings method with null style
	 */
	@Test
	public void setNullStyleTest()
	{
		MapObjectDrawSettings style1 = new MapObjectDrawSettings(true, true, false, null, 0, "style1", null, null);

		StyleEditor editor = DrawingStylesFactory.createStyleEditor();
		editor.addMapObjectDrawSettings(style1);
		try
		{
			editor.setMapObjectDrawSettings(0, null);
			fail();
		}
		catch (MapObjectStyleIsNullException ex)
		{
			assertEquals(editor, ex.getEditorThrowedException());
		}
	}

	/**
	 * Testing setMapObjectDrawSettings method with null index
	 */
	@Test
	public void setOnNullIndexTest()
	{
		MapObjectDrawSettings style2 = new MapObjectDrawSettings(true, false, true, null, 0, "style2", null, null);
		StyleEditor editor = DrawingStylesFactory.createStyleEditor();
		try
		{
			editor.setMapObjectDrawSettings(null, style2);
			fail();
		}
		catch (StyleIndexOutOfBoundsException ex)
		{
			assertEquals(editor, ex.getEditorThrowedException());
			assertEquals(null, ex.getIncorrectStyleIndex());
			assertEquals(0, (int) ex.getBoundsMinimum());
			assertEquals(editor.countOfMapObjectDrawSettings(), (int) ex.getBoundsMaximum());
		}
	}

	/**
	 * Test removeMapObjectDrawSettings normal work
	 */
	@Test
	public void removeByCorrectIndexTest()
	{
		MapObjectDrawSettings style1 = new MapObjectDrawSettings(true, true, false, null, 0, "style1", null, null);
		MapObjectDrawSettings style2 = new MapObjectDrawSettings(true, false, true, null, 0, "style2", null, null);

		StyleEditor editor = DrawingStylesFactory.createStyleEditor();
		editor.addMapObjectDrawSettings(style1);
		editor.addMapObjectDrawSettings(style2);

		assertEquals(2, editor.countOfMapObjectDrawSettings());

		editor.removeMapObjectDrawSettings(0);
		assertEquals(1, editor.countOfMapObjectDrawSettings());
		assertEquals(style2.getDescription(), editor.getMapObjectDrawSettings(0).getDescription());

		editor.removeMapObjectDrawSettings(0);
		assertEquals(0, editor.countOfMapObjectDrawSettings());
	}

	/**
	 * Test removeMapObjectDrawSettings by index less than bounds
	 */
	@Test
	public void removeByIndexLessThanBoundsTest()
	{
		MapObjectDrawSettings style1 = new MapObjectDrawSettings(true, true, false, null, 0, "style1", null, null);
		StyleEditor editor = DrawingStylesFactory.createStyleEditor();
		editor.addMapObjectDrawSettings(style1);
		try
		{
			editor.removeMapObjectDrawSettings(-1);
			fail();
		}
		catch (StyleIndexOutOfBoundsException ex)
		{
			assertEquals(-1, (int) ex.getIncorrectStyleIndex());
			assertEquals(0, (int) ex.getBoundsMinimum());
			assertEquals(editor.countOfMapObjectDrawSettings(), (int) ex.getBoundsMaximum());
		}
	}

	/**
	 * Test removeMapObjectDrawSettings by index more than bounds
	 */
	@Test
	public void removeByIndexMoreThanBoundsTest()
	{
		MapObjectDrawSettings style1 = new MapObjectDrawSettings(true, true, false, null, 0, "style1", null, null);
		StyleEditor editor = DrawingStylesFactory.createStyleEditor();
		editor.addMapObjectDrawSettings(style1);
		try
		{
			editor.removeMapObjectDrawSettings(editor.countOfMapObjectDrawSettings());
			fail();
		}
		catch (StyleIndexOutOfBoundsException ex)
		{
			assertEquals(editor.countOfMapObjectDrawSettings(), (int) ex.getIncorrectStyleIndex());
			assertEquals(0, (int) ex.getBoundsMinimum());
			assertEquals(editor.countOfMapObjectDrawSettings(), (int) ex.getBoundsMaximum());
		}
	}

	/**
	 * Test removeMapObjectDrawSettings by null index
	 */
	@Test
	public void removeByNullIndexTest()
	{
		MapObjectDrawSettings style1 = new MapObjectDrawSettings(true, true, false, null, 0, "style1", null, null);
		StyleEditor editor = DrawingStylesFactory.createStyleEditor();
		editor.addMapObjectDrawSettings(style1);
		try
		{
			editor.removeMapObjectDrawSettings(null);
			fail();
		}
		catch (StyleIndexOutOfBoundsException ex)
		{
			assertEquals(null, ex.getIncorrectStyleIndex());
			assertEquals(0, (int) ex.getBoundsMinimum());
			assertEquals(editor.countOfMapObjectDrawSettings(), (int) ex.getBoundsMaximum());
		}
	}

	/**
	 * Normal reading/writing test
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
			writedEditor.setMapDrawingSettings(new MapDrawingSettings(new IOColor(Color.CYAN)));
			IOTester.writeToTestFile(writedEditor);

			StyleEditor readEditor = DrawingStylesFactory.createStyleEditor();
			IOTester.readFromTestFile(readEditor);

			assertEquals(writedEditor.getMapDrawingSettings().getMapBackgroundColor().getColor(),
							readEditor.getMapDrawingSettings().getMapBackgroundColor().getColor());
			// comparing only by description
			assertEquals(writedEditor.countOfMapObjectDrawSettings(), readEditor.countOfMapObjectDrawSettings());
			for (int i = 0; i < writedEditor.countOfMapObjectDrawSettings(); i++)
				assertEquals(writedEditor.getMapObjectDrawSettings(i).getDescription(), readEditor.getMapObjectDrawSettings(i).getDescription());
		}
		catch (Exception ex)
		{
			fail();
		}
	}

	/**
	 * findStyleIndex test
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

		MapObjectDrawSettings style1 = new MapObjectDrawSettings(true, true, true, null, 0, "style1", null, tags1);
		MapObjectDrawSettings style2 = new MapObjectDrawSettings(true, true, true, null, 0, "style2", null, tags2);
		MapObjectDrawSettings style3 = new MapObjectDrawSettings(true, true, true, null, 0, "style3", null, tags3);

		StyleEditor editor = DrawingStylesFactory.createStyleEditor();
		editor.addMapObjectDrawSettings(style1);
		editor.addMapObjectDrawSettings(style2);
		editor.addMapObjectDrawSettings(style3);

		// full equal
		assertEquals(0, (int) editor.findStyleIndex(tags1));
		assertEquals(1, (int) editor.findStyleIndex(tags2));
		assertEquals(2, (int) editor.findStyleIndex(tags3));

		// part equal
		EditableDefenitionTags testTags1 = new EditableDefenitionTags();
		testTags1.add(new MapTag("k1", "v1"));
		testTags1.add(new MapTag("k2", "v2"));
		testTags1.add(new MapTag("k3", "v3"));
		testTags1.add(new MapTag("k4", "v4"));
		testTags1.add(new MapTag("k5", "v5"));
		testTags1.add(new MapTag("k6", "v6"));
		assertEquals(0, (int) editor.findStyleIndex(testTags1));

		EditableDefenitionTags testTags2 = new EditableDefenitionTags();
		testTags2.add(new MapTag("k1", "v1"));
		testTags2.add(new MapTag("k2", "v2"));
		testTags2.add(new MapTag("k3", "v3"));
		testTags2.add(new MapTag("k5", "v5"));
		testTags2.add(new MapTag("k6", "v6"));
		assertEquals(1, (int) editor.findStyleIndex(testTags2));

		// not found
		EditableDefenitionTags testTags3 = new EditableDefenitionTags();
		testTags3.add(new MapTag("k9", "v9"));
		assertNull(editor.findStyleIndex(testTags3));
		assertNull(editor.findStyleIndex(null));
	}
}
