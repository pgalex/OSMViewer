package mapTests;

import map.MapPoint;
import map.MapPosition;
import map.exceptions.PointPositionIsNullException;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author pgalex
 */
public class MapPointTest
{
	public MapPointTest()
	{
	}

	/**
	 * Testing constructor - normal work
	 */
	@Test
	public void constructorNormalTest()
	{
		MapPoint testPoint = new MapPoint(new MapPosition(10, 11), 12, null);
		assertEquals(10, testPoint.getPosition().getLatitude(), 0.01);
		assertEquals(10, testPoint.getPosition().getLatitude(), 0.01);
		assertEquals(12, testPoint.getId());
	}
	
	/**
	 * Testing constructor - null position
	 */
	@Test
	public void constructorNullPositionTest()
	{
		try
		{
			MapPoint testPoint = new MapPoint(null, 12, null);
			fail();
		}
		catch( PointPositionIsNullException ex )
		{
			assertEquals(12, ex.getPointId());
			assertNull(ex.getPointTags());
		}
	}


	@BeforeClass
	public static void setUpClass() throws Exception
	{
	}

	@AfterClass
	public static void tearDownClass() throws Exception
	{
	}
}
