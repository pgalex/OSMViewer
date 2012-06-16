package mapTests;

import drawingStyles.DrawingStylesFactory;
import drawingStyles.MapObjectStyle;
import drawingStyles.StyleEditor;
import java.util.ArrayList;
import java.util.Collections;
import map.EditableDefenitionTags;
import map.MapObject;
import map.MapObjectDrawPriorityComparator;
import map.MapTag;
import map.exceptions.StyleViewerIsNullException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 * MapObjectDrawPriorityComparator tests
 *
 * @author pgalex
 */
public class MapObjectDrawPriorityComparatorTest
{
	/**
	 * Testing constructor with null style viewer
	 */
	@Test
	public void creatingWithNullStyleViewerTest()
	{
		try
		{
			MapObjectDrawPriorityComparator testComparator = new MapObjectDrawPriorityComparator(null);
			fail();
		}
		catch (StyleViewerIsNullException ex)
		{
			//ok
		}
	}

	/**
	 * Testing sorting with comparator
	 */
	@Test
	public void sortingWithComparatorTest()
	{
		StyleEditor editor = DrawingStylesFactory.createStyleEditor();
		EditableDefenitionTags tags1 = new EditableDefenitionTags();
		tags1.add(new MapTag("k1", "v1"));
		editor.addMapObjectStyle(new MapObjectStyle(false, false, false, null, 10, "", null, tags1));
		
		EditableDefenitionTags tags2 = new EditableDefenitionTags();
		tags2.add(new MapTag("k2", "v2"));
		editor.addMapObjectStyle(new MapObjectStyle(false, false, false, null, 11, "", null, tags2));
		
		EditableDefenitionTags tags3 = new EditableDefenitionTags();
		tags3.add(new MapTag("k3", "v3"));
		editor.addMapObjectStyle(new MapObjectStyle(false, false, false, null, -10, "", null, tags3));

		ArrayList<MapObject> objects = new ArrayList<MapObject>();
		MapObject object1 = new MapObject(1, tags1);
		object1.assignStyleIndex(editor);
		objects.add(object1);

		MapObject object2 = new MapObject(2, tags2);
		object2.assignStyleIndex(editor);
		objects.add(object2);


		MapObject object3 = new MapObject(3, tags3);
		object3.assignStyleIndex(editor);
		objects.add(object3);

		MapObjectDrawPriorityComparator testComparator = new MapObjectDrawPriorityComparator(editor);
		Collections.sort(objects, testComparator);
		assertEquals(3, objects.get(0).getId());
		assertEquals(1, objects.get(1).getId());
		assertEquals(2, objects.get(2).getId());
	}
}
