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
public class MapObjectStyleEditorTest
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
		MapObjectStyle style1 = new MapObjectStyle(true, true, false, null, 0, "style1", null, null);
		MapObjectStyle style2 = new MapObjectStyle(true, false, true, null, 0, "style2", null, null);
		MapObjectStyle style3 = new MapObjectStyle(false, true, true, null, 0, "style3", null, null);

		StyleEditor editor = DrawingStylesFactory.createStyleEditor();
		assertEquals(0, editor.countOfMapObjectStyles());

		editor.addMapObjectStyle(style1);
		editor.addMapObjectStyle(style2);
		editor.addMapObjectStyle(style3);

		assertEquals(3, editor.countOfMapObjectStyles());

		assertEquals(style1.getDescription(), editor.getMapObjectStyle(0).getDescription());
		assertEquals(style2.getDescription(), editor.getMapObjectStyle(1).getDescription());
		assertEquals(style3.getDescription(), editor.getMapObjectStyle(2).getDescription());
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
			editor.addMapObjectStyle(null);
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
			MapObjectStyle style1 = new MapObjectStyle(true, true, false, null, 0, "style1", null, null);
			MapObjectStyle style2 = new MapObjectStyle(true, false, true, null, 0, "style2", null, null);

			StyleEditor editor = DrawingStylesFactory.createStyleEditor();
			editor.addMapObjectStyle(style1);
			editor.addMapObjectStyle(style2);

			assertEquals(2, editor.countOfMapObjectStyles());
			assertEquals(style1.getDescription(), editor.getMapObjectStyle(0).getDescription());
			assertEquals(style2.getDescription(), editor.getMapObjectStyle(1).getDescription());
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
		MapObjectStyle style = new MapObjectStyle(true, true, false, null, 0, "style1", null, null);
		StyleEditor editor = DrawingStylesFactory.createStyleEditor();
		editor.addMapObjectStyle(style);

		assertNull(editor.getMapObjectStyle(-1));
		assertNull(editor.getMapObjectStyle(editor.countOfMapObjectStyles() + 1));
		assertNull(editor.getMapObjectStyle(null));
	}

	/**
	 * Testing setMapObjectStyle method normal work
	 */
	@Test
	public void setWithCorrectParametersTest()
	{
		MapObjectStyle style1 = new MapObjectStyle(true, true, false, null, 0, "style1", null, null);
		MapObjectStyle style2 = new MapObjectStyle(true, false, true, null, 0, "style2", null, null);

		StyleEditor editor = DrawingStylesFactory.createStyleEditor();
		editor.addMapObjectStyle(style1);
		editor.setMapObjectStyle(0, style2);

		assertEquals(style2.getDescription(), editor.getMapObjectStyle(0).getDescription());
	}

	/**
	 * Testing setMapObjectStyle method with out of bounds index (less)
	 */
	@Test
	public void setOnLessThanBoundsIndexTest()
	{
		MapObjectStyle style1 = new MapObjectStyle(true, true, false, null, 0, "style1", null, null);
		MapObjectStyle style2 = new MapObjectStyle(true, false, true, null, 0, "style2", null, null);

		StyleEditor editor = DrawingStylesFactory.createStyleEditor();
		editor.addMapObjectStyle(style1);
		try
		{
			editor.setMapObjectStyle(-1, style2);
			fail();
		}
		catch (StyleIndexOutOfBoundsException ex)
		{
			assertEquals(editor, ex.getEditorThrowedException());
			assertEquals(-1, (int) ex.getIncorrectStyleIndex());
			assertEquals(0, (int) ex.getBoundsMinimum());
			assertEquals(editor.countOfMapObjectStyles(), (int) ex.getBoundsMaximum());
		}
	}

	/**
	 * Testing setMapObjectStyle method with out of bounds index (more)
	 */
	@Test
	public void setOnMoreThanBoundsIndexTest()
	{
		MapObjectStyle style1 = new MapObjectStyle(true, true, false, null, 0, "style1", null, null);
		MapObjectStyle style2 = new MapObjectStyle(true, false, true, null, 0, "style2", null, null);

		StyleEditor editor = DrawingStylesFactory.createStyleEditor();
		editor.addMapObjectStyle(style1);
		try
		{
			editor.setMapObjectStyle(editor.countOfMapObjectStyles() + 1, style2);
			fail();
		}
		catch (StyleIndexOutOfBoundsException ex)
		{
			assertEquals(editor, ex.getEditorThrowedException());
			assertEquals(editor.countOfMapObjectStyles() + 1, (int) ex.getIncorrectStyleIndex());
			assertEquals(0, (int) ex.getBoundsMinimum());
			assertEquals(editor.countOfMapObjectStyles(), (int) ex.getBoundsMaximum());
		}
	}

	/**
	 * Testing setMapObjectStyle method with null style
	 */
	@Test
	public void setNullStyleTest()
	{
		MapObjectStyle style1 = new MapObjectStyle(true, true, false, null, 0, "style1", null, null);

		StyleEditor editor = DrawingStylesFactory.createStyleEditor();
		editor.addMapObjectStyle(style1);
		try
		{
			editor.setMapObjectStyle(0, null);
			fail();
		}
		catch (MapObjectStyleIsNullException ex)
		{
			assertEquals(editor, ex.getEditorThrowedException());
		}
	}

	/**
	 * Testing setMapObjectStyle method with null index
	 */
	@Test
	public void setOnNullIndexTest()
	{
		MapObjectStyle style2 = new MapObjectStyle(true, false, true, null, 0, "style2", null, null);
		StyleEditor editor = DrawingStylesFactory.createStyleEditor();
		try
		{
			editor.setMapObjectStyle(null, style2);
			fail();
		}
		catch (StyleIndexOutOfBoundsException ex)
		{
			assertEquals(editor, ex.getEditorThrowedException());
			assertEquals(null, ex.getIncorrectStyleIndex());
			assertEquals(0, (int) ex.getBoundsMinimum());
			assertEquals(editor.countOfMapObjectStyles(), (int) ex.getBoundsMaximum());
		}
	}

	/**
	 * Test removeMapObjectStyle normal work
	 */
	@Test
	public void removeByCorrectIndexTest()
	{
		MapObjectStyle style1 = new MapObjectStyle(true, true, false, null, 0, "style1", null, null);
		MapObjectStyle style2 = new MapObjectStyle(true, false, true, null, 0, "style2", null, null);

		StyleEditor editor = DrawingStylesFactory.createStyleEditor();
		editor.addMapObjectStyle(style1);
		editor.addMapObjectStyle(style2);

		assertEquals(2, editor.countOfMapObjectStyles());

		editor.removeMapObjectStyle(0);
		assertEquals(1, editor.countOfMapObjectStyles());
		assertEquals(style2.getDescription(), editor.getMapObjectStyle(0).getDescription());

		editor.removeMapObjectStyle(0);
		assertEquals(0, editor.countOfMapObjectStyles());
	}

	/**
	 * Test removeMapObjectStyle by index less than bounds
	 */
	@Test
	public void removeByIndexLessThanBoundsTest()
	{
		MapObjectStyle style1 = new MapObjectStyle(true, true, false, null, 0, "style1", null, null);
		StyleEditor editor = DrawingStylesFactory.createStyleEditor();
		editor.addMapObjectStyle(style1);
		try
		{
			editor.removeMapObjectStyle(-1);
			fail();
		}
		catch (StyleIndexOutOfBoundsException ex)
		{
			assertEquals(-1, (int) ex.getIncorrectStyleIndex());
			assertEquals(0, (int) ex.getBoundsMinimum());
			assertEquals(editor.countOfMapObjectStyles(), (int) ex.getBoundsMaximum());
		}
	}

	/**
	 * Test removeMapObjectStyle by index more than bounds
	 */
	@Test
	public void removeByIndexMoreThanBoundsTest()
	{
		MapObjectStyle style1 = new MapObjectStyle(true, true, false, null, 0, "style1", null, null);
		StyleEditor editor = DrawingStylesFactory.createStyleEditor();
		editor.addMapObjectStyle(style1);
		try
		{
			editor.removeMapObjectStyle(editor.countOfMapObjectStyles());
			fail();
		}
		catch (StyleIndexOutOfBoundsException ex)
		{
			assertEquals(editor.countOfMapObjectStyles(), (int) ex.getIncorrectStyleIndex());
			assertEquals(0, (int) ex.getBoundsMinimum());
			assertEquals(editor.countOfMapObjectStyles(), (int) ex.getBoundsMaximum());
		}
	}

	/**
	 * Test removeMapObjectStyle by null index
	 */
	@Test
	public void removeByNullIndexTest()
	{
		MapObjectStyle style1 = new MapObjectStyle(true, true, false, null, 0, "style1", null, null);
		StyleEditor editor = DrawingStylesFactory.createStyleEditor();
		editor.addMapObjectStyle(style1);
		try
		{
			editor.removeMapObjectStyle(null);
			fail();
		}
		catch (StyleIndexOutOfBoundsException ex)
		{
			assertEquals(null, ex.getIncorrectStyleIndex());
			assertEquals(0, (int) ex.getBoundsMinimum());
			assertEquals(editor.countOfMapObjectStyles(), (int) ex.getBoundsMaximum());
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
			MapObjectStyle style1 = new MapObjectStyle(true, true, false, null, 0, "style1", null, null);
			MapObjectStyle style2 = new MapObjectStyle(true, false, true, null, 0, "style2", null, null);
			MapObjectStyle style3 = new MapObjectStyle(false, true, true, null, 0, "style3", null, null);

			StyleEditor writedEditor = DrawingStylesFactory.createStyleEditor();
			writedEditor.addMapObjectStyle(style1);
			writedEditor.addMapObjectStyle(style2);
			writedEditor.addMapObjectStyle(style3);
			writedEditor.setMapDrawingSettings(new MapDrawingSettings(new IOColor(Color.CYAN)));
			IOTester.writeToTestFile(writedEditor);

			StyleEditor readEditor = DrawingStylesFactory.createStyleEditor();
			IOTester.readFromTestFile(readEditor);

			assertEquals(writedEditor.getMapDrawingSettings().getMapBackgroundColor().getColor(),
							readEditor.getMapDrawingSettings().getMapBackgroundColor().getColor());
			// comparing only by description
			assertEquals(writedEditor.countOfMapObjectStyles(), readEditor.countOfMapObjectStyles());
			for (int i = 0; i < writedEditor.countOfMapObjectStyles(); i++)
				assertEquals(writedEditor.getMapObjectStyle(i).getDescription(), readEditor.getMapObjectStyle(i).getDescription());
		}
		catch (Exception ex)
		{
			fail();
		}
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

		MapObjectStyle style1 = new MapObjectStyle(true, true, true, null, 0, "style1", null, tags1);
		MapObjectStyle style2 = new MapObjectStyle(true, true, true, null, 0, "style2", null, tags2);
		MapObjectStyle style3 = new MapObjectStyle(true, true, true, null, 0, "style3", null, tags3);

		StyleEditor editor = DrawingStylesFactory.createStyleEditor();
		editor.addMapObjectStyle(style1);
		editor.addMapObjectStyle(style2);
		editor.addMapObjectStyle(style3);

		// full equal
		assertEquals(0, (int) editor.getStyleIndex(tags1));
		assertEquals(1, (int) editor.getStyleIndex(tags2));
		assertEquals(2, (int) editor.getStyleIndex(tags3));

		// part equal
		EditableDefenitionTags testTags1 = new EditableDefenitionTags();
		testTags1.add(new MapTag("k1", "v1"));
		testTags1.add(new MapTag("k2", "v2"));
		testTags1.add(new MapTag("k3", "v3"));
		testTags1.add(new MapTag("k4", "v4"));
		testTags1.add(new MapTag("k5", "v5"));
		testTags1.add(new MapTag("k6", "v6"));
		assertEquals(0, (int) editor.getStyleIndex(testTags1));

		EditableDefenitionTags testTags2 = new EditableDefenitionTags();
		testTags2.add(new MapTag("k1", "v1"));
		testTags2.add(new MapTag("k2", "v2"));
		testTags2.add(new MapTag("k3", "v3"));
		testTags2.add(new MapTag("k5", "v5"));
		testTags2.add(new MapTag("k6", "v6"));
		assertEquals(1, (int) editor.getStyleIndex(testTags2));

		// not found
		EditableDefenitionTags testTags3 = new EditableDefenitionTags();
		testTags3.add(new MapTag("k9", "v9"));
		assertNull(editor.getStyleIndex(testTags3));
		assertNull(editor.getStyleIndex(null));
	}
}
