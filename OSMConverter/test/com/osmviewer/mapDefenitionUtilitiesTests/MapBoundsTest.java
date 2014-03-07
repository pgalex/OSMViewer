package com.osmviewer.mapDefenitionUtilitiesTests;

import com.osmviewer.mapDefenitionUtilities.MapBounds;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * MapBounds tests
 *
 * @author pgalex
 */
public class MapBoundsTest
{
	/**
	 * Testing not swapping minimum and maximum if bounds are not inverted
	 */
	@Test
	public void notSwappingNormalBoundsTest()
	{
		MapBounds testBounds = new MapBounds(1, 2, 3, 4);
		assertEquals(1, testBounds.getLatitudeMinimum(), 0.0001);
		assertEquals(2, testBounds.getLatitudeMaximum(), 0.0001);
		assertEquals(3, testBounds.getLongitudeMinimum(), 0.0001);
		assertEquals(4, testBounds.getLongitudeMaximum(), 0.0001);
	}

	/**
	 * Testing swapping minimum and maximum if bounds are inverted
	 */
	@Test
	public void swappingInvertedBoundsTest()
	{
		MapBounds testBounds = new MapBounds(2, 1, 4, 3);
		assertEquals(1, testBounds.getLatitudeMinimum(), 0.0001);
		assertEquals(2, testBounds.getLatitudeMaximum(), 0.0001);
		assertEquals(3, testBounds.getLongitudeMinimum(), 0.0001);
		assertEquals(4, testBounds.getLongitudeMaximum(), 0.0001);
	}

	/**
	 * Testing isZero method with all bounds equals
	 */
	@Test
	public void isZeroAllBoundsEqualTest()
	{
		MapBounds testBounds = new MapBounds(0.00001, 0.00001, 0.00001, 0.00001);
		assertTrue(testBounds.isZero());
	}

	/**
	 * Testing isZero method with longitude maximum/minimum equals, more zero
	 */
	@Test
	public void isZeroLongitudeBoundsEqualMoreZeroTest()
	{
		MapBounds testBoundsMoreZero = new MapBounds(1, 2, 3, 3);
		assertTrue(testBoundsMoreZero.isZero());
	}

	/**
	 * Testing isZero method with longitude maximum/minimum equals, less zero
	 */
	@Test
	public void isZeroLongitudeBoundsEqualLessZeroTest()
	{
		MapBounds testBoundsLessZero = new MapBounds(1, 2, -3, -3);
		assertTrue(testBoundsLessZero.isZero());
	}

	/**
	 * Testing isZero method with latitude maximum/minimum equals, more zero
	 */
	@Test
	public void isZeroLatitudeBoundsEqualMoreZeroTest()
	{
		MapBounds testBoundsMoreZero = new MapBounds(-1, -1, 2, 3);
		assertTrue(testBoundsMoreZero.isZero());
	}

	/**
	 * Testing isZero method with latitude maximum/minimum equals, less zero
	 */
	@Test
	public void isZeroLatitudeBoundsEqualLessZeroTest()
	{
		MapBounds testBoundsLessZero = new MapBounds(-1, -1, 2, 3);
		assertTrue(testBoundsLessZero.isZero());
	}

	/**
	 * Testing isZero method with non zero area coordinates
	 */
	@Test
	public void isZeroAllBoundsNotEqualTest()
	{
		MapBounds testBounds = new MapBounds(0.00002, 0.00001, 0.00003, 0.00004);
		assertFalse(testBounds.isZero());
	}

	/**
	 * Testing isZero method with longitude max/min and latitude max/min as same
	 */
	@Test
	public void isZeroLatitudeAndLongitudeBoundsSameTest()
	{
		MapBounds testBounds = new MapBounds(1, 0, 1, 0);
		assertFalse(testBounds.isZero());
	}

	/**
	 * Testing computing latitude if min-max is less than zero
	 */
	@Test
	public void latitudeSizeBoundsLessThanZeroTest()
	{
		MapBounds testBounds = new MapBounds(-5, -4, 1, 0);
		assertEquals(1.0, testBounds.getLatitudeSize(), 0.001);
	}

	/**
	 * Testing computing latitude if min-max is more than zero
	 */
	@Test
	public void latitudeSizeBoundsMoreThanZeroTest()
	{
		MapBounds testBounds = new MapBounds(4, 7, 1, 0);
		assertEquals(3.0, testBounds.getLatitudeSize(), 0.001);
	}

	/**
	 * Testing computing latitude if min less than zero and max is more than zero
	 */
	@Test
	public void latitudeSizeOneBoundLessThanZeroTest()
	{
		MapBounds testBounds = new MapBounds(-5, 5, 1, 0);
		assertEquals(10.0, testBounds.getLatitudeSize(), 0.001);
	}

	/**
	 * Testing computing latitude if min-max is less than zero
	 */
	@Test
	public void longitudeSizeBoundsLessThanZeroTest()
	{
		MapBounds testBounds = new MapBounds(1, 0, -5, -3);
		assertEquals(2.0, testBounds.getLongitudeSize(), 0.001);
	}

	/**
	 * Testing computing latitude if min-max is more than zero
	 */
	@Test
	public void longitudeSizeBoundsMoreThanZeroTest()
	{
		MapBounds testBounds = new MapBounds(1, 0, 4, 7);
		assertEquals(3.0, testBounds.getLongitudeSize(), 0.001);
	}

	/**
	 * Testing computing latitude if min less than zero and max is more than zero
	 */
	@Test
	public void longitudeSizeOneBoundLessThanZeroTest()
	{
		MapBounds testBounds = new MapBounds(1, 0, -5, 5);
		assertEquals(10.0, testBounds.getLongitudeSize(), 0.001);
	}

	/**
	 * Testing intersection in latitude maximum border
	 */
	@Test
	public void intersectMaximumLatitudeTest()
	{
		MapBounds testBounds = new MapBounds(-10, 10, -20, 20);
		MapBounds maximumLatitudeBounds = new MapBounds(12, 8, -10, 10);
		assertTrue(testBounds.intersects(maximumLatitudeBounds));
	}

	/**
	 * Testing intersection in latitude minimum border
	 */
	@Test
	public void intersectMinimumLatitudeTest()
	{
		MapBounds testBounds = new MapBounds(-10, 10, -20, 20);
		MapBounds minimumLatitudeBounds = new MapBounds(-12, -8, -10, 10);
		assertTrue(testBounds.intersects(minimumLatitudeBounds));
	}

	/**
	 * Testing intersection in longitude minimum border
	 */
	@Test
	public void intersectMinimumLongitudeTest()
	{
		MapBounds testBounds = new MapBounds(-10, 10, -20, 20);
		MapBounds minimumLongitudeBounds = new MapBounds(-15, 15, -15, -25);
		assertTrue(testBounds.intersects(minimumLongitudeBounds));
	}

	/**
	 * Testing intersection in longitude maximum border
	 */
	@Test
	public void intersectMaximumLongitudeTest()
	{
		MapBounds testBounds = new MapBounds(-10, 10, -20, 20);
		MapBounds maximumLongitudeBounds = new MapBounds(-15, 15, 15, 25);
		assertTrue(testBounds.intersects(maximumLongitudeBounds));
	}

	/**
	 * Testing intersection when first bounds inside second
	 */
	@Test
	public void intersectFirstBoundsInSecondTest()
	{
		MapBounds firstBounds = new MapBounds(-8, 8, -10, 10);
		MapBounds secondBounds = new MapBounds(-10, 10, -20, 20);
		assertTrue(firstBounds.intersects(secondBounds));
	}

	/**
	 * Testing intersection when second bounds inside first
	 */
	@Test
	public void intersectSecondBoundsInFirstTest()
	{
		MapBounds firstBounds = new MapBounds(-10, 10, -20, 20);
		MapBounds secondBounds = new MapBounds(-8, 5, -10, 15);
		assertTrue(firstBounds.intersects(secondBounds));
	}

	/**
	 * Testing not intersect in latitude maximum border
	 */
	@Test
	public void notIntersectMaximumLatitudeTest()
	{
		MapBounds testBounds = new MapBounds(-10, 10, -20, 20);
		MapBounds maximumLatitudeBounds = new MapBounds(15, 12, -10, 10);
		assertFalse(testBounds.intersects(maximumLatitudeBounds));
	}

	/**
	 * Testing not intersect in latitude minimum border
	 */
	@Test
	public void notIntersectMinimumLatitudeTest()
	{
		MapBounds testBounds = new MapBounds(-10, 10, -20, 20);
		MapBounds minimumLatitudeBounds = new MapBounds(-15, -12, -10, 10);
		assertFalse(testBounds.intersects(minimumLatitudeBounds));
	}

	/**
	 * Testing not intersect in longitude minimum border
	 */
	@Test
	public void notIntersectMinimumLongitudeTest()
	{
		MapBounds testBounds = new MapBounds(-10, 10, -20, 20);
		MapBounds minimumLongitudeBounds = new MapBounds(-15, 15, -22, -25);
		assertFalse(testBounds.intersects(minimumLongitudeBounds));
	}

	/**
	 * Testing not intersect in longitude maximum border
	 */
	@Test
	public void notIntersectMaximumLongitudeTest()
	{
		MapBounds testBounds = new MapBounds(-10, 10, -20, 20);
		MapBounds maximumLongitudeBounds = new MapBounds(-15, 15, 22, 25);
		assertFalse(testBounds.intersects(maximumLongitudeBounds));
	}
}
