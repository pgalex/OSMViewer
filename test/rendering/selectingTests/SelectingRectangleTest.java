package rendering.selectingTests;

import drawingStyles.DefenitionTags;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import map.MapPoint;
import map.MapPosition;
import org.junit.Test;
import static org.junit.Assert.*;
import rendering.selectng.SelectingRectangle;

/**
 * Tests of SelectingRectangle class
 *
 * @author pgalex
 */
public class SelectingRectangleTest
{
	/**
	 * Test creating selecting rectangle with null associated map object
	 */
	@Test
	public void creatingWithNullAssociatedObjectTest()
	{
		try
		{
			SelectingRectangle testRectangle = new SelectingRectangle(null, 0, new Rectangle2D.Double());
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test creating selecting rectangle with null rectangle
	 */
	@Test
	public void creatingWithNullRectangleTest()
	{
		try
		{
			MapPoint someObject = new MapPoint(new MapPosition(), 1, new DefenitionTags());
			SelectingRectangle testRectangle = new SelectingRectangle(someObject, 0, null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test is hits with null point
	 */
	@Test
	public void isHitsByNullPointTest()
	{
		try
		{
			MapPoint someObject = new MapPoint(new MapPosition(), 1, new DefenitionTags());
			SelectingRectangle testRectangle = new SelectingRectangle(someObject, 0,
							new Rectangle2D.Double(10, 20, 5, 10));
			testRectangle.isHitsByPoint(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test is hits with point inside rectangle
	 */
	@Test
	public void isHitsByPointInsideRectangleTest()
	{
		MapPoint someObject = new MapPoint(new MapPosition(), 1, new DefenitionTags());
		SelectingRectangle testRectangle = new SelectingRectangle(someObject, 0,
						new Rectangle2D.Double(10, 20, 5, 10));
		assertTrue(testRectangle.isHitsByPoint(new Point2D.Double(12, 25)));
	}

	/**
	 * Test is hits with point outside rectangle
	 */
	@Test
	public void isHitsByPointOutsideRectangleTest()
	{
		MapPoint someObject = new MapPoint(new MapPosition(), 1, new DefenitionTags());
		SelectingRectangle testRectangle = new SelectingRectangle(someObject, 0,
						new Rectangle2D.Double(10, 20, 5, 10));
		assertFalse(testRectangle.isHitsByPoint(new Point2D.Double(5, 3)));
	}
}
