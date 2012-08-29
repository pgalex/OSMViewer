package drawingStylesTests;

import IOTesting.IOTester;
import drawingStyles.DrawSettingsOnScale;
import drawingStyles.DrawSettingsOnScaleArray;
import drawingStyles.EditableDefenitionTags;
import drawingStyles.FindStyleIndexComaprator;
import drawingStyles.MapObjectDrawSettings;
import drawingStyles.MapTag;
import java.util.ArrayList;
import java.util.Collections;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * MapObjectDrawSettings class tests
 *
 * @author abc
 */
public class MapObjectDrawSettingsTest
{
	/**
	 * Test finding point draw style if object can be point
	 */
	@Test
	public void findingPointDrawStyleCanBePoint()
	{
		MapObjectDrawSettings testSettings = new MapObjectDrawSettings(true, false, false, null, 0, "", null, null);

		testSettings.getScaledStyles().setDrawSettingsOnScale(testSettings.getScaledStyles().getMinimumScaleLevel(),
						new DrawSettingsOnScale(true, false, false, null, null, null, null));

		assertNotNull(testSettings.findPointDrawStyle(testSettings.getScaledStyles().getMinimumScaleLevel()));
	}

	/**
	 * Test finding point draw style if object can not be point
	 */
	@Test
	public void findingPointDrawStyleCannotBePoint()
	{
		MapObjectDrawSettings testSettings = new MapObjectDrawSettings(false, true, true, null, 0, "", null, null);

		testSettings.getScaledStyles().setDrawSettingsOnScale(testSettings.getScaledStyles().getMinimumScaleLevel(),
						new DrawSettingsOnScale(true, false, false, null, null, null, null));

		assertNull(testSettings.findPointDrawStyle(testSettings.getScaledStyles().getMinimumScaleLevel()));
	}

	/**
	 * Test finding line draw style if object can be line
	 */
	@Test
	public void findingLineDrawStyleCanBeLine()
	{
		MapObjectDrawSettings testSettings = new MapObjectDrawSettings(false, true, false, null, 0, "", null, null);

		testSettings.getScaledStyles().setDrawSettingsOnScale(testSettings.getScaledStyles().getMinimumScaleLevel(),
						new DrawSettingsOnScale(false, true, false, null, null, null, null));

		assertNotNull(testSettings.findLineDrawStyle(testSettings.getScaledStyles().getMinimumScaleLevel()));
	}

	/**
	 * Test finding line draw style if object can not be line
	 */
	@Test
	public void findingLineDrawStyleCannotBeLine()
	{
		MapObjectDrawSettings testSettings = new MapObjectDrawSettings(true, false, true, null, 0, "", null, null);

		testSettings.getScaledStyles().setDrawSettingsOnScale(testSettings.getScaledStyles().getMinimumScaleLevel(),
						new DrawSettingsOnScale(false, true, false, null, null, null, null));

		assertNull(testSettings.findLineDrawStyle(testSettings.getScaledStyles().getMinimumScaleLevel()));
	}

	/**
	 * Test finding polygon draw style if object can be polygon
	 */
	@Test
	public void findingPolygonDrawStyleCanBePolygon()
	{
		MapObjectDrawSettings testSettings = new MapObjectDrawSettings(false, false, true, null, 0, "", null, null);

		testSettings.getScaledStyles().setDrawSettingsOnScale(testSettings.getScaledStyles().getMinimumScaleLevel(),
						new DrawSettingsOnScale(false, false, true, null, null, null, null));

		assertNotNull(testSettings.findPolygonDrawStyle(testSettings.getScaledStyles().getMinimumScaleLevel()));
	}

	/**
	 * Test finding polygon draw style if object can not be polygon
	 */
	@Test
	public void findingPolygonDrawStyleCannotBePolygon()
	{
		MapObjectDrawSettings testSettings = new MapObjectDrawSettings(true, true, false, null, 0, "", null, null);

		testSettings.getScaledStyles().setDrawSettingsOnScale(testSettings.getScaledStyles().getMinimumScaleLevel(),
						new DrawSettingsOnScale(false, false, true, null, null, null, null));

		assertNull(testSettings.findPolygonDrawStyle(testSettings.getScaledStyles().getMinimumScaleLevel()));
	}

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

		ArrayList<MapObjectDrawSettings> styles = new ArrayList<MapObjectDrawSettings>();
		styles.add(new MapObjectDrawSettings(true, true, true, null, 0, "", null, tags1));
		styles.add(new MapObjectDrawSettings(true, true, true, null, 0, "", null, tags2));
		styles.add(new MapObjectDrawSettings(true, true, true, null, 0, "", null, tags3));
		Collections.sort(styles, new FindStyleIndexComaprator());

		assertTrue(styles.get(0).getDefenitionTags().includingIn(tags2));
		assertTrue(styles.get(1).getDefenitionTags().includingIn(tags1));
		assertTrue(styles.get(2).getDefenitionTags().includingIn(tags3));
	}

	/**
	 * Constructor should auto-initialize null values
	 */
	@Test
	public void autoInitializeTest()
	{
		MapObjectDrawSettings style = new MapObjectDrawSettings(true, true, true, null, 0, "", null, null);
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

			DrawSettingsOnScaleArray scaledStyles = new DrawSettingsOnScaleArray();
			scaledStyles.setDrawSettingsOnScale(someScaleLevelAtBegin, new DrawSettingsOnScale(true, false, true, null, null,
							null, null));
			scaledStyles.setDrawSettingsOnScale(someScaleLevelAtMiddle, new DrawSettingsOnScale(false, true, true, null, null,
							null, null));
			EditableDefenitionTags tags = new EditableDefenitionTags();
			tags.add(new MapTag("k1", "v1"));
			tags.add(new MapTag("k2", "v2"));

			MapObjectDrawSettings writedStyle = new MapObjectDrawSettings(true, false,
							true, null, 10, "object1", scaledStyles, tags);
			IOTester.writeToTestFile(writedStyle);

			MapObjectDrawSettings readStyle = new MapObjectDrawSettings();
			IOTester.readFromTestFile(readStyle);

			assertEquals(writedStyle.canBeLine(), readStyle.canBeLine());
			assertEquals(writedStyle.canBePoint(), readStyle.canBePoint());
			assertEquals(writedStyle.canBePolygon(), readStyle.canBePolygon());
			assertEquals(true, writedStyle.getDefenitionTags().includingIn(readStyle.getDefenitionTags()));
			assertEquals(writedStyle.getDescription(), readStyle.getDescription());
			assertArrayEquals(writedStyle.getTextTagKeys().getTagsKeys(), readStyle.getTextTagKeys().getTagsKeys());
			assertEquals(writedStyle.getDrawPriority(), readStyle.getDrawPriority());
			assertEquals(writedStyle.getDescription(), readStyle.getDescription());
			assertEquals(writedStyle.getScaledStyles().getDrawSettingsOnScale(someScaleLevelAtBegin).isDrawLine(), readStyle.getScaledStyles().getDrawSettingsOnScale(someScaleLevelAtBegin).isDrawLine());
			assertEquals(writedStyle.getScaledStyles().getDrawSettingsOnScale(someScaleLevelAtBegin).isDrawPoint(), readStyle.getScaledStyles().getDrawSettingsOnScale(someScaleLevelAtBegin).isDrawPoint());
			assertEquals(writedStyle.getScaledStyles().getDrawSettingsOnScale(someScaleLevelAtBegin).isDrawPolygon(), readStyle.getScaledStyles().getDrawSettingsOnScale(someScaleLevelAtBegin).isDrawPolygon());
			assertEquals(writedStyle.getScaledStyles().getDrawSettingsOnScale(someScaleLevelAtMiddle).isDrawLine(), readStyle.getScaledStyles().getDrawSettingsOnScale(someScaleLevelAtMiddle).isDrawLine());
			assertEquals(writedStyle.getScaledStyles().getDrawSettingsOnScale(someScaleLevelAtMiddle).isDrawPoint(), readStyle.getScaledStyles().getDrawSettingsOnScale(someScaleLevelAtMiddle).isDrawPoint());
			assertEquals(writedStyle.getScaledStyles().getDrawSettingsOnScale(someScaleLevelAtMiddle).isDrawPolygon(), readStyle.getScaledStyles().getDrawSettingsOnScale(someScaleLevelAtMiddle).isDrawPolygon());
		}
		catch (Exception ex)
		{
			fail();
		}
	}
}
