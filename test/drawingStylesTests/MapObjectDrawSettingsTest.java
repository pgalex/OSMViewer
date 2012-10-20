package drawingStylesTests;

import IOTesting.IOTester;
import drawingStyles.DrawSettingsOnScale;
import drawingStyles.DrawSettingsOnScaleArray;
import drawingStyles.EditableDefenitionTags;
import drawingStyles.FindStyleIndexComparator;
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
	 * Test setting null description
	 */
	@Test
	public void settingNullDescriptionTest()
	{
		try
		{
			MapObjectDrawSettings testSettings = new MapObjectDrawSettings();
			testSettings.setDescription(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test set description normal work
	 */
	@Test
	public void settingDescriptionNormalWorkTest()
	{
		final String someDescription = "some description";
		MapObjectDrawSettings testSettings = new MapObjectDrawSettings();
		testSettings.setDescription(someDescription);
		assertEquals(someDescription, testSettings.getDescription());
	}

	/**
	 * Test finding point draw style if object can be point
	 */
	@Test
	public void findingPointDrawStyleCanBePointTest()
	{
		MapObjectDrawSettings testSettings = new MapObjectDrawSettings(true, false, false, null, 0, "", null, null);

		testSettings.getDrawSettingsOnScales().setDrawSettingsOnScale(testSettings.getDrawSettingsOnScales().getMinimumScaleLevel(),
						new DrawSettingsOnScale(true, false, false, null, null, null, null));

		assertNotNull(testSettings.findPointDrawSettings(testSettings.getDrawSettingsOnScales().getMinimumScaleLevel()));
	}

	/**
	 * Test finding point draw style if object can not be point
	 */
	@Test
	public void findingPointDrawStyleCannotBePointTest()
	{
		MapObjectDrawSettings testSettings = new MapObjectDrawSettings(false, true, true, null, 0, "", null, null);

		testSettings.getDrawSettingsOnScales().setDrawSettingsOnScale(testSettings.getDrawSettingsOnScales().getMinimumScaleLevel(),
						new DrawSettingsOnScale(true, false, false, null, null, null, null));

		assertNull(testSettings.findPointDrawSettings(testSettings.getDrawSettingsOnScales().getMinimumScaleLevel()));
	}

	/**
	 * Test finding line draw style if object can be line
	 */
	@Test
	public void findingLineDrawStyleCanBeLineTest()
	{
		MapObjectDrawSettings testSettings = new MapObjectDrawSettings(false, true, false, null, 0, "", null, null);

		testSettings.getDrawSettingsOnScales().setDrawSettingsOnScale(testSettings.getDrawSettingsOnScales().getMinimumScaleLevel(),
						new DrawSettingsOnScale(false, true, false, null, null, null, null));

		assertNotNull(testSettings.findLineDrawSettings(testSettings.getDrawSettingsOnScales().getMinimumScaleLevel()));
	}

	/**
	 * Test finding line draw style if object can not be line
	 */
	@Test
	public void findingLineDrawStyleCannotBeLineTest()
	{
		MapObjectDrawSettings testSettings = new MapObjectDrawSettings(true, false, true, null, 0, "", null, null);

		testSettings.getDrawSettingsOnScales().setDrawSettingsOnScale(testSettings.getDrawSettingsOnScales().getMinimumScaleLevel(),
						new DrawSettingsOnScale(false, true, false, null, null, null, null));

		assertNull(testSettings.findLineDrawSettings(testSettings.getDrawSettingsOnScales().getMinimumScaleLevel()));
	}

	/**
	 * Test finding polygon draw style if object can be polygon
	 */
	@Test
	public void findingPolygonDrawStyleCanBePolygonTest()
	{
		MapObjectDrawSettings testSettings = new MapObjectDrawSettings(false, false, true, null, 0, "", null, null);

		testSettings.getDrawSettingsOnScales().setDrawSettingsOnScale(testSettings.getDrawSettingsOnScales().getMinimumScaleLevel(),
						new DrawSettingsOnScale(false, false, true, null, null, null, null));

		assertNotNull(testSettings.findPolygonDrawSettings(testSettings.getDrawSettingsOnScales().getMinimumScaleLevel()));
	}

	/**
	 * Test finding polygon draw style if object can not be polygon
	 */
	@Test
	public void findingPolygonDrawStyleCannotBePolygonTest()
	{
		MapObjectDrawSettings testSettings = new MapObjectDrawSettings(true, true, false, null, 0, "", null, null);

		testSettings.getDrawSettingsOnScales().setDrawSettingsOnScale(testSettings.getDrawSettingsOnScales().getMinimumScaleLevel(),
						new DrawSettingsOnScale(false, false, true, null, null, null, null));

		assertNull(testSettings.findPolygonDrawSettings(testSettings.getDrawSettingsOnScales().getMinimumScaleLevel()));
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
		Collections.sort(styles, new FindStyleIndexComparator());

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
		assertNotNull(style.getDrawSettingsOnScales());
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

			assertEquals(writedStyle.isCanBeLine(), readStyle.isCanBeLine());
			assertEquals(writedStyle.isCanBePoint(), readStyle.isCanBePoint());
			assertEquals(writedStyle.isCanBePolygon(), readStyle.isCanBePolygon());
			assertEquals(true, writedStyle.getDefenitionTags().includingIn(readStyle.getDefenitionTags()));
			assertEquals(writedStyle.getDescription(), readStyle.getDescription());

			assertEquals(writedStyle.getTextTagKeys().getKeysCount(), readStyle.getTextTagKeys().getKeysCount());
			for (int i = 0; i < writedStyle.getTextTagKeys().getKeysCount(); i++)
			{
				assertEquals(writedStyle.getTextTagKeys().getKey(i), readStyle.getTextTagKeys().getKey(i));
			}

			assertEquals(writedStyle.getDrawPriority(), readStyle.getDrawPriority());
			assertEquals(writedStyle.getDescription(), readStyle.getDescription());
			assertEquals(writedStyle.getDrawSettingsOnScales().getDrawSettingsOnScale(someScaleLevelAtBegin).isDrawLine(), readStyle.getDrawSettingsOnScales().getDrawSettingsOnScale(someScaleLevelAtBegin).isDrawLine());
			assertEquals(writedStyle.getDrawSettingsOnScales().getDrawSettingsOnScale(someScaleLevelAtBegin).isDrawPoint(), readStyle.getDrawSettingsOnScales().getDrawSettingsOnScale(someScaleLevelAtBegin).isDrawPoint());
			assertEquals(writedStyle.getDrawSettingsOnScales().getDrawSettingsOnScale(someScaleLevelAtBegin).isDrawPolygon(), readStyle.getDrawSettingsOnScales().getDrawSettingsOnScale(someScaleLevelAtBegin).isDrawPolygon());
			assertEquals(writedStyle.getDrawSettingsOnScales().getDrawSettingsOnScale(someScaleLevelAtMiddle).isDrawLine(), readStyle.getDrawSettingsOnScales().getDrawSettingsOnScale(someScaleLevelAtMiddle).isDrawLine());
			assertEquals(writedStyle.getDrawSettingsOnScales().getDrawSettingsOnScale(someScaleLevelAtMiddle).isDrawPoint(), readStyle.getDrawSettingsOnScales().getDrawSettingsOnScale(someScaleLevelAtMiddle).isDrawPoint());
			assertEquals(writedStyle.getDrawSettingsOnScales().getDrawSettingsOnScale(someScaleLevelAtMiddle).isDrawPolygon(), readStyle.getDrawSettingsOnScales().getDrawSettingsOnScale(someScaleLevelAtMiddle).isDrawPolygon());
		}
		catch (Exception ex)
		{
			fail();
		}
	}
}
