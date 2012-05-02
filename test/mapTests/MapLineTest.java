package mapTests;

import map.EditableDefenitionTags;
import map.MapLine;
import map.MapPosition;
import map.MapTag;
import map.exceptions.LinePointsIsIncorrectException;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author pgalex
 */
public class MapLineTest
{
	public MapLineTest()
	{
	}

	/**
	 * Creating MapLine normal work test
	 */
	@Test
	public void constructorNormalTest()
	{
		MapPosition[] points = new MapPosition[1];
		points[0] = new MapPosition(1, 2);

		EditableDefenitionTags lineTags = new EditableDefenitionTags();
		lineTags.add(new MapTag("k1", "v1"));
		MapLine testLine = new MapLine(12, lineTags, points);

		assertEquals(12, testLine.getId());
		assertTrue(lineTags.compareTo(testLine.getDefenitionTags()));
		assertArrayEquals(points, testLine.getPoints());
	}

	/**
	 * Creating MapLine with null tags
	 */
	@Test
	public void constructorNullTagsTest()
	{
		MapPosition[] points = new MapPosition[1];
		points[0] = new MapPosition(1, 2);
		MapLine testLine = new MapLine(0, null, points);
		assertNotNull(testLine.getDefenitionTags());
	}

	/**
	 * Creating MapLine with incorrect points tags
	 */
	@Test
	public void constructorIncorrectPointsTest()
	{
		// null
		try
		{
			MapLine testLine = new MapLine(11, null, null);
			fail();
		}
		catch (LinePointsIsIncorrectException ex)
		{
			assertEquals(11, ex.getCreatedObjectId());
		}

		// empty
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

		// contains null
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

	@BeforeClass
	public static void setUpClass() throws Exception
	{
	}

	@AfterClass
	public static void tearDownClass() throws Exception
	{
	}
}
