package mapTests;

import drawingStyles.DefenitionTags;
import drawingStyles.DrawingStylesFactory;
import drawingStyles.DefenitionTags;
import drawingStyles.MapObjectDrawSettings;
import drawingStyles.Tag;
import drawingStyles.StyleEditor;
import map.MapPolygon;
import map.MapPosition;
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
	 * Test creating with incorrect tags
	 */
	@Test
	public void creatingWithNullTagsTest()
	{
		try
		{
			MapPosition[] points = new MapPosition[3];
			points[0] = new MapPosition(1, 2);
			points[1] = new MapPosition(2, 3);
			points[2] = new MapPosition(5, 6);
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
	 * Test creating with points count less than 3
	 */
	@Test
	public void creatingWithPointsLess3Test()
	{
		try
		{
			MapPosition[] points = new MapPosition[2];
			points[0] = new MapPosition(1, 2);
			points[1] = new MapPosition(2, 3);
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
			points[3] = new MapPosition(2, 3);
			MapPolygon polygon = new MapPolygon(0, new DefenitionTags(), points);
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

		MapPosition[] points = new MapPosition[3];
		points[0] = new MapPosition(1, 2);
		points[1] = new MapPosition(2, 3);
		points[2] = new MapPosition(5, 6);
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

		MapPosition[] points = new MapPosition[3];
		points[0] = new MapPosition(1, 2);
		points[1] = new MapPosition(2, 3);
		points[2] = new MapPosition(5, 6);
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

		MapPosition[] points = new MapPosition[3];
		points[0] = new MapPosition(1, 2);
		points[1] = new MapPosition(2, 3);
		points[2] = new MapPosition(5, 6);
		MapPolygon testPolygon = new MapPolygon(1, new DefenitionTags(), points);
		testPolygon.assignStyleIndex(testEditor);

		assertNull(testPolygon.getStyleIndex());
	}

	/**
	 * Testing computing center of polygon
	 */
	@Test
	public void getCenterPointNormalWorkTest()
	{
		MapPosition[] polygonPoints = new MapPosition[4];
		polygonPoints[0] = new MapPosition(10, 5);
		polygonPoints[1] = new MapPosition(20, 10);
		polygonPoints[2] = new MapPosition(-5, 20);
		polygonPoints[3] = new MapPosition(15, -10);

		MapPolygon testPolygon = new MapPolygon(0, new DefenitionTags(), polygonPoints);

		MapPosition centerPoint = testPolygon.getCenterPoint();
		assertEquals(10, centerPoint.getLatitude(), 0.0001);
		assertEquals(6.25, centerPoint.getLongitude(), 0.0001);
	}
}
