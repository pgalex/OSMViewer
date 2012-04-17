package mapTests;

import drawingStyle.DrawingStyleFactory;
import drawingStyle.MapObjectStyle;
import drawingStyle.StyleEditor;
import map.EditableDefenitionTags;
import map.MapObject;
import map.MapTag;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author pgalex
 */
public class MapObjectTest
{
	public MapObjectTest()
	{
	}

	/**
	 * Auto initialize in contructor test
	 */
	@Test
	public void autoInitializeTest()
	{
		MapObject testObject = new MapObject(0, null);
		assertNotNull(testObject.getDefenitionTags());
	}

	/**
	 * assigning in contructor test
	 */
	@Test
	public void contructorTest()
	{
		EditableDefenitionTags tags = new EditableDefenitionTags();
		tags.add(new MapTag("k1", "v1"));

		MapObject testObject = new MapObject(10, tags);

		assertEquals(10, testObject.getId());
		assertNull(testObject.getStyleIndex());
		assertEquals(tags.size(), testObject.getDefenitionTags().size());
		for (int i = 0; i < tags.size(); i++)
			assertTrue(tags.get(i).compareTo(testObject.getDefenitionTags().get(i)));
	}

	/**
	 * Testing assigning, getting styleIndex - normal work
	 */
	@Test
	public void styleIndexNormalTest()
	{
		StyleEditor styleEditor = DrawingStyleFactory.createStyleEditor();

		EditableDefenitionTags tags1 = new EditableDefenitionTags();
		tags1.add(new MapTag("k1", "v1"));
		tags1.add(new MapTag("k2", "v2"));
		MapObjectStyle style1 = new MapObjectStyle(true, true, true, null, 0, "style1", null, tags1);
		styleEditor.add(style1);

		EditableDefenitionTags tags2 = new EditableDefenitionTags();
		tags2.add(new MapTag("k3", "v3"));
		tags2.add(new MapTag("k4", "v4"));
		tags2.add(new MapTag("k5", "v5"));
		MapObjectStyle style2 = new MapObjectStyle(false, false, false, null, 0, "style2", null, tags2);
		styleEditor.add(style2);

		EditableDefenitionTags objectTags = new EditableDefenitionTags();
		objectTags.add(new MapTag("k3", "v3"));
		objectTags.add(new MapTag("k4", "v4"));
		objectTags.add(new MapTag("k5", "v5"));
		objectTags.add(new MapTag("k6", "v6"));
		MapObject testObject = new MapObject(0, objectTags);
		testObject.assignStyleIndex(styleEditor);
		assertEquals("style2", styleEditor.getMapObjectStyle(testObject.getStyleIndex()).getDescription());

		// reassigning
		testObject.assignStyleIndex(null);
		assertEquals("style2", styleEditor.getMapObjectStyle(testObject.getStyleIndex()).getDescription());
	}

	/**
	 * Testing assignStyleIndex if style index not found
	 */
	@Test
	public void styleIndexNotFoundTest()
	{
		StyleEditor styleEditor = DrawingStyleFactory.createStyleEditor();

		EditableDefenitionTags tags1 = new EditableDefenitionTags();
		tags1.add(new MapTag("k1", "v1"));
		tags1.add(new MapTag("k2", "v2"));
		MapObjectStyle style1 = new MapObjectStyle(true, true, true, null, 0, "style1", null, tags1);
		styleEditor.add(style1);

		EditableDefenitionTags tags2 = new EditableDefenitionTags();
		tags2.add(new MapTag("k3", "v3"));
		tags2.add(new MapTag("k4", "v4"));
		tags2.add(new MapTag("k5", "v5"));
		MapObjectStyle style2 = new MapObjectStyle(false, false, false, null, 0, "style2", null, tags2);
		styleEditor.add(style2);

		EditableDefenitionTags objectTags = new EditableDefenitionTags();
		objectTags.add(new MapTag("k7", "v7"));
		objectTags.add(new MapTag("k8", "v8"));
		MapObject testObject = new MapObject(0, objectTags);
		testObject.assignStyleIndex(styleEditor);
		assertEquals(null, testObject.getStyleIndex());
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
