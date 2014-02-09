package com.osmviewer.renderingTests;

import com.osmviewer.mapDefenitionUtilities.DefenitionTags;
import com.osmviewer.mapDefenitionUtilities.Tag;
import com.osmviewer.rendering.RenderableMapObject;
import com.osmviewer.rendering.RenderableMapObjectsDrawPriorityComparator;
import java.util.ArrayList;
import java.util.Collections;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * RenderableMapObjectsDrawPriorityComparator tests
 *
 * @author pgalex
 */
public class RenderableMapObjectsDrawPriorityComparatorTest
{
	/**
	 * Testing sorting with comparator
	 */
	@Test
	public void sortingWithComparatorTest()
	{
		/*DefenitionTags tags1 = new DefenitionTags();
		tags1.add(new Tag("k1", "v1"));

		TestRenderableMapObjectDrawSettings style1 = new TestRenderableMapObjectDrawSettings();
		style1.pointDrawPriority = 10;

		DefenitionTags tags2 = new DefenitionTags();
		tags2.add(new Tag("k2", "v2"));

		TestRenderableMapObjectDrawSettings style2 = new TestRenderableMapObjectDrawSettings();
		style2.pointDrawPriority = 11;

		DefenitionTags tags3 = new DefenitionTags();
		tags3.add(new Tag("k3", "v3"));

		TestRenderableMapObjectDrawSettings style3 = new TestRenderableMapObjectDrawSettings();
		style3.pointDrawPriority = -10;

		ArrayList<RenderableMapObject> objects = new ArrayList<RenderableMapObject>();
		TestRenderableMapObject object1 = new TestRenderableMapObject();
		object1.drawSettings = style1;
		objects.add(object1);

		TestRenderableMapObject object2 = new TestRenderableMapObject();
		object2.drawSettings = style2;
		objects.add(object2);


		TestRenderableMapObject object3 = new TestRenderableMapObject();
		object3.drawSettings = style3;
		objects.add(object3);

		RenderableMapObjectsDrawPriorityComparator testComparator = new RenderableMapObjectsDrawPriorityComparator();
		Collections.sort(objects, testComparator);
		assertEquals(object3, objects.get(0));
		assertEquals(object1, objects.get(1));
		assertEquals(object2, objects.get(2));*/
	}
}
