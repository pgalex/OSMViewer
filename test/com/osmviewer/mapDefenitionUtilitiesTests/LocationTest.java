package com.osmviewer.mapDefenitionUtilitiesTests;

import com.osmviewer.mapDefenitionUtilities.Location;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Tests of Location class
 *
 * @author pgalex
 */
public class LocationTest
{
	/**
	 * Test comparing with null other position
	 */
	@Test
	public void comparingWithNullPositionTest()
	{
		try
		{
			Location position = new Location();
			position.compareTo(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test comparing with same position
	 */
	@Test
	public void comparingEqualPositionTest()
	{
		Location position = new Location(-2, 3);
		assertTrue(position.compareTo(new Location(-2.0, 3.0)));
	}

	/**
	 * Test comparing with not same position
	 */
	@Test
	public void comparingNotEqualPositionTest()
	{
		Location position = new Location(-2, 3);
		assertFalse(position.compareTo(new Location(-1.0, 2.0)));
	}
}
