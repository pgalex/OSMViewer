package mapTests;

import map.MapLine;
import map.MapPosition;
import map.exceptions.LinePointsIsIncorrectException;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * MapLine class tests
 *
 * @author pgalex
 */
public class MapLineTest
{
	/**
	 * Creating MapLine with null tags - should called constructor from MapObject
	 * or create null tags (null==empty)
	 */
	@Test
	public void autoInitializingNullTagsTest()
	{
		MapPosition[] points = new MapPosition[1];
		points[0] = new MapPosition(1, 2);
		MapLine testLine = new MapLine(0, null, points);

		assertNotNull(testLine.getDefenitionTags());
	}

	/**
	 * Creating MapLine with null points
	 */
	@Test
	public void creatingWithNullPointsTest()
	{
		try
		{
			MapLine testLine = new MapLine(11, null, null);
			fail();
		}
		catch (LinePointsIsIncorrectException ex)
		{
			assertEquals(11, ex.getCreatedObjectId());
		}
	}

	/**
	 * Creating MapLine by one point
	 */
	@Test
	public void creatingWithOnePointsTest()
	{
		try
		{
			MapLine testLine = new MapLine(12, null, new MapPosition[1]);
			assertNotNull(testLine.getDefenitionTags());
			fail();
		}
		catch (LinePointsIsIncorrectException ex)
		{
			assertEquals(12, ex.getCreatedObjectId());
		}
	}

	/**
	 * Creating MapLine by points contaning null elemetets
	 */
	@Test
	public void creatingWithPointsContainsNullTest()
	{
		try
		{
			MapPosition[] points = new MapPosition[2];
			points[0] = new MapPosition();
			points[1] = null;
			MapLine testLine = new MapLine(10, null, points);
			fail();
		}
		catch (LinePointsIsIncorrectException ex)
		{
			assertEquals(10, ex.getCreatedObjectId());
		}
	}
}
