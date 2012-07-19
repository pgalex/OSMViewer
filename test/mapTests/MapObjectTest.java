package mapTests;

import drawingStyles.DrawingStylesFactory;
import drawingStyles.MapObjectDrawSettings;
import drawingStyles.StyleEditor;
import drawingStyles.EditableDefenitionTags;
import map.MapObject;
import drawingStyles.MapTag;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

/**
 * MapObject class tests
 *
 * @author pgalex
 */
public class MapObjectTest
{
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
	 * Testing assigning styleIndex - normal work
	 */
	@Test
	public void assigningStyleIndexTest()
	{
		StyleEditor styleEditor = DrawingStylesFactory.createStyleEditor();

		EditableDefenitionTags tags1 = new EditableDefenitionTags();
		tags1.add(new MapTag("k1", "v1"));
		tags1.add(new MapTag("k2", "v2"));
		MapObjectDrawSettings style1 = new MapObjectDrawSettings(true, true, true, null, 0, "style1", null, tags1);
		styleEditor.addMapObjectStyle(style1);

		EditableDefenitionTags tags2 = new EditableDefenitionTags();
		tags2.add(new MapTag("k3", "v3"));
		tags2.add(new MapTag("k4", "v4"));
		tags2.add(new MapTag("k5", "v5"));
		MapObjectDrawSettings style2 = new MapObjectDrawSettings(false, false, false, null, 0, "style2", null, tags2);
		styleEditor.addMapObjectStyle(style2);

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
	 * Testing assigning styleIndex by null style viewer. Style index should not
	 * be setted as null
	 */
	@Test
	public void assigningStyleIndexByNullViewerTest()
	{
		StyleEditor styleEditor = DrawingStylesFactory.createStyleEditor();

		EditableDefenitionTags tags1 = new EditableDefenitionTags();
		tags1.add(new MapTag("k1", "v1"));
		MapObjectDrawSettings style1 = new MapObjectDrawSettings(true, true, true, null, 0, "style1", null, tags1);
		styleEditor.addMapObjectStyle(style1);

		EditableDefenitionTags objectTags = new EditableDefenitionTags();
		objectTags.add(new MapTag("k1", "v1"));
		MapObject testObject = new MapObject(0, objectTags);

		testObject.assignStyleIndex(styleEditor);
		testObject.assignStyleIndex(null);
		assertEquals("style1", styleEditor.getMapObjectStyle(testObject.getStyleIndex()).getDescription());
	}

	/**
	 * Testing assignStyleIndex if style index not found
	 */
	@Test
	public void styleIndexNotFoundTest()
	{
		StyleEditor styleEditor = DrawingStylesFactory.createStyleEditor();

		EditableDefenitionTags tags1 = new EditableDefenitionTags();
		tags1.add(new MapTag("k1", "v1"));
		tags1.add(new MapTag("k2", "v2"));
		MapObjectDrawSettings style1 = new MapObjectDrawSettings(true, true, true, null, 0, "style1", null, tags1);
		styleEditor.addMapObjectStyle(style1);

		EditableDefenitionTags tags2 = new EditableDefenitionTags();
		tags2.add(new MapTag("k3", "v3"));
		tags2.add(new MapTag("k4", "v4"));
		tags2.add(new MapTag("k5", "v5"));
		MapObjectDrawSettings style2 = new MapObjectDrawSettings(false, false, false, null, 0, "style2", null, tags2);
		styleEditor.addMapObjectStyle(style2);

		EditableDefenitionTags objectTags = new EditableDefenitionTags();
		objectTags.add(new MapTag("k7", "v7"));
		objectTags.add(new MapTag("k8", "v8"));
		MapObject testObject = new MapObject(0, objectTags);

		testObject.assignStyleIndex(styleEditor);

		assertEquals(null, testObject.getStyleIndex());
	}
}
