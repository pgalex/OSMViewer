package drawingStyleTests;

import drawingStyle.MapObjectStyle;
import drawingStyle.ScaledObjectStyle;
import drawingStyle.ScaledObjectStyleArray;
import java.util.ArrayList;
import java.util.Collections;
import map.EditableDefenitionTags;
import map.MapTag;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * MapObjectStyle class tests
 *
 * @author abc
 */
public class MapObjectStyleTest
{
	/**
	 * Test sorting, cuz its comparable. Sorting by tags count. More tags - less
	 * index
	 */
	@Test
	public void sortingTest()
	{
		EditableDefenitionTags tags1 = new EditableDefenitionTags();
		tags1.add(new MapTag("k1", "v1"));
		tags1.add(new MapTag("k2", "v2"));
		tags1.add(new MapTag("k3", "v3"));

		EditableDefenitionTags tags2 = new EditableDefenitionTags();
		tags2.add(new MapTag("k4", "v4"));
		tags2.add(new MapTag("k5", "v5"));
		tags2.add(new MapTag("k6", "v6"));
		tags2.add(new MapTag("k7", "v7"));

		EditableDefenitionTags tags3 = new EditableDefenitionTags();
		tags3.add(new MapTag("k8", "v8"));

		ArrayList<MapObjectStyle> styles = new ArrayList<MapObjectStyle>();
		styles.add(new MapObjectStyle(true, true, true, null, 0, "", null, tags1));
		styles.add(new MapObjectStyle(true, true, true, null, 0, "", null, tags2));
		styles.add(new MapObjectStyle(true, true, true, null, 0, "", null, tags3));
		Collections.sort(styles);

		assertTrue(styles.get(0).getDefenitionTags().compareTo(tags2));
		assertTrue(styles.get(1).getDefenitionTags().compareTo(tags1));
		assertTrue(styles.get(2).getDefenitionTags().compareTo(tags3));
	}

	/**
	 * Constructor should auto-initialize null values
	 */
	@Test
	public void autoInitializeTest()
	{
		MapObjectStyle style = new MapObjectStyle(true, true, true, null, 0, "", null, null);
		assertNotNull(style.getScaledStyles());
		assertNotNull(style.getDefenitionTags());
	}

	/**
	 * Writing/reading test
	 */
	@Test
	public void readingWritingTest()
	{
		try
		{
			ScaledObjectStyleArray scaledStyles = new ScaledObjectStyleArray();
			scaledStyles.setStyleOnScale(0, new ScaledObjectStyle(true, false, true, null, null,
							null, null, null));
			scaledStyles.setStyleOnScale(5, new ScaledObjectStyle(false, true, true, null, null,
							null, null, null));
			EditableDefenitionTags tags = new EditableDefenitionTags();
			tags.add(new MapTag("k1", "v1"));
			tags.add(new MapTag("k2", "v2"));

			MapObjectStyle writedStyle = new MapObjectStyle(true, false,
							true, null, 10, "object1", scaledStyles, tags);
			DrawingStyleIOTester.writeToTestFile(writedStyle);

			MapObjectStyle readStyle = new MapObjectStyle();
			DrawingStyleIOTester.readFromTestFile(readStyle);

			assertEquals(writedStyle.isCanBeLine(), readStyle.isCanBeLine());
			assertEquals(writedStyle.isCanBePoint(), readStyle.isCanBePoint());
			assertEquals(writedStyle.isCanBePolygon(), readStyle.isCanBePolygon());
			assertEquals(true, writedStyle.getDefenitionTags().compareTo(readStyle.getDefenitionTags()));
			assertEquals(writedStyle.getDescription(), readStyle.getDescription());
			assertArrayEquals(writedStyle.getTextTagKeys().getTagsKeys(), readStyle.getTextTagKeys().getTagsKeys());
			assertEquals(writedStyle.getDrawPriority(), readStyle.getDrawPriority());
			assertEquals(writedStyle.getDescription(), readStyle.getDescription());
			assertEquals(writedStyle.getScaledStyles().getStyleOnScale(0).isDrawLine(), readStyle.getScaledStyles().getStyleOnScale(0).isDrawLine());
			assertEquals(writedStyle.getScaledStyles().getStyleOnScale(0).isDrawPoint(), readStyle.getScaledStyles().getStyleOnScale(0).isDrawPoint());
			assertEquals(writedStyle.getScaledStyles().getStyleOnScale(0).isDrawPolygon(), readStyle.getScaledStyles().getStyleOnScale(0).isDrawPolygon());
			assertEquals(writedStyle.getScaledStyles().getStyleOnScale(5).isDrawLine(), readStyle.getScaledStyles().getStyleOnScale(5).isDrawLine());
			assertEquals(writedStyle.getScaledStyles().getStyleOnScale(5).isDrawPoint(), readStyle.getScaledStyles().getStyleOnScale(5).isDrawPoint());
			assertEquals(writedStyle.getScaledStyles().getStyleOnScale(5).isDrawPolygon(), readStyle.getScaledStyles().getStyleOnScale(5).isDrawPolygon());
			assertEquals(writedStyle.getScaledStyles().count(), readStyle.getScaledStyles().count());
		}
		catch (Exception ex)
		{
			fail();
		}
	}
}
