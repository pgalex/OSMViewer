package drawingStylesTests;

import IOTesting.IOTester;
import drawingStyles.StandartDrawSettingsContainer;
import drawingStyles.StandartMapObjectDrawSettings;
import mapDefenitionUtilities.DefenitionTags;
import mapDefenitionUtilities.Tag;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * StandartDrawSettingsContainer class tests
 *
 * @author pgalex
 */
public class StandartDrawSettingsContainerTest
{
	/**
	 * Test initializing with zero objects draw settingsz count
	 */
	@Test
	public void creatingEmptyTest()
	{
		StandartDrawSettingsContainer container = new StandartDrawSettingsContainer();
		assertEquals(0, container.countOfMapObjectDrawSettings());
	}

	/**
	 * Test initializing map draw settings after creating
	 */
	@Test
	public void creatingWithDefaultMapDrawSettingsTest()
	{
		StandartDrawSettingsContainer container = new StandartDrawSettingsContainer();
		assertNotNull(container.getMapDrawSettings());
	}

	/**
	 * Test removing with index less than bounds
	 */
	@Test
	public void removingByMoreThanBoundIndexTest()
	{
		try
		{
			StandartDrawSettingsContainer container = new StandartDrawSettingsContainer();
			StandartMapObjectDrawSettings drawSettings1 = new StandartMapObjectDrawSettings();
			drawSettings1.setDescription("1");
			container.addMapObjectDrawSettings(drawSettings1);
			container.removeMapObjectDrawSettings(2);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test removing with index less than bounds
	 */
	@Test
	public void removingByLessThanBoundIndexTest()
	{
		try
		{
			StandartDrawSettingsContainer container = new StandartDrawSettingsContainer();
			StandartMapObjectDrawSettings drawSettings1 = new StandartMapObjectDrawSettings();
			drawSettings1.setDescription("1");
			container.addMapObjectDrawSettings(drawSettings1);
			container.removeMapObjectDrawSettings(-1);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test removing normal work
	 */
	@Test
	public void removingDrawSettingsNormalWorkTest()
	{
		StandartDrawSettingsContainer container = new StandartDrawSettingsContainer();
		StandartMapObjectDrawSettings drawSettings1 = new StandartMapObjectDrawSettings();
		drawSettings1.setDescription("1");
		container.addMapObjectDrawSettings(drawSettings1);

		StandartMapObjectDrawSettings drawSettings2 = new StandartMapObjectDrawSettings();
		drawSettings2.setDescription("2");
		container.addMapObjectDrawSettings(drawSettings2);

		container.removeMapObjectDrawSettings(0);
		assertEquals(1, container.countOfMapObjectDrawSettings());
		assertEquals(drawSettings2, container.getMapObjectDrawSettings(0));
	}

	/**
	 * Test adding null draw settings
	 */
	@Test
	public void addingNullDrawSettingsTest()
	{
		try
		{
			StandartDrawSettingsContainer container = new StandartDrawSettingsContainer();
			container.addMapObjectDrawSettings(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test adding draw settings normal work
	 */
	@Test
	public void addingNormalWorkTestTest()
	{
		StandartDrawSettingsContainer container = new StandartDrawSettingsContainer();

		StandartMapObjectDrawSettings drawSettings1 = new StandartMapObjectDrawSettings();
		drawSettings1.setDescription("1");
		container.addMapObjectDrawSettings(drawSettings1);

		StandartMapObjectDrawSettings drawSettings2 = new StandartMapObjectDrawSettings();
		drawSettings2.setDescription("2");
		container.addMapObjectDrawSettings(drawSettings2);

		assertEquals(2, container.countOfMapObjectDrawSettings());
	}

	/**
	 * Finding draw settings by null object tags
	 */
	@Test
	public void findingDrawSettingsByNullTagsTest()
	{
		StandartDrawSettingsContainer container = new StandartDrawSettingsContainer();
		assertNull(container.findMapObjectDrawSettings(null));
	}

	/**
	 * Testing normal work of finding draw settings by tags. All draw settings
	 * tags must be in object defenition tags. If more than one draw settings
	 * found, choosing that contains more tags
	 */
	@Test
	public void findingDrawSettingsNormalWorkTest()
	{
		StandartDrawSettingsContainer container = new StandartDrawSettingsContainer();

		StandartMapObjectDrawSettings style1 = new StandartMapObjectDrawSettings();
		DefenitionTags tags1 = new DefenitionTags();
		tags1.add(new Tag("k1", "v1"));
		tags1.add(new Tag("k2", "v2"));
		style1.setDescription("style1");
		style1.setDefenitionTags(tags1);

		StandartMapObjectDrawSettings style2 = new StandartMapObjectDrawSettings();
		DefenitionTags tags2 = new DefenitionTags();
		tags2.add(new Tag("k1", "v1"));
		style2.setDescription("style2");
		style2.setDefenitionTags(tags2);

		StandartMapObjectDrawSettings style3 = new StandartMapObjectDrawSettings();
		DefenitionTags tags3 = new DefenitionTags();
		tags3.add(new Tag("k1", "v1"));
		tags3.add(new Tag("k2", "v2"));
		tags3.add(new Tag("k3", "v3"));
		tags3.add(new Tag("k4", "v4"));
		style3.setDescription("style3");
		style3.setDefenitionTags(tags3);

		StandartMapObjectDrawSettings style4 = new StandartMapObjectDrawSettings();
		style4.setDescription("style4");

		container.addMapObjectDrawSettings(style1);
		container.addMapObjectDrawSettings(style2);
		container.addMapObjectDrawSettings(style3);
		container.addMapObjectDrawSettings(style4);

		DefenitionTags objectTags1 = new DefenitionTags();
		objectTags1.add(new Tag("k1", "v1"));
		assertEquals(style2, container.findMapObjectDrawSettings(objectTags1));

		DefenitionTags objectTags2 = new DefenitionTags();
		objectTags2.add(new Tag("k1", "v1"));
		objectTags2.add(new Tag("k5", "v5"));
		assertEquals(style2, container.findMapObjectDrawSettings(objectTags2));

		DefenitionTags objectTags3 = new DefenitionTags();
		objectTags3.add(new Tag("k1", "v1"));
		objectTags3.add(new Tag("k5", "v5"));
		objectTags3.add(new Tag("k2", "v2"));
		assertEquals(style1, container.findMapObjectDrawSettings(objectTags3));

		DefenitionTags objectTags4 = new DefenitionTags();
		objectTags4.add(new Tag("k1", "v1"));
		objectTags4.add(new Tag("k3", "v3"));
		objectTags4.add(new Tag("k5", "v5"));
		objectTags4.add(new Tag("k4", "v4"));
		objectTags4.add(new Tag("k2", "v2"));
		assertEquals(style3, container.findMapObjectDrawSettings(objectTags4));

		assertEquals(style4, container.findMapObjectDrawSettings(new DefenitionTags()));

		DefenitionTags objectTags5 = new DefenitionTags();
		objectTags5.add(new Tag("k5", "v5"));
		assertNull(container.findMapObjectDrawSettings(objectTags5));
	}

	/**
	 * Reading/writing test with 0 draw settings count
	 */
	@Test
	public void nullLengthReadingWritingTest()
	{
		try
		{
			StandartDrawSettingsContainer writingContainer = new StandartDrawSettingsContainer();
			writingContainer.writeToStream(IOTester.createTestOutputStream());

			StandartDrawSettingsContainer readingContainer = new StandartDrawSettingsContainer();
			readingContainer.readFromStream(IOTester.createTestInputStream());

			assertEquals(writingContainer.countOfMapObjectDrawSettings(), readingContainer.countOfMapObjectDrawSettings());
		}
		catch (Exception ex)
		{
			fail();
		}
	}
}
