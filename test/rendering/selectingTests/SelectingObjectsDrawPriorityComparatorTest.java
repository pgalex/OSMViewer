package rendering.selectingTests;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import map.MapPoint;
import mapDefenitionUtilities.DefenitionTags;
import mapDefenitionUtilities.MapPosition;
import static org.junit.Assert.*;
import org.junit.Test;
import rendering.selectng.SelectingObject;
import rendering.selectng.SelectingObjectsDrawPriorityComparator;
import rendering.selectng.SelectingRectangle;

/**
 * Tests of SelectingObjectsDrawPriorityComparator class
 *
 * @author pgalex
 */
public class SelectingObjectsDrawPriorityComparatorTest
{
	/**
	 * Test sorting objects with comparator
	 */
	@Test
	public void sortingWithComparatorTest()
	{
		MapPoint someObject1 = new MapPoint(new MapPosition(), 1, new DefenitionTags());
		SelectingRectangle selectingRectangle1 = new SelectingRectangle(someObject1, -5, new Rectangle2D.Double());

		MapPoint someObject2 = new MapPoint(new MapPosition(), 2, new DefenitionTags());
		SelectingRectangle selectingRectangle2 = new SelectingRectangle(someObject2, 3, new Rectangle2D.Double());

		ArrayList<SelectingObject> selectingObjectsArray = new ArrayList<SelectingObject>();
		selectingObjectsArray.add(selectingRectangle1);
		selectingObjectsArray.add(selectingRectangle2);

		Collections.sort(selectingObjectsArray, new SelectingObjectsDrawPriorityComparator());

		assertEquals(someObject2, selectingObjectsArray.get(0).getAssociatedMapObject());
		assertEquals(someObject1, selectingObjectsArray.get(1).getAssociatedMapObject());
	}
}
