package onlineMapTests;

import drawingStyles.DefenitionTags;
import drawingStyles.DrawingStylesFactory;
import drawingStyles.MapObjectDrawSettings;
import drawingStyles.StyleEditor;
import drawingStyles.Tag;
import java.util.ArrayList;
import map.MapLine;
import map.MapObject;
import map.MapPoint;
import map.MapPolygon;
import MapDefenitionUtilities.MapPosition;
import map.onlineMap.OnlineMap;
import map.onlineMap.OnlineMapLoader;
import static org.junit.Assert.*;
import osmXml.OsmNode;
import osmXml.OsmTag;
import osmXml.OsmWay;

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
		findPointsInOsmNodesNullParametersTest();
		findPointsInOsmNodesEmptyNodesTest();
		findPointsInOsmNodesEmptyIdsTest();
		findPointsInOsmNodesTest();
		findPointsInOsmNodesIdsMoreThenNodesTest();
		findPointsInOsmNodesSomeNodesNotFounds();
		createMapObjectByWayNullParametersTest();
		createMapLineByWay();
		createMapPolygonByWay();
	}

	/**
	 * Test creating map polygon or map line with null parameters
	 */
	private void createMapObjectByWayNullParametersTest()
	{
		MapObject createdObject = createMapObjectByWay(null, null);
		assertNull(createdObject);
	}

	/**
	 * Test creating map object by way if way is non closed
	 */
	private void createMapLineByWay()
	{
		ArrayList<OsmNode> nodes = new ArrayList<OsmNode>();
		OsmNode node1 = new OsmNode();
		node1.setId(10000010);
		node1.setLatitude(10.0);
		node1.setLongitude(20.0);
		nodes.add(node1);

		OsmNode node2 = new OsmNode();
		node2.setId(10000020);
		node2.setLatitude(-15.0);
		node2.setLongitude(20.0);
		nodes.add(node2);

		OsmNode node3 = new OsmNode();
		node3.setId(10000030);
		node3.setLatitude(20.0);
		node3.setLongitude(-25.0);
		nodes.add(node3);

		OsmNode node4 = new OsmNode();
		node4.setId(10000040);
		node4.setLatitude(50.0);
		node4.setLongitude(-55.0);
		nodes.add(node4);

		OsmWay way = new OsmWay();
		way.setId(1000000);
		way.addNodeId(10000010);
		way.addNodeId(10000020);
		way.addNodeId(10000030);

		ArrayList<OsmTag> wayTags = new ArrayList<OsmTag>();
		wayTags.add(new OsmTag("key1", "value1"));
		wayTags.add(new OsmTag("key2", "value2"));
		way.setTags(wayTags);

		MapObject createdObject = createMapObjectByWay(way, nodes);
		assertTrue(createdObject instanceof MapLine);
		assertEquals(way.getId(), createdObject.getId());
		assertEquals(way.getNodesIds().size(), ((MapLine) createdObject).getPointsCount());
		assertEquals(wayTags.size(), createdObject.getDefenitionTags().count());
		for (int i = 0; i < wayTags.size(); i++)
		{
			OsmTag wayTag = way.getTags().get(i);
			assertEquals(wayTag.getKey(), createdObject.getDefenitionTags().get(i).getKey());
			assertEquals(wayTag.getValue(), createdObject.getDefenitionTags().get(i).getValue());
		}
	}

	/**
	 * Test creating map object by way if way is closed
	 */
	private void createMapPolygonByWay()
	{
		ArrayList<OsmNode> nodes = new ArrayList<OsmNode>();
		OsmNode node1 = new OsmNode();
		node1.setId(10000010);
		node1.setLatitude(10.0);
		node1.setLongitude(20.0);
		nodes.add(node1);

		OsmNode node2 = new OsmNode();
		node2.setId(10000020);
		node2.setLatitude(-15.0);
		node2.setLongitude(20.0);
		nodes.add(node2);

		OsmNode node3 = new OsmNode();
		node3.setId(10000030);
		node3.setLatitude(20.0);
		node3.setLongitude(-25.0);
		nodes.add(node3);

		OsmNode node4 = new OsmNode();
		node4.setId(10000040);
		node4.setLatitude(50.0);
		node4.setLongitude(-55.0);
		nodes.add(node4);

		OsmWay way = new OsmWay();
		way.setId(1000000);
		way.addNodeId(10000010);
		way.addNodeId(10000020);
		way.addNodeId(10000030);
		way.addNodeId(10000010);

		ArrayList<OsmTag> wayTags = new ArrayList<OsmTag>();
		wayTags.add(new OsmTag("key1", "value1"));
		wayTags.add(new OsmTag("key2", "value2"));
		way.setTags(wayTags);

		MapObject createdObject = createMapObjectByWay(way, nodes);
		assertTrue(createdObject instanceof MapPolygon);
		assertEquals(way.getId(), createdObject.getId());
		assertEquals(way.getNodesIds().size(), ((MapPolygon) createdObject).getPointsCount());
		assertEquals(wayTags.size(), createdObject.getDefenitionTags().count());
		for (int i = 0; i < wayTags.size(); i++)
		{
			OsmTag wayTag = way.getTags().get(i);
			assertEquals(wayTag.getKey(), createdObject.getDefenitionTags().get(i).getKey());
			assertEquals(wayTag.getValue(), createdObject.getDefenitionTags().get(i).getValue());
		}
	}

	/**
	 * Testing find points with null parameters
	 */
	private void findPointsInOsmNodesNullParametersTest()
	{
		MapPosition[] points = findPointsInOsmNodes(null, null);
		assertNotNull(points);
		assertEquals(0, points.length);
	}

	/**
	 * Testing find points with empty nodes
	 */
	private void findPointsInOsmNodesEmptyNodesTest()
	{
		ArrayList<Long> ids = new ArrayList<Long>();
		ids.add(new Long(10));
		ArrayList<OsmNode> nodes = new ArrayList<OsmNode>();
		MapPosition[] points = findPointsInOsmNodes(ids, nodes);
		assertNotNull(points);
		assertEquals(0, points.length);
	}

	/**
	 * Testing find points with empty ids
	 */
	private void findPointsInOsmNodesEmptyIdsTest()
	{
		ArrayList<Long> ids = new ArrayList<Long>();
		ArrayList<OsmNode> nodes = new ArrayList<OsmNode>();
		nodes.add(new OsmNode());
		MapPosition[] points = findPointsInOsmNodes(ids, nodes);
		assertNotNull(points);
		assertEquals(0, points.length);
	}

	/**
	 * Test finding points in osm nodes by ids
	 */
	private void findPointsInOsmNodesTest()
	{
		ArrayList<OsmNode> nodes = new ArrayList<OsmNode>();
		OsmNode node1 = new OsmNode();
		node1.setId(10000010);
		node1.setLatitude(10.0);
		node1.setLongitude(20.0);
		nodes.add(node1);

		OsmNode node2 = new OsmNode();
		node2.setId(10000020);
		node2.setLatitude(-15.0);
		node2.setLongitude(20.0);
		nodes.add(node2);

		OsmNode node3 = new OsmNode();
		node3.setId(10000030);
		node3.setLatitude(20.0);
		node3.setLongitude(-25.0);
		nodes.add(node3);

		OsmNode node4 = new OsmNode();
		node4.setId(10000040);
		node4.setLatitude(50.0);
		node4.setLongitude(-55.0);
		nodes.add(node4);

		ArrayList<Long> ids = new ArrayList<Long>();
		ids.add(new Long(10000030));
		ids.add(new Long(10000010));
		ids.add(new Long(10000020));

		MapPosition[] foundedPoints = findPointsInOsmNodes(ids, nodes);
		assertEquals(ids.size(), foundedPoints.length);

		assertEquals(node3.getLatitude(), foundedPoints[0].getLatitude(), 0.0001);
		assertEquals(node3.getLongitude(), foundedPoints[0].getLongitude(), 0.0001);
		assertEquals(node1.getLatitude(), foundedPoints[1].getLatitude(), 0.0001);
		assertEquals(node1.getLongitude(), foundedPoints[1].getLongitude(), 0.0001);
		assertEquals(node2.getLatitude(), foundedPoints[2].getLatitude(), 0.0001);
		assertEquals(node2.getLongitude(), foundedPoints[2].getLongitude(), 0.0001);
	}

	/**
	 * Test finding points in osm nodes if ids array count more than nodes count
	 */
	private void findPointsInOsmNodesIdsMoreThenNodesTest()
	{
		ArrayList<OsmNode> nodes = new ArrayList<OsmNode>();
		OsmNode node1 = new OsmNode();
		node1.setId(10000010);
		node1.setLatitude(10.0);
		node1.setLongitude(20.0);
		nodes.add(node1);

		OsmNode node2 = new OsmNode();
		node2.setId(10000020);
		node2.setLatitude(-15.0);
		node2.setLongitude(20.0);
		nodes.add(node2);

		ArrayList<Long> ids = new ArrayList<Long>();
		ids.add(new Long(10000030));
		ids.add(new Long(10000010));
		ids.add(new Long(10000020));
		ids.add(new Long(10000040));
		ids.add(new Long(10000050));

		MapPosition[] foundedPoints = findPointsInOsmNodes(ids, nodes);
		assertEquals(0, foundedPoints.length);
	}

	/**
	 * Test finding points in osm nodes if some points can not be found
	 */
	private void findPointsInOsmNodesSomeNodesNotFounds()
	{
		ArrayList<OsmNode> nodes = new ArrayList<OsmNode>();
		OsmNode node1 = new OsmNode();
		node1.setId(10000010);
		node1.setLatitude(10.0);
		node1.setLongitude(20.0);
		nodes.add(node1);

		OsmNode node2 = new OsmNode();
		node2.setId(10000020);
		node2.setLatitude(-15.0);
		node2.setLongitude(20.0);
		nodes.add(node2);

		OsmNode node3 = new OsmNode();
		node3.setId(10000030);
		node3.setLatitude(20.0);
		node3.setLongitude(-25.0);
		nodes.add(node3);

		ArrayList<Long> ids = new ArrayList<Long>();
		ids.add(new Long(10000010));
		ids.add(new Long(10000070));
		ids.add(new Long(10000030));

		MapPosition[] foundedPoints = findPointsInOsmNodes(ids, nodes);
		assertEquals(0, foundedPoints.length);
	}

	/**
	 * Testing creating map tag by osm tag normal work
	 */
	private void createMapTagNormalWorkTest()
	{
		OsmTag osmTag = new OsmTag("k1", "v1");
		Tag mapTag = createMapTagByOsmTag(osmTag);
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
		assertEquals(osmTags.size(), defenitionTags.count());
		for (int i = 0; i < defenitionTags.count(); i++)
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
		assertEquals(1, tagsByByContainsNull.count());
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
		assertEquals(osmNode.getTags().size(), pointByNode.getDefenitionTags().count());
		for (int i = 0; i < pointByNode.getDefenitionTags().count(); i++)
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
		assertNotNull(createMapPointByOsmNode(nodeWithEmptyTags));

		OsmNode nodeContainsNullTags = new OsmNode();
		ArrayList<OsmTag> tagsContiansNull = new ArrayList<OsmTag>(4);
		tagsContiansNull.add(null);
		tagsContiansNull.add(null);
		tagsContiansNull.add(null);
		tagsContiansNull.add(null);
		nodeContainsNullTags.setTags(tagsContiansNull);
		assertNotNull(createMapPointByOsmNode(nodeContainsNullTags));
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
			DefenitionTags testStyleTags = new DefenitionTags();
			testStyleTags.add(new Tag("k1", "v1"));
			MapObjectDrawSettings testStyle = new MapObjectDrawSettings();
			testStyle.setCanBePoint();
			testStyle.setCanBeLine();
			testStyle.setCanBePolygon();
			testStyle.setDefenitionTags(testStyleTags);
			
			styleEditor.addMapObjectDrawSettings(testStyle);


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
