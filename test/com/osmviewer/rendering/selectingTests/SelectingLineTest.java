package com.osmviewer.rendering.selectingTests;

import java.awt.geom.Point2D;
import static org.junit.Assert.*;
import org.junit.Test;
import com.osmviewer.rendering.RenderableMapObject;
import com.osmviewer.rendering.selectng.SelectingLine;

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
		/*try
		{
			RenderableMapObject someObject = new TestRenderableMapObject();

			SelectingLine testLine = new SelectingLine(someObject, 0, null, 1);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}*/
	}

	/**
	 * Test creating with points containes null elements
	 */
	@Test
	public void creatingWithPointsContainsNullTest()
	{
		/*try
		{
			RenderableMapObject someObject = new TestRenderableMapObject();

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
		}*/
	}

	/**
	 * Test creating with points containes less than 2 elements
	 */
	@Test
	public void creatingWithPointsContainsLessThan2ElementsTest()
	{
		/*try
		{
			RenderableMapObject someObject = new TestRenderableMapObject();

			Point2D[] linePoints = new Point2D[1];
			linePoints[0] = new Point2D.Double(1, 2);

			SelectingLine testLine = new SelectingLine(someObject, 0, linePoints, 1);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}*/
	}

	/**
	 * Test creating with zero line width
	 */
	@Test
	public void creatingWithZeroWidthTest()
	{
		/*try
		{
			RenderableMapObject someObject = new TestRenderableMapObject();

			Point2D[] linePoints = new Point2D[2];
			linePoints[0] = new Point2D.Double(1, 2);
			linePoints[1] = new Point2D.Double(3, 4);

			SelectingLine testLine = new SelectingLine(someObject, 0, linePoints, 0);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}*/
	}

	/**
	 * Test creating with width less than zero
	 */
	@Test
	public void creatingWithNegativeWidthTest()
	{
		/*try
		{
			RenderableMapObject someObject = new TestRenderableMapObject();

			Point2D[] linePoints = new Point2D[2];
			linePoints[0] = new Point2D.Double(1, 2);
			linePoints[1] = new Point2D.Double(3, 4);

			SelectingLine testLine = new SelectingLine(someObject, 0, linePoints, -1);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}*/
	}

	/**
	 * Test is hits with null point
	 */
	@Test
	public void isHitsByNullPointTest()
	{
		/*try
		{
			RenderableMapObject someObject = new TestRenderableMapObject();

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
		}*/
	}

	/**
	 * Test is hits by point on line
	 */
	@Test
	public void isHitsByPointOnLineTest()
	{
		/*RenderableMapObject someObject = new TestRenderableMapObject();

		Point2D[] linePoints = new Point2D[2];
		linePoints[0] = new Point2D.Double(0, 0);
		linePoints[1] = new Point2D.Double(5, 5);

		SelectingLine testLine = new SelectingLine(someObject, 0, linePoints, 1);
		assertTrue(testLine.isHitsByPoint(new Point2D.Double(3, 3)));*/
	}

	/**
	 * Test is hits by point on line with line width
	 */
	@Test
	public void isHitsByPointOnLineIncludingWidthTest()
	{
		/*RenderableMapObject someObject = new TestRenderableMapObject();

		Point2D[] linePoints = new Point2D[2];
		linePoints[0] = new Point2D.Double(0, 0);
		linePoints[1] = new Point2D.Double(15, 0);

		SelectingLine testLine = new SelectingLine(someObject, 0, linePoints, 14);
		assertTrue(testLine.isHitsByPoint(new Point2D.Double(8, -7)));*/
	}

	/**
	 * Test is hits by point on line with line width, not hits
	 */
	@Test
	public void isNotHitsByPointOnLineIncludingWidthTest()
	{
		/*RenderableMapObject someObject = new TestRenderableMapObject();

		Point2D[] linePoints = new Point2D[2];
		linePoints[0] = new Point2D.Double(0, 0);
		linePoints[1] = new Point2D.Double(15, 0);

		SelectingLine testLine = new SelectingLine(someObject, 0, linePoints, 14);
		assertFalse(testLine.isHitsByPoint(new Point2D.Double(8, 8)));*/
	}
}
