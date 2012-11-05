package mapTests;

import drawingStyles.DrawingStylesFactory;
import drawingStyles.EditableDefenitionTags;
import drawingStyles.MapObjectDrawSettings;
import drawingStyles.MapTag;
import drawingStyles.StyleEditor;
import java.util.ArrayList;
import java.util.Collections;
import map.MapObject;
import map.MapObjectDrawPriorityComparator;
import map.MapPoint;
import map.MapPosition;
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
		catch (IllegalArgumentException ex)
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
		
		MapObjectDrawSettings style1 = new MapObjectDrawSettings();
		style1.setCanBePoint();
		style1.setDrawPriority(10);
		style1.setDefenitionTags(tags1);
		editor.addMapObjectDrawSettings(style1);

		EditableDefenitionTags tags2 = new EditableDefenitionTags();
		tags2.add(new MapTag("k2", "v2"));
		
		MapObjectDrawSettings style2 = new MapObjectDrawSettings();
		style2.setCanBePoint();
		style2.setDrawPriority(11);
		style2.setDefenitionTags(tags2);
		editor.addMapObjectDrawSettings(style2);

		EditableDefenitionTags tags3 = new EditableDefenitionTags();
		tags3.add(new MapTag("k3", "v3"));
		
		MapObjectDrawSettings style3 = new MapObjectDrawSettings();
		style3.setCanBePoint();
		style3.setDrawPriority(-10);
		style3.setDefenitionTags(tags3);
		editor.addMapObjectDrawSettings(style3);

		ArrayList<MapObject> objects = new ArrayList<MapObject>();
		MapObject object1 = new MapPoint(new MapPosition(), 1, tags1);
		object1.assignStyleIndex(editor);
		objects.add(object1);

		MapObject object2 = new MapPoint(new MapPosition(), 2, tags2);
		object2.assignStyleIndex(editor);
		objects.add(object2);


		MapObject object3 = new MapPoint(new MapPosition(), 3, tags3);
		object3.assignStyleIndex(editor);
		objects.add(object3);

		MapObjectDrawPriorityComparator testComparator = new MapObjectDrawPriorityComparator(editor);
		Collections.sort(objects, testComparator);
		assertEquals(3, objects.get(0).getId());
		assertEquals(1, objects.get(1).getId());
		assertEquals(2, objects.get(2).getId());
	}
}
