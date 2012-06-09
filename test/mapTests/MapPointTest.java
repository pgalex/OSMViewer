package mapTests;

import map.MapPoint;
import map.MapPosition;
import map.exceptions.PointPositionIsNullException;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * MapPoint class tests
 *
 * @author pgalex
 */
public class MapPointTest
{
	/**
	 * Testing creating point with null style index
	 */
	@Test
	public void creatingWithNullStyleIndexTest()
	{
		MapPoint testPoint = new MapPoint(new MapPosition(10, 11), 12, null);
		assertEquals(10, testPoint.getPosition().getLatitude(), 0.01);
		assertEquals(10, testPoint.getPosition().getLatitude(), 0.01);
		assertEquals(12, testPoint.getId());
		assertNull(testPoint.getStyleIndex());
	}

	/**
	 * Testing creating with null position
	 */
	@Test
	public void creatingWithNullPositionTest()
	{
		try
		{
			MapPoint testPoint = new MapPoint(null, 12, null);
			fail();
		}
		catch (PointPositionIsNullException ex)
		{
			assertEquals(12, ex.getCreatedObjectId());
			assertNull(ex.getCreatedObjectTags());
		}
	}
}
