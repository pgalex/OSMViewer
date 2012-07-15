package onlineMapTests;

import drawingStyles.DrawingStylesFactory;
import drawingStyles.MapObjectStyle;
import drawingStyles.StyleEditor;
import java.util.ArrayList;
import drawingStyles.DefenitionTags;
import drawingStyles.EditableDefenitionTags;
import map.MapPoint;
import drawingStyles.MapTag;
import onlineMap.OnlineMap;
import onlineMap.OnlineMapLoader;
import osmXml.OsmNode;
import osmXml.OsmTag;
import static org.junit.Assert.*;

/**
 * Uses to get access to protected methods of OnlineMapLoader for testing
 */
public class TestOnlineMapLoader extends OnlineMapLoader
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
		createMapPointNormalWorkTest();
		createMapPointIncorrectParametersTest();
		fillMapWithPointsIncorrectParametersTest();
		fillMapWithPointsNormalWorkTest();
	}

	/**
	 * Testing creating map tag by osm tag normal work
	 */
	private void createMapTagNormalWorkTest()
	{
		OsmTag osmTag = new OsmTag("k1", "v1");
		MapTag mapTag = createMapTagByOsmTag(osmTag);
		assertNotNull(mapTag);
		assertEquals(osmTag.getKey(), mapTag.getKey());
		assertEquals(osmTag.getValue(), mapTag.getValue());

		osmTag = new OsmTag("k1", "");
		mapTag = createMapTagByOsmTag(osmTag);
		assertNotNull(mapTag);
		assertEquals(osmTag.getKey(), mapTag.getKey());
		assertEquals(osmTag.getValue(), mapTag.getValue());
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
	 * Testing creating defenition tags by osm tag array with incorrectParameters
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

	/**
	 * Test creating map point by osm node normal work
	 */
	private void createMapPointNormalWorkTest()
	{
		ArrayList<OsmTag> nodeTags = new ArrayList<OsmTag>();
		nodeTags.add(new OsmTag("k1", "v1"));
		nodeTags.add(new OsmTag("k2", "v2"));
		nodeTags.add(new OsmTag("k3", "v3"));

		OsmNode osmNode = new OsmNode();
		osmNode.setTags(nodeTags);
		osmNode.setId(123456789);
		osmNode.setLatitude(Math.random() * 10);
		osmNode.setLongitude(Math.random() * -10);

		MapPoint pointByNode = createMapPointByOsmNode(osmNode);
		assertNotNull(pointByNode);
		assertNull(pointByNode.getStyleIndex());
		assertEquals(osmNode.getId(), pointByNode.getId());
		assertEquals(osmNode.getLatitude(), pointByNode.getPosition().getLatitude(), 0.00001);
		assertEquals(osmNode.getLongitude(), pointByNode.getPosition().getLongitude(), 0.00001);
		assertEquals(osmNode.getTags().size(), pointByNode.getDefenitionTags().size());
		for (int i = 0; i < pointByNode.getDefenitionTags().size(); i++)
		{
			assertEquals(osmNode.getTags().get(i).getKey(), pointByNode.getDefenitionTags().get(i).getKey());
			assertEquals(osmNode.getTags().get(i).getValue(), pointByNode.getDefenitionTags().get(i).getValue());
		}
	}

	/**
	 * Test creating map point by osm node with incorrect parameters
	 */
	private void createMapPointIncorrectParametersTest()
	{
		assertNull(createMapPointByOsmNode(null));

		OsmNode nodeWithoutTags = new OsmNode();
		nodeWithoutTags.setTags(null);
		assertNull(createMapPointByOsmNode(nodeWithoutTags));

		OsmNode nodeWithEmptyTags = new OsmNode();
		nodeWithoutTags.setTags(new ArrayList<OsmTag>());
		assertNull(createMapPointByOsmNode(nodeWithEmptyTags));

		OsmNode nodeContainsNullTags = new OsmNode();
		ArrayList<OsmTag> tagsContiansNull = new ArrayList<OsmTag>(4);
		tagsContiansNull.add(null);
		tagsContiansNull.add(null);
		tagsContiansNull.add(null);
		tagsContiansNull.add(null);
		nodeContainsNullTags.setTags(tagsContiansNull);
		assertNull(createMapPointByOsmNode(nodeContainsNullTags));
	}

	/**
	 * Testing fillMapWithPoints with incorrect parameters
	 */
	private void fillMapWithPointsIncorrectParametersTest()
	{
		try
		{
			fillMapWithPoints(null, null, null);
			fillMapWithPoints(new ArrayList<OsmNode>(), null, null);
			fillMapWithPoints(new ArrayList<OsmNode>(1), DrawingStylesFactory.createStyleViewer(), null);
			fillMapWithPoints(new ArrayList<OsmNode>(2), null, new OnlineMap());
			fillMapWithPoints(new ArrayList<OsmNode>(3), DrawingStylesFactory.createStyleViewer(), new OnlineMap());
		}
		catch (Exception ex)
		{
			// not need exceptions
			fail();
		}
	}

	/**
	 * Testing fillMapWithPoints with incorrect parameters
	 */
	private void fillMapWithPointsNormalWorkTest()
	{

		try
		{
			StyleEditor styleEditor = DrawingStylesFactory.createStyleEditor();
			EditableDefenitionTags testStyleTags = new EditableDefenitionTags();
			testStyleTags.add(new MapTag("k1", "v1"));
			MapObjectStyle testStyle = new MapObjectStyle(true, true, true, null, 0, "", null, testStyleTags);
			styleEditor.addMapObjectStyle(testStyle);


			ArrayList<OsmTag> nodeTags = new ArrayList<OsmTag>();
			nodeTags.add(new OsmTag("k1", "v1"));
			OsmNode node = new OsmNode();
			node.setLatitude(11);
			node.setLongitude(12);
			node.setId(123456789);
			node.setTags(nodeTags);


			ArrayList<OsmNode> osmNodes = new ArrayList<OsmNode>();
			osmNodes.add(node);

			TestOnlineMap testMap = new TestOnlineMap();
			fillMapWithPoints(osmNodes, styleEditor, testMap);

			assertEquals(1, testMap.getObjects().size());
			assertEquals(node.getId(), testMap.getObjects().get(0).getId());
			assertEquals((Integer) 0, testMap.getObjects().get(0).getStyleIndex());
			for (int i = 0; i < nodeTags.size(); i++)
			{
				assertEquals(nodeTags.get(i).getKey(), testMap.getObjects().get(0).getDefenitionTags().get(i).getKey());
				assertEquals(nodeTags.get(i).getValue(), testMap.getObjects().get(0).getDefenitionTags().get(i).getValue());
			}
		}
		catch (Exception ex)
		{
			fail();
		}

	}
}
