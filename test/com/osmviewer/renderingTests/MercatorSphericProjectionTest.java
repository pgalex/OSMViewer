package com.osmviewer.renderingTests;

import java.awt.geom.Point2D;
import com.osmviewer.mapDefenitionUtilities.Location;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import com.osmviewer.rendering.MercatorSphericProjection;

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
	private void testByReconverting(Location pPositionOnMap, double pScale)
	{
		Point2D pointInMercator = MercatorSphericProjection.geographicsToMercator(pPositionOnMap, pScale);
		Location reconvertedPoint = MercatorSphericProjection.mercatorToGeographics(pointInMercator, pScale);

		assertEquals(pPositionOnMap.getLatitude(), reconvertedPoint.getLatitude(), 0.000001);
		assertEquals(pPositionOnMap.getLongitude(), reconvertedPoint.getLongitude(), 0.000001);
	}

	/**
	 * Test reconverting point on a map with some different scales
	 *
	 * @param pPositionOnMap
	 */
	private void testReconvertingWithDifferentScales(Location pPositionOnMap)
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
		testReconvertingWithDifferentScales(new Location(0, 0));
	}

	/**
	 * Test correct reconverting of north east point
	 */
	@Test
	public void reconvertingNorthEastTest()
	{
		testReconvertingWithDifferentScales(new Location(45, 45));
	}

	/**
	 * Test correct reconverting of north wast point
	 */
	@Test
	public void reconvertingNorthWestTest()
	{
		testReconvertingWithDifferentScales(new Location(45, -45));
	}

	/**
	 * Test correct reconverting of south wast point
	 */
	@Test
	public void reconvertingSouthWestTest()
	{
		testReconvertingWithDifferentScales(new Location(-45, -45));
	}

	/**
	 * Test correct reconverting of south east point
	 */
	@Test
	public void reconvertingSouthEastTest()
	{
		testReconvertingWithDifferentScales(new Location(-45, 45));
	}
}
