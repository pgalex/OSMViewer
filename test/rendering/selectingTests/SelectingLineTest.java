package rendering.selectingTests;

import drawingStyles.DefenitionTags;
import java.awt.geom.Point2D;
import map.MapPoint;
import MapDefenitionUtilities.MapPosition;
import static org.junit.Assert.*;
import org.junit.Test;
import rendering.selectng.SelectingLine;

/**
 * Tests of SelectingLine class
 *
 * @author pgalex
 */
public class SelectingLineTest
{
	/**
	 * Test creating with null associated object
	 */
	@Test
	public void creatingWithNullObjectTest()
	{
		try
		{
			Point2D[] linePoints = new Point2D[2];
			linePoints[0] = new Point2D.Double(1, 2);
			linePoints[1] = new Point2D.Double(3, 4);
			SelectingLine testLine = new SelectingLine(null, 0, linePoints, 1);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test creating with null points
	 */
	@Test
	public void creatingWithNullPointsTest()
	{
		try
		{
			MapPoint someObject = new MapPoint(new MapPosition(), 1, new DefenitionTags());

			SelectingLine testLine = new SelectingLine(someObject, 0, null, 1);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test creating with points containes null elements
	 */
	@Test
	public void creatingWithPointsContainsNullTest()
	{
		try
		{
			MapPoint someObject = new MapPoint(new MapPosition(), 1, new DefenitionTags());

			Point2D[] linePoints = new Point2D[3];
			linePoints[0] = new Point2D.Double(1, 2);
			linePoints[1] = new Point2D.Double(3, 4);
			linePoints[2] = null;

			SelectingLine testLine = new SelectingLine(someObject, 0, linePoints, 1);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test creating with points containes less than 2 elements
	 */
	@Test
	public void creatingWithPointsContainsLessThan2ElementsTest()
	{
		try
		{
			MapPoint someObject = new MapPoint(new MapPosition(), 1, new DefenitionTags());

			Point2D[] linePoints = new Point2D[1];
			linePoints[0] = new Point2D.Double(1, 2);

			SelectingLine testLine = new SelectingLine(someObject, 0, linePoints, 1);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test creating with zero line width
	 */
	@Test
	public void creatingWithZeroWidthTest()
	{
		try
		{
			MapPoint someObject = new MapPoint(new MapPosition(), 1, new DefenitionTags());

			Point2D[] linePoints = new Point2D[2];
			linePoints[0] = new Point2D.Double(1, 2);
			linePoints[1] = new Point2D.Double(3, 4);

			SelectingLine testLine = new SelectingLine(someObject, 0, linePoints, 0);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test creating with width less than zero
	 */
	@Test
	public void creatingWithNegativeWidthTest()
	{
		try
		{
			MapPoint someObject = new MapPoint(new MapPosition(), 1, new DefenitionTags());

			Point2D[] linePoints = new Point2D[2];
			linePoints[0] = new Point2D.Double(1, 2);
			linePoints[1] = new Point2D.Double(3, 4);

			SelectingLine testLine = new SelectingLine(someObject, 0, linePoints, -1);
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

			Point2D[] linePoints = new Point2D[2];
			linePoints[0] = new Point2D.Double(1, 2);
			linePoints[1] = new Point2D.Double(3, 4);

			SelectingLine testLine = new SelectingLine(someObject, 0, linePoints, 1);
			testLine.isHitsByPoint(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test is hits by point on line
	 */
	@Test
	public void isHitsByPointOnLineTest()
	{
		MapPoint someObject = new MapPoint(new MapPosition(), 1, new DefenitionTags());

		Point2D[] linePoints = new Point2D[2];
		linePoints[0] = new Point2D.Double(0, 0);
		linePoints[1] = new Point2D.Double(5, 5);

		SelectingLine testLine = new SelectingLine(someObject, 0, linePoints, 1);
		assertTrue(testLine.isHitsByPoint(new Point2D.Double(3, 3)));
	}

	/**
	 * Test is hits by point on line with line width
	 */
	@Test
	public void isHitsByPointOnLineIncludingWidthTest()
	{
		MapPoint someObject = new MapPoint(new MapPosition(), 1, new DefenitionTags());

		Point2D[] linePoints = new Point2D[2];
		linePoints[0] = new Point2D.Double(0, 5);
		linePoints[1] = new Point2D.Double(15, 5);

		SelectingLine testLine = new SelectingLine(someObject, 0, linePoints, 5);
		assertTrue(testLine.isHitsByPoint(new Point2D.Double(0, 0)));
	}

	/**
	 * Test is hits by point on line with line width, not hits
	 */
	@Test
	public void isNotHitsByPointOnLineIncludingWidthTest()
	{
		MapPoint someObject = new MapPoint(new MapPosition(), 1, new DefenitionTags());

		Point2D[] linePoints = new Point2D[2];
		linePoints[0] = new Point2D.Double(0, 5);
		linePoints[1] = new Point2D.Double(15, 5);

		SelectingLine testLine = new SelectingLine(someObject, 0, linePoints, 4);
		assertFalse(testLine.isHitsByPoint(new Point2D.Double(0, 0)));
	}
}
