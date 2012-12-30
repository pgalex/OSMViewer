package rendering.selectingTests;

import drawingStyles.DefenitionTags;
import java.awt.geom.Point2D;
import map.MapObject;
import map.MapPoint;
import map.MapPosition;
import static org.junit.Assert.*;
import org.junit.Test;
import rendering.selectng.SelectingBuffer;
import rendering.selectng.SelectingLine;

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
		MapObject[] foundObjects = testBuffer.findObjectsAtPoint(new Point2D.Double(0, 0));
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
		SelectingLine lineUnderPoint = new SelectingLine(objectUnderPoint, lineUnderPointPoints, 1);

		MapPoint objectNotUnderPoint = new MapPoint(new MapPosition(), 1, new DefenitionTags());
		Point2D[] lineNotUnderPointPoints = new Point2D[2];
		lineNotUnderPointPoints[0] = new Point2D.Double(10, 10);
		lineNotUnderPointPoints[1] = new Point2D.Double(7, 7);
		SelectingLine lineNotUnderPoint = new SelectingLine(objectNotUnderPoint, lineNotUnderPointPoints, 1);

		SelectingBuffer testBuffer = new SelectingBuffer();
		testBuffer.addSelectingObject(lineUnderPoint);
		testBuffer.addSelectingObject(lineNotUnderPoint);
		MapObject[] foundObject = testBuffer.findObjectsAtPoint(new Point2D.Double(3, 3));

		assertEquals(1, foundObject.length);
		assertEquals(objectUnderPoint, foundObject[0]);
	}
}
