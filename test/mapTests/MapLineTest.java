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
		ArrayList<MapPosition> points = new ArrayList<MapPosition>();
		points.add(new MapPosition(1, 2));

		EditableDefenitionTags lineTags = new EditableDefenitionTags();
		lineTags.add(new MapTag("k1", "v1"));
		MapLine testLine = new MapLine(12, lineTags, points);

		assertEquals(12, testLine.getId());
		assertTrue(lineTags.compareTo(testLine.getDefenitionTags()));
		assertEquals(points.size(), testLine.getPoints().size());
		assertEquals(points.get(0).getLatitude(), testLine.getPoints().get(0).getLatitude(), 0.01);
		assertEquals(points.get(0).getLongitude(), testLine.getPoints().get(0).getLongitude(), 0.01);
	}

	/**
	 * Creating MapLine with null tags
	 */
	@Test
	public void constructorNullTagsTest()
	{
		ArrayList<MapPosition> points = new ArrayList<MapPosition>();
		points.add(new MapPosition(1, 2));
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
			MapLine testLine = new MapLine(12, null, new ArrayList<MapPosition>());
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
			ArrayList<MapPosition> points = new ArrayList<MapPosition>();
			points.add(new MapPosition());
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
