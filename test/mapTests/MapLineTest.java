package mapTests;

import mapDefenitionUtilities.DefenitionTags;
import drawingStyles.DrawingStylesFactory;
import drawingStyles.MapObjectDrawSettings;
import drawingStyles.StyleEditor;
import mapDefenitionUtilities.Tag;
import mapDefenitionUtilities.MapBounds;
import map.MapLine;
import mapDefenitionUtilities.MapPosition;
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
	 * Test getting point with index less than bounds
	 */
	@Test
	public void getPointIndexLessThanBoundTest()
	{
		try
		{
			MapPosition[] points = new MapPosition[3];
			points[0] = new MapPosition(1, 2);
			points[1] = new MapPosition(2, 3);
			MapLine testLine = new MapLine(0, new DefenitionTags(), points);
			testLine.getPoint(-1);
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
			MapPosition[] points = new MapPosition[3];
			points[0] = new MapPosition(1, 2);
			points[1] = new MapPosition(2, 3);
			MapLine testLine = new MapLine(0, new DefenitionTags(), points);
			testLine.getPoint(points.length);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Creating MapLine with null tags
	 */
	@Test
	public void creatingWithNullTagsTest()
	{
		try
		{
			MapPosition[] points = new MapPosition[2];
			points[0] = new MapPosition(1, 2);
			points[1] = new MapPosition(2, 3);
			MapLine testLine = new MapLine(0, null, points);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Creating MapLine with null points
	 */
	@Test
	public void creatingWithNullPointsTest()
	{
		try
		{
			MapLine testLine = new MapLine(11, new DefenitionTags(), null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Creating MapLine by points contaning null elemetets
	 */
	@Test
	public void creatingWithOnePointTest()
	{
		try
		{
			MapPosition[] points = new MapPosition[1];
			points[0] = new MapPosition();
			MapLine testLine = new MapLine(10, new DefenitionTags(), points);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
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
			MapPosition[] points = new MapPosition[3];
			points[0] = new MapPosition();
			points[1] = null;
			points[2] = new MapPosition();
			MapLine testLine = new MapLine(10, new DefenitionTags(), points);
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
			MapPosition[] points = new MapPosition[2];
			points[0] = new MapPosition(1, 2);
			points[1] = new MapPosition(2, 3);
			MapLine testLine = new MapLine(0, new DefenitionTags(), points);
			testLine.acceptRenderingVisitor(null);
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
			MapPosition[] points = new MapPosition[2];
			points[0] = new MapPosition(1, 2);
			points[1] = new MapPosition(2, 3);
			MapLine testLine = new MapLine(0, new DefenitionTags(), points);
			testLine.assignStyleIndex(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Testing assigning style index and canBeDrawenWithStyle work if object can
	 * be line
	 */
	@Test
	public void assigningStyleIndexCanBeLineTest()
	{
		DefenitionTags tags = new DefenitionTags();
		tags.add(new Tag("k1", "v1"));
		MapObjectDrawSettings style = new MapObjectDrawSettings();
		style.setCanBeLine();
		style.setDescription("line style");
		style.setDefenitionTags(tags);
		
		StyleEditor testEditor = DrawingStylesFactory.createStyleEditor();
		testEditor.addMapObjectDrawSettings(style);
		
		MapPosition[] points = new MapPosition[2];
		points[0] = new MapPosition(1, 2);
		points[1] = new MapPosition(2, 3);
		MapLine testLine = new MapLine(1, tags, points);
		testLine.assignStyleIndex(testEditor);
		
		assertNotNull(testLine.getStyleIndex());
		assertEquals(style.getDescription(), testEditor.getMapObjectDrawSettings(testLine.getStyleIndex()).getDescription());
	}

	/**
	 * Testing assigning style index and canBeDrawenWithStyle work if object can
	 * not be line
	 */
	@Test
	public void assigningStyleIndexCanNotBeLineTest()
	{
		DefenitionTags tags = new DefenitionTags();
		tags.add(new Tag("k1", "v1"));
		MapObjectDrawSettings style = new MapObjectDrawSettings();
		style.setCanNotBeLine();
		style.setDescription("line style");
		style.setDefenitionTags(tags);
		
		StyleEditor testEditor = DrawingStylesFactory.createStyleEditor();
		testEditor.addMapObjectDrawSettings(style);
		
		MapPosition[] points = new MapPosition[2];
		points[0] = new MapPosition(1, 2);
		points[1] = new MapPosition(2, 3);
		MapLine testLine = new MapLine(1, tags, points);
		testLine.assignStyleIndex(testEditor);
		
		assertNull(testLine.getStyleIndex());
	}

	/**
	 * Testing assigning style index if style not found
	 */
	@Test
	public void assigningStyleNotFoundTest()
	{
		DefenitionTags tags = new DefenitionTags();
		tags.add(new Tag("k1", "v1"));
		MapObjectDrawSettings style = new MapObjectDrawSettings();
		style.setDescription("line style");
		style.setDefenitionTags(tags);
		
		StyleEditor testEditor = DrawingStylesFactory.createStyleEditor();
		testEditor.addMapObjectDrawSettings(style);
		
		MapPosition[] points = new MapPosition[2];
		points[0] = new MapPosition(1, 2);
		points[1] = new MapPosition(2, 3);
		MapLine testLine = new MapLine(1, new DefenitionTags(), points);
		testLine.assignStyleIndex(testEditor);
		
		assertNull(testLine.getStyleIndex());
	}

	/**
	 * Testing is visible in null area
	 */
	@Test
	public void isVisibleInNullAreaTest()
	{
		try
		{
			MapPosition[] points = new MapPosition[2];
			points[0] = new MapPosition(1, 2);
			points[1] = new MapPosition(2, 3);
			MapLine testLine = new MapLine(1, new DefenitionTags(), points);
			testLine.isVisibleInArea(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test is visible - all line point inside area
	 */
	@Test
	public void isVisibleAllPointInAreaTest()
	{
		MapPosition[] points = new MapPosition[3];
		points[0] = new MapPosition(-2, 2);
		points[1] = new MapPosition(5, -5);
		points[2] = new MapPosition(3, 5);
		MapLine testLine = new MapLine(1, new DefenitionTags(), points);
		MapBounds testArea = new MapBounds(-5, 7, -8, 10);
		assertTrue(testLine.isVisibleInArea(testArea));
	}

	/**
	 * Test is visible - all line point surround area, and line not intersect area
	 */
	@Test
	public void isVisibleAllPointSurroundAreaNotIntersectTest()
	{
		MapPosition[] points = new MapPosition[4];
		points[0] = new MapPosition(-7, -9);
		points[1] = new MapPosition(-7, 9);
		points[2] = new MapPosition(7, 9);
		points[3] = new MapPosition(7, -9);
		MapLine testLine = new MapLine(1, new DefenitionTags(), points);
		MapBounds testArea = new MapBounds(-5, 5, -7, 7);
		assertFalse(testLine.isVisibleInArea(testArea));
	}

	/**
	 * Test is visible - all line point outside area, and line intersect area
	 */
	@Test
	public void isVisibleAllPointOutAreaIntersectTest()
	{
		MapPosition[] points = new MapPosition[2];
		points[0] = new MapPosition(0, 0);
		points[1] = new MapPosition(5, 5);
		MapLine testLine = new MapLine(1, new DefenitionTags(), points);
		MapBounds testArea = new MapBounds(1, 4, 1, 4);
		assertTrue(testLine.isVisibleInArea(testArea));
	}

	/**
	 * Test is visible - some line point outside area, and line intersect area
	 */
	@Test
	public void isVisibleSomePointOutAreaIntersectTest()
	{
		MapPosition[] points = new MapPosition[4];
		points[0] = new MapPosition(-1, -1);
		points[1] = new MapPosition(2, 2);
		points[2] = new MapPosition(2, 10);
		points[3] = new MapPosition(3, 3);
		MapLine testLine = new MapLine(1, new DefenitionTags(), points);
		MapBounds testArea = new MapBounds(0, 5, 0, 5);
		assertTrue(testLine.isVisibleInArea(testArea));
	}
}
