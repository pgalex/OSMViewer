package mapTests;

import drawingStyles.DrawingStylesFactory;
import drawingStyles.EditableDefenitionTags;
import drawingStyles.MapObjectDrawSettings;
import drawingStyles.Tag;
import drawingStyles.StyleEditor;
import map.MapObject;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * MapObject class tests
 *
 * @author pgalex
 */
public class MapObjectTest
{
	/**
	 * Test creating with null tags
	 */
	@Test
	public void creatingWithNullTagsTest()
	{
	}

	/**
	 * Testing assigning styleIndex - normal work
	 */
	/*@Test
	public void assigningStyleIndexTest()
	{
		StyleEditor styleEditor = DrawingStylesFactory.createStyleEditor();

		EditableDefenitionTags tags1 = new EditableDefenitionTags();
		tags1.add(new Tag("k1", "v1"));
		tags1.add(new Tag("k2", "v2"));
		MapObjectDrawSettings style1 = new MapObjectDrawSettings(true, true, true, null, 0, "style1", null, tags1);
		styleEditor.addMapObjectDrawSettings(style1);

		EditableDefenitionTags tags2 = new EditableDefenitionTags();
		tags2.add(new Tag("k3", "v3"));
		tags2.add(new Tag("k4", "v4"));
		tags2.add(new Tag("k5", "v5"));
		MapObjectDrawSettings style2 = new MapObjectDrawSettings(false, false, false, null, 0, "style2", null, tags2);
		styleEditor.addMapObjectDrawSettings(style2);

		EditableDefenitionTags objectTags = new EditableDefenitionTags();
		objectTags.add(new Tag("k3", "v3"));
		objectTags.add(new Tag("k4", "v4"));
		objectTags.add(new Tag("k5", "v5"));
		objectTags.add(new Tag("k6", "v6"));
		MapObject testObject = new MapObject(0, objectTags);
		testObject.assignStyleIndex(styleEditor);

		assertEquals("style2", styleEditor.getMapObjectDrawSettings(testObject.getStyleIndex()).getDescription());

		// reassigning
		testObject.assignStyleIndex(null);
		assertEquals("style2", styleEditor.getMapObjectDrawSettings(testObject.getStyleIndex()).getDescription());
	}*/

	/**
	 * Testing assigning styleIndex by null style viewer. Style index should not
	 * be setted as null
	 */
	/*@Test
	public void assigningStyleIndexByNullViewerTest()
	{
		StyleEditor styleEditor = DrawingStylesFactory.createStyleEditor();

		EditableDefenitionTags tags1 = new EditableDefenitionTags();
		tags1.add(new Tag("k1", "v1"));
		MapObjectDrawSettings style1 = new MapObjectDrawSettings(true, true, true, null, 0, "style1", null, tags1);
		styleEditor.addMapObjectDrawSettings(style1);

		EditableDefenitionTags objectTags = new EditableDefenitionTags();
		objectTags.add(new Tag("k1", "v1"));
		MapObject testObject = new MapObject(0, objectTags);

		testObject.assignStyleIndex(styleEditor);
		testObject.assignStyleIndex(null);
		assertEquals("style1", styleEditor.getMapObjectDrawSettings(testObject.getStyleIndex()).getDescription());
	}*/

	/**
	 * Testing assignStyleIndex if style index not found
	 */
	/*@Test
	public void styleIndexNotFoundTest()
	{
		StyleEditor styleEditor = DrawingStylesFactory.createStyleEditor();

		EditableDefenitionTags tags1 = new EditableDefenitionTags();
		tags1.add(new Tag("k1", "v1"));
		tags1.add(new Tag("k2", "v2"));
		MapObjectDrawSettings style1 = new MapObjectDrawSettings(true, true, true, null, 0, "style1", null, tags1);
		styleEditor.addMapObjectDrawSettings(style1);

		EditableDefenitionTags tags2 = new EditableDefenitionTags();
		tags2.add(new Tag("k3", "v3"));
		tags2.add(new Tag("k4", "v4"));
		tags2.add(new Tag("k5", "v5"));
		MapObjectDrawSettings style2 = new MapObjectDrawSettings(false, false, false, null, 0, "style2", null, tags2);
		styleEditor.addMapObjectDrawSettings(style2);

		EditableDefenitionTags objectTags = new EditableDefenitionTags();
		objectTags.add(new Tag("k7", "v7"));
		objectTags.add(new Tag("k8", "v8"));
		MapObject testObject = new MapObject(0, objectTags);

		testObject.assignStyleIndex(styleEditor);

		assertEquals(null, testObject.getStyleIndex());
	}*/
}
