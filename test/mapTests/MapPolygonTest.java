package mapTests;

import drawingStyles.DrawingStylesFactory;
import drawingStyles.MapObjectStyle;
import drawingStyles.StyleEditor;
import drawingStyles.EditableDefenitionTags;
import map.MapPolygon;
import map.MapPosition;
import drawingStyles.MapTag;
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
		MapObjectStyle style = new MapObjectStyle(false, false, true, null, 0, "pokygon style", null, tags);

		StyleEditor testEditor = DrawingStylesFactory.createStyleEditor();
		testEditor.addMapObjectStyle(style);

		MapPosition[] points = new MapPosition[2];
		points[0] = new MapPosition(1, 2);
		points[1] = new MapPosition(2, 3);
		MapPolygon testPolygon = new MapPolygon(1, tags, points);
		testPolygon.assignStyleIndex(testEditor);

		assertNotNull(testPolygon.getStyleIndex());
		assertEquals(style.getDescription(), testEditor.getMapObjectStyle(testPolygon.getStyleIndex()).getDescription());
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
		MapObjectStyle style = new MapObjectStyle(false, false, false, null, 0, "pokygon style", null, tags);

		StyleEditor testEditor = DrawingStylesFactory.createStyleEditor();
		testEditor.addMapObjectStyle(style);

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
		MapObjectStyle style = new MapObjectStyle(false, false, false, null, 0, "pokygon style", null, tags);

		StyleEditor testEditor = DrawingStylesFactory.createStyleEditor();
		testEditor.addMapObjectStyle(style);

		MapPosition[] points = new MapPosition[2];
		points[0] = new MapPosition(1, 2);
		points[1] = new MapPosition(2, 3);
		MapPolygon testPolygon = new MapPolygon(1, null, points);
		testPolygon.assignStyleIndex(testEditor);

		assertNull(testPolygon.getStyleIndex());
	}
}
