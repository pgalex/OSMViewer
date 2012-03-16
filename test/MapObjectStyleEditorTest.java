
import drawingStyle.MapObjectStyle;
import drawingStyle.MapObjectStyleEditor;
import java.io.IOException;
import map.MapTag;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author abc
 */
public class MapObjectStyleEditorTest
{
	private final String TEST_FILE_NAME = "testFile.txt";

	public MapObjectStyleEditorTest()
	{
	}

	@Test
	public void fileTest()
	{
		MapObjectStyle style1 = new MapObjectStyle(true, true,
						true, "", 0, "style1", null);
		MapObjectStyle style2 = new MapObjectStyle(true, true,
						true, "", 0, "style2", null);

		MapObjectStyleEditor editor = new MapObjectStyleEditor();

		int style1Id = editor.addStyle(style1);
		int style2Id = editor.addStyle(style2);
		assertNotSame(style1, style2);

		try
		{
			editor.saveToFile(TEST_FILE_NAME);
		}
		catch (IOException ex)
		{
			fail();
		}

		editor = new MapObjectStyleEditor();
		try
		{
			editor.loadFromFile(TEST_FILE_NAME);
			assertEquals(style2.getDescription(), editor.getStyle(style2Id).getDescription());
			assertEquals(style1.getDescription(), editor.getStyle(style1Id).getDescription());
		}
		catch (IOException ex)
		{
			fail();
		}

	}

	@Test
	public void addingTest()
	{
		MapObjectStyleEditor editor = new MapObjectStyleEditor();
		assertEquals(0, editor.getStylesCount());

		assertEquals(0, editor.addStyle(new MapObjectStyle()));
		assertEquals(1, editor.getStylesCount());

		assertEquals(1, editor.addStyle(new MapObjectStyle()));
		assertEquals(2, editor.getStylesCount());

		editor.clear();
		assertEquals(0, editor.getStylesCount());
	}

	@Test
	public void editTest()
	{
		MapObjectStyleEditor editor = new MapObjectStyleEditor();

		MapObjectStyle style1 = new MapObjectStyle(true, true,
						true, "name", 1, "style1", null);
		style1.defenitionTags.add(new MapTag("k1", "v1"));

		MapObjectStyle style2 = new MapObjectStyle(false, false,
						false, "", 2, "style1", null);
		style2.defenitionTags.add(new MapTag("k2", "v2"));

		assertEquals(0, editor.getStylesCount());

		//один элемент
		int style1Id = editor.addStyle(style1);
		assertEquals(1, editor.getStylesCount());
		assertEquals(true, editor.getStyle(style1Id).equals(style1));

		//два
		int style2Id = editor.addStyle(style2);
		assertEquals(2, editor.getStylesCount());
		assertEquals(true, editor.getStyle(style2Id).equals(style2));

		//изменение
		editor.editStyle(style1Id, style2);
		assertEquals(true, editor.getStyle(style1Id).equals(style2));

		editor.clear();
		assertEquals(0, editor.getStylesCount());
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
