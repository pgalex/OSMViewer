package drawingStylesTests;

import IOTesting.IOTester;
import drawingStyles.MapObjectStyle;
import drawingStyles.ScaledObjectStyle;
import drawingStyles.ScaledObjectStyleArray;
import java.util.ArrayList;
import java.util.Collections;
import drawingStyles.EditableDefenitionTags;
import drawingStyles.MapTag;
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
	 * Test sorting. Sorting by tags count. More tags - less index
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
			final int someScaleLevelAtBegin = 3;
			final int someScaleLevelAtMiddle = 8;
			
			ScaledObjectStyleArray scaledStyles = new ScaledObjectStyleArray();
			scaledStyles.setStyleOnScale(someScaleLevelAtBegin, new ScaledObjectStyle(true, false, true, null, null,
							null, null, null));
			scaledStyles.setStyleOnScale(someScaleLevelAtMiddle, new ScaledObjectStyle(false, true, true, null, null,
							null, null, null));
			EditableDefenitionTags tags = new EditableDefenitionTags();
			tags.add(new MapTag("k1", "v1"));
			tags.add(new MapTag("k2", "v2"));

			MapObjectStyle writedStyle = new MapObjectStyle(true, false,
							true, null, 10, "object1", scaledStyles, tags);
			IOTester.writeToTestFile(writedStyle);

			MapObjectStyle readStyle = new MapObjectStyle();
			IOTester.readFromTestFile(readStyle);

			assertEquals(writedStyle.isCanBeLine(), readStyle.isCanBeLine());
			assertEquals(writedStyle.isCanBePoint(), readStyle.isCanBePoint());
			assertEquals(writedStyle.isCanBePolygon(), readStyle.isCanBePolygon());
			assertEquals(true, writedStyle.getDefenitionTags().compareTo(readStyle.getDefenitionTags()));
			assertEquals(writedStyle.getDescription(), readStyle.getDescription());
			assertArrayEquals(writedStyle.getTextTagKeys().getTagsKeys(), readStyle.getTextTagKeys().getTagsKeys());
			assertEquals(writedStyle.getDrawPriority(), readStyle.getDrawPriority());
			assertEquals(writedStyle.getDescription(), readStyle.getDescription());
			assertEquals(writedStyle.getScaledStyles().getStyleOnScale(someScaleLevelAtBegin).isDrawLine(), readStyle.getScaledStyles().getStyleOnScale(someScaleLevelAtBegin).isDrawLine());
			assertEquals(writedStyle.getScaledStyles().getStyleOnScale(someScaleLevelAtBegin).isDrawPoint(), readStyle.getScaledStyles().getStyleOnScale(someScaleLevelAtBegin).isDrawPoint());
			assertEquals(writedStyle.getScaledStyles().getStyleOnScale(someScaleLevelAtBegin).isDrawPolygon(), readStyle.getScaledStyles().getStyleOnScale(someScaleLevelAtBegin).isDrawPolygon());
			assertEquals(writedStyle.getScaledStyles().getStyleOnScale(someScaleLevelAtMiddle).isDrawLine(), readStyle.getScaledStyles().getStyleOnScale(someScaleLevelAtMiddle).isDrawLine());
			assertEquals(writedStyle.getScaledStyles().getStyleOnScale(someScaleLevelAtMiddle).isDrawPoint(), readStyle.getScaledStyles().getStyleOnScale(someScaleLevelAtMiddle).isDrawPoint());
			assertEquals(writedStyle.getScaledStyles().getStyleOnScale(someScaleLevelAtMiddle).isDrawPolygon(), readStyle.getScaledStyles().getStyleOnScale(someScaleLevelAtMiddle).isDrawPolygon());
		}
		catch (Exception ex)
		{
			fail();
		}
	}
}
