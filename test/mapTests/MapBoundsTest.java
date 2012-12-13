package mapTests;

import map.MapBounds;
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
}
