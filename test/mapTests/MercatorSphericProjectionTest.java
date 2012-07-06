package mapTests;

import java.awt.geom.Point2D;
import map.MapPosition;
import map.rendering.MercatorSphericProjection;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Test of mercator spheric projection realization
 *
 * @author pgalex
 */
public class MercatorSphericProjectionTest
{
	/**
	 * Test converting point on map to mercator projection and from mercator
	 * projection to point on map. Using to test converting different positions
	 *
	 * @param pPositionOnMap position of point on map
	 * @param pScale scale using in test
	 */
	private void testByReconverting(MapPosition pPositionOnMap, double pScale)
	{
		Point2D pointInMercator = MercatorSphericProjection.geographicsToMercator(pPositionOnMap, pScale);
		MapPosition reconvertedPoint = MercatorSphericProjection.mercatorToGeographics(pointInMercator, pScale);

		assertEquals(pPositionOnMap.getLatitude(), reconvertedPoint.getLatitude(), 0.000001);
		assertEquals(pPositionOnMap.getLongitude(), reconvertedPoint.getLongitude(), 0.000001);
	}

	/**
	 * Test reconverting point on a map with some different scales
	 *
	 * @param pPositionOnMap
	 */
	private void testReconvertingWithDifferentScales(MapPosition pPositionOnMap)
	{
		testByReconverting(pPositionOnMap, 1.2);
		testByReconverting(pPositionOnMap, 1.0);
		testByReconverting(pPositionOnMap, 0.5);
		testByReconverting(pPositionOnMap, 0.01);
	}

	/**
	 * Test correct reconverting of zero point
	 */
	@Test
	public void reconvertingZeroPointTest()
	{
		testReconvertingWithDifferentScales(new MapPosition(0, 0));
	}

	/**
	 * Test correct reconverting of north east point
	 */
	@Test
	public void reconvertingNorthEastTest()
	{
		testReconvertingWithDifferentScales(new MapPosition(45, 45));
	}

	/**
	 * Test correct reconverting of north wast point
	 */
	@Test
	public void reconvertingNorthWestTest()
	{
		testReconvertingWithDifferentScales(new MapPosition(45, -45));
	}

	/**
	 * Test correct reconverting of south wast point
	 */
	@Test
	public void reconvertingSouthWestTest()
	{
		testReconvertingWithDifferentScales(new MapPosition(-45, -45));
	}

	/**
	 * Test correct reconverting of south east point
	 */
	@Test
	public void reconvertingSouthEastTest()
	{
		testReconvertingWithDifferentScales(new MapPosition(-45, 45));
	}
}
