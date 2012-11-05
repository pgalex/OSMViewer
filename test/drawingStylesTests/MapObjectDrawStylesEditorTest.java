package drawingStylesTests;

import IOTesting.IOTester;
import drawingStyles.*;
import java.awt.Color;
import java.io.File;
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
		assertNotNull(testEditor.getMapDrawSettings());
	}

	/**
	 * Test setMapDrawSettings methods with null settings
	 */
	@Test
	public void setNullMapDrawingSettingsTest()
	{
		StyleEditor testEditor = DrawingStylesFactory.createStyleEditor();
		try
		{
			testEditor.setMapDrawSettings(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test adding valid (not null ) styles. New style adds to the end
	 */
	@Test
	public void addingValidStylesTest()
	{
		MapObjectDrawSettings style1 = new MapObjectDrawSettings();
		style1.setDescription("style1");

		MapObjectDrawSettings style2 = new MapObjectDrawSettings();
		style2.setDescription("style2");

		MapObjectDrawSettings style3 = new MapObjectDrawSettings();
		style3.setDescription("style3");

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
		catch (IllegalArgumentException ex)
		{
			// ok
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
			MapObjectDrawSettings style1 = new MapObjectDrawSettings();
			style1.setDescription("style1");

			MapObjectDrawSettings style2 = new MapObjectDrawSettings();
			style2.setDescription("style2");

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
		MapObjectDrawSettings style = new MapObjectDrawSettings();
		style.setDescription("style1");

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
		MapObjectDrawSettings style1 = new MapObjectDrawSettings();
		style1.setDescription("style1");

		MapObjectDrawSettings style2 = new MapObjectDrawSettings();
		style2.setDescription("style2");

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
		MapObjectDrawSettings style1 = new MapObjectDrawSettings();
		style1.setDescription("style1");

		MapObjectDrawSettings style2 = new MapObjectDrawSettings();
		style2.setDescription("style2");

		StyleEditor editor = DrawingStylesFactory.createStyleEditor();
		editor.addMapObjectDrawSettings(style1);
		try
		{
			editor.setMapObjectDrawSettings(-1, style2);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Testing setMapObjectDrawSettings method with out of bounds index (more)
	 */
	@Test
	public void setOnMoreThanBoundsIndexTest()
	{
		MapObjectDrawSettings style1 = new MapObjectDrawSettings();
		style1.setDescription("style1");

		MapObjectDrawSettings style2 = new MapObjectDrawSettings();
		style2.setDescription("style2");

		StyleEditor editor = DrawingStylesFactory.createStyleEditor();
		editor.addMapObjectDrawSettings(style1);
		try
		{
			editor.setMapObjectDrawSettings(editor.countOfMapObjectDrawSettings() + 1, style2);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Testing setMapObjectDrawSettings method with null style
	 */
	@Test
	public void setNullStyleTest()
	{
		MapObjectDrawSettings style1 = new MapObjectDrawSettings();
		style1.setDescription("style1");

		StyleEditor editor = DrawingStylesFactory.createStyleEditor();
		editor.addMapObjectDrawSettings(style1);
		try
		{
			editor.setMapObjectDrawSettings(0, null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Testing setMapObjectDrawSettings method with null index
	 */
	@Test
	public void setOnNullIndexTest()
	{
		MapObjectDrawSettings style2 = new MapObjectDrawSettings();
		style2.setDescription("style2");

		StyleEditor editor = DrawingStylesFactory.createStyleEditor();
		try
		{
			editor.setMapObjectDrawSettings(null, style2);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test removeMapObjectDrawSettings normal work
	 */
	@Test
	public void removeByCorrectIndexTest()
	{
		MapObjectDrawSettings style1 = new MapObjectDrawSettings();
		style1.setDescription("style1");

		MapObjectDrawSettings style2 = new MapObjectDrawSettings();
		style2.setDescription("style2");

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
		MapObjectDrawSettings style1 = new MapObjectDrawSettings();
		style1.setDescription("style1");

		StyleEditor editor = DrawingStylesFactory.createStyleEditor();
		editor.addMapObjectDrawSettings(style1);
		try
		{
			editor.removeMapObjectDrawSettings(-1);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test removeMapObjectDrawSettings by index more than bounds
	 */
	@Test
	public void removeByIndexMoreThanBoundsTest()
	{
		MapObjectDrawSettings style1 = new MapObjectDrawSettings();
		style1.setDescription("style1");

		StyleEditor editor = DrawingStylesFactory.createStyleEditor();
		editor.addMapObjectDrawSettings(style1);
		try
		{
			editor.removeMapObjectDrawSettings(editor.countOfMapObjectDrawSettings());
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test removeMapObjectDrawSettings by null index
	 */
	@Test
	public void removeByNullIndexTest()
	{
		MapObjectDrawSettings style1 = new MapObjectDrawSettings();
		style1.setDescription("style1");

		StyleEditor editor = DrawingStylesFactory.createStyleEditor();
		editor.addMapObjectDrawSettings(style1);
		try
		{
			editor.removeMapObjectDrawSettings(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
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
			MapObjectDrawSettings style1 = new MapObjectDrawSettings();
			style1.setDescription("style8");
			MapObjectDrawSettings style2 = new MapObjectDrawSettings();
			style2.setDescription("style9");
			MapObjectDrawSettings style3 = new MapObjectDrawSettings();
			style3.setDescription("style10");

			StyleEditor writedEditor = DrawingStylesFactory.createStyleEditor();
			writedEditor.addMapObjectDrawSettings(style1);
			writedEditor.addMapObjectDrawSettings(style2);
			writedEditor.addMapObjectDrawSettings(style3);
			writedEditor.setMapDrawSettings(new MapDrawSettings(Color.CYAN));
			IOTester.writeToTestFile(writedEditor);

			StyleEditor readEditor = DrawingStylesFactory.createStyleEditor();
			IOTester.readFromTestFile(readEditor);

			assertEquals(writedEditor.getMapDrawSettings().getMapBackgroundColor(),
							readEditor.getMapDrawSettings().getMapBackgroundColor());
			// comparing only by description
			assertEquals(writedEditor.countOfMapObjectDrawSettings(), readEditor.countOfMapObjectDrawSettings());
			for (int i = 0; i < writedEditor.countOfMapObjectDrawSettings(); i++)
			{
				assertEquals(writedEditor.getMapObjectDrawSettings(i).getDescription(), readEditor.getMapObjectDrawSettings(i).getDescription());
			}
		}
		catch (Exception ex)
		{
			fail();
		}
	}

	/**
	 * writeToFile normal work test
	 */
	@Test
	public void writeToFileTest()
	{
		try
		{
			MapObjectDrawSettings style1 = new MapObjectDrawSettings();
			style1.setDescription("style5");
			MapObjectDrawSettings style2 = new MapObjectDrawSettings();
			style2.setDescription("style6");
			MapObjectDrawSettings style3 = new MapObjectDrawSettings();
			style3.setDescription("style7");

			StyleEditor writedEditor = DrawingStylesFactory.createStyleEditor();
			writedEditor.addMapObjectDrawSettings(style1);
			writedEditor.addMapObjectDrawSettings(style2);
			writedEditor.addMapObjectDrawSettings(style3);
			writedEditor.setMapDrawSettings(new MapDrawSettings(Color.CYAN));
			writedEditor.writeToFile(new File(IOTester.TEST_FILE_NAME));

			StyleEditor readEditor = DrawingStylesFactory.createStyleEditor();
			IOTester.readFromTestFile(readEditor);


			assertEquals(writedEditor.getMapDrawSettings().getMapBackgroundColor(),
							readEditor.getMapDrawSettings().getMapBackgroundColor());
			// comparing only by description
			assertEquals(writedEditor.countOfMapObjectDrawSettings(), readEditor.countOfMapObjectDrawSettings());
			for (int i = 0; i < writedEditor.countOfMapObjectDrawSettings(); i++)
			{
				assertEquals(writedEditor.getMapObjectDrawSettings(i).getDescription(), readEditor.getMapObjectDrawSettings(i).getDescription());
			}
		}
		catch (Exception ex)
		{
			fail();
		}
	}

	/**
	 * readFromFile normal work test
	 */
	@Test
	public void readFromFileTest()
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
			writedEditor.setMapDrawSettings(new MapDrawSettings(Color.CYAN));
			IOTester.writeToTestFile(writedEditor);

			StyleEditor readEditor = DrawingStylesFactory.createStyleEditor();
			readEditor.readFromFile(new File(IOTester.TEST_FILE_NAME));

			assertEquals(writedEditor.getMapDrawSettings().getMapBackgroundColor(),
							readEditor.getMapDrawSettings().getMapBackgroundColor());
			// comparing only by description
			assertEquals(writedEditor.countOfMapObjectDrawSettings(), readEditor.countOfMapObjectDrawSettings());
			for (int i = 0; i < writedEditor.countOfMapObjectDrawSettings(); i++)
			{
				assertEquals(writedEditor.getMapObjectDrawSettings(i).getDescription(), readEditor.getMapObjectDrawSettings(i).getDescription());
			}
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
