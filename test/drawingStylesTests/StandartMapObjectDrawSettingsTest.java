package drawingStylesTests;

import IOTesting.IOTester;
import drawingStyles.DrawSettingsOnScale;
import drawingStyles.DrawSettingsOnScaleArray;
import drawingStyles.MapObjectDrawSettingsTagsCountComparator;
import drawingStyles.StandartMapObjectDrawSettings;
import java.util.ArrayList;
import java.util.Collections;
import mapDefenitionUtilities.DefenitionTags;
import mapDefenitionUtilities.Tag;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * StandartMapObjectDrawSettings class tests
 *
 * @author abc
 */
public class StandartMapObjectDrawSettingsTest
{
	/**
	 * Test setting null name
	 */
	@Test
	public void settingNullNameTest()
	{
		try
		{
			StandartMapObjectDrawSettings testSettings = new StandartMapObjectDrawSettings();
			testSettings.setName(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test setting null draw settings on scales
	 */
	@Test
	public void settingNullSettingsOnScalesTest()
	{
		try
		{
			StandartMapObjectDrawSettings testSettings = new StandartMapObjectDrawSettings();
			testSettings.setDrawSettingsOnScales(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test setting null defenition tags
	 */
	@Test
	public void settingNullTagsTest()
	{
		try
		{
			StandartMapObjectDrawSettings testSettings = new StandartMapObjectDrawSettings();
			testSettings.setDefenitionTags(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test setting null description
	 */
	@Test
	public void settingNullDescriptionTest()
	{
		try
		{
			StandartMapObjectDrawSettings testSettings = new StandartMapObjectDrawSettings();
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
		StandartMapObjectDrawSettings testSettings = new StandartMapObjectDrawSettings();
		testSettings.setDescription(someDescription);
		assertEquals(someDescription, testSettings.getDescription());
	}

	/**
	 * Test finding point draw style if object can be point
	 */
	@Test
	public void findingPointDrawStyleCanBePointTest()
	{
		StandartMapObjectDrawSettings testSettings = new StandartMapObjectDrawSettings();
		testSettings.setCanBePoint();
		testSettings.setCanNotBeLine();
		testSettings.setCanNotBePolygon();

		DrawSettingsOnScale styleOnScale = new DrawSettingsOnScale();
		styleOnScale.setDrawPoint();
		styleOnScale.setNotDrawLine();
		styleOnScale.setNotDrawPolygon();

		testSettings.getDrawSettingsOnScales().setDrawSettingsOnScale(testSettings.getDrawSettingsOnScales().getMinimumScaleLevel(),
						styleOnScale);

		assertNotNull(testSettings.findPointDrawSettings(testSettings.getDrawSettingsOnScales().getMinimumScaleLevel()));
	}

	/**
	 * Test finding point draw style if object can not be point
	 */
	@Test
	public void findingPointDrawStyleCannotBePointTest()
	{
		StandartMapObjectDrawSettings testSettings = new StandartMapObjectDrawSettings();
		testSettings.setCanNotBePoint();
		testSettings.setCanBeLine();
		testSettings.setCanBePolygon();

		DrawSettingsOnScale styleOnScale = new DrawSettingsOnScale();
		styleOnScale.setDrawPoint();
		styleOnScale.setNotDrawLine();
		styleOnScale.setNotDrawPolygon();

		testSettings.getDrawSettingsOnScales().setDrawSettingsOnScale(testSettings.getDrawSettingsOnScales().getMinimumScaleLevel(),
						styleOnScale);

		assertNull(testSettings.findPointDrawSettings(testSettings.getDrawSettingsOnScales().getMinimumScaleLevel()));
	}

	/**
	 * Test finding line draw style if object can be line
	 */
	@Test
	public void findingLineDrawStyleCanBeLineTest()
	{
		StandartMapObjectDrawSettings testSettings = new StandartMapObjectDrawSettings();
		testSettings.setCanNotBePoint();
		testSettings.setCanBeLine();
		testSettings.setCanNotBePolygon();

		DrawSettingsOnScale styleOnScale = new DrawSettingsOnScale();
		styleOnScale.setNotDrawPoint();
		styleOnScale.setDrawLine();
		styleOnScale.setNotDrawPolygon();

		testSettings.getDrawSettingsOnScales().setDrawSettingsOnScale(testSettings.getDrawSettingsOnScales().getMinimumScaleLevel(),
						styleOnScale);

		assertNotNull(testSettings.findLineDrawSettings(testSettings.getDrawSettingsOnScales().getMinimumScaleLevel()));
	}

	/**
	 * Test finding line draw style if object can not be line
	 */
	@Test
	public void findingLineDrawStyleCannotBeLineTest()
	{
		StandartMapObjectDrawSettings testSettings = new StandartMapObjectDrawSettings();
		testSettings.setCanBePoint();
		testSettings.setCanNotBeLine();
		testSettings.setCanBePolygon();

		DrawSettingsOnScale styleOnScale = new DrawSettingsOnScale();
		styleOnScale.setNotDrawPoint();
		styleOnScale.setDrawLine();
		styleOnScale.setNotDrawPolygon();

		testSettings.getDrawSettingsOnScales().setDrawSettingsOnScale(testSettings.getDrawSettingsOnScales().getMinimumScaleLevel(),
						styleOnScale);

		assertNull(testSettings.findLineDrawSettings(testSettings.getDrawSettingsOnScales().getMinimumScaleLevel()));
	}

	/**
	 * Test finding polygon draw style if object can be polygon
	 */
	@Test
	public void findingPolygonDrawStyleCanBePolygonTest()
	{
		StandartMapObjectDrawSettings testSettings = new StandartMapObjectDrawSettings();
		testSettings.setCanNotBePoint();
		testSettings.setCanNotBeLine();
		testSettings.setCanBePolygon();

		DrawSettingsOnScale styleOnScale = new DrawSettingsOnScale();
		styleOnScale.setNotDrawPoint();
		styleOnScale.setNotDrawLine();
		styleOnScale.setDrawPolygon();

		testSettings.getDrawSettingsOnScales().setDrawSettingsOnScale(testSettings.getDrawSettingsOnScales().getMinimumScaleLevel(),
						styleOnScale);

		assertNotNull(testSettings.findPolygonDrawSettings(testSettings.getDrawSettingsOnScales().getMinimumScaleLevel()));
	}

	/**
	 * Test finding polygon draw style if object can not be polygon
	 */
	@Test
	public void findingPolygonDrawStyleCannotBePolygonTest()
	{
		StandartMapObjectDrawSettings testSettings = new StandartMapObjectDrawSettings();
		testSettings.setCanBePoint();
		testSettings.setCanBeLine();
		testSettings.setCanNotBePolygon();

		DrawSettingsOnScale styleOnScale = new DrawSettingsOnScale();
		styleOnScale.setNotDrawPoint();
		styleOnScale.setNotDrawLine();
		styleOnScale.setDrawPolygon();

		testSettings.getDrawSettingsOnScales().setDrawSettingsOnScale(testSettings.getDrawSettingsOnScales().getMinimumScaleLevel(),
						styleOnScale);

		assertNull(testSettings.findPolygonDrawSettings(testSettings.getDrawSettingsOnScales().getMinimumScaleLevel()));
	}

	/**
	 * Test sorting. Sorting by tags count. More tags - less index
	 */
	@Test
	public void sortingTest()
	{
		DefenitionTags tags1 = new DefenitionTags();
		tags1.add(new Tag("k1", "v1"));
		tags1.add(new Tag("k2", "v2"));
		tags1.add(new Tag("k3", "v3"));

		DefenitionTags tags2 = new DefenitionTags();
		tags2.add(new Tag("k4", "v4"));
		tags2.add(new Tag("k5", "v5"));
		tags2.add(new Tag("k6", "v6"));
		tags2.add(new Tag("k7", "v7"));

		DefenitionTags tags3 = new DefenitionTags();
		tags3.add(new Tag("k8", "v8"));


		StandartMapObjectDrawSettings settings1 = new StandartMapObjectDrawSettings();
		settings1.setDefenitionTags(tags1);
		StandartMapObjectDrawSettings settings2 = new StandartMapObjectDrawSettings();
		settings2.setDefenitionTags(tags2);
		StandartMapObjectDrawSettings settings3 = new StandartMapObjectDrawSettings();
		settings3.setDefenitionTags(tags3);

		ArrayList<StandartMapObjectDrawSettings> styles = new ArrayList<StandartMapObjectDrawSettings>();
		styles.add(settings1);
		styles.add(settings2);
		styles.add(settings3);
		Collections.sort(styles, new MapObjectDrawSettingsTagsCountComparator());

		assertTrue(styles.get(0).getDefenitionTags().includingIn(tags2));
		assertTrue(styles.get(1).getDefenitionTags().includingIn(tags1));
		assertTrue(styles.get(2).getDefenitionTags().includingIn(tags3));
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

			DrawSettingsOnScale styleOnScale1 = new DrawSettingsOnScale();
			styleOnScale1.setDrawPoint();
			styleOnScale1.setNotDrawLine();
			styleOnScale1.setDrawPolygon();

			scaledStyles.setDrawSettingsOnScale(someScaleLevelAtBegin, styleOnScale1);

			DrawSettingsOnScale styleOnScale2 = new DrawSettingsOnScale();
			styleOnScale2.setNotDrawPoint();
			styleOnScale2.setDrawLine();
			styleOnScale2.setDrawPolygon();

			scaledStyles.setDrawSettingsOnScale(someScaleLevelAtMiddle, styleOnScale2);
			DefenitionTags tags = new DefenitionTags();
			tags.add(new Tag("k1", "v1"));
			tags.add(new Tag("k2", "v2"));

			StandartMapObjectDrawSettings writedStyle = new StandartMapObjectDrawSettings();
			writedStyle.setCanBePoint();
			writedStyle.setCanNotBeLine();
			writedStyle.setCanBePolygon();
			writedStyle.setDrawPriority(10);
			writedStyle.setDescription("object1");
			writedStyle.setDrawSettingsOnScales(scaledStyles);
			writedStyle.setDefenitionTags(tags);
			writedStyle.setName("someName");

			writedStyle.writeToStream(IOTester.createTestOutputStream());

			StandartMapObjectDrawSettings readStyle = new StandartMapObjectDrawSettings();
			readStyle.readFromStream(IOTester.createTestInputStream());

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
			assertEquals(writedStyle.getName(), readStyle.getName());
		}
		catch (Exception ex)
		{
			fail();
		}
	}
}
