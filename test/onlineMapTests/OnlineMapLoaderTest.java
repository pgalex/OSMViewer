package onlineMapTests;

import drawingStyle.DrawingStyleFactory;
import java.util.ArrayList;
import map.DefenitionTags;
import map.MapBounds;
import map.MapTag;
import map.exceptions.MapBoundsIsNullRuntimeException;
import map.exceptions.MapIsNullRutimeException;
import map.exceptions.StyleViewerIsNullException;
import onlineMap.OnlineMap;
import onlineMap.OnlineMapLoader;
import static org.junit.Assert.*;
import org.junit.*;
import osmXml.OsmTag;

/**
 *
 * @author pgalex
 */
public class OnlineMapLoaderTest
{
	/**
	 * Uses to get access to protected methods of OnlineMapLoader for testing
	 */
	private class TestOnlineMapLoader extends OnlineMapLoader
	{
		/**
		 * Run testing of protected methods of OnlineMapLoader
		 */
		public void testProtectedMethod()
		{
			createMapTagNormalWorkTest();
			createMapTagIncorrectParametersTest();
			createDefenitionTagsNormalWorkTest();
			createDefenitionTagsIncorrectParametersTest();
		}

		/**
		 * Testing creating map tag by osm tag normal work
		 */
		private void createMapTagNormalWorkTest()
		{
			OsmTag osmTag = new OsmTag("k1", "v1");
			MapTag mapTag = createMapTagByOsmTag(osmTag);
			assertNotNull(mapTag);
			assertEquals(mapTag.getKey(), osmTag.getKey());
			assertEquals(mapTag.getValue(), osmTag.getValue());

			osmTag = new OsmTag("k1", "");
			mapTag = createMapTagByOsmTag(osmTag);
			assertNotNull(mapTag);
			assertEquals(mapTag.getKey(), osmTag.getKey());
			assertEquals(mapTag.getValue(), osmTag.getValue());
		}

		/**
		 * Testing creating map tag by osm tag with incorrect parameters
		 */
		private void createMapTagIncorrectParametersTest()
		{
			try
			{
				assertNull(createMapTagByOsmTag(null));
				assertNull(createMapTagByOsmTag(new OsmTag("", "")));
				assertNull(createMapTagByOsmTag(new OsmTag("", "v1")));
			}
			catch (Exception ex)
			{
				fail();
				// not need an exceptions
			}
		}

		/**
		 * Testing creating defenition tags by osm tag array normal work
		 */
		private void createDefenitionTagsNormalWorkTest()
		{
			ArrayList<OsmTag> osmTags = new ArrayList<OsmTag>();
			osmTags.add(new OsmTag("k1", "v1"));
			osmTags.add(new OsmTag("k2", "v2"));
			osmTags.add(new OsmTag("k3", "v3"));
			osmTags.add(new OsmTag("k5", "v5"));

			DefenitionTags defenitionTags = createDefentionTagsByOsmTags(osmTags);
			assertNotNull(defenitionTags);
			assertEquals(osmTags.size(), defenitionTags.size());
			for (int i = 0; i < defenitionTags.size(); i++)
			{
				assertEquals(osmTags.get(i).getKey(), defenitionTags.get(i).getKey());
				assertEquals(osmTags.get(i).getValue(), defenitionTags.get(i).getValue());
			}
		}

		/**
		 * Testing creating defenition tags by osm tag array with
		 * incorrectParameters
		 */
		private void createDefenitionTagsIncorrectParametersTest()
		{
			assertNull(createDefentionTagsByOsmTags(null));

			DefenitionTags tagByEmptyArray = createDefentionTagsByOsmTags(new ArrayList<OsmTag>());
			assertNotNull(tagByEmptyArray);
			assertTrue(tagByEmptyArray.isEmpty());

			ArrayList<OsmTag> osmTags = new ArrayList<OsmTag>();
			osmTags.add(new OsmTag("", ""));
			osmTags.add(null);
			osmTags.add(new OsmTag("k3", "v3"));
			osmTags.add(null);
			osmTags.add(new OsmTag("", ""));

			DefenitionTags tagsByByContainsNull = createDefentionTagsByOsmTags(osmTags);
			assertNotNull(tagsByByContainsNull);
			assertEquals(1, tagsByByContainsNull.size());
			assertNotNull(tagsByByContainsNull.get(0));
		}
	}

	/**
	 * Test map loading with incorrect sector bounds
	 */
	@Test
	public void loadToMapIncorrectBoundsTest()
	{
		OnlineMapLoader testLoader = new OnlineMapLoader();
		try
		{
			testLoader.loadToMap(null, DrawingStyleFactory.createStyleViewer(), new OnlineMap());
			fail();
		}
		catch (MapBoundsIsNullRuntimeException ex)
		{
			// ok
		}
	}

	/**
	 * Test map loading with incorrect style viewer
	 */
	@Test
	public void loadToMapIncorrectViewerTest()
	{
		OnlineMapLoader testLoader = new OnlineMapLoader();
		try
		{
			testLoader.loadToMap(new MapBounds(-1, 1, -1, 1), null, new OnlineMap());
			fail();
		}
		catch (StyleViewerIsNullException ex)
		{
			// ok
		}
	}

	/**
	 * Test map loading with incorrect map
	 */
	@Test
	public void loadToMapIncorrectMapTest()
	{
		OnlineMapLoader testLoader = new OnlineMapLoader();
		try
		{
			testLoader.loadToMap(new MapBounds(-1, 1, -1, 1), DrawingStyleFactory.createStyleViewer(), null);
			fail();
		}
		catch (MapIsNullRutimeException ex)
		{
			// ok
		}
	}

	/**
	 * Test map loading with incorrect map
	 */
	@Test
	public void protectedMethodsTest()
	{
		TestOnlineMapLoader testLoader = new TestOnlineMapLoader();
		testLoader.testProtectedMethod();
	}
}
