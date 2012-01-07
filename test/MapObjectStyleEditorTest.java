/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import map.MapTag;
import drawingStyle.MapObjectStyle;
import drawingStyle.MapObjectStyleEditor;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author abc
 */
public class MapObjectStyleEditorTest
{
	public MapObjectStyleEditorTest()
	{
	}

	@Test
	public void AddingTest()
	{
		MapObjectStyleEditor editor = new MapObjectStyleEditor();
		assertEquals(0, editor.getStylesCount());

		assertEquals(0, editor.AddStyle(new MapObjectStyle()));
		assertEquals(1, editor.getStylesCount());

		assertEquals(1, editor.AddStyle(new MapObjectStyle()));
		assertEquals(2, editor.getStylesCount());

		editor.Clear();
		assertEquals(0, editor.getStylesCount());
	}

	@Test
	public void EditTest()
	{
		MapObjectStyleEditor editor = new MapObjectStyleEditor();
		MapObjectStyle style1 = new MapObjectStyle();
		style1.canBeLine = true;
		style1.canBePoint = true;
		style1.canBePolygon = true;
		style1.defenitionTags.add(new MapTag("k1", "v1"));
		style1.drawPriority = 1;
		style1.textTagKey = "name";
		MapObjectStyle style2 = new MapObjectStyle();
		style2.canBeLine = false;
		style2.canBePoint = false;
		style2.canBePolygon = false;
		style2.defenitionTags.add(new MapTag("k2", "v2"));
		style2.drawPriority = 2;
		style2.textTagKey = "";

		assertEquals(0, editor.getStylesCount());

		//один элемент
		int style1Id = editor.AddStyle(style1);
		assertEquals(1, editor.getStylesCount());
		assertEquals(true, editor.GetStyle(style1Id).equals(style1));

		//два
		int style2Id = editor.AddStyle(style2);
		assertEquals(2, editor.getStylesCount());
		assertEquals(true, editor.GetStyle(style2Id).equals(style2));

		//изменение
		editor.EditStyle(style1Id, style2);
		assertEquals(true, editor.GetStyle(style1Id).equals(style2));

		editor.Clear();
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
