package mapTests;

import java.util.ArrayList;
import map.*;
import map.exceptions.LinePointsIsIncorrectException;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

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
		ArrayList<MapPoint> points = new ArrayList<MapPoint>();
		points.add(new MapPoint(new MapPosition(), 1, new DefenitionTags()));

		EditableDefenitionTags lineTags = new EditableDefenitionTags();
		lineTags.add(new MapTag("k1", "v1"));
		MapLine testLine = new MapLine(12, lineTags, points);

		assertEquals(12, testLine.getId());
		assertTrue(lineTags.compareTo(testLine.getDefenitionTags()));
		assertEquals(points.size(), testLine.getPoints().size());
		assertEquals(points.get(0).getId(), testLine.getPoints().get(0).getId());
	}

	/**
	 * Creating MapLine with null tags
	 */
	@Test
	public void constructorNullTagsTest()
	{
		ArrayList<MapPoint> points = new ArrayList<MapPoint>();
		points.add(new MapPoint(new MapPosition(), 1, new DefenitionTags()));
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
			MapLine testLine = new MapLine(12, null, new ArrayList<MapPoint>());
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
			ArrayList<MapPoint> points = new ArrayList<MapPoint>();
			points.add(new MapPoint(new MapPosition(), 1, new DefenitionTags()));
			points.add(null);
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
