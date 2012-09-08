package mapTests;

import drawingStyles.DrawingStylesFactory;
import drawingStyles.EditableDefenitionTags;
import drawingStyles.MapObjectDrawSettings;
import drawingStyles.MapTag;
import drawingStyles.StyleEditor;
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
		MapPosition[] points = new MapPosition[2];
		points[0] = new MapPosition(1, 2);
		points[1] = new MapPosition(2, 3);
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
			MapLine testLine = new MapLine(10, null, points);
			fail();
		}
		catch (LinePointsIsIncorrectException ex)
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
			MapPosition[] points = new MapPosition[2];
			points[0] = new MapPosition();
			points[1] = null;
			MapLine testLine = new MapLine(10, null, points);
			fail();
		}
		catch (LinePointsIsIncorrectException ex)
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
		EditableDefenitionTags tags = new EditableDefenitionTags();
		tags.add(new MapTag("k1", "v1"));
		MapObjectDrawSettings style = new MapObjectDrawSettings(false, true, false, null, 0, "line style", null, tags);

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
		EditableDefenitionTags tags = new EditableDefenitionTags();
		tags.add(new MapTag("k1", "v1"));
		MapObjectDrawSettings style = new MapObjectDrawSettings(false, false, false, null, 0, "line style", null, tags);

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
	 * Testing assigning style index if style not
	 */
	@Test
	public void assigningStyleNotFoundTest()
	{
		EditableDefenitionTags tags = new EditableDefenitionTags();
		tags.add(new MapTag("k1", "v1"));
		MapObjectDrawSettings style = new MapObjectDrawSettings(false, false, false, null, 0, "line style", null, tags);

		StyleEditor testEditor = DrawingStylesFactory.createStyleEditor();
		testEditor.addMapObjectDrawSettings(style);

		MapPosition[] points = new MapPosition[2];
		points[0] = new MapPosition(1, 2);
		points[1] = new MapPosition(2, 3);
		MapLine testLine = new MapLine(1, null, points);
		testLine.assignStyleIndex(testEditor);

		assertNull(testLine.getStyleIndex());
	}
}
