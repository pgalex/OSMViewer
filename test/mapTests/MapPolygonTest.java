package mapTests;

import drawingStyles.DefenitionTags;
import drawingStyles.DrawingStylesFactory;
import drawingStyles.MapObjectDrawSettings;
import drawingStyles.StyleEditor;
import drawingStyles.Tag;
import MapDefenitionUtilities.MapBounds;
import map.MapPolygon;
import MapDefenitionUtilities.MapPosition;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * MapPolygon tests
 *
 * @author pgalex
 */
public class MapPolygonTest
{
	/**
	 * Test getting point with index less than bounds
	 */
	@Test
	public void getPointIndexLessThanBoundTest()
	{
		try
		{
			MapPosition[] points = new MapPosition[4];
			points[0] = new MapPosition(1, 2);
			points[1] = new MapPosition(2, 3);
			points[2] = new MapPosition(5, 6);
			points[3] = points[0];
			MapPolygon polygon = new MapPolygon(0, new DefenitionTags(), points);
			polygon.getPoint(-1);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test getting point with index more than bounds
	 */
	@Test
	public void getPointIndexMoreThanBoundTest()
	{
		try
		{
			MapPosition[] points = new MapPosition[4];
			points[0] = new MapPosition(1, 2);
			points[1] = new MapPosition(2, 3);
			points[2] = new MapPosition(5, 6);
			points[3] = points[0];
			MapPolygon polygon = new MapPolygon(0, new DefenitionTags(), points);
			polygon.getPoint(points.length);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test creating with incorrect tags
	 */
	@Test
	public void creatingWithNullTagsTest()
	{
		try
		{
			MapPosition[] points = new MapPosition[4];
			points[0] = new MapPosition(1, 2);
			points[1] = new MapPosition(2, 3);
			points[2] = new MapPosition(5, 6);
			points[3] = points[0];
			MapPolygon polygon = new MapPolygon(0, null, points);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test creating with null points
	 */
	@Test
	public void creatingWithNullPointsTest()
	{
		try
		{
			MapPolygon polygon = new MapPolygon(0, new DefenitionTags(), null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test creating with points count less than 4
	 */
	@Test
	public void creatingWithPointsLess4Test()
	{
		try
		{
			MapPosition[] points = new MapPosition[3];
			points[0] = new MapPosition(1, 2);
			points[1] = new MapPosition(2, 3);
			points[2] = new MapPosition(3, 4);
			MapPolygon polygon = new MapPolygon(0, new DefenitionTags(), points);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test creating when first point is not same as last
	 */
	@Test
	public void creatingWithFirstPointNotSameLastTest()
	{
		try
		{
			MapPosition[] points = new MapPosition[4];
			points[0] = new MapPosition(1, 2);
			points[1] = new MapPosition(2, 3);
			points[2] = new MapPosition(3, 4);
			points[3] = new MapPosition(5, 6);
			MapPolygon polygon = new MapPolygon(0, new DefenitionTags(), points);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test creating with points contains null
	 */
	@Test
	public void creatingWithPointsContainsNullTest()
	{
		try
		{
			MapPosition[] points = new MapPosition[4];
			points[0] = new MapPosition(1, 2);
			points[1] = null;
			points[2] = null;
			points[3] = points[0];
			MapPolygon polygon = new MapPolygon(0, new DefenitionTags(), points);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Testing accept render when render is null
	 */
	@Test
	public void acceptNullRendererViewerTest()
	{
		try
		{
			MapPosition[] points = new MapPosition[4];
			points[0] = new MapPosition(1, 2);
			points[1] = new MapPosition(2, 3);
			points[2] = new MapPosition(5, 6);
			points[3] = points[0];
			MapPolygon polygon = new MapPolygon(0, new DefenitionTags(), points);
			polygon.acceptRenderingVisitor(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Testing assigning style index if style viewer is null
	 */
	@Test
	public void assigningStyleIndexNullStyleViewerTest()
	{
		try
		{
			MapPosition[] points = new MapPosition[4];
			points[0] = new MapPosition(1, 2);
			points[1] = new MapPosition(2, 3);
			points[2] = new MapPosition(5, 6);
			points[3] = points[0];
			MapPolygon polygon = new MapPolygon(0, new DefenitionTags(), points);
			polygon.assignStyleIndex(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Testing assigning style index and canBeDrawenWithStyle work if object can
	 * be polygon
	 */
	@Test
	public void assigningStyleIndexCanBePolygonTest()
	{
		DefenitionTags tags = new DefenitionTags();
		tags.add(new Tag("k1", "v1"));
		
		MapObjectDrawSettings style = new MapObjectDrawSettings();
		style.setCanBePolygon();
		style.setDescription("polygon style");
		style.setDefenitionTags(tags);
		
		StyleEditor testEditor = DrawingStylesFactory.createStyleEditor();
		testEditor.addMapObjectDrawSettings(style);
		
		MapPosition[] points = new MapPosition[4];
		points[0] = new MapPosition(1, 2);
		points[1] = new MapPosition(2, 3);
		points[2] = new MapPosition(5, 6);
		points[3] = points[0];
		MapPolygon testPolygon = new MapPolygon(1, tags, points);
		testPolygon.assignStyleIndex(testEditor);
		
		assertNotNull(testPolygon.getStyleIndex());
		assertEquals(style.getDescription(), testEditor.getMapObjectDrawSettings(testPolygon.getStyleIndex()).getDescription());
	}

	/**
	 * Testing assigning style index and canBeDrawenWithStyle work if object can
	 * not be polygon
	 */
	@Test
	public void assigningStyleIndexCanNotBePolygonTest()
	{
		DefenitionTags tags = new DefenitionTags();
		tags.add(new Tag("k1", "v1"));
		
		MapObjectDrawSettings style = new MapObjectDrawSettings();
		style.setCanNotBePolygon();
		style.setDescription("polygon style");
		style.setDefenitionTags(tags);
		StyleEditor testEditor = DrawingStylesFactory.createStyleEditor();
		testEditor.addMapObjectDrawSettings(style);
		
		MapPosition[] points = new MapPosition[4];
		points[0] = new MapPosition(1, 2);
		points[1] = new MapPosition(2, 3);
		points[2] = new MapPosition(5, 6);
		points[3] = points[0];
		MapPolygon testPolygon = new MapPolygon(1, tags, points);
		testPolygon.assignStyleIndex(testEditor);
		
		assertNull(testPolygon.getStyleIndex());
	}

	/**
	 * Testing assigning style index if style not found
	 */
	@Test
	public void assigningStyleIndexNotFoundTest()
	{
		DefenitionTags tags = new DefenitionTags();
		tags.add(new Tag("k1", "v1"));
		
		MapObjectDrawSettings style = new MapObjectDrawSettings();
		style.setDescription("polygon style");
		style.setDefenitionTags(tags);
		
		StyleEditor testEditor = DrawingStylesFactory.createStyleEditor();
		testEditor.addMapObjectDrawSettings(style);
		
		MapPosition[] points = new MapPosition[4];
		points[0] = new MapPosition(1, 2);
		points[1] = new MapPosition(2, 3);
		points[2] = new MapPosition(5, 6);
		points[3] = points[0];
		MapPolygon testPolygon = new MapPolygon(1, new DefenitionTags(), points);
		testPolygon.assignStyleIndex(testEditor);
		
		assertNull(testPolygon.getStyleIndex());
	}

	/**
	 * Testing is visibile - null area
	 */
	@Test
	public void isVisibleInNullAreaTest()
	{
		try
		{
			MapPosition[] points = new MapPosition[4];
			points[0] = new MapPosition(-1, -2);
			points[1] = new MapPosition(1, 0);
			points[2] = new MapPosition(3, 3);
			points[3] = points[0];
			MapPolygon testPolygon = new MapPolygon(1, new DefenitionTags(), points);
			testPolygon.isVisibleInArea(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Testing is visible - all polygon points inside area
	 */
	@Test
	public void isVisibleAllPointsInAreaTest()
	{
		MapPosition[] points = new MapPosition[4];
		points[0] = new MapPosition(-1, -2);
		points[1] = new MapPosition(1, 0);
		points[2] = new MapPosition(3, 3);
		points[3] = points[0];
		MapPolygon testPolygon = new MapPolygon(1, new DefenitionTags(), points);
		MapBounds testArea = new MapBounds(-5, 5, -5, 5);
		assertTrue(testPolygon.isVisibleInArea(testArea));
	}

	/**
	 * Testing is visible - some polygon points inside area, some outside
	 */
	@Test
	public void isVisibleSomePointsInAreaTest()
	{
		MapPosition[] points = new MapPosition[5];
		points[0] = new MapPosition(-1, -2);
		points[1] = new MapPosition(1, 0);
		points[2] = new MapPosition(3, 3);
		points[3] = new MapPosition(7, 7);
		points[4] = points[0];
		MapPolygon testPolygon = new MapPolygon(1, new DefenitionTags(), points);
		MapBounds testArea = new MapBounds(0, 5, 0, 5);
		assertTrue(testPolygon.isVisibleInArea(testArea));
	}

	/**
	 * Testing is visible - all point outside area, and surround area, but not
	 * intersect (using to test visible, if bounding rectangle intersect area, but
	 * polygon not )
	 */
	@Test
	public void isVisiblePointsSurroundsAreaNotIntersectTest()
	{
		MapPosition[] points = new MapPosition[7];
		points[0] = new MapPosition(0, 9);
		points[1] = new MapPosition(9, 9);
		points[2] = new MapPosition(9, 0);
		points[3] = new MapPosition(7, 0);
		points[4] = new MapPosition(7, 7);
		points[5] = new MapPosition(0, 7);
		points[6] = points[0];
		MapPolygon testPolygon = new MapPolygon(1, new DefenitionTags(), points);
		MapBounds testArea = new MapBounds(0, 5, 0, 5);
		assertFalse(testPolygon.isVisibleInArea(testArea));
	}
}
