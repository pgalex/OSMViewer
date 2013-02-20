package com.osmviewer.rendering.selectingTests;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import static org.junit.Assert.*;
import org.junit.Test;
import com.osmviewer.rendering.RenderableMapObject;
import com.osmviewer.rendering.selectng.SelectingRectangle;
import com.osmviewer.renderingTests.TestRenderableMapObject;

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
			RenderableMapObject someObject = new TestRenderableMapObject();
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
			RenderableMapObject someObject = new TestRenderableMapObject();
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
		RenderableMapObject someObject = new TestRenderableMapObject();
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
		TestRenderableMapObject someObject = new TestRenderableMapObject();
		SelectingRectangle testRectangle = new SelectingRectangle(someObject, 0,
						new Rectangle2D.Double(10, 20, 5, 10));
		assertFalse(testRectangle.isHitsByPoint(new Point2D.Double(5, 3)));
	}
}
