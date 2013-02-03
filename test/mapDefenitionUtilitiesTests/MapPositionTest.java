package mapDefenitionUtilitiesTests;

import mapDefenitionUtilities.MapPosition;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Tests of MapPosition class
 *
 * @author pgalex
 */
public class MapPositionTest
{
	/**
	 * Test comparing with null other position
	 */
	@Test
	public void comparingWithNullPositionTest()
	{
		try
		{
			MapPosition position = new MapPosition();
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
		MapPosition position = new MapPosition(-2, 3);
		assertTrue(position.compareTo(new MapPosition(-2.0, 3.0)));
	}

	/**
	 * Test comparing with not same position
	 */
	@Test
	public void comparingNotEqualPositionTest()
	{
		MapPosition position = new MapPosition(-2, 3);
		assertFalse(position.compareTo(new MapPosition(-1.0, 2.0)));
	}
}
