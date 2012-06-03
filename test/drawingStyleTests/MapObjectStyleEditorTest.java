package drawingStyleTests;

import drawingStyle.DrawingStyleFactory;
import drawingStyle.MapObjectStyle;
import drawingStyle.StyleEditor;
import drawingStyle.exceptions.MapObjectStyleIsNullException;
import drawingStyle.exceptions.StyleIndexOutOfBoundsException;
import map.EditableDefenitionTags;
import map.MapTag;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author pgalex
 */
public class MapObjectStyleEditorTest
{
	/**
	 * Test adding valid (not null ) styles. New style adds to the end
	 */
	@Test
	public void addingValidStylesTest()
	{
		MapObjectStyle style1 = new MapObjectStyle(true, true, false, null, 0, "style1", null, null);
		MapObjectStyle style2 = new MapObjectStyle(true, false, true, null, 0, "style2", null, null);
		MapObjectStyle style3 = new MapObjectStyle(false, true, true, null, 0, "style3", null, null);

		StyleEditor editor = DrawingStyleFactory.createStyleEditor();
		assertEquals(0, editor.count());

		editor.add(style1);
		editor.add(style2);
		editor.add(style3);

		assertEquals(3, editor.count());

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
		StyleEditor editor = DrawingStyleFactory.createStyleEditor();
		try
		{
			editor.add(null);
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

			StyleEditor editor = DrawingStyleFactory.createStyleEditor();
			editor.add(style1);
			editor.add(style2);

			assertEquals(2, editor.count());
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
		StyleEditor editor = DrawingStyleFactory.createStyleEditor();
		editor.add(style);

		assertNull(editor.getMapObjectStyle(-1));
		assertNull(editor.getMapObjectStyle(editor.count() + 1));
		assertNull(editor.getMapObjectStyle(null));
	}

	/**
	 * Testing set method normal work
	 */
	@Test
	public void setWithCorrectParametersTest()
	{
		MapObjectStyle style1 = new MapObjectStyle(true, true, false, null, 0, "style1", null, null);
		MapObjectStyle style2 = new MapObjectStyle(true, false, true, null, 0, "style2", null, null);

		StyleEditor editor = DrawingStyleFactory.createStyleEditor();
		editor.add(style1);
		editor.set(0, style2);

		assertEquals(style2.getDescription(), editor.getMapObjectStyle(0).getDescription());
	}

	/**
	 * Testing set method with out of bounds index (less)
	 */
	@Test
	public void setOnLessThanBoundsIndexTest()
	{
		MapObjectStyle style1 = new MapObjectStyle(true, true, false, null, 0, "style1", null, null);
		MapObjectStyle style2 = new MapObjectStyle(true, false, true, null, 0, "style2", null, null);

		StyleEditor editor = DrawingStyleFactory.createStyleEditor();
		editor.add(style1);
		try
		{
			editor.set(-1, style2);
			fail();
		}
		catch (StyleIndexOutOfBoundsException ex)
		{
			assertEquals(editor, ex.getEditorThrowedException());
			assertEquals(-1, (int) ex.getIncorrectStyleIndex());
			assertEquals(0, (int) ex.getBoundsMinimum());
			assertEquals(editor.count(), (int) ex.getBoundsMaximum());
		}
	}

	/**
	 * Testing set method with out of bounds index (more)
	 */
	@Test
	public void setOnMoreThanBoundsIndexTest()
	{
		MapObjectStyle style1 = new MapObjectStyle(true, true, false, null, 0, "style1", null, null);
		MapObjectStyle style2 = new MapObjectStyle(true, false, true, null, 0, "style2", null, null);

		StyleEditor editor = DrawingStyleFactory.createStyleEditor();
		editor.add(style1);
		try
		{
			editor.set(editor.count() + 1, style2);
			fail();
		}
		catch (StyleIndexOutOfBoundsException ex)
		{
			assertEquals(editor, ex.getEditorThrowedException());
			assertEquals(editor.count() + 1, (int) ex.getIncorrectStyleIndex());
			assertEquals(0, (int) ex.getBoundsMinimum());
			assertEquals(editor.count(), (int) ex.getBoundsMaximum());
		}
	}

	/**
	 * Testing set method with null style
	 */
	@Test
	public void setNullStyleTest()
	{
		MapObjectStyle style1 = new MapObjectStyle(true, true, false, null, 0, "style1", null, null);

		StyleEditor editor = DrawingStyleFactory.createStyleEditor();
		editor.add(style1);
		try
		{
			editor.set(0, null);
			fail();
		}
		catch (MapObjectStyleIsNullException ex)
		{
			assertEquals(editor, ex.getEditorThrowedException());
		}
	}

	/**
	 * Testing set method with null index
	 */
	@Test
	public void setOnNullIndexTest()
	{
		MapObjectStyle style2 = new MapObjectStyle(true, false, true, null, 0, "style2", null, null);
		StyleEditor editor = DrawingStyleFactory.createStyleEditor();
		try
		{
			editor.set(null, style2);
			fail();
		}
		catch (StyleIndexOutOfBoundsException ex)
		{
			assertEquals(editor, ex.getEditorThrowedException());
			assertEquals(null, ex.getIncorrectStyleIndex());
			assertEquals(0, (int) ex.getBoundsMinimum());
			assertEquals(editor.count(), (int) ex.getBoundsMaximum());
		}
	}

	/**
	 * Test remove normal work
	 */
	@Test
	public void removeByCorrectIndexTest()
	{
		MapObjectStyle style1 = new MapObjectStyle(true, true, false, null, 0, "style1", null, null);
		MapObjectStyle style2 = new MapObjectStyle(true, false, true, null, 0, "style2", null, null);

		StyleEditor editor = DrawingStyleFactory.createStyleEditor();
		editor.add(style1);
		editor.add(style2);

		assertEquals(2, editor.count());

		editor.remove(0);
		assertEquals(1, editor.count());
		assertEquals(style2.getDescription(), editor.getMapObjectStyle(0).getDescription());

		editor.remove(0);
		assertEquals(0, editor.count());
	}

	/**
	 * Test remove by index less than bounds
	 */
	@Test
	public void removeByIndexLessThanBoundsTest()
	{
		MapObjectStyle style1 = new MapObjectStyle(true, true, false, null, 0, "style1", null, null);
		StyleEditor editor = DrawingStyleFactory.createStyleEditor();
		editor.add(style1);
		try
		{
			editor.remove(-1);
			fail();
		}
		catch (StyleIndexOutOfBoundsException ex)
		{
			assertEquals(-1, (int) ex.getIncorrectStyleIndex());
			assertEquals(0, (int) ex.getBoundsMinimum());
			assertEquals(editor.count(), (int) ex.getBoundsMaximum());
		}
	}

	/**
	 * Test remove by index more than bounds
	 */
	@Test
	public void removeByIndexMoreThanBoundsTest()
	{
		MapObjectStyle style1 = new MapObjectStyle(true, true, false, null, 0, "style1", null, null);
		StyleEditor editor = DrawingStyleFactory.createStyleEditor();
		editor.add(style1);
		try
		{
			editor.remove(editor.count());
			fail();
		}
		catch (StyleIndexOutOfBoundsException ex)
		{
			assertEquals(editor.count(), (int) ex.getIncorrectStyleIndex());
			assertEquals(0, (int) ex.getBoundsMinimum());
			assertEquals(editor.count(), (int) ex.getBoundsMaximum());
		}
	}

	/**
	 * Test remove by null index
	 */
	@Test
	public void removeByNullIndexTest()
	{
		MapObjectStyle style1 = new MapObjectStyle(true, true, false, null, 0, "style1", null, null);
		StyleEditor editor = DrawingStyleFactory.createStyleEditor();
		editor.add(style1);
		try
		{
			editor.remove(null);
			fail();
		}
		catch (StyleIndexOutOfBoundsException ex)
		{
			assertEquals(null, ex.getIncorrectStyleIndex());
			assertEquals(0, (int) ex.getBoundsMinimum());
			assertEquals(editor.count(), (int) ex.getBoundsMaximum());
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

			StyleEditor writedEditor = DrawingStyleFactory.createStyleEditor();
			writedEditor.add(style1);
			writedEditor.add(style2);
			writedEditor.add(style3);
			DrawingStyleIOTester.writeToTestFile(writedEditor);

			StyleEditor readEditor = DrawingStyleFactory.createStyleEditor();
			DrawingStyleIOTester.readFromTestFile(readEditor);

			// comparing only by description. full test in map object style tests
			assertEquals(writedEditor.count(), readEditor.count());
			for (int i = 0; i < writedEditor.count(); i++)
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

		StyleEditor editor = DrawingStyleFactory.createStyleEditor();
		editor.add(style1);
		editor.add(style2);
		editor.add(style3);

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
