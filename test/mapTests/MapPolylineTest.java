package mapTests;

import drawingStyles.DefenitionTags;
import drawingStyles.DrawingStylesFactory;
import drawingStyles.DefenitionTags;
import drawingStyles.MapObjectDrawSettings;
import drawingStyles.Tag;
import drawingStyles.StyleEditor;
import map.MapPolyline;
import map.MapPosition;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * MapPolyline class tests
 *
 * @author pgalex
 */
public class MapPolylineTest
{
	/**
	 * Creating MapPolyline with null tags
	 */
	@Test
	public void creatingWithNullTagsTest()
	{
		try
		{
			MapPosition[] points = new MapPosition[2];
			points[0] = new MapPosition(1, 2);
			points[1] = new MapPosition(2, 3);
			MapPolyline testLine = new MapPolyline(0, null, points);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Creating MapPolyline with null points
	 */
	@Test
	public void creatingWithNullPointsTest()
	{
		try
		{
			MapPolyline testLine = new MapPolyline(11, new DefenitionTags(), null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Creating MapPolyline by points contaning null elemetets
	 */
	@Test
	public void creatingWithOnePointTest()
	{
		try
		{
			MapPosition[] points = new MapPosition[1];
			points[0] = new MapPosition();
			MapPolyline testLine = new MapPolyline(10, new DefenitionTags(), points);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Creating MapPolyline by points contaning null elemetets
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
			MapPolyline testLine = new MapPolyline(10, new DefenitionTags(), points);
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
		MapPolyline testLine = new MapPolyline(1, tags, points);
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
		MapPolyline testLine = new MapPolyline(1, tags, points);
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
		MapPolyline testLine = new MapPolyline(1, new DefenitionTags(), points);
		testLine.assignStyleIndex(testEditor);

		assertNull(testLine.getStyleIndex());
	}
}
