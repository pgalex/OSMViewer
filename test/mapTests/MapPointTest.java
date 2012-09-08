package mapTests;

import drawingStyles.DrawingStylesFactory;
import drawingStyles.EditableDefenitionTags;
import drawingStyles.MapObjectDrawSettings;
import drawingStyles.MapTag;
import drawingStyles.StyleEditor;
import map.MapPoint;
import map.MapPosition;
import map.exceptions.PointPositionIsNullException;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * MapPoint class tests
 *
 * @author pgalex
 */
public class MapPointTest
{
	/**
	 * Testing creating point with null style index
	 */
	@Test
	public void creatingWithNullStyleIndexTest()
	{
		MapPoint testPoint = new MapPoint(new MapPosition(10, 11), 12, null);
		assertEquals(10, testPoint.getPosition().getLatitude(), 0.01);
		assertEquals(10, testPoint.getPosition().getLatitude(), 0.01);
		assertEquals(12, testPoint.getId());
		assertNull(testPoint.getStyleIndex());
	}

	/**
	 * Testing creating with null position
	 */
	@Test
	public void creatingWithNullPositionTest()
	{
		try
		{
			MapPoint testPoint = new MapPoint(null, 12, null);
			fail();
		}
		catch (PointPositionIsNullException ex)
		{
			// ok
		}
	}

	/**
	 * Testing assigning style index and canBeDrawenWithStyle work if object can
	 * be point
	 */
	@Test
	public void assigningStyleIndexCanBePointTest()
	{
		EditableDefenitionTags tags = new EditableDefenitionTags();
		tags.add(new MapTag("k1", "v1"));
		MapObjectDrawSettings style = new MapObjectDrawSettings(true, false, false, null, 0, "point style", null, tags);

		StyleEditor testEditor = DrawingStylesFactory.createStyleEditor();
		testEditor.addMapObjectDrawSettings(style);

		MapPoint testPoint = new MapPoint(new MapPosition(0, 0), 0, tags);
		testPoint.assignStyleIndex(testEditor);

		assertNotNull(testPoint.getStyleIndex());
		assertEquals(style.getDescription(), testEditor.getMapObjectDrawSettings(testPoint.getStyleIndex()).getDescription());
	}

	/**
	 * Testing assigning style index and canBeDrawenWithStyle work if object can
	 * not be point
	 */
	@Test
	public void assigningStyleIndexCanNotBePointTest()
	{
		EditableDefenitionTags tags = new EditableDefenitionTags();
		tags.add(new MapTag("k1", "v1"));
		MapObjectDrawSettings style = new MapObjectDrawSettings(false, false, false, null, 0, "point style", null, tags);

		StyleEditor testEditor = DrawingStylesFactory.createStyleEditor();
		testEditor.addMapObjectDrawSettings(style);

		MapPoint testPoint = new MapPoint(new MapPosition(0, 0), 0, tags);
		testPoint.assignStyleIndex(testEditor);

		assertNull(testPoint.getStyleIndex());
	}
	
	/**
	 * Testing assigning style index if style not found
	 */
	@Test
	public void assigningStyleIndexNotFoundTest()
	{
		EditableDefenitionTags tags = new EditableDefenitionTags();
		tags.add(new MapTag("k1", "v1"));
		MapObjectDrawSettings style = new MapObjectDrawSettings(false, false, false, null, 0, "point style", null, tags);

		StyleEditor testEditor = DrawingStylesFactory.createStyleEditor();
		testEditor.addMapObjectDrawSettings(style);

		MapPoint testPoint = new MapPoint(new MapPosition(0, 0), 0, null);
		testPoint.assignStyleIndex(testEditor);

		assertNull(testPoint.getStyleIndex());
	}
}
