package mapTests;

import drawingStyles.DrawingStylesFactory;
import drawingStyles.DefenitionTags;
import drawingStyles.MapObjectDrawSettings;
import drawingStyles.Tag;
import drawingStyles.StyleEditor;
import java.util.ArrayList;
import java.util.Collections;
import map.MapObject;
import rendering.RenderableMapObjectsDrawPriorityComparator;
import map.MapPoint;
import MapDefenitionUtilities.MapPosition;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 * RenderableMapObjectsDrawPriorityComparator tests
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
			RenderableMapObjectsDrawPriorityComparator testComparator = new RenderableMapObjectsDrawPriorityComparator(null);
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
		DefenitionTags tags1 = new DefenitionTags();
		tags1.add(new Tag("k1", "v1"));
		
		MapObjectDrawSettings style1 = new MapObjectDrawSettings();
		style1.setCanBePoint();
		style1.setDrawPriority(10);
		style1.setDefenitionTags(tags1);
		editor.addMapObjectDrawSettings(style1);

		DefenitionTags tags2 = new DefenitionTags();
		tags2.add(new Tag("k2", "v2"));
		
		MapObjectDrawSettings style2 = new MapObjectDrawSettings();
		style2.setCanBePoint();
		style2.setDrawPriority(11);
		style2.setDefenitionTags(tags2);
		editor.addMapObjectDrawSettings(style2);

		DefenitionTags tags3 = new DefenitionTags();
		tags3.add(new Tag("k3", "v3"));
		
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

		RenderableMapObjectsDrawPriorityComparator testComparator = new RenderableMapObjectsDrawPriorityComparator(editor);
		Collections.sort(objects, testComparator);
		assertEquals(3, objects.get(0).getId());
		assertEquals(1, objects.get(1).getId());
		assertEquals(2, objects.get(2).getId());
	}
}
