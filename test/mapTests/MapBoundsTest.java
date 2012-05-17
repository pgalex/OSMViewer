package mapTests;

import com.sun.imageio.spi.RAFImageInputStreamSpi;
import map.MapBounds;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author pgalex
 */
public class MapBoundsTest
{
	public MapBoundsTest()
	{
	}

	/**
	 * Testing isZero method with zero area coordinates
	 */
	@Test
	public void isZeroTrueTest()
	{
		// not random often values
		MapBounds testBounds = new MapBounds(0, 0, 0, 0);
		assertTrue(testBounds.isZero());
		
		testBounds = new MapBounds(0.00001, 0.00001, 0.00001, 0.00001);
		assertTrue(testBounds.isZero());
		
		testBounds = new MapBounds(1, 2, 3, 3);
		assertTrue(testBounds.isZero());
		
		testBounds = new MapBounds(1, 1, 2, 3);
		assertTrue(testBounds.isZero());
		
		testBounds = new MapBounds(-1, -1, 2, 3);
		assertTrue(testBounds.isZero());
		
		testBounds = new MapBounds(1, 2, -5, -5);
		assertTrue(testBounds.isZero());
		
		// some random zero area values
		for (int i = 0; i < 10; i++)
		{
			double random1 = Math.random() * i;
			double random2 = Math.random() * i;
			double random3 = Math.random() * i;
			
			MapBounds zeroLatitudeBounds = new MapBounds(random1, random1, random2, random3);
			assertTrue(zeroLatitudeBounds.isZero());
			
			MapBounds zeroLongitudeBounds = new MapBounds(random2, random3, random1, random1);
			assertTrue(zeroLongitudeBounds.isZero());
		}
	}
	
	
	/**
	 * Testing isZero method with non zero area coordinates
	 */
	@Test
	public void isZeroFalseTest()
	{
		// not random often values
		MapBounds testBounds = new MapBounds(1, 2, 3, 4);
		assertFalse(testBounds.isZero());
		
		testBounds = new MapBounds(0.00002, 0.00001, 0.00003, 0.00004);
		assertFalse(testBounds.isZero());
		
		testBounds = new MapBounds(1, 0, 1, 0);
		assertFalse(testBounds.isZero());
		
		testBounds = new MapBounds(0, 1, 0, 1);
		assertFalse(testBounds.isZero());
		
		testBounds = new MapBounds(-1, -2, -2, -1);
		assertFalse(testBounds.isZero());
		
		testBounds = new MapBounds(-2, -1, -1, -2);
		assertFalse(testBounds.isZero());
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
