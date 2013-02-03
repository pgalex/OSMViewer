package rendering.selectingTests;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import map.MapPoint;
import mapDefenitionUtilities.DefenitionTags;
import mapDefenitionUtilities.MapPosition;
import static org.junit.Assert.*;
import org.junit.Test;
import rendering.RenderableMapObject;
import rendering.selectng.SelectingBuffer;
import rendering.selectng.SelectingLine;
import rendering.selectng.SelectingRectangle;

/**
 * Tests of SelectingBuffer class
 *
 * @author pgalex
 */
public class SelectingBufferTest
{
	/**
	 * Test adding null selecting object
	 */
	@Test
	public void addingNullObjectTest()
	{
		try
		{
			SelectingBuffer testBuffer = new SelectingBuffer();
			testBuffer.addSelectingObject(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test finding objects at null point
	 */
	@Test
	public void findObjectsAtNullPointTest()
	{
		try
		{
			SelectingBuffer testBuffer = new SelectingBuffer();
			testBuffer.findObjectsAtPoint(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test finding objects at null point
	 */
	@Test
	public void findObjectsInEmptyBufferTest()
	{
		SelectingBuffer testBuffer = new SelectingBuffer();
		RenderableMapObject[] foundObjects = testBuffer.findObjectsAtPoint(new Point2D.Double(0, 0));
		assertNotNull(foundObjects);
		assertEquals(0, foundObjects.length);
	}

	/**
	 * Test using selecting buffer in standart way
	 */
	@Test
	public void normalWorkTest()
	{
		MapPoint objectUnderPoint = new MapPoint(new MapPosition(), 1, new DefenitionTags());
		Point2D[] lineUnderPointPoints = new Point2D[2];
		lineUnderPointPoints[0] = new Point2D.Double(0, 0);
		lineUnderPointPoints[1] = new Point2D.Double(5, 5);
		SelectingLine lineUnderPoint = new SelectingLine(objectUnderPoint, 1, lineUnderPointPoints, 1);

		MapPoint objectNotUnderPoint = new MapPoint(new MapPosition(), 1, new DefenitionTags());
		Point2D[] lineNotUnderPointPoints = new Point2D[2];
		lineNotUnderPointPoints[0] = new Point2D.Double(10, 10);
		lineNotUnderPointPoints[1] = new Point2D.Double(7, 7);
		SelectingLine lineNotUnderPoint = new SelectingLine(objectNotUnderPoint, 2, lineNotUnderPointPoints, 1);

		SelectingBuffer testBuffer = new SelectingBuffer();
		testBuffer.addSelectingObject(lineUnderPoint);
		testBuffer.addSelectingObject(lineNotUnderPoint);
		RenderableMapObject[] foundObject = testBuffer.findObjectsAtPoint(new Point2D.Double(3, 3));

		assertEquals(1, foundObject.length);
		assertEquals(objectUnderPoint, foundObject[0]);
	}

	/**
	 * Test sorting found objects by draw priority
	 */
	@Test
	public void sortingByDrawPriorityTest()
	{
		MapPoint someObject1 = new MapPoint(new MapPosition(), 1, new DefenitionTags());
		SelectingRectangle selectingRectangle1 = new SelectingRectangle(someObject1, -5,
						new Rectangle2D.Double(0, 0, 5, 5));

		MapPoint someObject2 = new MapPoint(new MapPosition(), 2, new DefenitionTags());
		SelectingRectangle selectingRectangle2 = new SelectingRectangle(someObject2, 3,
						new Rectangle2D.Double(2, 2, 7, 7));

		SelectingBuffer testBuffer = new SelectingBuffer();
		testBuffer.addSelectingObject(selectingRectangle1);
		testBuffer.addSelectingObject(selectingRectangle2);
		RenderableMapObject[] foundObject = testBuffer.findObjectsAtPoint(new Point2D.Double(3, 3));

		assertEquals(someObject2, foundObject[0]);
		assertEquals(someObject1, foundObject[1]);
	}
}
