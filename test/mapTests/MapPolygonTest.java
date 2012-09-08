package mapTests;

import drawingStyles.DrawingStylesFactory;
import drawingStyles.EditableDefenitionTags;
import drawingStyles.MapObjectDrawSettings;
import drawingStyles.MapTag;
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
	 * Testing assigning style index and canBeDrawenWithStyle work if object can
	 * be polygon
	 */
	@Test
	public void assigningStyleIndexCanBePolygonTest()
	{
		EditableDefenitionTags tags = new EditableDefenitionTags();
		tags.add(new MapTag("k1", "v1"));
		MapObjectDrawSettings style = new MapObjectDrawSettings(false, false, true, null, 0, "pokygon style", null, tags);

		StyleEditor testEditor = DrawingStylesFactory.createStyleEditor();
		testEditor.addMapObjectDrawSettings(style);

		MapPosition[] points = new MapPosition[2];
		points[0] = new MapPosition(1, 2);
		points[1] = new MapPosition(2, 3);
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
		EditableDefenitionTags tags = new EditableDefenitionTags();
		tags.add(new MapTag("k1", "v1"));
		MapObjectDrawSettings style = new MapObjectDrawSettings(false, false, false, null, 0, "pokygon style", null, tags);

		StyleEditor testEditor = DrawingStylesFactory.createStyleEditor();
		testEditor.addMapObjectDrawSettings(style);

		MapPosition[] points = new MapPosition[2];
		points[0] = new MapPosition(1, 2);
		points[1] = new MapPosition(2, 3);
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
		EditableDefenitionTags tags = new EditableDefenitionTags();
		tags.add(new MapTag("k1", "v1"));
		MapObjectDrawSettings style = new MapObjectDrawSettings(false, false, false, null, 0, "pokygon style", null, tags);

		StyleEditor testEditor = DrawingStylesFactory.createStyleEditor();
		testEditor.addMapObjectDrawSettings(style);

		MapPosition[] points = new MapPosition[2];
		points[0] = new MapPosition(1, 2);
		points[1] = new MapPosition(2, 3);
		MapPolygon testPolygon = new MapPolygon(1, null, points);
		testPolygon.assignStyleIndex(testEditor);

		assertNull(testPolygon.getStyleIndex());
	}
	
	/**
	 * 
	 */
	@Test
	public void getCenterPointTest()
	{
		MapPosition[] polygonPoints = new MapPosition[4];
		polygonPoints[0] = new MapPosition(10, 5);
		polygonPoints[1] = new MapPosition(20, 10);
		polygonPoints[2] = new MapPosition(-5, 20);
		polygonPoints[3] = new MapPosition(15, -10);
		
		MapPolygon testPolygon = new MapPolygon(0, null, polygonPoints);
		
		MapPosition centerPoint = testPolygon.getCenterPoint();
		assertEquals(10, centerPoint.getLatitude(), 0.0001);
		assertEquals(6.25, centerPoint.getLongitude(), 0.0001);
	}
}
