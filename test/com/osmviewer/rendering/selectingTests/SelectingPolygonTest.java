package com.osmviewer.rendering.selectingTests;

import java.awt.Polygon;
import java.awt.geom.Point2D;
import static org.junit.Assert.*;
import org.junit.Test;
import com.osmviewer.rendering.RenderableMapObject;
import com.osmviewer.rendering.selectng.SelectingPolygon;
import com.osmviewer.renderingTests.TestRenderableMapObject;

/**
 * Tests of SelectingPolygon class
 *
 * @author pgalex
 */
public class SelectingPolygonTest
{
	/**
	 * Test creating with null associated map object
	 */
	@Test
	public void creatingWithNullAssociatedObjectTest()
	{
		try
		{
			SelectingPolygon testPolygon = new SelectingPolygon(null, 0, new Polygon());
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test creating with null polygon
	 */
	@Test
	public void creatingWithNullPolygonTest()
	{
		try
		{
			RenderableMapObject someObject = new TestRenderableMapObject();
			SelectingPolygon testPolygon = new SelectingPolygon(someObject, 0, null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test is hits method with null point
	 */
	@Test
	public void isHitByNullPointTest()
	{
		try
		{
			RenderableMapObject someObject = new TestRenderableMapObject();
			SelectingPolygon testPolygon = new SelectingPolygon(someObject, 0, new Polygon());
			testPolygon.isHitsByPoint(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test is hits method when point inside polygon
	 */
	@Test
	public void isHitByPointInsidePolygonTest()
	{
		RenderableMapObject someObject = new TestRenderableMapObject();
		Polygon polygon = new Polygon();
		polygon.addPoint(0, 0);
		polygon.addPoint(5, 5);
		polygon.addPoint(5, 0);
		polygon.addPoint(0, 0);

		SelectingPolygon testPolygon = new SelectingPolygon(someObject, 0, polygon);
		assertTrue(testPolygon.isHitsByPoint(new Point2D.Double(3, 1)));
	}

	/**
	 * Test is hits method when point outnside polygon
	 */
	@Test
	public void isHitByPointOutnsidePolygonTest()
	{
		RenderableMapObject someObject = new TestRenderableMapObject();
		Polygon polygon = new Polygon();
		polygon.addPoint(0, 0);
		polygon.addPoint(5, 5);
		polygon.addPoint(5, 0);
		polygon.addPoint(0, 0);

		SelectingPolygon testPolygon = new SelectingPolygon(someObject, 0, polygon);
		assertFalse(testPolygon.isHitsByPoint(new Point2D.Double(-2, -2)));
	}
}
