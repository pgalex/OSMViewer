package mapTests;

import drawingStyles.DrawingStylesFactory;
import drawingStyles.MapObjectDrawSettings;
import drawingStyles.StyleEditor;
import java.util.ArrayList;
import java.util.Collections;
import map.MapObject;
import map.MapPoint;
import mapDefenitionUtilities.DefenitionTags;
import mapDefenitionUtilities.MapPosition;
import mapDefenitionUtilities.Tag;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;
import rendering.RenderableMapObjectsDrawPriorityComparator;

/**
 * RenderableMapObjectsDrawPriorityComparator tests
 *
 * @author pgalex
 */
public class MapObjectDrawPriorityComparatorTest
{
	/**
	 * Testing sorting with comparator
	 */
	@Test
	public void sortingWithComparatorTest()
	{
		DefenitionTags tags1 = new DefenitionTags();
		tags1.add(new Tag("k1", "v1"));

		MapObjectDrawSettings style1 = new MapObjectDrawSettings();
		style1.setCanBePoint();
		style1.setDrawPriority(10);
		style1.setDefenitionTags(tags1);

		DefenitionTags tags2 = new DefenitionTags();
		tags2.add(new Tag("k2", "v2"));

		MapObjectDrawSettings style2 = new MapObjectDrawSettings();
		style2.setCanBePoint();
		style2.setDrawPriority(11);
		style2.setDefenitionTags(tags2);

		DefenitionTags tags3 = new DefenitionTags();
		tags3.add(new Tag("k3", "v3"));

		MapObjectDrawSettings style3 = new MapObjectDrawSettings();
		style3.setCanBePoint();
		style3.setDrawPriority(-10);
		style3.setDefenitionTags(tags3);

		ArrayList<MapObject> objects = new ArrayList<MapObject>();
		MapObject object1 = new MapPoint(new MapPosition(), 1, tags1);
		object1.setDrawSettings(style1);
		objects.add(object1);

		MapObject object2 = new MapPoint(new MapPosition(), 2, tags2);
		object2.setDrawSettings(style2);
		objects.add(object2);


		MapObject object3 = new MapPoint(new MapPosition(), 3, tags3);
		object3.setDrawSettings(style3);
		objects.add(object3);

		RenderableMapObjectsDrawPriorityComparator testComparator = new RenderableMapObjectsDrawPriorityComparator();
		Collections.sort(objects, testComparator);
		assertEquals(3, objects.get(0).getId());
		assertEquals(1, objects.get(1).getId());
		assertEquals(2, objects.get(2).getId());
	}
}
